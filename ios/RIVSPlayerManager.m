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
RCT_EXPORT_VIEW_PROPERTY(autoPlay, BOOL)
RCT_EXPORT_VIEW_PROPERTY(scaleMode, NSString)
RCT_EXPORT_VIEW_PROPERTY(liveLowLatencyEnabled, BOOL)
RCT_EXPORT_VIEW_PROPERTY(muted, BOOL)
RCT_EXPORT_VIEW_PROPERTY(onChangeState, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onChangeVideoSize, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onFailWithError, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onOutputCue, RCTBubblingEventBlock)


RCT_EXPORT_METHOD(play:(nonnull NSNumber *)reactTag)
{

  [self.bridge.uiManager addUIBlock:
   ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RIVSPlayerView *> *viewRegistry){
      RIVSPlayerView *view = viewRegistry[reactTag];
     [view play];
   }];
}

RCT_EXPORT_METHOD(pause:(nonnull NSNumber *)reactTag)
{

  [self.bridge.uiManager addUIBlock:
   ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RIVSPlayerView *> *viewRegistry){
      RIVSPlayerView *view = viewRegistry[reactTag];
     [view pause];
   }];
}

@end

