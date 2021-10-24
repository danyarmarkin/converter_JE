package com.kanistra.converter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class JpgRstIntervalTextField extends JTextField {
    SessionsLib mSessionsLib;

    public JpgRstIntervalTextField(SessionsLib sessionsLib) {
        mSessionsLib = sessionsLib;

        for (Session session : mSessionsLib) {
            for (Camera camera : session.getCameras()) setText(String.valueOf(camera.getJpgRstInterval()));
        }

        getDocument().addDocumentListener(new DocumentListener() {
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
                    int value = Integer.parseInt(getText());
                    for (Session session : mSessionsLib) {
                        for (Camera camera : session.getCameras()) {
                            camera.setJpgRstInterval(value);
                        }
                    }
                    setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
                } catch (NumberFormatException e) {
                    setBorder(new BevelBorder(0, Color.red, Color.red));
                }
            }
        });
    }
}
