import React, { useCallback, useMemo, useRef } from 'react';
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  ViewPropTypes,
} from 'react-native';

const RCTCameraView = requireNativeComponent('RTMPCamera');

const CameraView = React.forwardRef((props, ref) => {
  const camera = useRef();
  const refObject = useMemo(() => {
    return {
      start: (...args) => {
        UIManager.dispatchViewManagerCommand(
            findNodeHandle(camera.current),
            UIManager.getViewManagerConfig('RTMPCamera').Commands.start,
            args,
        );
      },
      stop: (...args) => {
        UIManager.dispatchViewManagerCommand(
            findNodeHandle(camera.current),
            UIManager.getViewManagerConfig('RTMPCamera').Commands.stop,
            args,
        );
      },
    };
  }, []);
  ref.current = refObject;
  const { onChangeState, ...viewProps } = props;

  const handleChangeState = useCallback(
      ({ nativeEvent }) => {
        onChangeState?.(nativeEvent?.code, nativeEvent?.msg);
      },
      [onChangeState],
  );

  return (
      <RCTCameraView ref={camera} {...props} onChangeState={handleChangeState} />
  );
});

CameraView.propTypes = ViewPropTypes;
export default CameraView;
