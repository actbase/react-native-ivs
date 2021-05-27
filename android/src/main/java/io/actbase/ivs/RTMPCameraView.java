package io.actbase.ivs;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.pedro.encoder.input.video.CameraHelper;
import com.pedro.encoder.utils.CodecUtil;
import com.pedro.rtplibrary.rtmp.RtmpCamera2;
import com.pedro.rtplibrary.view.OpenGlView;

import net.ossrs.rtmp.ConnectCheckerRtmp;

public class RTMPCameraView extends FrameLayout implements ConnectCheckerRtmp, SurfaceHolder.Callback {

    private ReactContext context;
    private RtmpCamera2 rtmpCamera2;
    private OpenGlView openGlView;
    private String rtmpUrl;

    private int mWidth = 720;
    private int mHeight = 1280;

    public RTMPCameraView(@NonNull Context context) {
        super(context);
        this.context = (ReactContext) context;

        openGlView = new OpenGlView(context);
        this.addView(openGlView, new LayoutParams(200, 200, 17));

        rtmpCamera2 = new RtmpCamera2(openGlView, this);
        rtmpCamera2.setReTries(10);
        openGlView.getHolder().addCallback(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        scaleToLayout();
    }

    private void scaleToLayout() {
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        float viewRatio = (float) viewHeight / (float) viewWidth;
        float videoRatio = (float) mHeight / (float) mWidth;

        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        if (viewRatio > videoRatio) {
            // horizontal base
            int r = (int) (viewHeight / videoRatio);
            int g = (r - viewWidth) / 2;

            left = -g;
            right = viewWidth + g;
            bottom = viewHeight;
        } else {
            // vertical base
            int r = (int) (viewWidth * videoRatio);
            int g = (r - viewHeight) / 2;

            top = -g;
            bottom = viewHeight + g;
            right = viewWidth;
        }

        openGlView.layout(left, top, right, bottom);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setFrontCamera(Boolean frontCamera) {
        boolean isFront = rtmpCamera2.isFrontCamera();
        if (isFront != frontCamera) {
            rtmpCamera2.switchCamera();
        }
    }

    public void setUri(String inputUrl) {
        rtmpUrl = inputUrl;
    }

    public void setZoomScale(Integer zoomScale) {
        rtmpCamera2.setZoom(zoomScale.floatValue());
    }

    public void start() {
        if (rtmpCamera2.isStreaming()) return;
        scaleToLayout();

        rtmpCamera2.prepareVideo(mHeight, mWidth, 1200 * 1024);
        rtmpCamera2.prepareAudio();
        rtmpCamera2.startStream(rtmpUrl);
    }

    public void stop() {
        if (!rtmpCamera2.isStreaming()) return;
        rtmpCamera2.stopStream();
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        rtmpCamera2.startPreview(CameraHelper.Facing.BACK, mHeight, mWidth);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (rtmpCamera2.isStreaming()) {
            rtmpCamera2.stopStream();
        }
        rtmpCamera2.stopPreview();
    }

    @Override
    public void onConnectionSuccessRtmp() {
        ReactContext reactContext = (ReactContext) getContext();
        RCTEventEmitter emitter = reactContext.getJSModule(RCTEventEmitter.class);

        WritableMap event = Arguments.createMap();
        event.putInt("code", 2);
        event.putString("msg", "RTMPCameraStart");
        emitter.receiveEvent(getId(), "topChangeState", event);
    }

    @Override
    public void onConnectionFailedRtmp(String reason) {
        ReactContext reactContext = (ReactContext) getContext();
        RCTEventEmitter emitter = reactContext.getJSModule(RCTEventEmitter.class);

        WritableMap event = Arguments.createMap();
        event.putInt("code", 4);
        event.putString("msg", reason);
        emitter.receiveEvent(getId(), "topChangeState", event);
    }

    @Override
    public void onNewBitrateRtmp(long bitrate) {
    }

    @Override
    public void onDisconnectRtmp() {
        ReactContext reactContext = (ReactContext) getContext();
        RCTEventEmitter emitter = reactContext.getJSModule(RCTEventEmitter.class);

        WritableMap event = Arguments.createMap();
        event.putInt("code", 3);
        event.putString("msg", "RTMPCameraStop");
        emitter.receiveEvent(getId(), "topChangeState", event);
    }

    @Override
    public void onAuthErrorRtmp() {
        ReactContext reactContext = (ReactContext) getContext();
        RCTEventEmitter emitter = reactContext.getJSModule(RCTEventEmitter.class);

        WritableMap event = Arguments.createMap();
        event.putInt("code", 4);
        event.putString("msg", "onAuthErrorRtmp");
        emitter.receiveEvent(getId(), "topChangeState", event);
    }

    @Override
    public void onAuthSuccessRtmp() {
        ReactContext reactContext = (ReactContext) getContext();
        RCTEventEmitter emitter = reactContext.getJSModule(RCTEventEmitter.class);

        WritableMap event = Arguments.createMap();
        event.putInt("code", 1);
        event.putString("msg", "RTMPCameraPending");
        emitter.receiveEvent(getId(), "topChangeState", event);
    }
}
