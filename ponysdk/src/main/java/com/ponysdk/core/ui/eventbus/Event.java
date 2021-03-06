/*
 * Copyright (c) 2011 PonySDK
 *  Owners:
 *  Luciano Broussal  <luciano.broussal AT gmail.com>
 *	Mathieu Barbier   <mathieu.barbier AT gmail.com>
 *	Nicolas Ciaravola <nicolas.ciaravola.pro AT gmail.com>
 *
 *  WebSite:
 *  http://code.google.com/p/pony-sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ponysdk.core.ui.eventbus;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Event<H extends EventHandler> {

    private static AtomicLong count = new AtomicLong();
    private final long eventID;
    private Object source;
    private Object data;

    protected Event(final Object source) {
        this.source = source;
        eventID = count.getAndIncrement();
    }

    public Object getData() {
        return data;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    public Object getSource() {
        return source;
    }

    void setSource(final Object source) {
        this.source = source;
    }

    public String toDebugString() {
        String name = this.getClass().getName();
        name = name.substring(name.lastIndexOf(".") + 1);
        return "eventbus: " + name + ":";
    }

    @Override
    public String toString() {
        return "An eventbus type";
    }

    public long getEventID() {
        return eventID;
    }

    public abstract Type getAssociatedType();

    protected abstract void dispatch(H handler);

    public static class Type {

        private static final AtomicInteger nextHashCode = new AtomicInteger(0);

        private final int index;

        public Type() {
            index = nextHashCode.incrementAndGet();
        }

        @Override
        public final int hashCode() {
            return index;
        }

        @Override
        public String toString() {
            return "Event type";
        }
    }
}
