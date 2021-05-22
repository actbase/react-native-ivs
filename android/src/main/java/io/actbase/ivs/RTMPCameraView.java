package io.actbase.ivs;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactContext;
import com.pedro.rtplibrary.rtmp.RtmpCamera1;
import com.pedro.rtplibrary.view.OpenGlView;

import net.ossrs.rtmp.ConnectCheckerRtmp;

public class RTMPCameraView extends FrameLayout implements ConnectCheckerRtmp, SurfaceHolder.Callback, View.OnTouchListener {

    private ReactContext context;
    private OpenGlView openGlView;
    private RtmpCamera1 rtmpCamera;

    public RTMPCameraView(@NonNull Context context) {
        super(context);

        this.context = (ReactContext) context;

        openGlView = new OpenGlView(context);
        openGlView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(openGlView);

        rtmpCamera = new RtmpCamera1(openGlView, this);
//        rtmpCamera.isFrontCamera()
        openGlView.getHolder().addCallback(this);
        openGlView.setOnTouchListener(this);
    }

    public void setFrontCamera(Boolean frontCamera) {
        if (rtmpCamera != null) {
            if (frontCamera != rtmpCamera.isFrontCamera()) {
                rtmpCamera.switchCamera();
            }
//            rtmpCamera.
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        rtmpCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (rtmpCamera.isRecording()) {
            rtmpCamera.stopRecord();
        }
        if (rtmpCamera.isStreaming()) {
            rtmpCamera.stopStream();
        }
        rtmpCamera.stopPreview();
    }

    @Override
    public void onConnectionSuccessRtmp() {

    }

    @Override
    public void onConnectionFailedRtmp(String reason) {

    }

    @Override
    public void onNewBitrateRtmp(long bitrate) {

    }

    @Override
    public void onDisconnectRtmp() {

    }

    @Override
    public void onAuthErrorRtmp() {

    }

    @Override
    public void onAuthSuccessRtmp() {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
