https://kotlinlang.org/docs/control-flow.html#for-loops

# For loops

Similar to java `for (xx:xx)` loop:

```kotlin
for (item in collection) print(item)
```

If you want to iterate through an array or a list with an index, you can do it this way:

```kotlin
for (i in array.indices) {
    println(array[i])
}
```

Alternatively, you can use the `withIndex` library function:

```kotlin
for ((index, value) in array.withIndex()) {
    println("the element at $index is $value")
}
```

# While, break & continue

They all work in the same way as Java