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

