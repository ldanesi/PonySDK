
package com.ponysdk.core.stm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ponysdk.core.Application;
import com.ponysdk.core.Parser;
import com.ponysdk.core.Parser2;
import com.ponysdk.core.servlet.Request;
import com.ponysdk.core.servlet.Response;
import com.ponysdk.core.useragent.UserAgent;

public class TxnContextHttp implements TxnContext {

    private static final Logger log = LoggerFactory.getLogger(TxnContextHttp.class);

    private Request request;
    private Response response;

    private Parser2 parser;

    private UserAgent userAgent;

    private Application application;

    private final Map<String, Object> parameters = new HashMap<>();

    @Override
    public void flush() {
        response.flush();
        request = null;
        response = null;
    }

    @Override
    public Parser getParser() {
        return parser;
    }

    @Override
    public JsonReader getReader() {
        try {
            return Json.createReader(request.getReader());
        } catch (final IOException e) {
            log.error("Cannot build reader from HTTP request", e);
        }

        return null;
    }

    public void setRequest(final Request request) {
        this.request = request;
        this.userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }

    public void setResponse(final Response response) throws IOException {
        this.response = response;
        this.parser = new Parser2(response.getWriter());
    }

    @Override
    public UserAgent getUserAgent() {
        return userAgent;
    }

    @Override
    public String getRemoteAddr() {
        return request.getRemoteAddr();
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
