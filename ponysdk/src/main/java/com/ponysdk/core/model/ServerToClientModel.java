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

package com.ponysdk.core.model;

public enum ServerToClientModel {

    HEARTBEAT(ValueTypeModel.NULL),
    PING_SERVER(ValueTypeModel.LONG),
    OBJECT_ID(ValueTypeModel.INTEGER),

    TYPE_CREATE(ValueTypeModel.INTEGER),
    TYPE_UPDATE(ValueTypeModel.INTEGER),
    TYPE_GC(ValueTypeModel.INTEGER),
    TYPE_ADD(ValueTypeModel.INTEGER),
    TYPE_REMOVE(ValueTypeModel.INTEGER),
    TYPE_CLOSE(ValueTypeModel.NULL),
    TYPE_ADD_HANDLER(ValueTypeModel.BYTE),
    TYPE_HISTORY(ValueTypeModel.STRING),
    TYPE_REMOVE_HANDLER(ValueTypeModel.NULL),

    UI_CONTEXT_ID(ValueTypeModel.INTEGER),
    NATIVE(ValueTypeModel.JSON_OBJECT),
    DATE(ValueTypeModel.LONG),
    VALUE(ValueTypeModel.STRING),
    PARENT_OBJECT_ID(ValueTypeModel.INTEGER),
    PUT_STYLE_KEY(ValueTypeModel.STRING),
    STYLE_VALUE(ValueTypeModel.STRING),
    REMOVE_STYLE_KEY(ValueTypeModel.STRING),
    PUT_PROPERTY_KEY(ValueTypeModel.STRING),
    PROPERTY_VALUE(ValueTypeModel.STRING),
    PUT_ATTRIBUTE_KEY(ValueTypeModel.STRING),
    ATTRIBUTE_VALUE(ValueTypeModel.STRING),
    REMOVE_ATTRIBUTE_KEY(ValueTypeModel.STRING),
    PREVENT_EVENT(ValueTypeModel.INTEGER),
    ADD_STYLE_NAME(ValueTypeModel.STRING),
    REMOVE_STYLE_NAME(ValueTypeModel.STRING),
    HREF(ValueTypeModel.STRING),
    TEXT(ValueTypeModel.STRING),
    HTML(ValueTypeModel.STRING),
    WIDGET_ID(ValueTypeModel.INTEGER),
    WIDGET(ValueTypeModel.NULL),
    VALUE_CHECKBOX(ValueTypeModel.BYTE),
    WORD_WRAP(ValueTypeModel.BOOLEAN),
    CLEAR(ValueTypeModel.NULL),
    CLEAR_ROW(ValueTypeModel.INTEGER),
    INSERT_ROW(ValueTypeModel.INTEGER),
    CELL_PADDING(ValueTypeModel.INTEGER),
    CELL_SPACING(ValueTypeModel.INTEGER),
    HORIZONTAL_ALIGNMENT(ValueTypeModel.BYTE),
    WIDGET_HORIZONTAL_ALIGNMENT(ValueTypeModel.BYTE),
    VERTICAL(ValueTypeModel.BOOLEAN),
    VERTICAL_ALIGNMENT(ValueTypeModel.BYTE),
    WIDGET_VERTICAL_ALIGNMENT(ValueTypeModel.BYTE),
    WIDGET_HIDDEN(ValueTypeModel.BOOLEAN),
    LEFT(ValueTypeModel.DOUBLE),
    RIGHT(ValueTypeModel.DOUBLE),
    WIDTH(ValueTypeModel.DOUBLE),
    TOP(ValueTypeModel.DOUBLE),
    BOTTOM(ValueTypeModel.DOUBLE),
    HEIGHT(ValueTypeModel.DOUBLE),
    UNIT(ValueTypeModel.BYTE),
    ANIMATE(ValueTypeModel.INTEGER),
    ANIMATION_DURATION(ValueTypeModel.INTEGER),
    OPEN(ValueTypeModel.NULL),
    OPEN_CLOSE(ValueTypeModel.BOOLEAN),
    IMAGE_URL(ValueTypeModel.STRING),
    BIND(ValueTypeModel.STRING),
    CELL_HEIGHT(ValueTypeModel.STRING),
    CELL_WIDTH(ValueTypeModel.STRING),
    INDEX(ValueTypeModel.INTEGER),
    REMOVE_COOKIE(ValueTypeModel.STRING),
    COOKIE_EXPIRE(ValueTypeModel.LONG),
    ADD_COOKIE(ValueTypeModel.STRING),
    TIME(ValueTypeModel.LONG),
    ANIMATION(ValueTypeModel.BOOLEAN),
    MAX_LENGTH(ValueTypeModel.INTEGER),
    VISIBLE_LENGTH(ValueTypeModel.INTEGER),
    REGEX_FILTER(ValueTypeModel.STRING),
    MASK(ValueTypeModel.STRING),
    VISIBILITY(ValueTypeModel.BOOLEAN),
    REPLACEMENT_STRING(ValueTypeModel.STRING),
    DATE_FORMAT_PATTERN(ValueTypeModel.STRING),
    TAG(ValueTypeModel.STRING),
    INNER_HTML(ValueTypeModel.STRING),
    INNER_TEXT(ValueTypeModel.STRING),
    PICKER(ValueTypeModel.INTEGER),
    ROW(ValueTypeModel.INTEGER),
    ROW_FORMATTER_ADD_STYLE_NAME(ValueTypeModel.STRING),
    ROW_FORMATTER_REMOVE_STYLE_NAME(ValueTypeModel.STRING),
    ROW_FORMATTER_SET_STYLE_NAME(ValueTypeModel.STRING),
    COLUMN(ValueTypeModel.INTEGER),
    CELL_FORMATTER_ADD_STYLE_NAME(ValueTypeModel.STRING),
    CELL_FORMATTER_REMOVE_STYLE_NAME(ValueTypeModel.STRING),
    CELL_FORMATTER_SET_STYLE_NAME(ValueTypeModel.STRING),
    COLUMN_FORMATTER_COLUMN_WIDTH(ValueTypeModel.STRING),
    COLUMN_FORMATTER_ADD_STYLE_NAME(ValueTypeModel.STRING),
    COLUMN_FORMATTER_REMOVE_STYLE_NAME(ValueTypeModel.STRING),
    COLUMN_FORMATTER_SET_STYLE_NAME(ValueTypeModel.STRING),
    PLACEHOLDER(ValueTypeModel.STRING),
    DATE_ENABLED(ValueTypeModel.STRING),
    ENABLED(ValueTypeModel.BOOLEAN),
    ADD_DATE_STYLE(ValueTypeModel.STRING),
    STYLE_NAME(ValueTypeModel.STRING),
    REMOVE_DATE_STYLE(ValueTypeModel.STRING),
    YEAR_ARROWS_VISIBLE(ValueTypeModel.BOOLEAN),
    POPUP_CAPTION(ValueTypeModel.STRING),
    WIDGET_VISIBLE(ValueTypeModel.BOOLEAN),
    IMAGE_WIDTH(ValueTypeModel.INTEGER),
    IMAGE_HEIGHT(ValueTypeModel.INTEGER),
    WIDGET_WIDTH(ValueTypeModel.STRING),
    WIDGET_HEIGHT(ValueTypeModel.STRING),
    WIDGET_TITLE(ValueTypeModel.STRING),
    STYLE_PRIMARY_NAME(ValueTypeModel.STRING),
    ENSURE_DEBUG_ID(ValueTypeModel.STRING),
    ITEM_INSERTED(ValueTypeModel.STRING),
    ITEM_UPDATED(ValueTypeModel.STRING),
    ITEM_REMOVED(ValueTypeModel.INTEGER),
    SELECTED_INDEX(ValueTypeModel.INTEGER),
    SELECTED(ValueTypeModel.BOOLEAN),
    MULTISELECT(ValueTypeModel.BOOLEAN),
    VISIBLE_ITEM_COUNT(ValueTypeModel.INTEGER),
    ITEM_ADD(ValueTypeModel.STRING),
    ITEM_GROUP(ValueTypeModel.STRING),
    POPUP_AUTO_HIDE(ValueTypeModel.BOOLEAN),
    POPUP_MODAL(ValueTypeModel.BOOLEAN),
    POPUP_GLASS_ENABLED(ValueTypeModel.BOOLEAN),
    POPUP_DRAGGABLE(ValueTypeModel.NULL),
    POPUP_CENTER(ValueTypeModel.NULL),
    POPUP_SHOW(ValueTypeModel.NULL),
    POPUP_HIDE(ValueTypeModel.NULL),
    POPUP_POSITION_LEFT(ValueTypeModel.INTEGER),
    POPUP_POSITION_TOP(ValueTypeModel.INTEGER),
    FIXDELAY(ValueTypeModel.LONG),
    MIN_SIZE(ValueTypeModel.INTEGER),
    SNAP_CLOSED_SIZE(ValueTypeModel.INTEGER),
    TOGGLE_DISPLAY_ALLOWED(ValueTypeModel.BOOLEAN),
    CREATE_LINK(ValueTypeModel.STRING),
    INSERT_HORIZONTAL_RULE(ValueTypeModel.NULL),
    INSERT_HTML(ValueTypeModel.STRING),
    ORDERED(ValueTypeModel.NULL),
    UNORDERED(ValueTypeModel.NULL),
    BACK_COLOR(ValueTypeModel.STRING),
    FONT_NAME(ValueTypeModel.STRING),
    FONT_SIZE(ValueTypeModel.STRING),
    FONT_COLOR(ValueTypeModel.STRING),
    JUSTIFICATION(ValueTypeModel.STRING),
    TOGGLE_BOLD(ValueTypeModel.NULL),
    TOGGLE_ITALIC(ValueTypeModel.NULL),
    TOGGLE_SUBSCRIPT(ValueTypeModel.NULL),
    TOGGLE_UNDERLINE(ValueTypeModel.NULL),
    LEFT_INDENT(ValueTypeModel.NULL),
    REDO(ValueTypeModel.NULL),
    REMOVE_FORMAT(ValueTypeModel.NULL),
    REMOVE_LINK(ValueTypeModel.NULL),
    TOGGLE_RIGHT_INDENT(ValueTypeModel.NULL),
    SELECT_ALL(ValueTypeModel.NULL),
    COMMAND_ID(ValueTypeModel.LONG),
    ORACLE(ValueTypeModel.INTEGER),
    STREAM_REQUEST_ID(ValueTypeModel.INTEGER),
    ROOT(ValueTypeModel.BOOLEAN),
    STATE(ValueTypeModel.BOOLEAN),
    FACTORY(ValueTypeModel.STRING),
    DIRECTION(ValueTypeModel.BYTE),
    SIZE(ValueTypeModel.DOUBLE),
    WIDGET_SIZE(ValueTypeModel.DOUBLE),
    RESIZE(ValueTypeModel.NULL),
    BEFORE_INDEX(ValueTypeModel.INTEGER),
    MENU_BAR_IS_VERTICAL(ValueTypeModel.BOOLEAN),
    TAB_WIDGET(ValueTypeModel.INTEGER),
    TAB_TEXT(ValueTypeModel.STRING),
    ENABLED_ON_REQUEST(ValueTypeModel.BOOLEAN),
    TABINDEX(ValueTypeModel.INTEGER),
    END_OF_PROCESSING(ValueTypeModel.NULL),
    FOCUSED(ValueTypeModel.BOOLEAN),
    DOM_HANDLER_CODE(ValueTypeModel.BYTE),
    IMAGE_TOP(ValueTypeModel.INTEGER),
    IMAGE_LEFT(ValueTypeModel.INTEGER),
    HISTORY_FIRE_EVENTS(ValueTypeModel.BOOLEAN),
    LOADING_ON_REQUEST(ValueTypeModel.BOOLEAN),
    SET_COL_SPAN(ValueTypeModel.INTEGER),
    SET_ROW_SPAN(ValueTypeModel.INTEGER),
    KEY_FILTER(ValueTypeModel.JSON_OBJECT),
    POPUP_POSITION_AND_SHOW(ValueTypeModel.NULL),
    ROOT_ID(ValueTypeModel.STRING),
    WINDOW_ID(ValueTypeModel.INTEGER),
    EVAL(ValueTypeModel.STRING),
    VISIBLE_LINES(ValueTypeModel.INTEGER),
    CHARACTER_WIDTH(ValueTypeModel.INTEGER),
    HORIZONTAL_SCROLL_POSITION(ValueTypeModel.INTEGER),
    VERTICAL_SCROLL_POSITION(ValueTypeModel.INTEGER),
    SCROLL_TO(ValueTypeModel.INTEGER),
    LIMIT(ValueTypeModel.INTEGER),
    SUGGESTION(ValueTypeModel.STRING),
    FEATURES(ValueTypeModel.STRING),
    URL(ValueTypeModel.STRING),
    CLOSE(ValueTypeModel.NULL),
    WIDGET_TYPE(ValueTypeModel.BYTE),
    CLEAR_DOM(ValueTypeModel.BOOLEAN),
    STOP_EVENT(ValueTypeModel.INTEGER),
    BORDER_WIDTH(ValueTypeModel.INTEGER),
    SPACING(ValueTypeModel.INTEGER),
    NAME(ValueTypeModel.STRING),
    WIDGET_FULL_SIZE(ValueTypeModel.NULL),
    POPUP_GLASS_STYLE_NAME(ValueTypeModel.INTEGER), // Not used ?
    DISCLOSURE_PANEL_OPEN_IMG(ValueTypeModel.INTEGER),
    DISCLOSURE_PANEL_CLOSE_IMG(ValueTypeModel.INTEGER),
    SUGGESTIONS(ValueTypeModel.STRING),
    DEFAULT_SUGGESTIONS(ValueTypeModel.STRING),
    TEXTBOX_ID(ValueTypeModel.INTEGER),
    WINDOW_TITLE(ValueTypeModel.STRING),
    PRINT(ValueTypeModel.NULL),
    DESTROY(ValueTypeModel.NULL);

    private final ValueTypeModel type;

    ServerToClientModel(final ValueTypeModel size) {
        this.type = size;
    }

    public short getValue() {
        return (short) ordinal();
    }

    public ValueTypeModel getTypeModel() {
        return type;
    }

    public boolean equals(final ServerToClientModel other) {
        return other != null && this.ordinal() == other.ordinal();
    }

}