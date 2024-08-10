# References

- [Official Kotlin tour](https://kotlinlang.org/docs/kotlin-tour-welcome.html)

# Installation

Kotlin is bundled with IntelliJ. So you already have Kotlin if IntelliJ is installed

# Hello World

```kotlin
package org.example

fun main() {
    println("Hello World!")
}
```

# Variables

`val` is for final value and `var` is for non-final value

```kotlin
val a = 5
a = 10 // Compiler error
var b = 3
b = 5 // This works
```

# String Template

```kotlin
val customers = 10
println("There are $customers customers")
// There are 10 customers
println("There are ${customers + 1} customers")
```

# Type

Similar to typescript, kotlin can infer type and use `:<type>`

```kotlin
val d: Int
```

# Collections

## List

- To create a read-only list ([`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/)), use the [`listOf()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/list-of.html) function.

- To create a mutable list ([`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list.html)), use the [`mutableListOf()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/mutable-list-of.html) function.
  - MutableList can be cast to List to provide a readonly view: `val shapesLocked: List<String> = mutableShapes`

Kotlin can infer generic type. But generic type declaration is also supported

`[]` can be used to access list element

`in` & `!in` can be used to check item existence:

```kotlin
val readOnlyShapes = listOf("triangle", "square", "circle")
println("circle" in readOnlyShapes)
```

`count` is used to get size

## Set

Similar to list, there are readonly and mutable versions:

- To create a read-only set ([`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/)), use the [`setOf()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/set-of.html) function.

- To create a mutable set ([`MutableSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/)), use the [`mutableSetOf()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/mutable-set-of.html) function.

`in` can be used too

`count` is used to get size

## Map

- To create a read-only map ([`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/)), use the [`mapOf()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/map-of.html) function.

- To create a mutable map ([`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/)), use the [`mutableMapOf()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/mutable-map-of.html) function.

`[]` can be used to access list element

`in` can be used to check item existence:

# Control Flow

## if

`condition ? then : else` is replaced with `if (a > b) a else b`

# when

Similar to switch but it breaks by default

```kotlin
val obj = "Hello"

when (obj) {
    // Checks whether obj equals to "1"
    "1" -> println("One")
    // Checks whether obj equals to "Hello"
    "Hello" -> println("Greeting")
    // Default statement
    else -> println("Unknown")     
}
```

when can also be used to achieve things like if, else if, else if, else

```kotlin
fun main() {
    val trafficLightState = "Red" // This can be "Green", "Yellow", or "Red"

    val trafficAction = when {
        trafficLightState == "Green" -> "Go"
        trafficLightState == "Yellow" -> "Slow down"
        trafficLightState == "Red" -> "Stop"
        else -> "Malfunction"
    }

    println(trafficAction)
    // Stop
}
```

## Ranges

- `1..4` is equivalent to `1, 2, 3, 4`.
- `1..<4` is equivalent to `1, 2, 3`.
- `4 downTo 1` is equivalent to `4, 3, 2, 1`
- `1..5 step 2` is equivalent to `1, 3, 5`.
- `'a'..'d'` is equivalent to `'a', 'b', 'c', 'd'`
- `'z' downTo 's' step 2` is equivalent to `'z', 'x', 'v', 't'`

## For

```kotlin
for (number in 1..5) { 
    // number is the iterator and 1..5 is the range
    print(number)
}
```

# function

Same as typescript

```kotlin
fun sum(x: Int, y: Int): Int {
    return x + y
}

fun main() {
    println(sum(1, 2))
    // 3
}
```

## Named argument

```kotlin
fun printMessageWithPrefix(message: String, prefix: String) {
    println("[$prefix] $message")
}

fun main() {
    // Uses named arguments with swapped parameter order
    printMessageWithPrefix(prefix = "Log", message = "Hello")
    // [Log] Hello
}
```

## Single line function

```kotlin
fun sum(x: Int, y: Int) = x + y

fun main() {
    println(sum(1, 2))
    // 3
}
```

## Lambda expression

```kotlin
fun main() {
    val upperCaseString = { text: String -> text.uppercase() }
    println(upperCaseString("hello"))
    // HELLO
}
// No parameter lambda expression
{ println("Log message") }
```

Compared to java lambda expression:

- A surrounding `{}` is needed
- `=>` is replaced with `->`
- `->` can be removed when there is no parameter

If lambda expression is the only parameter, then `()` can be removed

```kotlin
val positives = numbers.filter { x -> x > 0 }
```

## Function type

It's similar to typescript, `(String) -> String` or `(Int, Int) -> Int`.

## Trainling lambda parameter in function invocation

```kotlin
// The initial value is zero. 
// The operation sums the initial value with every item in the list cumulatively.
println(listOf(1, 2, 3).fold(0, { x, item -> x + item })) // 6

// Alternatively, in the form of a trailing lambda
println(listOf(1, 2, 3).fold(0) { x, item -> x + item })  // 6
```

# Class

```kotlin
// Similar to typescript, without val, var, id & email would not become property
// You can also add private modifier to it
class Contact(val id: Int, var email: String)

fun main() {
    val contact = Contact(1, "mary@gmail.com")
}
```

Property can also be declared directly:

```kotlin
class Contact(val id: Int, var email: String = "example@gmail.com") {
    val category: String = "work"
}
```



## constructor

This is a bit messy. It has so called primary constructor:

```kotlin
class Person constructor(firstName: String) { /*...*/ }
```

If the primary constructor does not have any annotations or visibility modifiers, the `constructor` keyword can be omitted:

```kotlin
class Person(firstName: String) { /*...*/ }
```

The body of the primary constructor are in `init` logic block, inside which primary constructor's parameters can be accessed

```kotlin
class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also(::println)
    
    init {
        println("First initializer block that prints $name")
    }
    // Primary constructor parameter is also visible for property expression
    val secondProperty = "Second property: ${name.length}".also(::println)
    
    init {
        println("Second initializer block that prints ${name.length}")
    }
}
```

Secondary constructor's syntax is similar to typescript's constructor. And before it's executed, primary constructor would be executed first (including all init blocks)

```kotlin
class Person(val name: String) {
    val children: MutableList<Person> = mutableListOf()
    constructor(name: String, parent: Person) : this(name) {
        parent.children.add(this)
    }
}
```

## Member function

```kotlin
class Contact(val id: Int, var email: String) {
    fun printId() {
        println(id)
    }
}
```

## Data class

It's similar to java's record but is **NOT immutable** if var is used

```kotlin
data class User(val name: String, val id: Int)
```

### toString of data class

```kotlin
val user = User("Alex", 1)

// Automatically uses toString() function so that output is easy to read
println(user)            
// User(name=Alex, id=1)
```

### equals is implemented

```kotlin
val user = User("Alex", 1)
val secondUser = User("Alex", 1)
val thirdUser = User("Max", 2)

// Compares user to second user
println("user == secondUser: ${user == secondUser}") 
// user == secondUser: true

// Compares user to third user
println("user == thirdUser: ${user == thirdUser}")   
// user == thirdUser: false
```

### copy

```kotlin
val user = User("Alex", 1)
val secondUser = User("Alex", 1)
val thirdUser = User("Max", 2)

// Creates an exact copy of user
println(user.copy())       
// User(name=Alex, id=1)

// Creates a copy of user with name: "Max"
println(user.copy("Max"))  
// User(name=Max, id=1)

// Creates a copy of user with id: 3
println(user.copy(id = 3)) 
// User(name=Alex, id=3)
```

# null

Similar to typescript, nullable & non-nullable value have different types:

```kotlin
fun main() {
    // neverNull has String type
    var neverNull: String = "This can't be null"

    // Throws a compiler error
    neverNull = null

    // nullable has nullable String type
    var nullable: String? = "You can keep a null here"

    // This is OK
    nullable = null

    // By default, null values aren't accepted
    var inferredNonNull = "The compiler assumes non-nullable"

    // Throws a compiler error
    inferredNonNull = null

    // notNull doesn't accept null values
    fun strLength(notNull: String): Int {                 
        return notNull.length
    }

    println(strLength(neverNull)) // 18
    println(strLength(nullable))  // Throws a compiler error
}
```

And similar to typescript, `?.` can be used

```kotlin
fun lengthString(maybeString: String?): Int? = maybeString?.length

fun main() { 
    val nullString: String? = null
    println(lengthString(nullString))
    // null
}
```

Use ` ?: ` to specify default value. It's same as `??` in js

```kotlin
fun main() {
    val nullString: String? = null
    println(nullString?.length ?: 0)
    // 0
}
```

