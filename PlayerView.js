import React from 'react';
import {
  Text,
  View,
  ViewPropTypes,
  requireNativeComponent,
} from 'react-native';

const RCTPlayerView = requireNativeComponent('RIVSPlayer');

const PlayerView = (props) => {
  const { source, ...viewProps } = props;
  return <RCTPlayerView {...props} />;
};

PlayerView.propTypes = ViewPropTypes;
export default PlayerView;
