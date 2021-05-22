//
//  PlayerView.m
//  react-native-ivs
//
//  Created by Suhan Moon on 2021/05/22.
//

#import "PlayerView.h"

@interface PlayerView()

@property (strong,nonatomic) IVSPlayer *player;

@end

@implementation PlayerView

- (id)init
{
    self = [super init];
    if (self) {
        _player = [[IVSPlayer alloc] init];
        self.player = _player;
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

@end
