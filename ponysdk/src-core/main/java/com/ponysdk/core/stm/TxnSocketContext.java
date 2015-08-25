
package com.ponysdk.core.stm;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonReader;

import com.ponysdk.core.Application;
import com.ponysdk.core.ParserImpl;
import com.ponysdk.core.socket.WebSocket;
import com.ponysdk.core.useragent.UserAgent;

public class TxnSocketContext implements TxnContext, TxnListener {

    private WebSocket socket;

    private boolean polling = false;

    private boolean flushNow = false;

    private ParserImpl parser;

    private Application application;

    private final Map<String, Object> parameters = new HashMap<>();

    public TxnSocketContext() {}

    public void setSocket(final WebSocket socket) {
        this.socket = socket;
        this.parser = new ParserImpl(socket.getByteBuffer());
    }

    @Override
    public void flush() {
        if (polling) return;
        socket.flush();
    }

    public void switchToPollingMode() {
        polling = true;
    }

    public void flushNow() {
        flushNow = true;
        Txn.get().addTnxListener(this);
    }

    @Override
    public void beforeFlush(final TxnContext txnContext) {
        if (!flushNow) return;

        flushNow = false;

        Txn.get().getTxnContext().flush();
    }

    @Override
    public void beforeRollback() {}

    @Override
    public void afterFlush(final TxnContext txnContext) {}

    @Override
    public ParserImpl getParser() {
        return parser;
    }

    @Override
    public JsonReader getReader() {
        return null;
    }

    @Override
    public UserAgent getUserAgent() {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public Application getApplication() {
        return application;
    }

    @Override
    public void setApplication(final Application application) {
        this.application = application;
    }

    @Override
    public void setAttribute(final String name, final Object value) {
        parameters.put(name, value);
    }

    @Override
    public Object getAttribute(final String name) {
        return parameters.get(name);
    }

}
