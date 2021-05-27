package io.actbase.ivs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

public class RTMPCameraManager extends ViewGroupManager<RTMPCameraView> {

    private static final int COMMAND_STOP_ID = 1;
    private static final String COMMAND_STOP_NAME = "stop";
    private static final int COMMAND_START_ID = 2;
    private static final String COMMAND_START_NAME = "start";

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

    @Override
    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put("topChangeState", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onChangeState")))
                .put("topFailWithError", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onFailWithError")))
                .build();
    }

    @ReactProp(name = "frontCamera")
    public void setFrontCamera(RTMPCameraView view, @Nullable Boolean frontCamera) {
        view.setFrontCamera(frontCamera);
    }

    @ReactProp(name = "uri")
    public void setUri(RTMPCameraView view, @Nullable String inputUrl) {
        view.setUri(inputUrl);
    }

    @ReactProp(name = "zoomScale")
    public void setZoomScale(RTMPCameraView view, @Nullable Integer zoomScale) {
        view.setZoomScale(zoomScale);
    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                COMMAND_STOP_NAME, COMMAND_STOP_ID,
                COMMAND_START_NAME, COMMAND_START_ID
        );
    }

    @Override
    public void receiveCommand(RTMPCameraView root, int commandId, @Nullable ReadableArray args) {
        switch (commandId) {
            case COMMAND_STOP_ID:
                root.stop();
                break;
            case COMMAND_START_ID:
                root.start();
                break;
        }
    }
}