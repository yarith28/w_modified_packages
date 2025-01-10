import 'dart:io';

import 'customplugin_platform_interface.dart';

class CustomPlugin {
  Future<List<String>> getListOfMockApps() async {
    var mockApps = <String>[];
    if (Platform.isAndroid) {
      mockApps =
          await CustomPluginPlatform.instance.getListOfMockApps();
    }
    return mockApps;
  }
}
