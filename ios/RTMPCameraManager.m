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


@interface RIVSPlayerManager : RCTViewManager
@end

@implementation RIVSPlayerManager
RCT_EXPORT_MODULE(RTMPCamera)

- (UIView *)view {
  return [[RTMPCameraView alloc] init];
}

@end

