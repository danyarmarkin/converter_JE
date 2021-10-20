package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;

public class SessionDetailsPanel extends JPanel {

    Session mSession;

    public SessionDetailsPanel(Session session) {
        mSession = session;
        if (session == null) return;
        setPreferredSize(new Dimension(Interface.WINDOW_WIDTH - Interface.SESSIONS_PANEL_WIDTH - 10,
                Math.max(Interface.SESSION_DETAILS_PANEL_HEIGHT * session.getCameras().size(), Interface.WINDOW_HEIGHT)));
        setLayout(new GridLayout(0, 1));

        for (Camera camera : session.getCameras()) {
            add(new CameraPanel(camera));
        }

        int totalHeight = Interface.SESSION_DETAILS_PANEL_HEIGHT * session.getCameras().size();
        while (totalHeight < Interface.WINDOW_HEIGHT) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(Interface.WINDOW_WIDTH - Interface.SESSIONS_PANEL_WIDTH - 10,
                    Interface.SESSION_DETAILS_PANEL_HEIGHT));
            add(panel);
            totalHeight += Interface.SESSION_DETAILS_PANEL_HEIGHT;
        }
    }
}
