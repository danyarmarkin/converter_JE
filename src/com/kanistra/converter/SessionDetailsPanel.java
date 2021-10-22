package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SessionDetailsPanel extends JPanel {

    Session mSession;
    ArrayList<Camera> mCameras = new ArrayList<>();
    ArrayList<CameraPanel> mCameraPanels = new ArrayList<>();

    public SessionDetailsPanel(Session session) {
        mSession = session;
        if (session == null) return;
        int width = Interface.WINDOW_WIDTH - Interface.SESSIONS_PANEL_WIDTH - 20;
        int height = (Interface.SESSION_DETAILS_PANEL_HEIGHT + 10) * mSession.getCameras().size()
                + Interface.ALL_CAMERAS_PANEL_HEIGHT;
        setSize(new Dimension(width, Math.max(height, Interface.WINDOW_HEIGHT)));
        setLayout(new GridBagLayout());

        mCameras.addAll(mSession.getCameras());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 0, 5, 0);

        add(new AllCamerasPanel(mSession), constraints);

        for (Camera camera : mCameras) {
            constraints.gridy += 1;
            CameraPanel cameraPanel = new CameraPanel(camera);
            cameraPanel.addSetParamListener(cam -> {
                for (CameraPanel  cp : mCameraPanels) {
                    cp.updateParams(cam);
                }

                for (Camera c : mCameras) {
                    c.setJpg(cam.isJpg());
                    c.setTolerance(cam.getTolerance());
                    c.setStep(cam.getStep());
                }
            });
            mCameraPanels.add(cameraPanel);
            add(cameraPanel, constraints);
        }

        constraints.gridy += 1;
        if (Interface.WINDOW_HEIGHT - height > 0) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(width, Interface.WINDOW_HEIGHT - height));
            add(panel, constraints);
        }
    }
}
