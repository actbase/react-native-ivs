//
//  RIVSPlayerView.h
//  react-native-ivs
//
//  Created by Suhan Moon on 2021/05/22.
//

#import <UIKit/UIKit.h>
#import <React/RCTView.h>
#import <AmazonIVSPlayer/AmazonIVSPlayer.h>

@interface RIVSPlayerView : UIView <IVSPlayerDelegate>

@property (strong, nonatomic) NSString *uri;
@property (nonatomic) BOOL autoPlay;
@property (strong, nonatomic) NSString *scaleMode;

@property (nonatomic) BOOL liveLowLatencyEnabled;
@property (nonatomic) BOOL muted;

@property (nonatomic, copy) RCTBubblingEventBlock onChangeState;
@property (nonatomic, copy) RCTBubblingEventBlock onChangeVideoSize;
@property (nonatomic, copy) RCTBubblingEventBlock onFailWithError;
@property (nonatomic, copy) RCTBubblingEventBlock onOutputCue;

- (int)play;
- (int)pause;


@end
