package com.kanistra.converter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

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
    JLabel mJpgLabel;
    JCheckBox mJpg;
    JLabel mJpgQualityLabel;
    JTextField mJpgQuality;
    JLabel mJpgOptimizationLabel;
    JCheckBox mJpgOptimization;
    JLabel mJpgProgressiveLabel;
    JCheckBox mJpgProgressive;
    JLabel mJpgLumaQualityLabel;
    JTextField mJpgLumaQuality;
    JLabel mJpgChromaQualityLabel;
    JTextField mJpgChromaQuality;
    JLabel mJpgRstIntervalLabel;
    JTextField mJpgRstInterval;
    JLabel mThreadsAmountLabel;
    JTextField mThreadsAmount;
    JLabel mOutputAmountFramesLabel;
    JLabel mOutputAmountFrames;
    JButton mStartButton;
    JButton mStopButton;
    JLabel mSpeedLabel;
    JLabel mSpeed;
    JLabel mTimeLabel;
    JLabel mTime;

    boolean mIsEditing = false;

    public AllSessionDetailsPanel(SessionsLib sessionsLib) {
        mSessionsLib = sessionsLib;
        setLayout(new GridLayout(21, 2));
        int width = Interface.WINDOW_WIDTH - Interface.SESSIONS_PANEL_WIDTH - 20;
        int height = (int) (Interface.WINDOW_HEIGHT * 1.5f);
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
                Runnable runnable = () -> {
                    try {
                        int value = Integer.parseInt(mStep.getText());
                        for (Session session : mSessionsLib) {
                            for (Camera camera : session.getCameras()) {
                                if (camera.getStep() != value)
                                    camera.setStep(value, false);
                            }
                        }
                        mStep.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
                        mSessionsLib.updateCameras();
                        updateParams(false, false);
                    } catch (NumberFormatException e) {
                        mStep.setBorder(new BevelBorder(0, Color.red, Color.red));
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        });
        add(mStep);

        mToleranceLabel = new JLabel(Interface.TOLERANCE_TITLE);
        add(mToleranceLabel);

        mTolerance = new JTextField(6);
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
                Runnable runnable = () -> {
                    try {
                        int value = Integer.parseInt(mTolerance.getText());
                        for (Session session : mSessionsLib) {
                            for (Camera camera : session.getCameras()) {
                                camera.setTolerance(value);
                            }
                        }
                        updateParams(false, false);
                        mTolerance.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
                    } catch (NumberFormatException e) {
                        mTolerance.setBorder(new BevelBorder(0, Color.red, Color.red));
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        });
        add(mTolerance);

        mJpgLabel = new JLabel(Interface.JPG_TITLE);
        add(mJpgLabel);

        mJpg = new JCheckBox();
        mJpg.addItemListener(e -> {
            for (Session session : mSessionsLib) {
                for (Camera camera : session.getCameras()) {
                    camera.setJpg(mJpg.isSelected());
                }
            }
        });
        add(mJpg);

        mJpgQualityLabel = new JLabel(Interface.JPG_QUALITY_TITLE);
        add(mJpgQualityLabel);

        mJpgQuality = new JpgQualityTextField(mSessionsLib);
        add(mJpgQuality);

        mJpgOptimizationLabel = new JLabel(Interface.JPG_OPTIMIZE_TITLE);
        add(mJpgOptimizationLabel);

        mJpgOptimization = new JpgOptimizeCheckBox(mSessionsLib);
        add(mJpgOptimization);

        mJpgProgressiveLabel = new JLabel(Interface.JPG_PROGRESSIVE_TITLE);
        add(mJpgProgressiveLabel);

        mJpgProgressive = new JpgProgressiveCheckBox(mSessionsLib);
        add(mJpgProgressive);

        mJpgLumaQualityLabel = new JLabel(Interface.JPG_LUMA_QUALITY_TITLE);
        add(mJpgLumaQualityLabel);

        mJpgLumaQuality = new JpgLumaQualityTextField(mSessionsLib);
        add(mJpgLumaQuality);

        mJpgChromaQualityLabel = new JLabel(Interface.JPG_CHROMA_QUALITY_TITLE);
        add(mJpgChromaQualityLabel);

        mJpgChromaQuality = new JpgChromaQualityTextField(mSessionsLib);
        add(mJpgChromaQuality);

        mJpgRstIntervalLabel = new JLabel(Interface.JPG_RST_INTERVAL_TITLE);
        add(mJpgRstIntervalLabel);

        mJpgRstInterval = new JpgRstIntervalTextField(mSessionsLib);
        add(mJpgRstInterval);

        mThreadsAmountLabel = new JLabel(Interface.THREADS_AMOUNT_TITLE);
        add(mThreadsAmountLabel);

        mThreadsAmount = new JTextField();
        mThreadsAmount.setText(String.valueOf(mSessionsLib.getThreadsAmount()));
        mThreadsAmount.getDocument().addDocumentListener(new DocumentListener() {
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
                    int value = Integer.parseInt(mThreadsAmount.getText());
                    mSessionsLib.setThreadsAmount(value);
                    mThreadsAmount.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
                } catch (NumberFormatException e) {
                    mThreadsAmount.setBorder(new BevelBorder(0,Color.red, Color.red));
                }
            }
        });
        add(mThreadsAmount);

        mOutputAmountFramesLabel = new JLabel(Interface.OUTPUT_AMOUNT_FRAMES_TITLE);
        add(mOutputAmountFramesLabel);

        mOutputAmountFrames = new JLabel("1000");
        add(mOutputAmountFrames);

        mStartButton = new JButton(Interface.START_BUTTON_TITLE);
        mStartButton.addActionListener(e -> new ConverterQueue(mSessionsLib, mSessionsLib.getThreadsAmount()));
        add(mStartButton);

        mStopButton = new JButton(Interface.STOP_BUTTON_TITLE);
        mStopButton.addActionListener(e -> {
            for (Session session : mSessionsLib) {
                for (Camera camera : session.getCameras()) {
                    camera.setStartedConvert(false);
                }
            }
        });
        add(mStopButton);

        mSpeedLabel = new JLabel(Interface.SPEED_TITLE);
        add(mSpeedLabel);

        mSpeed = new JLabel("0 fps");
        add(mSpeed);

        mTimeLabel = new JLabel(Interface.TIME_TITLE);
        add(mTimeLabel);

        mTime = new JLabel("0 sec");
        add(mTime);

        updateParams(true, true);

        for (Session session : mSessionsLib) {
            for (Camera camera : session.getCameras()) {
                camera.addCameraDataListener(event -> {
                    updateParams(false, false);
                    repaint();
                });
            }
        }
        repaint();
    }

    public void updateParams(boolean withStep, boolean withTolerance) {
        mIsEditing = true;
        int total = 0;
        int outTotal = 0;
        int doneTotal = 0;
        float speed = 0f;
        int step = -1;
        int tolerance = -1;
        boolean isJpg = true;
        for (Session session : mSessionsLib) {
            for (Camera camera : session.getCameras()) {
                total += camera.getTotalFrames();
                outTotal += camera.getOutputTotalFrames();
                speed += camera.getConvertedSpeed();
                doneTotal += camera.getConvertedFrames();
                if (step == -1) step = camera.getStep();
                if (step != camera.getStep()) step = -2;
                if (tolerance == -1) tolerance = camera.getTolerance();
                if (tolerance != camera.getTolerance()) tolerance = -2;
                if (!camera.isJpg()) isJpg = false;
            }
        }
        mJpg.setSelected(isJpg);
        mTotalFrames.setText(String.valueOf(total));
        mOutputAmountFrames.setText(String.valueOf(outTotal));
        mSpeed.setText(speed + " fps");
        if (withStep) {
            if (step < 0) {
                mStep.setText("difference");
            } else {
                mStep.setText(String.valueOf(step));
            }
        }
        if (withTolerance) {
            if (tolerance < 0) {
                mTolerance.setText("difference");
            } else {
                mTolerance.setText(String.valueOf(tolerance));
            }
        }

        mIsEditing = false;

        if (speed == 0) return;

        int time = Math.round((outTotal - doneTotal) / speed);
        String zm = "0".repeat(2 - String.valueOf(time / 60).length());
        String zs = "0".repeat(2 - String.valueOf(time % 60).length());
        mTime.setText(zm + (time / 60) + ":" + zs + (time % 60));

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        ArrayList<float[]> coords = mSessionsLib.getSpeedCoords();
        int height = 150;
        int width = 250;
        float eWidth = ((float) getWidth() - 50) / width;
        float max = 0;
        for (float[] i : coords) {
            if (i[1] > max) max = i[1];
        }
        int ind = 0;
        int start = coords.size() - width;
        if (start <= 0) start = 1;
        for (int i = start; i < coords.size() - 2; i++) {
            float a = coords.get(i)[1] + coords.get(i - 1)[1] + coords.get(i + 1)[1];
            float b = coords.get(i + 1)[1] + coords.get(i)[1] + coords.get(i + 2)[1];
            a /= 3;
            b /= 3;
            int h1 = Math.round(( a / max) * height);
            int h2 = Math.round(( b / max) * height);
            g.drawLine(Math.round(ind * eWidth), getHeight() - h1, Math.round((ind + 1) * eWidth), getHeight() - h2);
            ind += 1;
        }
    }

}
