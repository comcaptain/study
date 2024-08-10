- https://kotlinlang.org/docs/numbers.html

# Integer types

Same as java, has Byte, Short, Int and Long. But there is no boxed & unboxed type

```kotlin
val one = 1 // Int
val threeBillion = 3000000000 // Long
val oneLong = 1L // Long
val oneByte: Byte = 1
```

# Floating-point types﻿

Float & Double. Same as java.

```kotlin
val pi = 3.14 // Double
val oneDouble = 1.0 // Double
val eFloat = 2.7182818284f // Float, actual value is 2.7182817
```

Unlike Java, there are no implicit widening conversions for numbers in Kotlin. For example, a function with a `Double` parameter can be called only on `Double` values, but not `Float`, `Int`, or other numeric values:

```kotlin
fun main() {
    fun printDouble(d: Double) { print(d) }

    val i = 1
    val d = 1.0
    val f = 1.0f

    printDouble(d)
//    printDouble(i) // Error: Type mismatch
//    printDouble(f) // Error: Type mismatch
}
```

# Number in compiled class file

- On the JVM platform, numbers are stored as primitive types: `int`, `double`, and so on.

- Exceptions are cases when you create a nullable number reference such as `Int?` or use generics.
  - In these cases numbers are boxed in Java classes `Integer`, `Double`, and so on.

For boxed value, similar to Java, `new Integer(1234) !== new Integer(1234)`

```kotlin
val a: Int = 100
val boxedA: Int? = a
val anotherBoxedA: Int? = a

val b: Int = 10000
val boxedB: Int? = b
val anotherBoxedB: Int? = b

println(boxedA === anotherBoxedA) // true
println(boxedB === anotherBoxedB) // false
```

All nullable references to `a` are actually the same object because of the memory optimization that JVM applies to `Integer`s between `-128` and `127`. It doesn't apply to the `b` references, so they are different objects.

```kotlin
val b: Int = 10000
println(b == b) // Prints 'true'
val boxedB: Int? = b
val anotherBoxedB: Int? = b
println(boxedB == anotherBoxedB) // Prints 'true'
```

# Number type conversion

```kotlin
val b: Byte = 1 // OK, literals are checked statically
// val i: Int = b // ERROR
val i1: Int = b.toInt()
```

All number types support conversions to other types:

- `toByte(): Byte`
- `toShort(): Short`
- `toInt(): Int`
- `toLong(): Long`
- `toFloat(): Float`
- `toDouble(): Double`

Arithmetical operations are overloaded for appropriate conversions, for example

```kotlin
val l = 1L + 3 // Long + Int => Long
```

# Unsigned integer

In addition to [integer types](https://kotlinlang.org/docs/numbers.html#integer-types), Kotlin provides the following types for unsigned integer numbers:

| Type     | Size (bits) | Min value | Max value                            |
| -------- | ----------- | --------- | ------------------------------------ |
| `UByte`  | 8           | 0         | 255                                  |
| `UShort` | 16          | 0         | 65,535                               |
| `UInt`   | 32          | 0         | 4,294,967,295 (232 - 1)              |
| `ULong`  | 64          | 0         | 18,446,744,073,709,551,615 (264 - 1) |

```kotlin
val b: UByte = 1u  // UByte, expected type provided
val s: UShort = 1u // UShort, expected type provided
val l: ULong = 1u  // ULong, expected type provided

val a1 = 42u // UInt: no expected type provided, constant fits in UInt
val a2 = 0xFFFF_FFFF_FFFFu // ULong: no expected type provided, constant doesn't fit in UInt
```

