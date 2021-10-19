package com.kanistra.converter;

import java.util.ArrayList;
import java.util.UUID;

public class Session {
    private String mObjectName;
    private String mSessionId;
    private int mDeviceAmount;
    private int mDate = -1;
    private int mTime = -1;
    private UUID mId;
    private String mOutputPath;
    ArrayList<Camera> mCameras;

    public Session(String objectName, String sessionId, int deviceAmount, int date, int time, String outputFolder) {
        mObjectName = objectName;
        mSessionId = sessionId;
        mDeviceAmount = deviceAmount;
        mDate = date;
        mTime = time;
        mId = UUID.randomUUID();
        mOutputPath = outputFolder + "/" + getFullName();
        mCameras = new ArrayList<>();
    }

    public Session(String objectName, String sessionId, int deviceAmount, String outputFolder) {
        mObjectName = objectName;
        mSessionId = sessionId;
        mDeviceAmount = deviceAmount;
        mId = UUID.randomUUID();
        mOutputPath = outputFolder + "/" + getFullName();
        mCameras = new ArrayList<>();
    }

    public String getObjectName() {
        return mObjectName;
    }

    public String getSessionId() {
        return mSessionId;
    }

    public int getDeviceAmount() {
        return mDeviceAmount;
    }

    public int getDate() {
        return mDate;
    }

    public int getTime() {
        return mTime;
    }

    public UUID getId() {
        return mId;
    }

    public String getFullName() {
        if (mTime == -1 && mDate == -1) {
            return mObjectName + "_" + mSessionId + "_" + mDeviceAmount;
        }
        return mObjectName + "_" + mSessionId + "_" + mDeviceAmount + "_" + mDate + "_" + mTime;
    }

    public boolean equals(Session session) {
        return mSessionId.equals(session.getSessionId()) &&
                mObjectName.equals(session.getObjectName()) &&
                mDeviceAmount == session.getDeviceAmount();
    }

    public void addCamera(Camera camera) {
        mCameras.add(camera);
    }

    public boolean containCamera(Camera camera) {
        for (Camera c : mCameras) {
            if (camera.equals(c)) return true;
        }
        return false;
    }
}
