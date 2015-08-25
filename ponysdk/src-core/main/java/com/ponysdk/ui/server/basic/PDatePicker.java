/*
 * Copyright (c) 2011 PonySDK
 *  Owners:
 *  Luciano Broussal  <luciano.broussal AT gmail.com>
 *  Mathieu Barbier   <mathieu.barbier AT gmail.com>
 *  Nicolas Ciaravola <nicolas.ciaravola.pro AT gmail.com>
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

package com.ponysdk.ui.server.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.json.JsonObject;

import com.ponysdk.core.Parser;
import com.ponysdk.core.Parser;
import com.ponysdk.core.stm.Txn;
import com.ponysdk.core.tools.ListenerCollection;
import com.ponysdk.ui.server.basic.event.PShowRangeEvent;
import com.ponysdk.ui.server.basic.event.PShowRangeHandler;
import com.ponysdk.ui.server.basic.event.PValueChangeEvent;
import com.ponysdk.ui.server.basic.event.PValueChangeHandler;
import com.ponysdk.ui.terminal.WidgetType;
import com.ponysdk.ui.terminal.model.Model;

public class PDatePicker extends PWidget implements HasPValue<Date>, PValueChangeHandler<Date> {

    private final ListenerCollection<PValueChangeHandler<Date>> handlers = new ListenerCollection<>();
    private final ListenerCollection<PShowRangeHandler<Date>> showRangeHandlers = new ListenerCollection<>();

    private Date date;

    private int year = -1;
    private int month = -1;
    private int day = -1;

    public PDatePicker() {
        this(null);
    }

    public PDatePicker(final TimeZone timeZone) {
        saveAddHandler(Model.HANDLER_DATE_VALUE_CHANGE_HANDLER);
    }

    @Override
    protected WidgetType getWidgetType() {
        return WidgetType.DATEPICKER;
    }

    @Override
    public void onClientData(final JsonObject jsonObject) {
        if (jsonObject.containsKey(Model.HANDLER_DATE_VALUE_CHANGE_HANDLER.getKey())) {
            final String data = jsonObject.getString(Model.VALUE.getKey());
            Date date = null;
            if (data != null && !data.isEmpty()) date = new Date(Long.parseLong(data));

            year = jsonObject.getInt(Model.YEAR.getKey());
            month = jsonObject.getInt(Model.MONTH.getKey());
            day = jsonObject.getInt(Model.DAY.getKey());
            onValueChange(new PValueChangeEvent<>(this, date));
        } else if (jsonObject.containsKey(Model.HANDLER_SHOW_RANGE)) {
            final String start = jsonObject.getString(Model.START.getKey());
            final String end = jsonObject.getString(Model.END.getKey());
            final Date sd = new Date(Long.parseLong(start));
            final Date ed = new Date(Long.parseLong(end));

            // TODO nicolas Use date ???

            final PShowRangeEvent<Date> showRangeEvent = new PShowRangeEvent<>(this, sd, ed);
            for (final PShowRangeHandler<Date> handler : showRangeHandlers) {
                handler.onShowRange(showRangeEvent);
            }
        } else {
            super.onClientData(jsonObject);
        }

    }

    @Override
    public void addValueChangeHandler(final PValueChangeHandler<Date> handler) {
        handlers.add(handler);
    }

    @Override
    public void removeValueChangeHandler(final PValueChangeHandler<Date> handler) {
        handlers.remove(handler);
    }

    @Override
    public Collection<PValueChangeHandler<Date>> getValueChangeHandlers() {
        return Collections.unmodifiableCollection(handlers);
    }

    public Collection<PShowRangeHandler<Date>> getShowRangeHandlers() {
        return Collections.unmodifiableCollection(showRangeHandlers);
    }

    public void addShowRangeHandler(final PShowRangeHandler<Date> handler) {
        if (showRangeHandlers.isEmpty()) {
            saveAddHandler(Model.HANDLER_KEY_SHOW_RANGE);
        }

        showRangeHandlers.add(handler);
    }

    public void removeShowRangeHandler(final PShowRangeHandler<Date> handler) {
        showRangeHandlers.remove(handler);
    }

    @Override
    public void onValueChange(final PValueChangeEvent<Date> event) {
        this.date = event.getValue();
        for (final PValueChangeHandler<Date> handler : handlers) {
            handler.onValueChange(event);
        }
    }

    @Override
    public Date getValue() {
        return date;
    }

    @Override
    public void setValue(final Date date) {
        this.date = date;
        saveUpdate(Model.VALUE, date != null ? Long.toString(date.getTime()) : "");
    }

    public void setCurrentMonth(final Date date) {
        saveUpdate(Model.MONTH, date != null ? Long.toString(date.getTime()) : "");
    }

    /**
     * Sets a visible date to be enabled or disabled. This is only set until the next time the DatePicker is
     * refreshed.
     */
    public final void setTransientEnabledOnDates(final boolean enabled, final Collection<Date> dates) {
        final List<String> asString = dateToString(dates);

        final Parser parser = Txn.get().getTxnContext().getParser();
        parser.beginObject();
        parser.parse(Model.TYPE_UPDATE);
        parser.parse(Model.OBJECT_ID, ID);
        parser.parse(Model.DATE_ENABLED, asString);
        parser.parse(Model.ENABLED, enabled);
        parser.endObject();
    }

    /**
     * Add a style name to the given dates.
     */
    public void addStyleToDates(final String styleName, final Collection<Date> dates) {
        final List<String> asString = dateToString(dates);

        final Parser parser = Txn.get().getTxnContext().getParser();
        parser.beginObject();
        parser.parse(Model.TYPE_UPDATE);
        parser.parse(Model.OBJECT_ID, ID);
        parser.parse(Model.ADD_DATE_STYLE, asString);
        parser.parse(Model.STYLE_NAME, styleName);
        parser.endObject();
    }

    /**
     * Removes the styleName from the given dates (even if it is transient).
     */
    public void removeStyleFromDates(final String styleName, final Collection<Date> dates) {
        final List<String> asString = dateToString(dates);

        final Parser parser = Txn.get().getTxnContext().getParser();
        parser.beginObject();
        parser.parse(Model.TYPE_UPDATE);
        parser.parse(Model.OBJECT_ID, ID);
        parser.parse(Model.REMOVE_DATE_STYLE, asString);
        parser.parse(Model.STYLE_NAME, styleName);
        parser.endObject();
    }

    private List<String> dateToString(final Collection<Date> dates) {
        final List<String> asString = new ArrayList<>();
        for (final Date d : dates) {
            asString.add(Long.toString(d.getTime()));
        }
        return asString;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(final int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

}
