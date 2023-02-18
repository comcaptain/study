# [Lecture 3: MVVM and the Swift type system](https://youtu.be/--qKOhdgJAs)

## MVVM

![image-20210820230047956](./assets/image-20210820230047956.png)

- MVVM is short for Model, ViewModel, View
- The philosophy behind is a bit like react + redux:
  - **Model:** Is similar to redux store, which holds the state
    - It's the single source of truth
    - It contains UI independent data and logic
  - **ViewModel:** Is a bit like an enhanced redux reducer
    - It binds view to model, and might convert data from model to more UI-fridendly data and then pass to View
    - It handles "intent" from view (intent is similar to redux action) and notifies Model to do changes
    - When model does some changes, it would also notify view model, and then view model would notify view
  - **View:** Is basically react component
    - It's stateless and would only get state data from view model (which gets data from model)
    - It would send intent to view model when something happens and would subscribe for state changes so that UI is synchronized with state values

## struct and class

### Both struct and class have

- `var`

- computed `var`

- `let` that is similar to `var` but it means constant

- `func`

  ```swift
  struct Test {
      func multiple(num1: Int, num2: Int) -> Int {
          return num1 * num2;
      }
  }
  Test().multiple(num1: 1, num2: 2);
  // You can make name of the parameter different for invoker and the function itself 
  struct Test {
      func multiple(_ num1: Int, by num2: Int) -> Int {
          return num1 * num2;
      }
  }
  // The first name is parameter name, and `_` means invoker does not have to specify name
  Test().multiple(1, by: 2);
  ```

- `init` function. i.e. constructor

  ```swift
  struct Test {
      let value: Int;
      init(_ value: Int) {
          self.value = value;
      }
      func multiple(_ num2: Int) -> Int {
          return value * num2;
      }
  }
  ```

  

### Differences

- struct is value type while class is reference type
  - reference type is like class in java, we you pass it around, value would not be copied. Instead, reference would be passed around
  - value type is like primitive type in java. When passing it around, value would be copied
- array is a struct
- copy on write
  - struct would not be immediately copied when you pass it around
  - Instead, copy would only happen when you do modification on the struct
- As you can see, struct is designed to be immutable and is suitable for functional programming
- struct does not have inheritance
  - Although it can implement protocol (i.e. interface in java)
- default constructor
  - struct's default constructor would let you init any struct vars
  - class's default constructor is a no-arg constructor, same as java
- similar to java, both struct & class's default constructor would be gone after you declare one
- If you assign struct to a `let` variable, then struct cannot be modified. e.g. For array, function like `append` would not be available

## Generics

It's basically same as java.

## function as variable

Similar to js, you can assign function to a variable, and a function variable's type can be declared like below:

```swift
(Int, Int) -> Bool
(Double) -> Void
() -> Void
() -> Array<String>
```

## private & private(set)

private is similar as java, which makes a class/struct var inivisible outside. While `private(set)` would make the var readable outside but not writable outside:

```swift
class EmojiMemoryGame {
    private(set) var model: MemoryGame<String> = MemoryGame(cards: []);
}
```

## lambda

```swift
// A lambda that takes an int and return a string
{ (index: Int) -> String in
    return "😀";
}

// And if swift can get parameter type and return type from context, you can omit them:
{ index in
    return "😀";
}

// It's a one-linear, so similar to lambda in java, you can omit return
{index in "😀"}

// And since we are not using index, so we can change index to _
{_ in "😀"}
```

## static var and functions

It's same as java:

```swift
class EmojiMemoryGame {
    static let emojis = ["🚗", "🚌", "🚑", "🚀", "🛸", "🚁", "🛶", "⛵️", "🚤", "🛥", "🛳", "🪝", "⚓️", "🚢", "⛴", "⛽️", "🚧", "🚦", "🚥", "🗽", "🗿", "🗺", "🚏", "🗼", "🏰"]

    static func createMemoryGame() -> MemoryGame<String> {
        // For a one-liner, you do not have to add return
        MemoryGame(numberOfCards: 3) { pairIndex in emojis[pairIndex] };
    }
}
```

























