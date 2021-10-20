package com.kanistra.converter;

public class Camera {
    private String mObjectName;
    private String mSessionId;
    private int mDeviceAmount;
    private int mDeviceIndex;
    private String mPath;
    private String mOutputPath;
    private int mStep = 10;
    private int mTolerance = 0;
    private boolean mStartedConvert = false;
    private boolean mJpg = false;
    private CameraConverter mCameraConverter;

    public Camera(String objectName, String sessionId, int deviceAmount, int deviceIndex, String path) {
        mObjectName = objectName;
        mSessionId = sessionId;
        mDeviceAmount = deviceAmount;
        mDeviceIndex = deviceIndex;
        this.mPath = path;
        mCameraConverter = new CameraConverter(this);
    }

    public Camera(String path) {
        mPath = path;
        String[] components = path.substring(0, path.length() - 4).split("/");
        String name = components[components.length - 1];
        components = name.split("_");
        mObjectName = components[0];
        mSessionId = components[1];
        mDeviceAmount = Integer.parseInt(components[2]) % 10;
        mDeviceIndex = (int) Math.floor(Integer.parseInt(components[2]) / 10f);
        mCameraConverter = new CameraConverter(this);
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

    public String getPath() {
        return mPath;
    }

    public String getOutputPath() {
        return mOutputPath;
    }

    public void setOutputPath(String outputPath) {
        mOutputPath = outputPath;
    }

    public int getStep() {
        return mStep;
    }

    public void setStep(int step) {
        mStep = step;
    }

    public int getTolerance() {
        return mTolerance;
    }

    public void setTolerance(int tolerance) {
        mTolerance = tolerance;
    }

    public boolean isStartedConvert() {
        return mStartedConvert;
    }

    public void setStartedConvert(boolean startedConvert) {
        mStartedConvert = startedConvert;
        if (mStartedConvert == true) {
            mCameraConverter = new CameraConverter(this);
            mCameraConverter.start();
        }
    }

    public boolean isJpg() {
        return mJpg;
    }

    public void setJpg(boolean jpg) {
        mJpg = jpg;
    }

    public boolean equals(Camera camera) {
        return mObjectName.equals(camera.getObjectName()) &&
                mSessionId.equals(camera.getSessionId()) &&
                mDeviceAmount == camera.getDeviceAmount() &&
                mDeviceIndex == camera.getDeviceIndex();
    }
}
