package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;


public class SessionPanel extends JPanel{
    private Session mSession;
    private Label mNameLabel;
    private JProgressBar mProgressBar;

    public SessionPanel(Session session) {
        mSession = session;

        setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH, Interface.SESSION_PANEL_HEIGHT));
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());

        mNameLabel = new Label(mSession.getFullName());
        add(mNameLabel, BorderLayout.NORTH);

        mProgressBar = new JProgressBar();
        mProgressBar.setMaximum(100);
        mProgressBar.setMinimum(0);
        mProgressBar.setValue(50);
        mProgressBar.setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH - 40, 10));
        add(mProgressBar, BorderLayout.WEST);

    }
}
