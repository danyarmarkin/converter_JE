package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;

public class AllSessionDetailsPanel extends JPanel {

    SessionsLib mSessionsLib;

    JLabel mTotalSessionsLabel;
    JLabel mTotalSessions;
    JLabel mTotalCamerasLabel;
    JLabel mTotalCameras;
    JLabel mTotalFramesLabel;
    JLabel mTotalFrames;
    JLabel mStepLabel;
    JTextField mStep;
    JLabel mToleranceLabel;
    JTextField mTolerance;
    JLabel mOutputAmountFramesLabel;
    JLabel mOutputAmountFrames;
    JButton mStartButton;
    JButton mStopButton;
    JLabel mSpeedLabel;
    JLabel mSpeed;
    JLabel mTimeLabel;
    JLabel mTime;


    public AllSessionDetailsPanel(SessionsLib sessionsLib) {
        mSessionsLib = sessionsLib;
        setLayout(new GridLayout(15, 2));
        int width = Interface.WINDOW_WIDTH - Interface.SESSIONS_PANEL_WIDTH - 20;
        int height = Interface.WINDOW_HEIGHT;
        setPreferredSize(new Dimension(width, height));

        mTotalSessionsLabel = new JLabel(Interface.TOTAL_SESSIONS_TITLE);
        add(mTotalSessionsLabel);

        mTotalSessions = new JLabel(String.valueOf(mSessionsLib.size()));
        add(mTotalSessions);

        mTotalCamerasLabel = new JLabel(Interface.TOTAL_CAMERAS_TITLE);
        add(mTotalCamerasLabel);

        mTotalCameras = new JLabel(String.valueOf(mSessionsLib.camerasSize()));
        add(mTotalCameras);

        mTotalFramesLabel = new JLabel(Interface.TOTAL_FRAMES_TITLE);
        add(mTotalFramesLabel);

        mTotalFrames = new JLabel("1000");
        add(mTotalFrames);

        mStepLabel = new JLabel(Interface.STEP_TITLE);
        add(mStepLabel);

        mStep = new JTextField(6);
        add(mStep);

        mToleranceLabel = new JLabel(Interface.TOLERANCE_TITLE);
        add(mToleranceLabel);

        mTolerance = new JTextField(6);
        add(mTolerance);

        mOutputAmountFramesLabel = new JLabel(Interface.OUTPUT_AMOUNT_FRAMES_TITLE);
        add(mOutputAmountFramesLabel);

        mOutputAmountFrames = new JLabel("1000");
        add(mOutputAmountFrames);

        mStartButton = new JButton(Interface.START_BUTTON_TITLE);
        add(mStartButton);

        mStopButton = new JButton(Interface.STOP_BUTTON_TITLE);
        add(mStopButton);

        mSpeedLabel = new JLabel(Interface.SPEED_TITLE);
        add(mSpeedLabel);

        mSpeed = new JLabel("0 fps");
        add(mSpeed);

        mTimeLabel = new JLabel(Interface.TIME_TITLE);
        add(mTimeLabel);

        mTime = new JLabel("0 sec");
        add(mTime);

//        JPanel panel = new JPanel();
//        panel.setPreferredSize(new Dimension(width, 500));
//        add(panel);
    }
}
