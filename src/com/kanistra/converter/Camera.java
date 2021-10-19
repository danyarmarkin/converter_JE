package com.kanistra.converter;

import org.opencv.core.Mat;

public class Camera {
    private String mObjectName;
    private String mSessionId;
    private int mDeviceAmount;
    private int mDeviceIndex;
    private String mPath;

    public Camera(String objectName, String sessionId, int deviceAmount, int deviceIndex, String path) {
        mObjectName = objectName;
        mSessionId = sessionId;
        mDeviceAmount = deviceAmount;
        mDeviceIndex = deviceIndex;
        this.mPath = path;
    }

    public Camera(String path) {
        mPath = path.substring(0, path.length() - 4);
        String[] components = mPath.split("/");
        String name = components[components.length - 1];
        components = name.split("_");
        mObjectName = components[0];
        mSessionId = components[1];
        mDeviceAmount = Integer.parseInt(components[2]) % 10;
        mDeviceIndex = (int) Math.floor(Integer.parseInt(components[2]) / 10);
    }

    public String getFolder() {
        String[] components = mPath.split("/");
        int len = mPath.length() - components[components.length - 1].length();
        return mPath.substring(0, len);
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

    public int getDeviceIndex() {
        return mDeviceIndex;
    }

    public boolean equals(Camera camera) {
        return mObjectName.equals(camera.getObjectName()) &&
                mSessionId.equals(camera.getSessionId()) &&
                mDeviceAmount == camera.getDeviceAmount() &&
                mDeviceIndex == camera.getDeviceIndex();
    }
}
