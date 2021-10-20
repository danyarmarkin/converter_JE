package com.kanistra.converter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.io.File;

public class CameraConverter extends Thread{

    Camera mCamera;

    public CameraConverter(Camera camera) {
        mCamera = camera;
    }

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Override
    public void run() {
        super.run();

        File theDir = new File(mCamera.getOutputPath());
        if (!theDir.exists()){
            theDir.mkdirs();
        }

        VideoCapture videoCapture = new VideoCapture(mCamera.getPath());
        Mat image = new Mat();
        if (videoCapture.isOpened()) {
            int index = 0;
            int frame = 0;
            int step = mCamera.getStep();
            boolean success = videoCapture.read(image);
            while (success) {
                if (index == 0) {
                    Imgcodecs.imwrite(mCamera.getOutputPath() + "/" + mCamera.getSessionId() + "_" +
                            mCamera.getDeviceIndex() + mCamera.getDeviceAmount() +"_" + frame + ".png", image);
                    System.out.println(frame);
                    frame += 1;
                }
                index += 1;
                index %= step;
                success = videoCapture.read(image);
            }

        }
    }
}
