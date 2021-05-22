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
        [_screen setBounds: self.bounds];
        [_screen setBackgroundColor: UIColor.blackColor];
        [_screen setPlayer:[[IVSPlayer alloc] init]];
        [_screen setVideoGravity: AVLayerVideoGravityResizeAspectFill];
        [self addSubview:_screen];
    }
    return self;
}

- (void)setBounds:(CGRect)bounds {
    [super setBounds:bounds];
    if (_screen != nil) {
        CGFloat screenScale = [[UIScreen mainScreen] scale];
        [_screen setBounds: CGRectMake(
                                       bounds.origin.x * screenScale,
                                       bounds.origin.y * screenScale,
                                       bounds.size.width * screenScale,
                                       bounds.size.height * screenScale
                                       )];
    }
}

- (void)onEventCallback:(nonnull id)sender event:(int)event msg:(nonnull NSString*)msg {
}

- (void)setUri:(NSString *)uri {
    _uri = uri;
    [_screen.player load:[NSURL URLWithString:uri]];
    _screen.player.delegate = self;
}

- (void)player:(IVSPlayer *)player didChangeState:(IVSPlayerState)state {
    if (state == IVSPlayerStateReady) {
        [_screen.player play];
    }
}

- (void)setAutoPlay:(BOOL)autoPlay {
  _autoPlay = autoPlay;
}


- (void)play {
  [_screen.player play];
}

- (void)pause {
  [_screen.player pause];
}

- (void)setLiveLowLatencyEnabled:(BOOL)enable {
    [_screen.player setLiveLowLatencyEnabled: enable];
}

@end
