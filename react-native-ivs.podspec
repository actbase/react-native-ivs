require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name           = 'react-native-ivs'
  s.version        = package['version']
  s.summary        = package['description']
  s.description    = package['description']
  s.author         = package['author']
  s.homepage       = 'https://github.com/actbase/react-native-ivs'
  s.license        = package['license']

  s.source         = { :git => "https://github.com/actbase/react-native-ivs.git", :tag => "master" }
  s.source_files   = "ios/*.{h,m}"
  s.platforms      = { :ios => "10.0" }

#  s.frameworks     = 'MediaPlayer'
  s.vendored_frameworks = "ios/AmazonIVSPlayer.xcframework"

  s.dependency "React"
  s.dependency "LFLiveKit"

  s.static_framework = true
end
