package com.kanistra.converter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class JpgLumaQualityTextField extends JTextField {
    SessionsLib mSessionsLib;

    public JpgLumaQualityTextField(SessionsLib sessionsLib) {
        mSessionsLib = sessionsLib;

        for (Session session : mSessionsLib) {
            for (Camera camera : session.getCameras()) setText(String.valueOf(camera.getJpgLumaQuality()));
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
                            camera.setJpgLumaQuality(value);
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
