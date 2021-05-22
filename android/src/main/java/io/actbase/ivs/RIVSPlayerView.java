package io.actbase.ivs;

import android.content.Context;
import android.net.Uri;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.amazonaws.ivs.player.Cue;
import com.amazonaws.ivs.player.Player;
import com.amazonaws.ivs.player.PlayerException;
import com.amazonaws.ivs.player.PlayerView;
import com.amazonaws.ivs.player.Quality;
import com.amazonaws.ivs.player.TextMetadataCue;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class RIVSPlayerView extends FrameLayout {

    private PlayerView playerView;
    private boolean autoPlay = true;

    public RIVSPlayerView(@NonNull Context context) {
        super(context);

        playerView = new PlayerView(context);
        playerView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        playerView.setBackgroundColor(getResources().getColor(android.R.color.black));
        playerView.setControlsEnabled(false);
        this.addView(playerView);

        Player player = playerView.getPlayer();
        player.addListener(new Player.Listener() {
            @Override
            public void onCue(@NonNull Cue cue) {
                TextMetadataCue textMetadataCue = (TextMetadataCue) cue;
                if (textMetadataCue != null) {
                    WritableMap event = Arguments.createMap();
                    event.putInt("code", 0);
                    event.putString("msg", textMetadataCue.text);
                    ReactContext reactContext = (ReactContext) getContext();
                    reactContext.getJSModule(RCTEventEmitter.class)
                            .receiveEvent(
                                    getId(),
                                    "topOutputCue",
                                    event);
                }
            }

            @Override
            public void onDurationChanged(long l) {

            }

            @Override
            public void onStateChanged(@NonNull Player.State state) {
                ReactContext reactContext = (ReactContext) getContext();
                RCTEventEmitter emitter = reactContext.getJSModule(RCTEventEmitter.class);
                switch (state) {
                    case IDLE: {
                        WritableMap event = Arguments.createMap();
                        event.putInt("code", 0);
                        event.putString("msg", "IVSPlayerStateIdle");
                        emitter.receiveEvent(getId(), "topChangeState", event);
                        break;
                    }
                    case READY: {
                        WritableMap event = Arguments.createMap();
                        event.putInt("code", 1);
                        event.putString("msg", "IVSPlayerStateReady");
                        emitter.receiveEvent(getId(), "topChangeState", event);
                        if (RIVSPlayerView.this.autoPlay) {
                            Player player = playerView.getPlayer();
                            player.play();
                        }
                        break;
                    }
                    case BUFFERING: {
                        WritableMap event = Arguments.createMap();
                        event.putInt("code", 2);
                        event.putString("msg", "IVSPlayerStateBuffering");
                        emitter.receiveEvent(getId(), "topChangeState", event);
                        break;
                    }
                    case PLAYING: {
                        WritableMap event = Arguments.createMap();
                        event.putInt("code", 4);
                        event.putString("msg", "IVSPlayerStatePlaying");
                        emitter.receiveEvent(getId(), "topChangeState", event);
                        break;
                    }
                    case ENDED: {
                        WritableMap event = Arguments.createMap();
                        event.putInt("code", 5);
                        event.putString("msg", "IVSPlayerStateEnded");
                        emitter.receiveEvent(getId(), "topChangeState", event);
                        break;

                    }
                }
            }

            @Override
            public void onError(@NonNull PlayerException e) {

            }

            @Override
            public void onRebuffering() {

            }

            @Override
            public void onSeekCompleted(long l) {

            }

            @Override
            public void onVideoSizeChanged(int w, int h) {

                WritableMap event = Arguments.createMap();
                event.putInt("code", 0);
                event.putInt("width", w);
                event.putInt("height", h);
                ReactContext reactContext = (ReactContext) getContext();
                reactContext.getJSModule(RCTEventEmitter.class)
                        .receiveEvent(
                                getId(),
                                "topOutputCue",
                                event);
            }

            @Override
            public void onQualityChanged(@NonNull Quality quality) {

            }
        });
    }

    public void setUri(String uri) {
        Player player = playerView.getPlayer();
        player.load(Uri.parse(uri));
    }

    public void setAutoPlay(Boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public void setScaleMode(String mode) {
    }

    public void setLiveLowLatencyEnabled(Boolean autoPlay) {
        playerView.getPlayer().setLiveLowLatencyEnabled(autoPlay);
    }

    public void setMuted(Boolean muted) {
        playerView.getPlayer().setMuted(muted);
    }

    public void play() {
        playerView.getPlayer().play();
    }

    public void pause() {
        playerView.getPlayer().pause();
    }

}
