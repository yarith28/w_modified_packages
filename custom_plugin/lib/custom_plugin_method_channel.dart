import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'custom_plugin_platform_interface.dart';

/// An implementation of [CustomPluginPlatform] that uses method channels.
class MethodChannelCustomPlugin extends CustomPluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel =
      const MethodChannel('com.anondev.customplugin');

  @override
  Future<List<String>> getListOfMockApps() async {
    if (!Platform.isAndroid) return [];
    var castList = <String>[];
    try {
      var result = await methodChannel.invokeMethod("getListOfMockApps");
      castList = result.cast<String>();
    } catch (e) {
      if (kDebugMode) print(e);
    }
    return castList;
  }
}
