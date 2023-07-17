# [Lecture 4: Memorize Game Logic](https://www.youtube.com/watch?v=oWZOFSYS5GE)

## ForEach and Identifiable

ForEach is used to build a list a views, and the array that it iterate has to fall into one of the followign cases:

**Case 1**: The element in array implements protocol (i.e. interface) `Hashable` and, you set `id` parameter to `\.self` like below:

```swift
ForEach(emojis[0..<emojiCount], id: \.self, content: { emoji in
    CardView(content: emoji).aspectRatio(2/3, contentMode: .fit)
})
```

**Case 2**: The element in array implements protocol (i.e. interface) `Identifiable`, then `id` parameter is not necessary.

To make a struct `Identifiable`, you just need to create an `id` field that is both `Hashable` and `Equatable`:

```swift
struct Card: Identifiable {
    var id: Int
    var isFaceUp: Bool
    var isMatched: Bool
    var content: CardContent
}
```

## print

In swift, you can use `print` to write something to console like below:

```swift
print("Hello World")
```

The console is in bottom of XCode and you have to drag it up. If it's blank, you need to drag from right most to left.

This print only works in simulator and does not work in preview.

You can configure XCode to open console when there is console output from simulator: Settings -> Behaviors -> Generates output -> Show debugger with Variables & Console View

You can use `\(expression)`:

```swift
print("Print name: \(name)")
```

## mutating in struct

If a function in struct would change the struct, `mutating` keyword should be added:

```swift
struct Card: Identifiable {
    var isFaceUp: Bool
    var isMatched: Bool
    var content: CardContent
    var id: Int
    // Without mutating, compiler error would arise
    mutating func toggle() {
        isFaceUp.toggle()
    }
}
```

## How to make UI listen to model change?

### Step 1: Make view model Observable

Make view model class implement `ObservableObject`. Then broadcast when model changes. There are 2 ways to broadcast:

**1st way:** Invoke `objectWillChange.send()` before doing the change

```swift
class EmojiMemoryGame: ObservableObject {
    //...    
    func choose(_ card: MemoryGame<String>.Card) {
        // objectWillChange comes from ObservalbeObject
        objectWillChange.send()
        model.choose(card)
    }
}
```

**2nd way:** Mark model field as `@Published`

```swift
class EmojiMemoryGame: ObservableObject {
    //...
    // @Published would make swift automatically invoke `objectWillChange.send()` before this struct changes
    @Published private var model: MemoryGame<String> = MemoryGame(numberOfPairsOfCards: 3) { pairIndex in emojis[pairIndex] }    
    func choose(_ card: MemoryGame<String>.Card) {
        model.choose(card)
    }
}
```

### Step 2: Make view model field observed in view

```swift
struct ContentView: View {    
    @ObservedObject var viewModel: EmojiMemoryGame    
    var body: some View {
        //...
    }
}
```

## Enum

### Basic Enum

```swift
enum FastFoodMenuItem {
    case hamburger
    case fries
    case drink
    case cookie
}
```

Similar to structs, it is a value type

### Enum with fields

```swift
enum FastFoodMenuItem {
    case hamburger(numberOfPatties: Int)
    case fries(size: FryOrderSize)
    case drink(String, ounces: Int) // Unnabled string is the brand, e.g. Pepsi
    case cookie
}
enum FryOrderSize {
    case large
    case small
}
let menuItem = FastFoodMenuItem.fries(size: .large)
```

### Switch enums

```swift
let menuItem = FastFoodMenuItem.fries(size: .large)
switch menuItem {
    case .hamburger: print("burger")
    case .fries: print("fries")
    case .drink: print("drink")
    case .cookie: print("cookie")
}
```

break & default

```swift
let menuItem = FastFoodMenuItem.fries(size: .large)
switch menuItem {
	case .hamburger: break // This is to skip this case
	case .fries: print("fries")
	default: print("other")
}
```

- Similar to java, you can switch other value types like string

- Unlike java, case does not fall through next case without `break`

  - You can achieve fall through with `fallthrough keyword`:

    ```swift
    let menuItem = FastFoodMenuItem.fries(size: .large)
    switch menuItem {
    	case .hamburger: break
    	case .fries:
    		print("fries")
    		fallthrough
    	default: print("other")
    }
    ```

Checking an enum's field value:

```swift
let menuItem = FastFoodMenuItem.fries(size: .large)
switch menuItem {
	case .hamburger(let numberOfPatties):
		print("Hamburger \(numberOfPatties)")
	case .fries(let size):
		print("Fries \(size)")
	case .drink(let brand, let ounces):
		print("Drink \(brand) \(ounces)")
	case .cookie:
		print("Cookie")
}
```

### Add function to enum

```swift
enum FastFoodMenuItem {
    case hamburger(numberOfPatties: Int)
    case fries(size: FryOrderSize)
    case drink(String, ounces: Int) // Unnabled string is the brand, e.g. Pepsi
    case cookie
	
	func isIncludedInSpeicalOrder(number: Int) -> Bool {
		switch self {
			case .hamburger(let numberOfPatties):
				return numberOfPatties == 2
			case .fries(let size):
				return size == .large;
			default:
				return false;
		}
	}
}
```

### Iterate over enum values

First, make the enum implement `CaseIterable`:

```swift
enum TelsaModel: CaseIterable {
	case X
	case S
	case Three
	case Y
}
```

Then:

```swift
for model in TelsaModel.allCases {
	print(model)
}
```

## Optional

It's a generic enum:

```swift
enum Optional<T> {
  case none
  case some(T)
}
```

You can use `?` to easily define an optional type:

```swift
var hello: String?; // This is identical to the following statement:
var hello: String? = nil; // nil means null. This is identitcal to following statement:
var hello: Optional<String> = .none;
var hello: String? = "abc"; // This is identical to following statement:
var hello: Optional<String> = .some("abc")
```

How to get a value out of optional-typed variable:

```swift
let hello: String? = ...
print(hello!) // If hello is nil, then exception would be thrown. Otherwise print value of `hello`

// Or you can check before using it:
if let safeHello = hello {
	print(safeHello)
}
else {
	print("Nothing")
}

// In the if statement, if you want to add more conditions based on safeHello, you can use , to join muliple && conditions:
if let safeHello = hello, safeHello != "def" {
	print(safeHello)
}

// Or you can use a syntax similar to typescript:
print(hello ?? "default hello")
// And another syntax similar to typescript:
print(hello?.count ?? 0)
```

 

