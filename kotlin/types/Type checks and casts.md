- https://kotlinlang.org/docs/typecasts.html

# is and !is operators﻿

To perform a runtime check that identifies whether an object conforms to a given type, use the `is` operator or its negated form `!is`:

```kotlin
if (obj is String) {
    print(obj.length)
}

if (obj !is String) { // Same as !(obj is String)
    print("Not a String")
} else {
    print(obj.length)
}
```

# Smart type guess

This is officially called `smart casts`. It's similar to typescript:

```typescript
fun demo(x: Any) {
    if (x is String) {
        print(x.length) // x is automatically cast to String
    }
}
```

# Force cast with `as`

Same as typescript

```kotlin
val x: String = y as String
```

If y is null, the above code would throw exception. To avoid that, `as?` can be used

```kotlin
val x: String? = y as? String
```

