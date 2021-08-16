# [Lecture 1: Getting started with SwiftUI - YouTube](https://www.youtube.com/watch?v=bqu6BquVi2M)

## Kick Off

### Create a new IOS app called Memorize in Xcode

<img src="/Users/tony/Library/Application Support/typora-user-images/image-20210815230952400.png" alt="image-20210815230952400" style="zoom:50%;" />

### Project Structure

The new project would have 4 files:

```
Memorize
    |
    |----- MemorizeApp.swift: Entry point of the program
    |----- ContentView.swift: Draws the view area of the app
    |----- Assets.xcassets: A collection of assets, e.g. images
    |----- Info.plist: Configurations
```

### ContentView.swift analysis

Whole code:

```swift
import SwiftUI

struct ContentView: View {    
    var body: some View { 
        Text("Hello there")
            .padding()
    }
}

// This is code to make the preview feature work in Xcode
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
```

`SwiftUI` should be imported on every swift view class:

```swift
import SwiftUI
```

- struct is a collection of variables and functions
- It looks similar to class but is not class ContentView is name of the struct, which is similar to class's class name
- `:View` is to make ContentView look like a view. It's similar to class inheritence but it's not
- `var` is a keyword saying body is a variable
- `some View` is saying that the type is a View-like type. i.e. struts that `:View`, like ContentView

Following part is a function that has no parameters:

```swift
{ 
   Text("Hello there").padding()
}
```

The `return` keyword is omitted, you can explicitly add return like below

```swift
{ 
   return Text("Hello there").padding()
}
```

- type of `body` is `some View`, but value is a function, which looks weird. In fact, this is to tell swift that value of variable `body` is calculate, its value should be returned value of the function

- `.padding()` is called modifier, you can add modifier in Xcode's inspection window
  - `modifier` would return a modified view
  - And its return value is no longer original type, i.e. `Text("Hello there").padding()` is not `Text`

