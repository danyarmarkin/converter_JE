package com.kanistra.converter;

public class CameraDataChangeEvent {
    int mStep;
    int mTolerance;
    boolean mIsJpg;
    int mConvertedFrames;
    int mTotalFrames;
    int mOutputTotalFrames;
    float mSpeed;
    boolean mStartedConvert;

    public CameraDataChangeEvent(int step, int tolerance, boolean isJpg, int convertedFrames, int outputTotalFrames, int totalFrames, float speed, boolean startedConvert) {
        mStep = step;
        mTolerance = tolerance;
        mIsJpg = isJpg;
        mConvertedFrames = convertedFrames;
        mTotalFrames = totalFrames;
        mSpeed = speed;
        mStartedConvert = startedConvert;
        mOutputTotalFrames = outputTotalFrames;
    }

    public int getStep() {
        return mStep;
    }

    public int getTolerance() {
        return mTolerance;
    }

    public boolean isJpg() {
        return mIsJpg;
    }

    public int getConvertedFrames() {
        return mConvertedFrames;
    }

    public int getTotalFrames() {
        return mTotalFrames;
    }

    public int getOutputTotalFrames() {
        return mOutputTotalFrames;
    }

    public float getSpeed() {
        return mSpeed;
    }

    public boolean isStartedConvert() {
        return mStartedConvert;
    }
}
