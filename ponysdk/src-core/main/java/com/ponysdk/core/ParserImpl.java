
package com.ponysdk.core;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Collection;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ponysdk.ui.terminal.model.Model;

public class ParserImpl implements Parser {

    private static final Logger log = LoggerFactory.getLogger(ParserImpl.class);

    private static final byte CURVE_LEFT = '{';
    private static final byte CURVE_RIGHT = '}';
    private static final byte BRACKET_LEFT = '[';
    private static final byte BRACKET_RIGHT = ']';
    private static final byte COLON = ':';
    private static final byte COMMA = ',';
    private static final byte QUOTE = '\'';

    private final byte[] ZERO = "true".getBytes();
    private final byte[] ONE = "false".getBytes();

    private ByteBuffer buffer = null;

    public ParserImpl() {}

    public ParserImpl(final ByteBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void beginObject() {
        buffer.put(CURVE_LEFT);
    }

    @Override
    public void endObject() {
        buffer.put(CURVE_RIGHT);
    }

    @Override
    public void comma() {
        buffer.put(COMMA);
    }

    @Override
    public void quote() {
        buffer.put(QUOTE);
    }

    @Override
    public void beginArray() {
        buffer.put(BRACKET_LEFT);
    }

    @Override
    public void endArray() {
        buffer.put(BRACKET_RIGHT);
    }

    @Override
    public void parse(final JsonObject jsonObject) {
        try {
            buffer.put(jsonObject.toString().getBytes("UTF8"));
        } catch (final UnsupportedEncodingException e) {
            log.error("Cannot encode value " + jsonObject, e);
        }
    }

    @Override
    public void parse(final Model type) {
        buffer.put(Model.TYPE.getBytesKey());
        buffer.put(COLON);
        buffer.put(type.getBytesKey());
    }

    @Override
    public void parse(final Model type, final String value) {
        buffer.put(type.getBytesKey());
        buffer.put(COLON);
        try {
            buffer.put(QUOTE);
            buffer.put(value.getBytes("UTF8"));
            buffer.put(QUOTE);
        } catch (final UnsupportedEncodingException e) {
            log.error("Cannot encode value " + value, e);
        }
    }

    @Override
    public void parse(final Model type, final JsonObjectBuilder builder) {
        buffer.put(type.getBytesKey());
        buffer.put(COLON);
        try {
            buffer.put(builder.build().toString().getBytes("UTF8"));
        } catch (final UnsupportedEncodingException e) {
            log.error("Cannot encode value " + builder.toString(), e);
        }
    }

    @Override
    public void parse(final Model type, final boolean value) {
        buffer.put(type.getBytesKey());
        buffer.put(COLON);
        if (value) {
            buffer.put(ZERO);
        } else {
            buffer.put(ONE);
        }
    }

    @Override
    public void parse(final Model type, final long value) {
        buffer.put(type.getBytesKey());
        buffer.put(COLON);
        buffer.putLong(value);
    }

    @Override
    public void parse(final Model type, final int value) {
        buffer.put(type.getBytesKey());
        buffer.put(COLON);
        buffer.putInt(value);
    }

    @Override
    public void parse(final Model type, final double value) {
        buffer.put(type.getBytesKey());
        buffer.put(COLON);
        buffer.putDouble(value);
    }

    @Override
    public void parse(final Model type, final Collection<String> collection) {
        buffer.put(type.getBytesKey());
        buffer.put(COLON);
        beginArray();

        for (final String s : collection) {
            try {
                buffer.put(QUOTE);
                buffer.put(s.getBytes("UTF8"));
                buffer.put(QUOTE);
                buffer.put(COMMA);
            } catch (final UnsupportedEncodingException e) {
                log.error("Cannot encode value " + s, e);
            }
        }

        endArray();
    }

    @Override
    public void parse(final Model model, final JsonObject jsonObject) {
        buffer.put(Model.TYPE.getBytesKey());
        buffer.put(COLON);
        try {
            buffer.put(jsonObject.toString().getBytes("UTF8"));
        } catch (final UnsupportedEncodingException e) {
            log.error("Cannot encode value " + jsonObject, e);
        }
    }

}
