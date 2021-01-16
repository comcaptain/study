```dart
import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      home: Scaffold(
        appBar: AppBar(title: Text("Flutter layout demo")),
        body: Center(child: Text("Hello World"))
      )
    );
  }
}
```

- In main method, pass a `Widget` instance to `runApp` method to start the app
- App itself is a widget. And its `build` method is like the `render` method in react, is used to render content of the app
- `MaterialApp` widget would render app in material design style
- `Scaffold` widget provides a default banner, background color....

