import React from 'react';
import {Text, View} from "react-native";

const CameraView = (props) => {
  const { source, ...viewProps} = props;
  return (
      <View {...viewProps}>
        <Text>video..</Text>
      </View>
  );
}
export default CameraView;
