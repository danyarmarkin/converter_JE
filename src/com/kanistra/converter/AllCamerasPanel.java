package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;

public class AllCamerasPanel extends JPanel {
    JLabel mTotalFramesLabel;
    JLabel mTotalFrames;
    JLabel mOutputTotalFramesLabel;
    JLabel mOutputsTotalFrames;
    JButton mStartAllButton;
    JButton mStopAllButton;

    Session mSession;

    public AllCamerasPanel(Session session) {
        mSession = session;
        int width = Interface.WINDOW_WIDTH - Interface.SESSIONS_PANEL_WIDTH - 20;
        int height = Interface.ALL_CAMERAS_PANEL_HEIGHT;
        setPreferredSize(new Dimension(width, height));
//        setMaximumSize(new Dimension(width, height));
        setBackground(Color.white);
        setLayout(new GridLayout(3, 2));

        mTotalFramesLabel = new JLabel(Interface.TOTAL_FRAMES_TITLE);
        add(mTotalFramesLabel);

        mTotalFrames = new JLabel();
        add(mTotalFrames);

        mOutputTotalFramesLabel = new JLabel(Interface.OUTPUT_AMOUNT_FRAMES_TITLE);
        add(mOutputTotalFramesLabel);

        mOutputsTotalFrames = new JLabel();
        add(mOutputsTotalFrames);

        mStartAllButton = new JButton(Interface.START_ALL_CAMERAS_TITLE);
        mStartAllButton.addActionListener(e -> {
            for (Camera camera : mSession.getCameras()) {
                camera.setStartedConvert(true);
            }
        });
        add(mStartAllButton);

        mStopAllButton = new JButton(Interface.STOP_ALL_CAMERAS_TITLE);
        mStopAllButton.addActionListener(e -> {
            for (Camera camera : mSession.getCameras()) {
                camera.setStartedConvert(false);
            }
        });
        add(mStopAllButton);

        for (Camera camera : mSession.getCameras())
            camera.addUpdateProgressListener(camera1 -> updateValues());


        updateValues();
    }

    void updateValues() {
        int total = 0;
        int outputTotal = 0;
        for (Camera camera : mSession.getCameras()) {
            total += camera.getTotalFrames();
            outputTotal += camera.getOutputTotalFrames();
            mTotalFrames.setText(String.valueOf(total));
            mOutputsTotalFrames.setText(String.valueOf(outputTotal));
        }
    }
}
