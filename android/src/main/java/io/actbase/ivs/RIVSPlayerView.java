package io.actbase.ivs;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amazonaws.ivs.player.Cue;
import com.amazonaws.ivs.player.Player;
import com.amazonaws.ivs.player.Player.Factory;
import com.amazonaws.ivs.player.Player.Listener;
import com.amazonaws.ivs.player.PlayerException;
import com.amazonaws.ivs.player.Quality;
import com.amazonaws.ivs.player.Size;
import com.amazonaws.ivs.player.TextMetadataCue;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class RIVSPlayerView extends FrameLayout implements Callback, OnAttachStateChangeListener,
        OnLayoutChangeListener {

    private SurfaceView surfaceView;
    private Player player;
    private Surface surface;
    private ProgressBar progressView;
    private Uri mediaUri;
    private int videoWidth;
    private int videoHeight;
    private Listener playerListener;

    private boolean autoPlay = true;

    public RIVSPlayerView(@NonNull Context context) {
        super(context);
        this.playerListener = new PlayerListener();
        this.initialize();
    }

    public RIVSPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.playerListener = new PlayerListener();
        this.initialize();
    }

    public RIVSPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.playerListener = new PlayerListener();
        this.initialize();
    }

    private void initialize() {

        this.surfaceView = new SurfaceView(this.getContext());
        this.surfaceView.setLayoutParams(new LayoutParams(-1, -1, 17));
        this.surfaceView.getHolder().addCallback(this);
        this.addView(this.surfaceView);

        this.progressView = new ProgressBar(this.getContext());
        this.progressView.setIndeterminate(true);
        this.progressView.setVisibility(View.INVISIBLE);
        this.addView(this.progressView, new LayoutParams(-2, -2, 17));

        this.player = Factory.create(this.getContext());
        this.player.addListener(this.playerListener);
        this.addOnLayoutChangeListener(this);
        this.addOnAttachStateChangeListener(this);
    }

    public void setUri(String uri) {
        this.mediaUri = Uri.parse(uri);
        if (this.player != null) {
            this.player.load(Uri.parse(uri));
        }
    }

    public void setAutoPlay(Boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public void setScaleMode(String mode) {
    }

    public void setLiveLowLatencyEnabled(Boolean autoPlay) {
        this.player.setLiveLowLatencyEnabled(autoPlay);
    }

    public void setMuted(Boolean muted) {
        this.player.setMuted(muted);
    }

    public void play() {
        this.player.play();
    }

    public void pause() {
        this.player.pause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.surface = surfaceHolder.getSurface();
        if (this.player != null) {
            this.player.setSurface(this.surface);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.surface = null;
        if (this.player != null) {
            this.player.setSurface((Surface) null);
        }
    }

    @Override
    public void onViewAttachedToWindow(View view) {
        if (this.mediaUri != null) {
            this.player.load(this.mediaUri);
            this.player.play();
        }

        if (this.surface != null) {
            this.player.setSurface(this.surface);
        }
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        this.progressView.setVisibility(View.INVISIBLE);
    }

    private Size scaleTo(int width, int height) {
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        float ratio = (float) height / (float) width;
        int scaledWidth;
        int scaledHeight;
        if ((float) viewHeight > (float) viewWidth * ratio) {
            scaledWidth = viewWidth;
            scaledHeight = (int) ((float) viewWidth * ratio);
        } else {
            scaledWidth = (int) ((float) viewHeight / ratio);
            scaledHeight = viewHeight;
        }

        return new Size(300, 300);
    }

    private void scaleToLayout(int width, int height) {
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        float viewRatio = (float) viewHeight / (float) viewWidth;
        float videoRatio = (float) height / (float) width;

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

        surfaceView.layout(left, top, right, bottom);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
                               int oldTop, int oldRight, int oldBottom) {
        videoResize();
    }

    private void videoResize(int w, int h) {
        this.videoWidth = w;
        this.videoHeight = h;
        this.videoResize();
    }

    private void videoResize() {
        if (videoWidth <= 0 || videoHeight <= 0) return;
//        this.surfaceView.post(new Runnable() {
//            public void run() {
//                Size scaled = scaleTo(videoWidth, videoHeight);
//                ViewGroup.LayoutParams layoutParams = surfaceView.getLayoutParams();
//                layoutParams.width = scaled.width;
//                layoutParams.height = scaled.height;
//                surfaceView.setLayoutParams(layoutParams);
//            }
//        });

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                scaleToLayout(videoWidth, videoHeight);
                getViewTreeObserver().dispatchOnGlobalLayout();
            }
        });
    }

    class PlayerListener extends Listener {

        @Override
        public void onCue(@NonNull Cue cue) {
            if (cue instanceof TextMetadataCue) {
                TextMetadataCue textMetadataCue = (TextMetadataCue) cue;

                WritableMap event = Arguments.createMap();
                event.putInt("code", 0);
                event.putString("msg", textMetadataCue.text);

                ReactContext reactContext = (ReactContext) getContext();
                RCTEventEmitter emitter = reactContext.getJSModule(RCTEventEmitter.class);
                emitter.receiveEvent(getId(), "topOutputCue", event);
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
            videoResize(w, h);
            WritableMap event = Arguments.createMap();
            event.putInt("code", 0);
            event.putInt("width", w);
            event.putInt("height", h);
            ReactContext reactContext = (ReactContext) getContext();
            reactContext.getJSModule(RCTEventEmitter.class)
                    .receiveEvent(
                            getId(),
                            "topChangeVideoSize",
                            event);
        }

        @Override
        public void onQualityChanged(@NonNull Quality quality) {

        }

    }
}
