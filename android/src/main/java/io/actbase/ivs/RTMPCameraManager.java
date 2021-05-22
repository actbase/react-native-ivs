package io.actbase.ivs;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

public class RTMPCameraManager extends ViewGroupManager<RTMPCameraView> {

    @NonNull
    @Override
    public String getName() {
        return "RTMPCamera";
    }

    @NonNull
    @Override
    protected RTMPCameraView createViewInstance(@NonNull ThemedReactContext reactContext) {
        RTMPCameraView view = new RTMPCameraView(reactContext);
        return view;
    }

}
