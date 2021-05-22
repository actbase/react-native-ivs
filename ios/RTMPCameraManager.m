//
//  RTMPCameraManager.m
//  react-native-ivs
//
//  Created by Suhan Moon on 2021/05/22.
//

#import <Foundation/Foundation.h>

#import <React/RCTViewManager.h>
#import <React/RCTBridge.h>
#import <React/RCTUIManager.h>

#import "RTMPCameraView.h"


@interface RTMPCameraManager : RCTViewManager
@end

@implementation RTMPCameraManager
RCT_EXPORT_MODULE(RTMPCamera)

- (UIView *)view {
  return [[RTMPCameraView alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(frontCamera, BOOL)
RCT_EXPORT_VIEW_PROPERTY(uri, NSString)
RCT_EXPORT_VIEW_PROPERTY(zoomScale, int)
RCT_EXPORT_VIEW_PROPERTY(onChangeState, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onFailWithError, RCTBubblingEventBlock)


RCT_EXPORT_METHOD(start:(nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock: ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RTMPCameraView *> *viewRegistry){
        RTMPCameraView *view = viewRegistry[reactTag];
        [view start];
    }];
}

RCT_EXPORT_METHOD(stop:(nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock: ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RTMPCameraView *> *viewRegistry){
        RTMPCameraView *view = viewRegistry[reactTag];
        [view stop];
    }];
}


@end

