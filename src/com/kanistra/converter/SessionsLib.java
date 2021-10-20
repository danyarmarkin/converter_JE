package com.kanistra.converter;

import java.util.ArrayList;
import java.util.UUID;

public class SessionsLib extends ArrayList<Session> {
    public Session getSessionById(UUID id) {
        for (Session session : this) {
            if (id == session.getId()) return session;
        }
        return null;
    }

    public boolean contain(Session session) {
        for (Session s : this) {
            if (s.equals(session)) return true;
        }
        return false;
    }

    @Override
    public void add(int index, Session element) {
        if (contain(element)) return;
        super.add(index, element);
    }

    public void addCamera(Camera camera) {
        Session cameraSession = new Session(
                camera.getObjectName(),
                camera.getSessionId(),
                camera.getDeviceAmount(),
                camera.getFolder());
        camera.setOutputPath(cameraSession.getOutputPath());
        for (Session session : this) {
            if (session.equals(cameraSession) && !session.containCamera(camera)) {
                session.addCamera(camera);
                return;
            } else if (session.containCamera(camera)) {
                return;
            }
        }
        add(cameraSession);
        addCamera(camera);
    }

    public int camerasSize() {
        int total = 0;
        for (Session session : this) {
            total += session.getCameras().size();
        }
        return total;
    }
}
