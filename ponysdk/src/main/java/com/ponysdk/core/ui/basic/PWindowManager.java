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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ponysdk.core.server.application.UIContext;

public class PWindowManager {

    private static final Logger log = LoggerFactory.getLogger(PWindowManager.class);

    private static final String ROOT = "WindowManager";

    private final Map<Integer, PWindow> preregisteredWindows = new HashMap<>();
    private final Map<Integer, PWindow> registeredWindows = new HashMap<>();
    private final List<RegisterWindowListener> listeners = new ArrayList<>();

    private PWindowManager() {
    }

    public static PWindowManager get() {
        final UIContext session = UIContext.get();
        PWindowManager windowManager = session.getAttribute(ROOT);
        if (windowManager == null) {
            windowManager = new PWindowManager();
            session.setAttribute(ROOT, windowManager);
        }
        return windowManager;
    }

    public static void preregisterWindow(final PWindow pWindow) {
        get().preregisteredWindows.put(pWindow.getID(), pWindow);
    }

    static void registerWindow(final PWindow window) {
        get().registerWindow0(window);
    }

    static void unregisterWindow(final PWindow window) {
        get().unregisterWindow0(window);
    }

    public static void addWindowListener(final RegisterWindowListener listener) {
        get().addWindowListener0(listener);
    }

    public static final Collection<Integer> getWindowIds() {
        return get().registeredWindows.keySet();
    }

    public static PWindow getWindow(final int windowID) {
        if (windowID == PWindow.EMPTY_WINDOW_ID) {
            log.debug("Window ID is not already set, so no Window is associated");
            return null;
        } else {
            final PWindow window = PWindowManager.get().registeredWindows.get(windowID);
            if (window != null) {
                log.debug("Window ID is set on window #" + windowID);
                return window;
            } else {
                log.debug("Window ID is set on window #" + windowID + ", but no Window is already associated");
                return null;
            }
        }
    }

    private void registerWindow0(final PWindow window) {
        registeredWindows.put(window.getID(), window);
        listeners.forEach(listener -> listener.registered(window.getID()));
    }

    private void unregisterWindow0(final PWindow window) {
        preregisteredWindows.remove(window.getID());
        registeredWindows.remove(window.getID());
    }

    private void addWindowListener0(final RegisterWindowListener listener) {
        this.listeners.add(listener);
    }

    public void closeAll() {
        registeredWindows.forEach((id, window) -> window.close());
    }

    interface RegisterWindowListener {

        void registered(int windowID);

        void unregistered(int windowID);
    }

}
