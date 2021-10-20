package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;


public class SessionPanel extends JPanel{
    private Session mSession;
    private JLabel mNameLabel;
    private JProgressBar mProgressBar;

    public SessionPanel(Session session) {
        mSession = session;

        setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH, Interface.SESSION_PANEL_HEIGHT));
        setBackground(Interface.NOT_SELECT_COLOR);
        setLayout(new BorderLayout());

        mNameLabel = new JLabel(mSession.getFullName());
        add(mNameLabel, BorderLayout.NORTH);

        mProgressBar = new JProgressBar();
        mProgressBar.setMaximum(100);
        mProgressBar.setMinimum(0);
        mProgressBar.setValue(50);
        mProgressBar.setForeground(Color.green);
        mProgressBar.setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH - 40, 10));
        add(mProgressBar, BorderLayout.WEST);

    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (mNameLabel != null) mNameLabel.setBackground(bg);
        if (mProgressBar != null) mProgressBar.setBackground(bg);
    }
}
