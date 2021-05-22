package io.actbase.ivs;

import android.content.Context;
import android.hardware.Camera;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactContext;
import com.github.faucamp.simplertmp.RtmpHandler;

import net.ossrs.yasea.SrsCameraView;
import net.ossrs.yasea.SrsEncodeHandler;
import net.ossrs.yasea.SrsPublisher;
import net.ossrs.yasea.SrsRecordHandler;

import java.io.IOException;
import java.net.SocketException;

public class RTMPCameraView extends FrameLayout implements RtmpHandler.RtmpListener,
        SrsRecordHandler.SrsRecordListener, SrsEncodeHandler.SrsEncodeListener {

    private ReactContext context;
    private SrsCameraView srsCameraView;
    private SrsPublisher srsPublisher;

    private int mWidth = 720;
    private int mHeight = 1280;

    public RTMPCameraView(@NonNull Context context) {
        super(context);
        this.context = (ReactContext) context;

        srsCameraView = new SrsCameraView(context);
        srsCameraView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        srsCameraView.setCameraCallbacksHandler(new SrsCameraView.CameraCallbacksHandler() {
            @Override
            public void onCameraParameters(Camera.Parameters params) {
                //params.setFocusMode("custom-focus");
                //params.setWhiteBalance("custom-balance");
                //etc...
            }
        });

        srsPublisher = new SrsPublisher(srsCameraView);
        srsPublisher.setEncodeHandler(new SrsEncodeHandler(this));
        srsPublisher.setRtmpHandler(new RtmpHandler(this));
//            srsPublisher.setRecordHandler(new SrsRecordHandler(this));
        srsPublisher.setPreviewResolution(mWidth, mHeight);
        srsPublisher.setOutputResolution(mHeight, mWidth); // 这里要和preview反过来
        srsPublisher.setVideoHDMode();

        this.addView(srsCameraView);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        srsPublisher.setPreviewResolution(right - left, bottom - top);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        srsPublisher.startCamera();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        srsPublisher.stopCamera();
    }

    public void setFrontCamera(Boolean frontCamera) {

//        srsCameraView.getCamera()

    }

    @Override
    public void onRtmpConnecting(String msg) {

    }

    @Override
    public void onRtmpConnected(String msg) {

    }

    @Override
    public void onRtmpVideoStreaming() {

    }

    @Override
    public void onRtmpAudioStreaming() {

    }

    @Override
    public void onRtmpStopped() {

    }

    @Override
    public void onRtmpDisconnected() {

    }

    @Override
    public void onRtmpVideoFpsChanged(double fps) {

    }

    @Override
    public void onRtmpVideoBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpAudioBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpSocketException(SocketException e) {

    }

    @Override
    public void onRtmpIOException(IOException e) {

    }

    @Override
    public void onRtmpIllegalArgumentException(IllegalArgumentException e) {

    }

    @Override
    public void onRtmpIllegalStateException(IllegalStateException e) {

    }

    @Override
    public void onNetworkWeak() {

    }

    @Override
    public void onNetworkResume() {

    }

    @Override
    public void onEncodeIllegalArgumentException(IllegalArgumentException e) {

    }

    @Override
    public void onRecordPause() {

    }

    @Override
    public void onRecordResume() {

    }

    @Override
    public void onRecordStarted(String msg) {

    }

    @Override
    public void onRecordFinished(String msg) {

    }

    @Override
    public void onRecordIllegalArgumentException(IllegalArgumentException e) {

    }

    @Override
    public void onRecordIOException(IOException e) {

    }
}
