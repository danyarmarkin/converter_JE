package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class SessionsLibPanel extends JPanel {
    SessionsLib mSessionsLib;
    int minSessions = (int) (Math.floor((Interface.WINDOW_HEIGHT - Interface.SESSION_PANEL_HEIGHT) /
            Interface.SESSION_PANEL_HEIGHT) + 1);

    public SessionsLibPanel(SessionsLib sessionsLib) {
        mSessionsLib = sessionsLib;
        int h = Math.max(minSessions, sessionsLib.size() + 2);
        setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH, Interface.SESSION_PANEL_HEIGHT * h));
        setBackground(Color.lightGray);
        setLayout(new GridLayout(0, 1, 0, 15));
        AllSessionMonitoringBottomPanel headerPanel = new AllSessionMonitoringBottomPanel("All sessions");
        add(headerPanel);

        for (Session session : sessionsLib) {
            SessionPanel sessionPanel = new SessionPanel(session);
            System.out.println(session.mCameras.size());
            add(sessionPanel);
        }

        for (int i = 0; i < minSessions - sessionsLib.size(); i++) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH, Interface.SESSION_PANEL_HEIGHT));
            panel.setBackground(Color.lightGray);
            add(panel);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseReleased(e);
                super.mouseClicked(e);
                int section = (int) Math.floor(e.getY() / Interface.SESSION_PANEL_HEIGHT);
                System.out.println(section);
            }
        });
    }
}
