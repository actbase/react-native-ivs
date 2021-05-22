//
//  RIVSPlayerView.m
//  react-native-ivs
//
//  Created by Suhan Moon on 2021/05/22.
//

#import "RIVSPlayerView.h"

@interface RIVSPlayerView()

@property (strong,nonatomic) IVSPlayer *player;

@end

@implementation RIVSPlayerView

- (id)init
{
    self = [super init];
    if (self) {
        _player = [[IVSPlayer alloc] init];
        [self setBackgroundColor:UIColor.blackColor];
        [self setBounds:CGRectMake(0, 0, 200, 200)];
        [self setPlayer: _player];
//    _np = [[NodePlayer alloc] initWithLicense:[RCTNodeMediaClient license]];
//    [_np setNodePlayerDelegate:self];
//    [_np setPlayerView:self];
//    _autoplay = NO;
//    _audioEnable = YES;
//    _inputUrl = nil;
//    _onChange = nil;
    }
    return self;
}

- (void)onEventCallback:(nonnull id)sender event:(int)event msg:(nonnull NSString*)msg {
}

- (void)setUri:(NSString *)uri {
    _uri = uri;
    [_player load:[NSURL URLWithString:uri]];
    _player.delegate = self;

    [self setPlayer: _player];
}

- (void)player:(IVSPlayer *)player didChangeState:(IVSPlayerState)state {
    if (state == IVSPlayerStateReady) {
        [player play];
    }
}

@end
