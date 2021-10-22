package com.kanistra.converter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

public class CameraPanel extends JPanel {
    Camera mCamera;

    JLabel mInputPathLabel;
    JTextField mInputPath;
    JLabel mOutputPathLabel;
    JTextField mOutputPath;
    JButton mStartButton;
    JButton mStopButton;
    JLabel mStepLabel;
    JTextField mStep;
    JLabel mToleranceLabel;
    JTextField mTolerance;
    JLabel mJpgLabel;
    JCheckBox mJpg;
    JButton mSetAll;
    JLabel mSpeedLabel;
    JLabel mSpeed;
    JLabel mTimeLabel;
    JLabel mTime;
    JLabel mFramesLabel;
    JLabel mFrames;
    JLabel mProgressLabel;
    JLabel mProgress;

    ArrayList<SetParamsListener> mListeners = new ArrayList<>();

    public CameraPanel(Camera camera) {
        mCamera = camera;
        setBackground(Color.white);
        setPreferredSize(new Dimension(
                Interface.WINDOW_WIDTH - Interface.SESSIONS_PANEL_WIDTH - 20,
                Interface.SESSION_DETAILS_PANEL_HEIGHT));
        setLayout(new GridLayout(11, 2));

        mInputPathLabel = new JLabel(Interface.INPUT_PATH_TITLE);
        add(mInputPathLabel);

        mInputPath = new JTextField(camera.getPath());
        mInputPath.setEnabled(false);
        add(mInputPath);

        mOutputPathLabel = new JLabel(Interface.OUTPUT_PATH_TITLE);
        add(mOutputPathLabel);

        mOutputPath = new JTextField(camera.getOutputPath());
        add(mOutputPath);

        mStepLabel = new JLabel(Interface.STEP_TITLE);
        add(mStepLabel);

        mStep = new JTextField(6);
        mStep.setText(String.valueOf(mCamera.getStep()));
        mStep.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            void update() {
                try {
                    int value = Integer.parseInt(mStep.getText());
                    mCamera.setStep(value);
                    mStep.setBorder(mOutputPath.getBorder());
                    mFrames.setText(mCamera.getTotalFrames() + " -> " + mCamera.getOutputTotalFrames());
                    mProgress.setText(mCamera.getConvertedFrames() + " / " + mCamera.getOutputTotalFrames());
                } catch (NumberFormatException e) {
                    mStep.setBorder(new BevelBorder(0, Color.red, Color.red));
                }

            }
        });
        add(mStep);

        mToleranceLabel = new JLabel(Interface.TOLERANCE_TITLE);
        add(mToleranceLabel);

        mTolerance = new JTextField(6);
        mTolerance.setText(String.valueOf(mCamera.getTolerance()));
        mTolerance.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            void update() {
                try {
                    int value = Integer.parseInt(mTolerance.getText());
                    mCamera.setTolerance(value);
                    mTolerance.setBorder(mOutputPath.getBorder());
                } catch (NumberFormatException e) {
                    mTolerance.setBorder(new BevelBorder(0, Color.red, Color.red));
                }

            }
        });
        add(mTolerance);

        mJpgLabel = new JLabel(Interface.JPG_TITLE);
        add(mJpgLabel);

        mJpg = new JCheckBox();
        mJpg.setSelected(mCamera.isJpg());
        mJpg.addItemListener(e -> {
            boolean value = mJpg.isSelected();
            mCamera.setJpg(value);
        });
        add(mJpg);

        mSetAll = new JButton(Interface.SET_ALL_TITLE);
        mSetAll.addActionListener(e -> {
            for (SetParamsListener listener : mListeners) {
                listener.onSetParamsToAllCameras(mCamera);
            }
        });
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        add(panel);
        add(mSetAll);

        mFramesLabel = new JLabel(Interface.FRAMES_TITLE);
        add(mFramesLabel);

        mFrames = new JLabel(mCamera.getTotalFrames() + " -> "  + mCamera.getOutputTotalFrames());
        add(mFrames);

        mProgressLabel = new JLabel(Interface.FRAME_PROGRESS_TITLE);
        add(mProgressLabel);

        mProgress = new JLabel("0 / " + mCamera.getOutputTotalFrames());
        add(mProgress);

        mSpeedLabel = new JLabel(Interface.SPEED_TITLE);
        add(mSpeedLabel);

        mSpeed = new JLabel("0");
        add(mSpeed);

        mTimeLabel = new JLabel(Interface.TIME_TITLE);
        add(mTimeLabel);

        mTime = new JLabel("infinity");
        add(mTime);

        mStartButton = new JButton(Interface.START_BUTTON_TITLE);
        if (mCamera.isStartedConvert()) mStartButton.setEnabled(false);
        add(mStartButton);
        mStopButton = new JButton(Interface.STOP_BUTTON_TITLE);
        if (!mCamera.isStartedConvert()) mStopButton.setEnabled(false);
        add(mStopButton);

        mStartButton.addActionListener(e -> {
            mStartButton.setEnabled(false);
            mStopButton.setEnabled(true);
            mCamera.setStartedConvert(true);
        });

        mStopButton.addActionListener(e -> {
            mStartButton.setEnabled(true);
            mStopButton.setEnabled(false);
            mCamera.setStartedConvert(false);
        });


        mCamera.addCameraDataListener(event -> {
            boolean isStart = event.isStartedConvert();
            mStep.setText(String.valueOf(event.getStep()));
            mTolerance.setText(String.valueOf(event.getTolerance()));
            mJpg.setSelected(event.isJpg());
            mStartButton.setEnabled(!isStart);
            mStopButton.setEnabled(isStart);
            mStep.setEnabled(!isStart);
            mTolerance.setEnabled(!isStart);
            mSetAll.setEnabled(!isStart);
            mOutputPath.setEnabled(!isStart);
            int time = Math.round((event.getOutputTotalFrames() - event.getConvertedFrames()) / event.getSpeed());
            String zm = "0".repeat(2 - String.valueOf(time / 60).length());
            String zs = "0".repeat(2 - String.valueOf(time % 60).length());
            mTime.setText(zm + (time / 60) + ":" + zs + (time % 60));
            mSpeed.setText(event.getSpeed() + " fps");
            mFrames.setText(event.getTotalFrames() + " -> " + event.getOutputTotalFrames());
            mProgress.setText(event.getConvertedFrames() + " / " + event.getOutputTotalFrames());
        });
    }

    public void addSetParamListener(SetParamsListener listener) {
        mListeners.add(listener);
    }

    public void updateParams(Camera camera) {
        mStep.setText(String.valueOf(camera.getStep()));
        mTolerance.setText(String.valueOf(camera.getTolerance()));
        mJpg.setSelected(camera.isJpg());
        mFrames.setText(camera.getTotalFrames() + " -> " + camera.getOutputTotalFrames());
        mProgress.setText(camera.getConvertedFrames() + " / " + camera.getOutputTotalFrames());
    }

}
