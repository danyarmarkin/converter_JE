package com.kanistra.converter;

public interface CameraConverterListener {
    void onConvertDone();
    void onNewFrame(ConvertEventData eventData);
}
