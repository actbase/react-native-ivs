//
//  RTMPCameraView.m
//  react-native-ivs
//
//  Created by Suhan Moon on 2021/05/22.
//

#import "RTMPCameraView.h"
#import "LFLiveKit.h"

@interface RTMPCameraView()


@end

@implementation RTMPCameraView

- (id)init
{
    self = [super init];
    if (self) {
        [self requestAccessForVideo];
        [self requestAccessForAudio];
    }
    return self;
}

#pragma mark -- Public Method
- (void)requestAccessForVideo {
    __weak typeof(self) _self = self;
    AVAuthorizationStatus status = [AVCaptureDevice authorizationStatusForMediaType:AVMediaTypeVideo];
    switch (status) {
    case AVAuthorizationStatusNotDetermined: {
        // 许可对话没有出现，发起授权许可
        [AVCaptureDevice requestAccessForMediaType:AVMediaTypeVideo completionHandler:^(BOOL granted) {
                if (granted) {
                    dispatch_async(dispatch_get_main_queue(), ^{
                        [_self.session setRunning:YES];
                    });
                }
            }];
        break;
    }
    case AVAuthorizationStatusAuthorized: {
        // 已经开启授权，可继续
        dispatch_async(dispatch_get_main_queue(), ^{
            [_self.session setRunning:YES];
        });
        break;
    }
    case AVAuthorizationStatusDenied:
    case AVAuthorizationStatusRestricted:
        // 用户明确地拒绝授权，或者相机设备无法访问

        break;
    default:
        break;
    }
}

- (void)requestAccessForAudio {
    AVAuthorizationStatus status = [AVCaptureDevice authorizationStatusForMediaType:AVMediaTypeAudio];
    switch (status) {
    case AVAuthorizationStatusNotDetermined: {
        [AVCaptureDevice requestAccessForMediaType:AVMediaTypeAudio completionHandler:^(BOOL granted) {
            }];
        break;
    }
    case AVAuthorizationStatusAuthorized: {
        break;
    }
    case AVAuthorizationStatusDenied:
    case AVAuthorizationStatusRestricted:
        break;
    default:
        break;
    }
}

@end
