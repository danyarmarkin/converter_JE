package com.kanistra.converter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.io.File;
import java.util.ArrayList;

public class CameraConverter extends Thread{

    Camera mCamera;
    long mTime = System.currentTimeMillis();

    ArrayList<CameraConverterListener> mListeners = new ArrayList<>();

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

        float[] durations = {0f, 0f, 0f, 0f, 0f};
        int durationIndex = 0;

        int tolerance = mCamera.getTolerance();
        int toleranceIndex = 0;
        Mat[] images = new Mat[tolerance * 2 + 1];
        float[] imagesScore = new float[tolerance * 2 + 1];

        if (videoCapture.isOpened()) {
            int index = 0;
            int frame = 0;
            int step = mCamera.getStep();

            boolean success = videoCapture.read(image);
            while (success) {
                if (!mCamera.isStartedConvert()) return;
                if (index >= step - tolerance || index <= tolerance) {
                    images[toleranceIndex] = image;
                    Mat imageGray = new Mat();
                    Mat destination = new Mat();
                    Imgproc.cvtColor(image, imageGray, Imgproc.COLOR_RGB2GRAY);
                    Imgproc.Laplacian(imageGray, destination, 3);
                    MatOfDouble median = new MatOfDouble();
                    MatOfDouble std = new MatOfDouble();
                    Core.meanStdDev(destination, median , std);
                    float score = (float) Math.pow(std.get(0,0)[0],2);
                    imagesScore[toleranceIndex] = score;
                    System.out.print(frame + " " + toleranceIndex + " ");
                    System.out.println(score);
                    toleranceIndex += 1;
                    imageGray.release();
                    destination.release();
                    median.release();
                    std.release();
                }
                if (index == tolerance) {
                    float maxImageScore = max(imagesScore);
                    Mat maxImage = images[index(imagesScore, maxImageScore)];
                    String z = "0".repeat(4 - String.valueOf(frame).length());
                    System.out.println("max image " + index(imagesScore, maxImageScore) + " with score " + maxImageScore);
                    String extension = mCamera.isJpg()? ".jpg" : ".png";
                    Imgcodecs.imwrite(mCamera.getOutputPath() + "/" + mCamera.getSessionId() + "_" +
                            mCamera.getDeviceIndex() + mCamera.getDeviceAmount() +"_"+ z + frame + extension, maxImage);
                    frame += 1;

                    float duration = (System.currentTimeMillis() - mTime) / 1000f;
                    durations[durationIndex] = duration;
                    float durationOut = 0;
                    for (float f : durations) {
                        if (f == 0f) f = durations[0];
                        durationOut += f;
                    }
                    durationOut /= durations.length;
                    for (CameraConverterListener listener : mListeners) listener.onNewFrame(
                            new ConvertEventData(durationOut, frame));
                    mTime = System.currentTimeMillis();
                    durationIndex += 1;
                    durationIndex %= durations.length;
                    toleranceIndex = 0;
                    maxImage.release();
                }
                image.release();
                index += 1;
                index %= step;
                success = videoCapture.read(image);
            }
            image.release();
            for (Mat mat : images) mat.release();
            for (CameraConverterListener listener : mListeners) listener.onConvertDone();
        }
    }

    static int getFramesAmount(Camera camera) {
        VideoCapture videoCapture = new VideoCapture(camera.getPath());
        return (int) videoCapture.get(Videoio.CAP_PROP_FRAME_COUNT);
    }

    private float max(float[] floats) {
        float m = floats[0];
        for (float f : floats) {
            if (f > m) m = f;
        }
        return m;
    }

    private int index(float[] floats, float i) {
        int ind = 0;
        for (float f : floats) {
            if (f == i) return ind;
            ind += 1;
        }
        return 0;
    }

    public void addCameraConverterListener(CameraConverterListener listener) {
        mListeners.add(listener);
    }
}
