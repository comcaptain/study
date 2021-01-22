# References

- [Layouts in Flutter - Flutter](https://flutter.dev/docs/development/ui/layout)

# Widget

Similar to html, node in html node tree is corresponding to widget in flutter

# Layout Widget

- Widget that helps to control the position of visible widgets

- Each layout widge has either of the following properties:

  - child
  - children

- e.g. Use `Center` widget to centralize the text:

  ```dart
  Center(child: Text("Hello World"))
  ```


# [build](https://api.flutter.dev/flutter/widgets/StatelessWidget/build.html) method in Widget

```dart
Widget build (BuildContext context)
```

- This method exists in most widget. e.g. The App itself is a `StatelessWidget`, and you can override `build` method to render content of the app
- It's like the `render` method in react

# A material app

```dart
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      // Scaffold widget provides a default banner, background color, and has API for adding drawers, snack bars, and bottom sheets.
      home: Scaffold(
        appBar: AppBar(
          title: Text('Flutter layout demo'),
        ),
        body: Center(
          child: Text('Hello World'),
        ),
      ),
    );
  }
}
```

# Non-Material app

```dart
class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(color: Colors.white),
      child: Center(child: Text(
        "Hello World",
        textDirection: TextDirection.ltr,
        style: TextStyle(fontSize: 32, color: Colors.black87),
      )),
    );
  }
}
```

Then there is nothing (no banner, demo icon or anything) but `Hello World` text in the middle

# How to add image?

1. Put image files into `images` (or other names you like) in root directory

2. In `pubsepc.yaml`, under `flutter` section, add following `assets` section:

   ```yaml
     assets:
       - images/pic1.jpg
       - images/pic2.jpg
       - images/pic3.jpg
   ```

# `Row` and `Column`

They are both layout widgets. `MaterialApp` is used for all following examples because otherwise it behaves in a weird way sometimes. And the full code is like this:

```dart
void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Demo',
        home: Scaffold(
          appBar: AppBar(title: Text('Demo')),
          body: Center(child: buildTestWidget()),
        )
    );
  }
}
Widget buildTestWidget() {
  return xxxx;
}
```



## Alignment

There are two kinds of axis: Main Axis and Cross Axis

![Diagram showing the main axis and cross axis for a row](C:\git\study\images\row-diagram-ad51795e19e3e1d412815b287c9caa694ad357892e3ab8b3ef1da0c4c6e011db.png)![Diagram showing the main axis and cross axis for a column](C:\git\study\images\column-diagram-4e2ce8e33c32a09d090280fb7b2925baaf58f6de7876a551c207ab904e2fafc6.png)

You can use property `mainAxisAlignment` and `crossAxisAlignment` in `Row`/`Column` constructor to set them.

e.g. There are 3 images in a row and you would like the spaces between images are assigned evenly:

```dart
Row(
    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
    children: [
        Image.asset('images/pic1.jpg'),
        Image.asset('images/pic2.jpg'),
        Image.asset('images/pic3.jpg'),
    ],
)
```

## Expanded widget

- If image is too big, and device screen cannot display the full image, you can use `Expanded` widget to make the image size fit the row/column
- And you can use flex property to make one image occupy more space than others

```dart
Row(
    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
    children: [
        Expanded(child: Image.asset("images/pic1.jpg")),
        Expanded(flex: 2, child: Image.asset("images/pic2.jpg")),
        Expanded(child: Image.asset("images/pic3.jpg")),
    ],
);
```

![Row of 3 images with the middle image twice as wide as the others](C:\git\study\images\row-expanded-visual-fa9a01df7b96ba5cb33162b91369658fea86554139953e3cc0357de83281133d.png)



## Compact row/column

By default, a row or column occupies as much space along its main axis as possible, but if you want children to be compact, you can set `MainAxisSize.min` to `mainAxisSize` property:

```dart
Row(
  mainAxisSize: MainAxisSize.min,
  children: [
    Icon(Icons.star, color: Colors.green[500]),
    Icon(Icons.star, color: Colors.green[500]),
    Icon(Icons.star, color: Colors.green[500]),
    Icon(Icons.star, color: Colors.black),
    Icon(Icons.star, color: Colors.black),
  ],
)
```

![Row of 5 stars, packed together in the middle of the row](C:\git\study\images\packed-5583166ea8f018a2c3c829cb075eb63b465cf859e74ae1d7792d2c50813e32aa.png)

# Inherited text style

```dart
final descTextStyle = TextStyle(
    color: Colors.black,
    fontWeight: FontWeight.w800,
    fontFamily: 'Roboto',
    letterSpacing: 0.5,
    fontSize: 18,
    height: 2
);
return DefaultTextStyle.merge(
    style: descTextStyle,
    // The complex widget itself and its children would all inherit descTextStyle
    child: <a-complex-widget>
)
```

# Standard vs Material Widgets

- Standard widgets from the [widgets library](https://api.flutter.dev/flutter/widgets/widgets-library.html)
- Material widgets from the [Material library](https://api.flutter.dev/flutter/material/material-library.html)
- Any app can use the widgets library but only Material apps can use the Material Components library

Standard wid

# Standard Widget

## Container Widget

- It's similar to `div` in html. With it, you can:
  - Add padding, margins, borders
  - Change background color or image
  - Contains a single child widget, but that child can be a Row, Column, or even the root of a widget tree

```dart
Container(
    margin: EdgeInsets.fromLTRB(2, 50, 2, 0),
    padding: EdgeInsets.all(20),
    decoration: BoxDecoration(
    	// Background color
        color: Colors.black26,
        border: Border.all(width: 10, color: Colors.black38),
        borderRadius: const BorderRadius.all(const Radius.circular(8)),
    ),
    child: xxx
);
```

![Diagram showing: margin, border, padding, and content](C:\git\study\images\margin-padding-border-9616dd0d7af45b95e6fcface25cd933b6b4a0fda51c1ab1bb9287bc8ed92c356.png)

## [GridView](https://flutter.dev/docs/development/ui/layout#gridview)

- Creates a grid
- It would automatically scroll if content overflows
- Main direction is scroll direction
- There are two built-in constructors:
  - `GridView.count`: Specify number of columns
  - `GridView.extent`: Specify maximum pixel with of a tile

```dart
Widget _buildGrid() => GridView.extent(
    maxCrossAxisExtent: 150,
    padding: const EdgeInsets.all(4),
    mainAxisSpacing: 4,
    crossAxisSpacing: 4,
    children: _buildGridTileList(30));

// The images are saved with names pic0.jpg, pic1.jpg...pic29.jpg.
// The List.generate() constructor allows an easy way to create
// a list when objects have a predictable naming pattern.
List<Container> _buildGridTileList(int count) => List.generate(
    count, (i) => Container(child: Image.asset('images/pic$i.jpg')));
```

## ListView

- A speicialized Column widget
- Can be laied out horizontally or vertically
- Scrollable
- Less configurable than Column but easier to use

![ListView containing movie theaters and restaurants](C:\git\study\images\listview-34918a738b5d18af88d16a8773e563a3b97f2ffd2710304c5543f8541cbd1345.png)



## Stack

- Make one widget be able to overlap another
- Its children property lists all overlapped components. Latter ones would cover earlier ones
- You can choose to clip children that exceed the render box

![Circular avatar image with a label](C:\git\study\images\stack-8b743fe7d0f5fe7e58e3a257572f4b48b1552194f7e3f3ffa15a87edaf99a749.png)

```dart
Widget _buildStack() => Stack(
    alignment: const Alignment(0.6, 0.6),
    children: [
      CircleAvatar(
        backgroundImage: AssetImage('images/pic.jpg'),
        radius: 100,
      ),
      Container(
        decoration: BoxDecoration(
          color: Colors.black45,
        ),
        child: Text(
          'Mia B',
          style: TextStyle(
            fontSize: 20,
            fontWeight: FontWeight.bold,
            color: Colors.white,
          ),
        ),
      ),
    ],
  );

```

# Material Widget

## Card widget

Create a material design style card-like thing:

- Drop shadow around the widget, rounded corners
- Not scrollable
- Make it looks as if a card with thickness

![Card containing 3 ListTiles](C:\git\study\flutter\images\card-3db18421d33c96f34f8d19889a5f28c61287063a62c9770f311fd56aeff0b364.png)![Card containing an image, text and buttons](C:\git\study\flutter\images\card-flutter-gallery-184963eb23d8824ef3df612a6b40205ed113e7c00da98fa22228cc6e6f619d88.png)

## ListTile Widget

- A specialized row  that contains up to 3 lines of text and optional icons
- Less configurable than Row, but easier to use

![Card containing 3 ListTiles](C:\git\study\flutter\images\card-3db18421d33c96f34f8d19889a5f28c61287063a62c9770f311fd56aeff0b364.png)

Each line is a `ListTile`