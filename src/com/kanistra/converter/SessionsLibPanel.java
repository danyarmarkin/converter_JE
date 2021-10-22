package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class SessionsLibPanel extends JPanel implements MouseListener {
    SessionsLib mSessionsLib;
    ArrayList<SessionPanel> mSessionPanels;
    ArrayList<SelectSessionListener> mListeners = new ArrayList<>();
    int minSessions = (int) (Math.floor((Interface.WINDOW_HEIGHT - Interface.SESSION_PANEL_HEIGHT) /
            Interface.SESSION_PANEL_HEIGHT) + 1);

    JPanel mHeaderPanel;

    public SessionsLibPanel(SessionsLib sessionsLib) {
        mSessionsLib = sessionsLib;
        int h = Math.max(minSessions, sessionsLib.size() + 2);
        setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH, Interface.SESSION_PANEL_HEIGHT * h));
        setBackground(Interface.NOT_SELECT_COLOR);
        setLayout(new GridLayout(0, 1, 0, 0));
        mHeaderPanel = new AllSessionMonitoringBottomPanel("All sessions");

        add(mHeaderPanel);

        mSessionPanels = new ArrayList<>();

        for (Session session : mSessionsLib) {
            SessionPanel sessionPanel = new SessionPanel(session);
            mSessionPanels.add(sessionPanel);
            add(sessionPanel);
        }

        for (int i = 0; i < minSessions - mSessionsLib.size(); i++) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH, Interface.SESSION_PANEL_HEIGHT));
            panel.setBackground(Interface.NOT_SELECT_COLOR);
            add(panel);
        }

        addMouseListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int section = (int) Math.floor(e.getY() / Interface.SESSION_PANEL_HEIGHT);
        if (section < 0 || section > mSessionPanels.size()) return;
        if (section == 0) {
            for (SessionPanel sessionPanel : mSessionPanels) {
                sessionPanel.setBackground(Interface.NOT_SELECT_COLOR);
            }
            mHeaderPanel.setBackground(Interface.SELECT_COLOR);
            for (SelectSessionListener listener : mListeners) {
                listener.onAllSectionsSelected();
            }
            return;
        }
        int index = 0;
        mHeaderPanel.setBackground(Interface.NOT_SELECT_COLOR);
        for (SessionPanel sessionPanel : mSessionPanels) {
            if (index == section - 1) {
                sessionPanel.setBackground(Interface.SELECT_COLOR);
                for (SelectSessionListener listener : mListeners) {
                    listener.onSessionSelected(mSessionsLib.get(index));
                }
            } else {
                sessionPanel.setBackground(Interface.NOT_SELECT_COLOR);
            }
            index++;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void addSelectSessionListener(SelectSessionListener listener) {
        mListeners.add(listener);
    }
}
