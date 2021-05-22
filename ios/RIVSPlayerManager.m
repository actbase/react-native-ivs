//
//  RIVSPlayerManager.m
//  react-native-ivs
//
//  Created by Suhan Moon on 2021/05/22.
//

#import <Foundation/Foundation.h>

#import <React/RCTViewManager.h>
#import <React/RCTBridge.h>
#import <React/RCTUIManager.h>

#import "RIVSPlayerView.h"


@interface RIVSPlayerManager : RCTViewManager
@end

@implementation RIVSPlayerManager
RCT_EXPORT_MODULE(RIVSPlayer)

- (UIView *)view {
  return [[RIVSPlayerView alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(uri, NSString)
RCT_EXPORT_VIEW_PROPERTY(onChangeState, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onChangeVideoSize, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onFailWithError, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onOutputCue, RCTBubblingEventBlock)

@end

