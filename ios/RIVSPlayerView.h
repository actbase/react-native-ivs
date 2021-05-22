//
//  RIVSPlayerView.h
//  react-native-ivs
//
//  Created by Suhan Moon on 2021/05/22.
//

#import <UIKit/UIKit.h>
#import <React/RCTView.h>
#import <AmazonIVSPlayer/AmazonIVSPlayer.h>

@interface RIVSPlayerView : UIView

@property (strong, nonatomic) NSString *uri;
@property (nonatomic) BOOL autoPlay;
@property (strong, nonatomic) NSString *scaleMode;

@property (nonatomic) BOOL liveLowLatencyEnabled;

- (int)play;
- (int)pause;


@end
