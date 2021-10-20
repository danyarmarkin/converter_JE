package com.kanistra.converter;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Main {

    static SessionsLib mSessionsLib;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("mat = " + mat.dump());
        mSessionsLib = new SessionsLib();
//        mSessionsLib.addCamera(new Camera("goodMan_EDDS23_13.mov"));
//        mSessionsLib.addCamera(new Camera("goodMan_EDDS23_23.mov"));
//        mSessionsLib.addCamera(new Camera("goodMan_KVJE43_11.mov"));
//        mSessionsLib.addCamera(new Camera("Man_IJJC23_22.mov"));
//        mSessionsLib.addCamera(new Camera("goodMan_EDDS23_33.mov"));

        new Interface(mSessionsLib);
    }
}
