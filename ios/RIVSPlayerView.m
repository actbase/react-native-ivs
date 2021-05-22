//
//  RIVSPlayerView.m
//  react-native-ivs
//
//  Created by Suhan Moon on 2021/05/22.
//

#import "RIVSPlayerView.h"

@interface RIVSPlayerView()

@property (strong,nonatomic) IVSPlayerView *screen;

@end

@implementation RIVSPlayerView

- (id)init
{
    self = [super init];
    if (self) {
        _screen = [[IVSPlayerView alloc] init];
        [_screen setFrame: self.frame];
        [_screen setBackgroundColor: UIColor.blackColor];
        [_screen setPlayer:[[IVSPlayer alloc] init]];
        [_screen setVideoGravity: AVLayerVideoGravityResizeAspectFill];
        [self addSubview:_screen];

        NSNotificationCenter *defaultCenter = NSNotificationCenter.defaultCenter;
        [defaultCenter addObserver:self
                          selector:@selector(applicationDidEnterBackground:)
                              name:UIApplicationDidEnterBackgroundNotification
                            object:nil];
    }
    return self;
}

- (void)applicationDidEnterBackground:(NSNotification *)notification {
    [_screen.player pause];
}

- (void)setFrame:(CGRect)frame {
    [super setFrame:frame];
    [_screen setFrame: frame];
}

- (void)setBounds:(CGRect)bounds {
    [super setBounds:bounds];
    if (_screen != nil) {
        CGFloat screenScale = [[UIScreen mainScreen] scale];
        [_screen setFrame: CGRectMake(
                                       0,
                                       0,
                                       bounds.size.width,
                                       bounds.size.height
                                       )];
    }
}


- (void)setUri:(NSString *)uri {
    _uri = uri;
    [_screen.player load:[NSURL URLWithString:uri]];
    _screen.player.delegate = self;
}

- (void)player:(IVSPlayer *)player didFailWithError:(NSError *)error {
    _onFailWithError(@{@"code": [NSNumber numberWithInt:0], @"msg": error.description });
}

- (void)player:(IVSPlayer *)player didOutputCue:(__kindof IVSCue *)cue {
    IVSTextMetadataCue* textMetadataCue = (IVSTextMetadataCue*) cue;
    _onOutputCue(@{@"code": [NSNumber numberWithInt:0], @"msg": textMetadataCue.text});
}

- (void)player:(IVSPlayer *)player didChangeVideoSize:(CGSize)videoSize {
    _onChangeVideoSize(@{
        @"code": [NSNumber numberWithInt:0],
        @"width": [NSNumber numberWithFloat:videoSize.width],
        @"height": [NSNumber numberWithFloat:videoSize.height] });
}

- (void)player:(IVSPlayer *)player didChangeState:(IVSPlayerState)state {

//    /// Indicates that the status of the player is idle.
//    IVSPlayerStateIdle,
//    /// Indicates that the player is ready to play the selected source.
//    IVSPlayerStateReady,
//    /// Indicates that the player is buffering content.
//    IVSPlayerStateBuffering,
//    /// Indicates that the player is playing.
//    IVSPlayerStatePlaying,
//    /// Indicates that the player reached the end of the stream.
//    IVSPlayerStateEnded,
//    @property (nonatomic, copy) RCTBubblingEventBlock onChangeState;
//    @property (nonatomic, copy) RCTBubblingEventBlock onChangeVideoSize;
//    @property (nonatomic, copy) RCTBubblingEventBlock onFailWithError;
//    @property (nonatomic, copy) RCTBubblingEventBlock onOutputCue;

    if (state == IVSPlayerStateIdle) {
        _onChangeState(@{@"code": [NSNumber numberWithInt: 0], @"msg": @"IVSPlayerStateIdle"});
    }
    else if (state == IVSPlayerStateReady) {
        [_screen.player play];
        _onChangeState(@{@"code": [NSNumber numberWithInt: 1], @"msg": @"IVSPlayerStateReady"});
    }
    else if (state == IVSPlayerStateBuffering) {
        _onChangeState(@{@"code": [NSNumber numberWithInt: 2], @"msg": @"IVSPlayerStateBuffering"});
    }
    else if (state == IVSPlayerStatePlaying) {
        _onChangeState(@{@"code": [NSNumber numberWithInt: 3], @"msg": @"IVSPlayerStatePlaying"});
    }
    else if (state == IVSPlayerStateEnded) {
        _onChangeState(@{@"code": [NSNumber numberWithInt: 4], @"msg": @"IVSPlayerStateEnded"});
    }
}

- (void)setAutoPlay:(BOOL)autoPlay {
  _autoPlay = autoPlay;
}

- (void)setLiveLowLatencyEnabled:(BOOL)enable {
    [_screen.player setLiveLowLatencyEnabled: enable];
}

- (void)setMuted:(BOOL)muted {
  _muted = muted;
    [_screen.player setMuted:muted];
}

- (void)play {
  [_screen.player play];
}

- (void)pause {
  [_screen.player pause];
}

@end


