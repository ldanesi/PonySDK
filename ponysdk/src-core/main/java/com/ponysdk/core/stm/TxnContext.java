
package com.ponysdk.core.stm;

import javax.json.JsonReader;

import com.ponysdk.core.Application;
import com.ponysdk.core.Parser;
import com.ponysdk.core.useragent.UserAgent;

public interface TxnContext {

    void flush();

    Parser getParser();

    JsonReader getReader();

    UserAgent getUserAgent();

    String getRemoteAddr();

    Application getApplication();

    void setApplication(Application application);

    void setAttribute(String name, Object value);

    Object getAttribute(String name);
}
