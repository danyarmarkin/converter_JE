package com.kanistra.converter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

    public CameraPanel(Camera camera) {
        mCamera = camera;
        setBackground(Color.white);
        setPreferredSize(new Dimension(
                Interface.WINDOW_WIDTH - Interface.SESSIONS_PANEL_WIDTH,
                Interface.SESSION_DETAILS_PANEL_HEIGHT));
        setLayout(new GridLayout(6, 2));

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
            boolean value = mJpg.isEnabled();
            mCamera.setJpg(value);
        });
        add(mJpg);

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
    }
}
