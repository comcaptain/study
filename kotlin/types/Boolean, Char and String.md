- https://kotlinlang.org/docs/booleans.html
- https://kotlinlang.org/docs/characters.html
- https://kotlinlang.org/docs/strings.html

# Boolean

```kotlin
val myTrue: Boolean = true
val myFalse: Boolean = false
val boolNull: Boolean? = null

println(myTrue || myFalse)
// true
println(myTrue && myFalse)
// false
println(!myTrue)
// false
println(boolNull)
// null
```

`||`, `&&` and `!` work same as Java

On the JVM, nullable references to boolean objects are boxed in Java classes, just like with [numbers](https://kotlinlang.org/docs/numbers.html#numbers-representation-on-the-jvm).

# Char

```kotlin
val aChar: Char = 'a'

println(aChar)
println('\n') // Prints an extra newline character
println('\uFF00')
```

Same as Java

On the JVM, characters are boxed in Java classes when a nullable reference is needed, just like with [numbers](https://kotlinlang.org/docs/numbers.html#numbers-representation-on-the-jvm). Identity is not preserved by the boxing operation.

# String

Same as Java. With additional string template (same as typescript):

```kotlin
val i = 10
println("i = $i") 
// i = 10

val letters = listOf("a","b","c","d","e")
println("Letters: $letters") 
// Letters: [a, b, c, d, e]
```

```kotlin
val s = "abc"
println("$s.length is ${s.length}") 
```

```kotlin
val price = """
${'$'}_9.99
"""
```

