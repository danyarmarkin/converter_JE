package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;

public class AllSessionMonitoringBottomPanel extends JPanel {

    JProgressBar mProgressBar;
    JLabel mLabel;
    SessionsLib mSessionsLib;

    public AllSessionMonitoringBottomPanel(SessionsLib sessionLib, String labelText) {
        mSessionsLib = sessionLib;
        setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH, Interface.SESSION_PANEL_HEIGHT));
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());

        mLabel = new JLabel(labelText);
        add(mLabel, BorderLayout.NORTH);

        mProgressBar = new JProgressBar();
        mProgressBar.setMaximum(100);
        mProgressBar.setMinimum(0);
        mProgressBar.setForeground(Color.orange);
        mProgressBar.setStringPainted(true);
        mProgressBar.setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH - 40, 10));
        add(mProgressBar, BorderLayout.WEST);


        if (mSessionsLib == null) return;
        for (Session session : mSessionsLib) {
            for (Camera camera : session.getCameras()) {
                camera.addCameraDataListener(event -> update());
            }
        }
        update();

    }

    void update() {
        int outTotal = 0;
        int total = 0;
        int doneTotal = 0;
        int camerasAmount = 0;

        for (Session session : mSessionsLib) {
            for (Camera camera : session.getCameras()) {
                outTotal += camera.getOutputTotalFrames();
                total += camera.getTotalFrames();
                doneTotal += camera.getConvertedFrames();
                camerasAmount += 1;
            }
        }
        if (outTotal == 0) return;
        int progress = doneTotal;
        progress *= 100;
        progress /= outTotal;

        mProgressBar.setValue(progress);
        mProgressBar.setString(camerasAmount + " Cameras | " + total + "->" + outTotal + " | " + progress + "%");
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (mLabel != null) mLabel.setBackground(bg);
        if (mProgressBar != null) mProgressBar.setBackground(bg);
    }

}
