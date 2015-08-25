
package com.ponysdk.core;

import java.util.Arrays;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ponysdk.core.main.EntryPoint;
import com.ponysdk.core.stm.Txn;
import com.ponysdk.core.stm.TxnContext;
import com.ponysdk.ui.server.basic.PCookies;
import com.ponysdk.ui.terminal.exception.ServerException;
import com.ponysdk.ui.terminal.model.Model;

public abstract class AbstractApplicationManager {

    private static final Logger log = LoggerFactory.getLogger(AbstractApplicationManager.class);

    private final ApplicationManagerOption options;

    private final String applicationID;

    private final String applicationName;

    public AbstractApplicationManager() {
        this(new ApplicationManagerOption());
    }

    public AbstractApplicationManager(final ApplicationManagerOption options) {
        this.options = options;

        this.applicationID = System.getProperty(SystemProperty.APPLICATION_ID);
        this.applicationName = System.getProperty(SystemProperty.APPLICATION_NAME);

        log.info(options.toString());
    }

    public void process(final TxnContext context) throws Exception {
        final JsonReader reader = context.getReader();
        final JsonObject data = reader.readObject();
        if (!data.containsKey(Model.APPLICATION_VIEW_ID.getKey())) {
            startApplication(data, context);
        } else {
            fireInstructions(data, context);
        }
    }

    public void startApplication(final JsonObject data, final TxnContext context) throws Exception {
        synchronized (context) {
            Long reloadedViewID = null;
            boolean isNewHttpSession = false;
            Application application = context.getApplication();
            if (application == null) {
                log.info("Creating a new application, {}", context.toString());

                application = new Application(applicationID, applicationName, context, options);
                context.setApplication(application);
                isNewHttpSession = true;
            } else {
                if (data.containsKey(Model.APPLICATION_VIEW_ID.getKey())) {
                    final JsonNumber jsonNumber = data.getJsonNumber(Model.APPLICATION_VIEW_ID.getKey());
                    reloadedViewID = jsonNumber.longValue();
                }
                log.info("Reloading application {} {}", reloadedViewID, context);
            }

            final UIContext uiContext = new UIContext(application);
            UIContext.setCurrent(uiContext);

            if (reloadedViewID != null) {
                final UIContext previousUIContext = application.getUIContext(reloadedViewID);
                if (previousUIContext != null) previousUIContext.destroy();
            }

            try {
                final Txn txn = Txn.get();
                txn.begin(context);
                try {
                    final JsonNumber jsonNumber = data.getJsonNumber(Model.APPLICATION_SEQ_NUM.getKey());
                    final long receivedSeqNum = jsonNumber.longValue();
                    uiContext.updateIncomingSeqNum(receivedSeqNum);

                    final EntryPoint entryPoint = initializePonySession(uiContext);

                    final String historyToken = data.getString(Model.HISTORY_TOKEN.getKey());
                    if (historyToken != null && !historyToken.isEmpty()) {
                        uiContext.getHistory().newItem(historyToken, false);
                    }

                    final PCookies pCookies = uiContext.getCookies();
                    final JsonArray cookies = data.getJsonArray(Model.COOKIES.getKey());

                    for (int i = 0; i < cookies.size(); i++) {
                        final JsonObject cookie = cookies.getJsonObject(i);
                        final String name = cookie.getString(Model.KEY.getKey());
                        final String value = cookie.getString(Model.VALUE.getKey());
                        pCookies.cacheCookie(name, value);
                    }

                    if (isNewHttpSession) {
                        entryPoint.start(uiContext);
                    } else {
                        entryPoint.restart(uiContext);
                    }

                    txn.commit();
                } catch (final Throwable e) {
                    log.error("Cannot send instructions to the browser " + context, e);
                    txn.rollback();
                }
            } finally {
                UIContext.remove();
            }
        }
    }

    protected void fireInstructions(final JsonObject data, final TxnContext context) throws Exception {
        final long key = data.getJsonNumber(Model.APPLICATION_VIEW_ID.getKey()).longValue();

        final Application applicationSession = context.getApplication();

        if (applicationSession == null) { throw new ServerException(ServerException.INVALID_SESSION, "Invalid session, please reload your application (viewID #" + key + ")."); }

        final UIContext uiContext = applicationSession.getUIContext(key);

        if (uiContext == null) { throw new ServerException(ServerException.INVALID_SESSION, "Invalid session (no UIContext found), please reload your application (viewID #" + key + ")."); }

        uiContext.acquire();
        UIContext.setCurrent(uiContext);
        try {
            final Txn txn = Txn.get();
            txn.begin(context);
            try {
                final Long receivedSeqNum = checkClientMessage(data, uiContext);

                if (receivedSeqNum != null) {
                    process(uiContext, data);

                    final List<JsonObject> datas = uiContext.expungeIncomingMessageQueue(receivedSeqNum);
                    for (final JsonObject jsoObject : datas) {
                        process(uiContext, jsoObject);
                    }
                }

                txn.commit();
            } catch (final Throwable e) {
                log.error("Cannot process client instruction", e);
                txn.rollback();
            }
        } finally {
            UIContext.remove();
            uiContext.release();
        }
    }

    private Long checkClientMessage(final JsonObject jsonObject, final UIContext uiContext) {
        printClientErrorMessage(jsonObject);

        final long receivedSeqNum = jsonObject.getJsonNumber(Model.APPLICATION_SEQ_NUM.getKey()).longValue();
        if (!uiContext.updateIncomingSeqNum(receivedSeqNum)) {
            final long key = jsonObject.getJsonNumber(Model.APPLICATION_VIEW_ID.getKey()).longValue();
            uiContext.stackIncomingMessage(receivedSeqNum, jsonObject);
            if (options.maxOutOfSyncDuration > 0 && uiContext.getLastSyncErrorTimestamp() > 0) {
                if (System.currentTimeMillis() - uiContext.getLastSyncErrorTimestamp() > options.maxOutOfSyncDuration) {
                    log.info("Unable to sync message for " + (System.currentTimeMillis() - uiContext.getLastSyncErrorTimestamp()) + " ms. Dropping connection (viewID #" + key + ").");
                    uiContext.destroy();
                    return null;
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("Stacking incoming message #{}. Data #{} (viewID #{})", Arrays.asList(receivedSeqNum, jsonObject, key));
            }

            return null;
        }
        return receivedSeqNum;

    }

    private void printClientErrorMessage(final JsonObject data) {
        try {
            final JsonArray errors = data.getJsonArray(Model.APPLICATION_ERRORS.getKey());
            for (int i = 0; i < errors.size(); i++) {
                final JsonObject jsoObject = errors.getJsonObject(i);
                final String message = jsoObject.getString("message");
                final String details = jsoObject.getString("details");
                log.error("There was an unexpected error on the terminal. Message: " + message + ". Details: " + details);
            }
        } catch (final Throwable e) {
            log.error("Failed to display errors", e);
        }
    }

    private void process(final UIContext uiContext, final JsonObject jsonObject) {
        if (jsonObject.containsKey(Model.APPLICATION_INSTRUCTIONS.getKey())) {
            final JsonArray array = jsonObject.getJsonArray(Model.APPLICATION_INSTRUCTIONS.getKey());

            for (int i = 0; i < array.size(); i++) {
                final JsonObject item = array.getJsonObject(i);
                uiContext.fireClientData(item);
            }
        }
    }

    protected abstract EntryPoint initializePonySession(final UIContext ponySession) throws ServletException;

}
