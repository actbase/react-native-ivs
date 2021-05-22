//
//  RTMPCameraView.h
//  react-native-ivs
//
//  Created by Suhan Moon on 2021/05/22.
//

#import <UIKit/UIKit.h>
#import <React/RCTView.h>

#import "LFLiveKit.h"

@interface RTMPCameraView : UIView <LFLiveSessionDelegate>

@property (strong, nonatomic) NSString *uri;
@property (nonatomic) BOOL frontCamera;
@property (nonatomic) NSInteger zoomScale;

@property (nonatomic, copy) RCTBubblingEventBlock onChangeState;
@property (nonatomic, copy) RCTBubblingEventBlock onFailWithError;

@property (nonatomic, strong) UIView *containerView;
@property (nonatomic, strong) LFLiveSession *session;

- (void)start;
- (void)stop;


@end
