import React from 'react';
import {
  Text,
  View,
  ViewPropTypes,
  requireNativeComponent,
} from 'react-native';

const RCTCameraView = requireNativeComponent('RTMPCamera');

const CameraView = (props) => {
  const { source, ...viewProps } = props;
  return <RCTCameraView {...props} />;
};

CameraView.propTypes = ViewPropTypes;
export default CameraView;
