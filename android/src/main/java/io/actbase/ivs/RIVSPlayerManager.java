package io.actbase.ivs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

public class RIVSPlayerManager extends ViewGroupManager<RIVSPlayerView> {

    private static final int COMMAND_PAUSE_ID = 1;
    private static final String COMMAND_PAUSE_NAME = "pause";
    private static final int COMMAND_PLAY_ID = 2;
    private static final String COMMAND_PLAY_NAME = "play";

    @NonNull
    @Override
    public String getName() {
        return "RIVSPlayer";
    }

    @NonNull
    @Override
    protected RIVSPlayerView createViewInstance(@NonNull ThemedReactContext reactContext) {
        RIVSPlayerView view = new RIVSPlayerView(reactContext);
        return view;
    }

    @Override
    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put("topChangeState", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onChangeState")))
                .put("topChangeVideoSize", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onChangeVideoSize")))
                .put("topFailWithError", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onFailWithError")))
                .put("topOutputCue", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onOutputCue")))
                .build();
    }

    @ReactProp(name = "uri")
    public void setUri(RIVSPlayerView view, @Nullable String inputUrl) {
        view.setUri(inputUrl);
    }

    @ReactProp(name = "autoPlay")
    public void setAutoPlay(RIVSPlayerView view, Boolean autoPlay) {
        view.setAutoPlay(autoPlay);
    }

    @ReactProp(name = "scaleMode")
    public void setScaleMode(RIVSPlayerView view, String mode) {
        view.setScaleMode(mode);
    }

    @ReactProp(name = "liveLowLatencyEnabled")
    public void setLiveLowLatencyEnabled(RIVSPlayerView view, Boolean autoPlay) {
        view.setLiveLowLatencyEnabled(autoPlay);
    }

    @ReactProp(name = "muted")
    public void setMuted(RIVSPlayerView view, Boolean muted) {
        view.setMuted(muted);
    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                COMMAND_PAUSE_NAME, COMMAND_PAUSE_ID,
                COMMAND_PLAY_NAME, COMMAND_PLAY_ID
        );
    }

    @Override
    public void receiveCommand(RIVSPlayerView root, int commandId, @Nullable ReadableArray args) {
        switch (commandId) {
            case COMMAND_PAUSE_ID:
                root.pause();
                break;
            case COMMAND_PLAY_ID:
                root.play();
                break;
        }
    }

}
