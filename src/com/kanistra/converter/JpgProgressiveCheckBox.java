package com.kanistra.converter;

import javax.swing.*;

public class JpgProgressiveCheckBox extends JCheckBox {
    SessionsLib mSessionsLib;

    public JpgProgressiveCheckBox(SessionsLib sessionsLib) {
        mSessionsLib = sessionsLib;
        for (Session session : mSessionsLib) {
            for (Camera camera : session.getCameras()) setSelected(camera.isJpgProgressive());
        }

        addItemListener(e -> {
            for (Session session : mSessionsLib) {
                for (Camera camera : session.getCameras()) camera.setJpgProgressive(isSelected());
            }
        });
    }
}
