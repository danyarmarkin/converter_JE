package com.kanistra.converter;

public class ConverterQueue{
    SessionsLib mSessionsLib;
    int mThreadsAmount;
    int mStartedThreadAmount;

    public ConverterQueue(SessionsLib sessionsLib, int threadsAmount) {
        mSessionsLib = sessionsLib;
        mThreadsAmount = threadsAmount;

        mStartedThreadAmount = 0;

        startNewThreads();
    }

    void startNewThreads() {
        for (Session session : mSessionsLib) {
            for (Camera camera : session.getCameras()) {
                if (mStartedThreadAmount >= mThreadsAmount) continue;
                if (camera.getConvertedFrames() != camera.getOutputTotalFrames() && !camera.isStartedConvert()) {
                    camera.setStartedConvert(true);
                    mStartedThreadAmount += 1;
                    camera.addConvertDoneListener(() -> {
                        mStartedThreadAmount -= 1;
                        startNewThreads();
                    });
                }
            }
        }
    }

    public int getThreadsAmount() {
        return mThreadsAmount;
    }
}
