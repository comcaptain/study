- https://kotlinlang.org/docs/arrays.html

# Create array

`arrayOf`

```kotlin
// Creates an array with values [1, 2, 3]
val simpleArray = arrayOf(1, 2, 3)
println(simpleArray.joinToString())
// 1, 2, 3
```

`arrayOfNulls`

```kotlin
// Creates an array with values [null, null, null]
val nullArray: Array<Int?> = arrayOfNulls(3)
println(nullArray.joinToString())
// null, null, null
```

`emptyArray()`

```kotlin
var exampleArray = emptyArray<String>()
```

`Array` constructor

```kotlin
// Creates an Array<Int> that initializes with zeros [0, 0, 0]
val initArray = Array<Int>(3) { 0 }
println(initArray.joinToString())
// 0, 0, 0

// Creates an Array<String> with values ["0", "1", "4", "9", "16"]
val asc = Array(5) { i -> (i * i).toString() }
asc.forEach { print(it) }
```

Create nested array:

```kotlin
// Creates a two-dimensional array
val twoDArray = Array(2) { Array<Int>(2) { 0 } }
println(twoDArray.contentDeepToString())
// [[0, 0], [0, 0]]
```

# Access and modify elements﻿

Same as Java, use `[]`

```kotlin
val simpleArray = arrayOf(1, 2, 3)
val twoDArray = Array(2) { Array<Int>(2) { 0 } }

// Accesses the element and modifies it
simpleArray[0] = 10
twoDArray[0][0] = 2

// Prints the modified element
println(simpleArray[0].toString()) // 10
println(twoDArray[0][0].toString()) // 2
```

# Spread operator `*`

```kotlin
fun main() {
    val lettersArray = arrayOf("c", "d")
    printAllStrings("a", "b", *lettersArray)
    // abcd
}

fun printAllStrings(vararg strings: String) {
    for (string in strings) {
        print(string)
    }
}
```

# Compare arrays﻿

Same as Java behavior, equals/`==` would do reference equal check. So use `contentEquals` or `contentDeepEquals` to compare

- `contentDeepEquals` would do deep compare for nested array

```kotlin
val simpleArray = arrayOf(1, 2, 3)
val anotherArray = arrayOf(1, 2, 3)

// Compares contents of arrays
println(simpleArray.contentEquals(anotherArray))
// true

// Using infix notation, compares contents of arrays after an element 
// is changed
simpleArray[0] = 10
println(simpleArray contentEquals anotherArray)
// false
```

# Useful methods

sum

```kotlin
val sumArray = arrayOf(1, 2, 3)

// Sums array elements
println(sumArray.sum())
// 6
```

shuffle to randomly shuffle the array:

```kotlin
val simpleArray = arrayOf(1, 2, 3)
// Shuffles elements [3, 2, 1]
simpleArray.shuffle()
println(simpleArray.joinToString())
// Shuffles elements again [2, 3, 1]
simpleArray.shuffle()
println(simpleArray.joinToString())
```

`toList` and `toSet`

```kotlin
val simpleArray = arrayOf("a", "b", "c", "c")

// Converts to a Set
println(simpleArray.toSet())
// [a, b, c]

// Converts to a List
println(simpleArray.toList())
// [a, b, c, c]
```

`toMap` for array of `Pair<K,V>`. Pair can be created using `to`: `"apple" to 123`

```kotlin
val pairArray = arrayOf("apple" to 120, "banana" to 150, "cherry" to 90, "apple" to 140)

// Converts to a Map
// The keys are fruits and the values are their number of calories
// Note how keys must be unique, so the latest value of "apple"
// overwrites the first
println(pairArray.toMap())
// {apple=140, banana=150, cherry=90}
```

# Primitive-type arrays﻿

If you use the `Array` class with primitive values, these values are boxed into objects.

As an alternative, you can use primitive-type arrays, which allow you to store primitives in an array without the side effect of boxing overhead:

| Primitive-type array                                         | Equivalent in Java |
| ------------------------------------------------------------ | ------------------ |
| [`BooleanArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean-array/) | `boolean[]`        |
| [`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/) | `byte[]`           |
| [`CharArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-array/) | `char[]`           |
| [`DoubleArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/) | `double[]`         |
| [`FloatArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float-array/) | `float[]`          |
| [`IntArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/) | `int[]`            |
| [`LongArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long-array/) | `long[]`           |
| [`ShortArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short-array/) | `short[]`          |