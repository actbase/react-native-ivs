import CameraView from './CameraView';
import PlayerView from './PlayerView';
import { NativeModules } from 'react-native';
module.exports = { CameraView, PlayerView, NodeMediaClient: NativeModules.NodeMediaClient };
