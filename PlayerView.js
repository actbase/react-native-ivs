import React, {useCallback} from 'react';
import {
  Text,
  View,
  ViewPropTypes,
  requireNativeComponent,
} from 'react-native';

const RCTPlayerView = requireNativeComponent('RIVSPlayer');

const PlayerView = (props) => {
  const { onChangeState, ...viewProps } = props;

  const handleChangeState = useCallback(({ nativeEvent }) => {
    onChangeState?.(nativeEvent?.code, nativeEvent?.msg);
  }, []);

  return <RCTPlayerView {...props} onChangeState={handleChangeState} />;
};

PlayerView.propTypes = ViewPropTypes;
export default PlayerView;
