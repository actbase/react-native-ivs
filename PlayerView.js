import React, {useCallback} from 'react';
import {requireNativeComponent, ViewPropTypes,} from 'react-native';

const RCTPlayerView = requireNativeComponent('RIVSPlayer');

const PlayerView = (props) => {
  const { onChangeState, onChangeVideoSize, onFailWithError,onOutputCue, ...viewProps } = props;

  const handleChangeState = useCallback(({ nativeEvent }) => {
    onChangeState?.(nativeEvent?.code, nativeEvent?.msg);
  }, []);

  const handleChangeVideoSize = useCallback(({ nativeEvent }) => {
    onChangeVideoSize?.(nativeEvent?.width, nativeEvent?.height);
  }, []);

  const handleFailWithError = useCallback(({ nativeEvent }) => {
    onFailWithError?.(nativeEvent?.code);
  }, []);

  const handleOutputCue = useCallback(({ nativeEvent }) => {
    onOutputCue?.(nativeEvent?.code);
  }, []);

  return <RCTPlayerView {...props} onChangeState={handleChangeState} onChangeVideoSize={handleChangeVideoSize} onFailWithError={handleFailWithError} onOutputCue={handleOutputCue} />;
};

PlayerView.propTypes = ViewPropTypes;
export default PlayerView;
