import React from 'react';
import {Text, View, ViewPropTypes} from "react-native";

const PlayerView = (props) => {
  const { source, ...viewProps} = props;
  return (
      <View {...viewProps}>
        <Text>video..</Text>
      </View>
  );
}

PlayerView.propTypes = ViewPropTypes;
export default PlayerView;
