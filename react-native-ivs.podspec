require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name           = 'react-native-ivs'
  s.version        = package['version']
  s.summary        = package['description']
  s.description    = package['description']
  s.author         = package['author']
  s.homepage       = 'https://github.com/actbase/react-native-ivs'

  s.source         = { :git => "https://github.com/actbase/react-native-ivs.git", :tag => "master" }
  s.source_files   = "ios/*.{h,m,swift}"

  s.ios.deployment_target = "10.0"

#   s.subspec "RCTNodeMediaClient" do |ss|
#     ss.source_files  = "ios/RCTNodeMediaClient/*.{h,m}"
#     s.static_framework = true
#   end

  s.dependency "React"
  s.dependency "NodeMediaClient", '2.9.5'
  s.dependency "AmazonIVSPlayer"

end
