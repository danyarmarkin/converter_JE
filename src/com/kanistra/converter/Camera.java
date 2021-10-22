package com.kanistra.converter;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Camera {
    private final String mObjectName;
    private final String mSessionId;
    private final int mDeviceAmount;
    private final int mDeviceIndex;
    private final String mPath;
    private String mOutputPath;
    private int mStep = 10;
    private int mTolerance = 0;
    private boolean mStartedConvert = false;
    private boolean mJpg = false;
    private CameraConverter mCameraConverter;
    private int mConvertedFrames = 0;
    private float mConvertedSpeed = 0f;
    private final int mTotalFrames;
    private int mOutputTotalFrames = 52;

    ArrayList<CameraDataListener> mCameraDataListeners = new ArrayList<>();
    ArrayList<UpdateProgressListener> mUpdateProgressListeners = new ArrayList<>();
    ArrayList<ConvertDoneListener> mConvertDoneListeners = new ArrayList<>();

    public Camera(String path) {
        path = path.replaceAll("\\\\", "/");
        mPath = path;
        String[] components = path.substring(0, path.length() - 4).split("/");
        String name = components[components.length - 1];
        components = name.split("_");
        mObjectName = components[0];
        mSessionId = components[1];
        mDeviceAmount = Integer.parseInt(components[2]) % 10;
        mDeviceIndex = (int) Math.floor(Integer.parseInt(components[2]) / 10f);
        mCameraConverter = new CameraConverter(this);
        mTotalFrames = CameraConverter.getFramesAmount(this);
        setOutputTotalFrames();
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
        setOutputTotalFrames();
        for (UpdateProgressListener listener : mUpdateProgressListeners) listener.onUpdateProgress(this);
    }

    public void setStep(int step, boolean update) {
        mStep = step;
        setOutputTotalFrames();
        if (!update) return;
        for (UpdateProgressListener listener : mUpdateProgressListeners) listener.onUpdateProgress(this);
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
        if (mStartedConvert) {
            mCameraConverter = new CameraConverter(this);
            mConvertedFrames = 0;
            mConvertedSpeed = 0;
            mCameraConverter.addCameraConverterListener(new CameraConverterListener() {
                @Override
                public void onConvertDone() {
                    mStartedConvert = false;
                    mConvertedFrames = mOutputTotalFrames;
                    mConvertedSpeed = 0f;
                    registerEvent();
                    for (ConvertDoneListener listener : mConvertDoneListeners) listener.onDone();
                }

                @Override
                public void onNewFrame(ConvertEventData eventData) {
                    mConvertedFrames = eventData.getFrameIndex();
                    mConvertedSpeed = 1.0f / eventData.getDuration();
                    registerEvent();
                }
            });
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

    public int getConvertedFrames() {
        return mConvertedFrames;
    }

    public void addCameraDataListener(CameraDataListener listener) {
        mCameraDataListeners.add(listener);
    }

    public void addUpdateProgressListener(UpdateProgressListener listener) {
        mUpdateProgressListeners.add(listener);
    }

    public int getTotalFrames() {
        return mTotalFrames;
    }

    public void registerEvent() {
        try {
            for (UpdateProgressListener listener : mUpdateProgressListeners) listener.onUpdateProgress(this);
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        for (CameraDataListener listener : mCameraDataListeners) listener.onDataChanged(
                new CameraDataChangeEvent(mStep,
                        mTolerance,
                        mJpg,
                        mConvertedFrames,
                        mOutputTotalFrames,
                        mTotalFrames,
                        mConvertedSpeed,
                        mStartedConvert));

    }

    public void setOutputTotalFrames() {
        mOutputTotalFrames = (mTotalFrames - 1) / mStep + 1;
    }

    public int getOutputTotalFrames() {
        return mOutputTotalFrames;
    }

    public float getConvertedSpeed() {
        return mConvertedSpeed;
    }

    public void addConvertDoneListener(ConvertDoneListener listener) {
        mConvertDoneListeners.add(listener);
    }
}
