- https://kotlinlang.org/docs/equality.html

# Introduction

In Kotlin, there are two types of equality:

- *Structural* equality (`==`) - a check for the `equals()` function

- *Referential* equality (`===`) - a check for two references pointing to the same object

  - This is same as `==` in Java

  - ```
    a == null` will be automatically translated to `a === null
    ```