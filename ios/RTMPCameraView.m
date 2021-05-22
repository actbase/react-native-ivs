//
//  RTMPCameraView.m
//  react-native-ivs
//
//  Created by Suhan Moon on 2021/05/22.
//

#import "RTMPCameraView.h"

inline static NSString *formatedSpeed(float bytes, float elapsed_milli) {
    if (elapsed_milli <= 0) {
        return @"N/A";
    }

    if (bytes <= 0) {
        return @"0 KB/s";
    }

    float bytes_per_sec = ((float)bytes) * 1000.f /  elapsed_milli;
    if (bytes_per_sec >= 1000 * 1000) {
        return [NSString stringWithFormat:@"%.2f MB/s", ((float)bytes_per_sec) / 1000 / 1000];
    } else if (bytes_per_sec >= 1000) {
        return [NSString stringWithFormat:@"%.1f KB/s", ((float)bytes_per_sec) / 1000];
    } else {
        return [NSString stringWithFormat:@"%ld B/s", (long)bytes_per_sec];
    }
}


@implementation RTMPCameraView

- (instancetype)initWithFrame:(CGRect)frame {
    if (self = [super initWithFrame:frame]) {
        self.backgroundColor = [UIColor clearColor];
        [self addSubview:self.containerView];
    }
    return self;
}

- (void)setUri:(NSString *)uri {
    _uri = uri;
}

- (void)setFrontCamera:(BOOL)frontCamera {
    _frontCamera = frontCamera;
    if (!_session) {
        [self.session setRunning:YES];
    } else {
        _session.captureDevicePosition = frontCamera ? AVCaptureDevicePositionFront : AVCaptureDevicePositionBack;
    }
}

- (void)setZoomScale:(NSInteger)zoomScale {
    _zoomScale = zoomScale;
    _session.zoomScale = zoomScale;
}

- (void)start {
    LFLiveStreamInfo *stream = [LFLiveStreamInfo new];
    stream.url = _uri;
    [self.session startLive:stream];
}

- (void)stop {
    [self.session stopLive];
}

#pragma mark -- LFStreamingSessionDelegate
/** live status changed will callback */
- (void)liveSession:(nullable LFLiveSession *)session liveStateDidChange:(LFLiveState)state {
    switch (state) {
    case LFLiveReady:
        _onChangeState(@{@"code": [NSNumber numberWithInt: 0], @"msg": @"RTMPCameraReady"});
        break;
    case LFLivePending:
        _onChangeState(@{@"code": [NSNumber numberWithInt: 1], @"msg": @"RTMPCameraPending"});
        break;
    case LFLiveStart:
        _onChangeState(@{@"code": [NSNumber numberWithInt: 2], @"msg": @"RTMPCameraStart"});
        break;
    case LFLiveStop:
        _onChangeState(@{@"code": [NSNumber numberWithInt: 3], @"msg": @"RTMPCameraStop"});
        break;
    case LFLiveError:
        _onChangeState(@{@"code": [NSNumber numberWithInt: 4], @"msg": @"RTMPCameraError"});
        break;
    default:
        break;
    }
}

/** callback socket errorcode */
- (void)liveSession:(nullable LFLiveSession *)session errorCode:(LFLiveSocketErrorCode)errorCode {
    _onFailWithError(@{@"code": [NSNumber numberWithInt:errorCode], @"msg": @"" });
}

#pragma mark -- Getter Setter
- (LFLiveSession *)session {
    if (!_session) {
        LFLiveAudioConfiguration *ac = [LFLiveAudioConfiguration defaultConfigurationForQuality:LFLiveAudioQuality_Medium];

        LFLiveVideoConfiguration *vc = [LFLiveVideoConfiguration
                                        defaultConfigurationForQuality: LFLiveVideoQuality_High3
                                        outputImageOrientation: UIDeviceOrientationPortrait];

        _session = [[LFLiveSession alloc] initWithAudioConfiguration:ac videoConfiguration:vc];
        _session.beautyLevel = 0.0;
        _session.beautyFace = NO;
        _session.delegate = self;
        _session.showDebugInfo = NO;
        _session.preView = self;
        _session.captureDevicePosition = _frontCamera == YES ? AVCaptureDevicePositionFront : AVCaptureDevicePositionBack;
        _session.zoomScale = _zoomScale ? _zoomScale : 1;
    }
    return _session;
}

- (UIView *)containerView {
    if (!_containerView) {
        _containerView = [UIView new];
        _containerView.frame = self.bounds;
        _containerView.backgroundColor = [UIColor clearColor];
        _containerView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
    }
    return _containerView;
}

@end
