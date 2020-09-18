# [Youtube: Why Flutter uses dart?](https://youtu.be/5F-6n_2XWR8)

Flutter is a framework that develops apps for IOS, Android and Web.

Flutter has following goals:

- Write once, run everywhere (IOS, Android and Web)\
- Fast development experience

Dart is chosen because:

- Write once, run everywhere
- It compiles to platform's native code, so it's fast
- It has a hot-reload feature. Code change can be applied to your app almost immediately without re-compiling, restarting...
- Easy to learn for java/swift/js developers
- Optimized for writing UI

# Environment Setup

[Set up IntelliJ and Dart SDK](https://dart.dev/tools/jetbrains-plugin)

# [Intro to Dart for Java Developers](https://codelabs.developers.google.com/codelabs/from-java-to-dart)

## Define a simple class

```dart
class Bicycle {
  int cadence;
  int speed;
  int gear;
}
```

Nothing is marked as `public`, `private` or `protected`. Because dart does not support those modifiers and all identifiers are public by default

## `main` method

```dart
void main(List<String> args) {
  print(args.length);
  if (args.length > 0) print(args[0]);
}
```

- Unlike java, method & variables can be defined outside a class. e.g. this main method is defined outside a class
- Like java, main accepts a list of string as program arguments
- But unlike java, you can remove `List<String> args` from `main` method if there is program arguments

## Class constructor

```java
Bicycle(this.cadence, this.speed, this.gear);
```

- No-body constructor like above is valid, but remember to add `;` to the end

- This is convenient syntax sugar for setting properties, it's identical to:

  ```dart
  Bicycle(int cadence, int speed, int gear) {
    this.cadence = cadence;
    this.speed = speed;
    this.gear = gear;
  }
  ```

## Create instance

```java
void main(List<String> args) {
  var bike = new Bicycle(2, 0, 1);
  bike = new Bicycle(3, 4, 5);
  print(bike);
  final bike2 = new Bicycle(5, 6, 7);
  print(bike2);
}
```

- It's same as java, `new` is used to create a instance
- Similar to java 11, you can use `var` as type
- If value would not change, `final` can be replaced with `var`

## `toString` method

```dart
class Bicycle {
  int cadence;
  int speed;
  int gear;

  Bicycle(this.cadence, this.speed, this.gear);

  @override
  String toString() => 'Bicycle: $speed mph';
}

void main() {
  print(new Bicycle(2, 3, 1));
}
```

- Same as java, every dart class has a `toString` method, can you can override like above

- Same as java, you can omit the `@override`

- Either `'` or `"` can be used for string, and they have no difference

- Similar to js, you can use `${expression}` in a string. If it's a plain variable, you can simplify it a bit: `$variableName`

- The method above uses lambda, but you can also define it in plain old way:

  ```dart
  @override
  String toString() {
    return "Bicycle: $speed mph";
  }
  ```

## Library private

**bicycle.dart**

```dart
class Bicycle {
  int cadence;
  int _speed;
  int gear;
  Bicycle(this.cadence, this._speed, this.gear);
}

main() {
  // works well
  print(new Bicycle(2, 3, 4)._speed);
}
```

**test.dart**

```dart
import 'bicycle.dart';

void main() {
  // compiler error
  print(new Bicycle(2, 3, 4)._speed);
}
```

- Library privacy is achieved by adding `_` prefix to field name
- Library private field would only be visible within the same file. So the main in `bicycle.dart` would work but the one in `test.dart` would not work

## Getter

```dart
class Bicycle {
  int cadence;
  int _speed;
  int gear;
  Bicycle(this.cadence, this._speed, this.gear);

  int get speed => _speed;
}

main() {
  print(new Bicycle(2, 3, 4).speed);
}
```

- Unlike java, after setting getter, you do not have to invoke the getter method, you can use `.` to invoke getter directly

- You can also define getter without using lambda expression:

  ```dart
  int get speed {
    return _speed;
  }
  ```

## Named parameter

```dart
import 'dart:math';

class Rectangle1 {
  Point origin;
  int width;
  int height;
  Rectangle1(this.width, this.height, {this.origin = const Point(0, 0)});
}
class Rectangle2 {
  Point origin;
  int width;
  int height;
  Rectangle2({this.width = 10, this.height, this.origin});
}

void main() {
  new Rectangle1(2, 3, origin:new Point(2,3));
  print(new Rectangle2(width: 2, origin:new Point(2,3)).height);
}

```

- You can specify default value for named parameter
- If default value of named parameter is not specified, then default value would be null
- `int` can also be null
- Named parameter and normal parameter can be mixed, but named parameters should always come after all normal parameters. i.e. `Rectangle1(this.width, {this.origin = const Point(0, 0)}, this.height);` is not allowed

## Progress-pin

Next section is: `4. Create a factory`























