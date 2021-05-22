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

public class RTMPCameraView extends FrameLayout {

    public RTMPCameraView(@NonNull Context context) {
        super(context);
        this.context = (ReactContext) context;
    }

    public void setFrontCamera(Boolean frontCamera) {
    }

}
