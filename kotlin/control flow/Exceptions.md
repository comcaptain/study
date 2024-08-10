https://kotlinlang.org/docs/exceptions.html

# Introduction

Kotlin treats all exceptions as **unchecked** by default

# Functions that help to throw exception

## require() function﻿

If the condition in `require()` is not met, it throws an [`IllegalArgumentException`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-illegal-argument-exception/):

```kotlin
fun getIndices(count: Int): List<Int> {
    require(count >= 0) { "Count must be non-negative. You set count to $count." }
    return List(count) { it + 1 }
}

fun main() {
    // This fails with an IllegalArgumentException
    println(getIndices(-1))
    
    // Uncomment the line below to see a working example
    // println(getIndices(3))
    // [1, 2, 3]
}
```

Exception message can be omitted

```kotlin
fun printNonNullString(str: String?) {
    // Nullability check
    require(str != null)
    // After this successful check, 'str' is guaranteed to be
    // non-null and is automatically smart cast to non-nullable String
    println(str.length)
}
```

## check() function﻿

Use the [`check()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/check.html) function to validate the state of an object or variable. If the check fails, it indicates a logic error that needs to be addressed.

Usage is exactly same as `require`, except that `IllegalStateException` is thrown

## error() function﻿

The [`error()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/error.html) function is used to signal an illegal state or a condition in the code that logically should not occur

- It's suitable for scenarios when you want to throw an exception intentionally in your code, such as when the code encounters an unexpected state. 
- This function is particularly useful in `when` expressions, providing a clear way to handle cases that shouldn't logically happen.

`error("Undefined role: ${user.role}")` would simply throw an `IllegalStateException`

# Try-catch-finally

Same as Java

```kotlin
try {
    // Code that may throw an exception
}
catch (e: YourException) {
    // Exception handler
}
finally {
    // Code that is always executed
}
```

# Nothing type

- In Kotlin, every expression has a type

- The type of the expression `throw IllegalArgumentException()` is [`Nothing`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing.html)
  - a built-in type that is a subtype of all other types, also known as [the bottom type](https://en.wikipedia.org/wiki/Bottom_type). 

- `Nothing` is a special type in Kotlin used to represent functions or expressions that never complete successfully
  - either because they always throw an exception or enter an endless execution path like an infinite loop. 
- You can use `Nothing` to mark functions that are not yet implemented or are designed to always throw an exception
  - clearly indicating your intentions to both the compiler and code readers. 

```kotlin
class Person(val name: String?)

fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
    // This function will never return successfully.
    // It will always throw an exception.
}

fun main() {
    // Creates an instance of Person with 'name' as null
    val person = Person(name = null)
    
    val s: String = person.name ?: fail("Name required")

    // 's' is guaranteed to be initialized at this point
    println(s)
}
```

## TODO function

```kotlin
fun notImplementedFunction(): Int {
    TODO("This function is not yet implemented")
}

fun main() {
    val result = notImplementedFunction()
    // This throws a NotImplementedError
    println(result)
}
```

