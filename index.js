import CameraView from './CameraModule';
import PlayerView from './PlayerModule';
import { NativeModules } from 'react-native';
module.exports = { CameraView, PlayerView, NodeMediaClient: NativeModules.NodeMediaClient };
