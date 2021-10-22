package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;


public class SessionPanel extends JPanel{
    private Session mSession;
    private JLabel mNameLabel;
    private JLabel mProgressLabel;
    private JProgressBar mProgressBar;

    public SessionPanel(Session session) {
        mSession = session;

        setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH, Interface.SESSION_PANEL_HEIGHT));
        setBackground(Interface.NOT_SELECT_COLOR);
        setLayout(new BorderLayout());

        mNameLabel = new JLabel(mSession.getFullName());
        add(mNameLabel, BorderLayout.NORTH);

        mProgressBar = new JProgressBar();
        mProgressBar.setForeground(Color.orange);
        mProgressBar.setPreferredSize(new Dimension(Interface.SESSIONS_PANEL_WIDTH - 40, 10));
        mProgressBar.setStringPainted(true);
        add(mProgressBar, BorderLayout.WEST);

        updateProgress();

        for (Camera camera : mSession.getCameras()) {
            camera.addUpdateProgressListener(cam -> updateProgress());
        }
    }

    public void updateProgress() {
        int progress;
        int outTotalFrames = 0;
        int totalFrames = 0;
        int totalDone = 0;
        for (Camera camera : mSession.getCameras()) {
            outTotalFrames += camera.getOutputTotalFrames();
            totalDone += camera.getConvertedFrames();
            totalFrames += camera.getTotalFrames();
        }

        progress = 100 * totalDone;
        progress /= outTotalFrames;

        mProgressBar.setMaximum(100);
        mProgressBar.setMinimum(0);
        mProgressBar.setValue(progress);

        mProgressBar.setString(mSession.getCameras().size() + " Cameras | " + totalFrames + "->" + outTotalFrames + " | "
                + progress + "%");
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (mNameLabel != null) mNameLabel.setBackground(bg);
        if (mProgressBar != null) mProgressBar.setBackground(bg);
    }
}
