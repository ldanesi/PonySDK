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

package com.ponysdk.core.ui.basic;

import java.time.Duration;

import com.ponysdk.core.model.PUnit;
import com.ponysdk.core.model.ServerToClientModel;
import com.ponysdk.core.model.WidgetType;
import com.ponysdk.core.server.servlet.WebsocketEncoder;
import com.ponysdk.core.ui.model.ServerBinaryModel;

/**
 * A panel that lays its child widgets out "docked" at its outer edges, and
 * allows its last widget to take up the remaining space in its center.
 * <p>
 * This widget will <em>only</em> work in standards mode, which requires that
 * the HTML page in which it is run have an explicit &lt;!DOCTYPE&gt;
 * declaration.
 * </p>
 * DockLayoutPanel contains children tagged with the cardinal directions, and
 * center:
 * <p>
 * <dl>
 * <dt>center</dt>
 * <dt>north</dt>
 * <dt>south</dt>
 * <dt>west</dt>
 * <dt>east</dt>
 * </dl>
 * <p>
 * Each child can hold only widget, and there can be only one &lt;g:center>.
 * However, there can be any number of the directional children.
 * </p>
 */
public class PDockLayoutPanel extends PComplexPanel implements PAnimatedLayout {

    private final PUnit unit;

    protected PDockLayoutPanel(final PUnit unit) {
        super();
        this.unit = unit;
    }

    @Override
    protected void enrichOnInit(final WebsocketEncoder parser) {
        super.enrichOnInit(parser);
        parser.encode(ServerToClientModel.UNIT, unit.getByteValue());
    }

    @Override
    protected WidgetType getWidgetType() {
        return WidgetType.DOCK_LAYOUT_PANEL;
    }

    @Override
    public void add(final PWidget child) {
        add(child, Direction.CENTER, 0);
    }

    public void addNorth(final PWidget widget, final double size) {
        add(widget, Direction.NORTH, size);
    }

    public void addSouth(final PWidget widget, final double size) {
        add(widget, Direction.SOUTH, size);
    }

    public void addEast(final PWidget widget, final double size) {
        add(widget, Direction.EAST, size);
    }

    public void addWest(final PWidget widget, final double size) {
        add(widget, Direction.WEST, size);
    }

    public void addLineEnd(final PWidget widget, final double size) {
        add(widget, Direction.LINE_END, size);
    }

    public void addLineStart(final PWidget widget, final double size) {
        add(widget, Direction.LINE_START, size);
    }

    public void setWidgetSize(final PWidget widget, final double size) {
        saveUpdate((writer) -> {
            writer.writeModel(ServerToClientModel.WIDGET_SIZE, size);
            writer.writeModel(ServerToClientModel.WIDGET_ID, widget.getID());
        });
    }

    public void setWidgetHidden(final PWidget widget, final boolean hidden) {
        saveUpdate((writer) -> {
            writer.writeModel(ServerToClientModel.WIDGET_HIDDEN, hidden);
            writer.writeModel(ServerToClientModel.WIDGET_ID, widget.getID());
        });
    }

    public void add(final PWidget child, final Direction direction, final double size) {
        // Detach new child.
        child.removeFromParent();
        // Logical attach.
        children.add(child);
        // Adopt.
        adopt(child);

        child.saveAdd(child.getID(), ID, new ServerBinaryModel(ServerToClientModel.DIRECTION, direction.getValue()),
            new ServerBinaryModel(ServerToClientModel.SIZE, size));
        child.attach(windowID);
    }

    @Override
    public void animate(final Duration duration) {
        saveUpdate(writer -> writer.writeModel(ServerToClientModel.ANIMATE, duration.toMillis()));
    }

    public PUnit getUnit() {
        return unit;
    }

    public enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
        CENTER,
        LINE_START,
        LINE_END;

        public byte getValue() {
            return (byte) ordinal();
        }
    }
}
