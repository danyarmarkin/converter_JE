package com.kanistra.converter;

public class ConvertEventData {
    float mDuration;
    int mFrameIndex;

    public ConvertEventData(float duration, int frameIndex) {
        mDuration = duration;
        mFrameIndex = frameIndex;
    }

    public float getDuration() {
        return mDuration;
    }

    public int getFrameIndex() {
        return mFrameIndex;
    }
}
