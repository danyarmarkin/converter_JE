package com.kanistra.converter;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.*;
import java.awt.*;

public class AllSessionMonitoringBottomPanel extends JPanel {

    JProgressBar mProgressBar;
    JLabel mLabel;

    public AllSessionMonitoringBottomPanel(String labelText) {
        setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH, Interface.SESSION_PANEL_HEIGHT));
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());

        mLabel = new JLabel(labelText);
        add(mLabel, BorderLayout.NORTH);

        mProgressBar = new JProgressBar();
        mProgressBar.setMaximum(100);
        mProgressBar.setMinimum(0);
        mProgressBar.setValue(50);
        mProgressBar.setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH - 40, 10));
        add(mProgressBar, BorderLayout.WEST);
    }
}
