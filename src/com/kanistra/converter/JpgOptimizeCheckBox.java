package com.kanistra.converter;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class JpgOptimizeCheckBox extends JCheckBox {

    SessionsLib mSessionsLib;

    public JpgOptimizeCheckBox(SessionsLib sessionsLib) {
        mSessionsLib = sessionsLib;
        for (Session session : mSessionsLib) {
            for (Camera camera : session.getCameras()) setSelected(camera.isJpgOptimize());
        }

        addItemListener(e -> {
            for (Session session : mSessionsLib) {
                for (Camera camera : session.getCameras()) camera.setJpgOptimize(isSelected());
            }
        });
    }
}
