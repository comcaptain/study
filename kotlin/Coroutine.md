# Introduction

Coroutine is not a language built-in feature and does not even exist in standard library. `org.jetbrains.kotlinx:kotlinx-coroutines-core:<version>` needs to be added to dependency list.

It's similar to virtual thread, web flux.

A coroutine is not bound to any particular thread. It may suspend its execution in one thread and resume in another one.

# Hello World

```kotlin
// runBlocking and launch are function and only has one lambda expression as parameter. So they can be written like below
fun main() = runBlocking { // this: CoroutineScope
    launch { // launch a new coroutine and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello") // main coroutine continues while a previous one is delayed
}
```

- launcher can only be invoked inside coroutine scope ( `runBlocking` provides this scope)
- delay does not block the thread. It's similar to `await sleep(xxx)` in js
- The name of `runBlocking` means that the thread that runs it (in this case — the main thread) gets *blocked* for the duration of the call, until all the coroutines inside `runBlocking { ... }` complete their execution.
  - You will often see `runBlocking` used like that at the very top-level of the application and quite rarely inside the real code, as threads are expensive resources and blocking them is inefficient and is often not desired.

# Structured Concurrency

The core concept is the encapsulation of concurrent threads of execution (here encompassing kernel and userland threads and processes) by way of control flow constructs that have clear entry and exit points and that ensure all spawned threads have completed before exit.

# Suspend function

```kotlin
fun main() = runBlocking { // this: CoroutineScope
    launch { doWorld() }
    println("Hello")
}

// this is your first suspending function
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}
```

This is similar to `async` in js.

Unlike js, there is no need to `await` to make it suspend. Invoking it would suspend

# coroutineScope

```kotlin
fun main() = runBlocking {
    doWorld()
}

suspend fun doWorld() = coroutineScope {  // this: CoroutineScope
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}
```

unlike runBlocking, coroutineScope does not block the current thread. It suspends until all internal coroutine finishes

```kotlin
// Sequentially executes doWorld followed by "Done"
fun main() = runBlocking {
    doWorld()
    println("Done")
}

// Concurrently executes both sections
suspend fun doWorld() = coroutineScope { // this: CoroutineScope
    launch {
        delay(2000L)
        println("World 2")
    }
    launch {
        delay(1000L)
        println("World 1")
    }
    println("Hello")
}
```

# job

```kotlin
fun main() = runBlocking { // this: CoroutineScope
    val job = launch { // launch a new coroutine and keep a reference to its Job
        delay(1000L)
        println("World!")
    }
    println("Hello")
    job.join() // suspend until child coroutine completes
    println("Done")
}
```

`launch` works like creating a Promise and do not await in js

Job is similar to Promise in js