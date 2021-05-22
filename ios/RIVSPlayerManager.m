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


@interface PlayerManager : RCTViewManager
@end

@implementation PlayerManager
RCT_EXPORT_MODULE()

- (UIView *)view {
  return [[RIVSPlayerView alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(uri, NSString)

@end

