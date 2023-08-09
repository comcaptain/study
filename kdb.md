## References

- [(20) Intro to kdb+ and q | Tutorial #2 | Q Console, Types and Lists - YouTube](https://www.youtube.com/watch?v=TBHRVCnH8u4)

## Numeric

- Plain number is 64-bit by default
  - You can add `i` suffix to declare 32-bit integer, like `3i`
- Division operator is `%` rather than `/`

## List

You can declare a list like this: `1 2 3`. Yeah, just space in-between elements

And if 2 lists have the same length, you can even add them up:

```q
// This would produce 11 22 33
1 2 3 + 10 20 30
```

You can even add a single value to a list:

```q
// Output: 3 4 5
1 + 2 3 4
// Output: 3 4 5
2 3 4 + 1
```

Similarly, you can use other operators:

```q
// Output: 1 2 3f
10 20 30 % 10
// Output: 30 60 90
10 20 30 * 3
```

## Operator

All operators have the same precedence (i.e. priority) and expression is evaluated from right to left so `2 * 3 + 4` would produce 14 rather than 10 (`3 + 4` is calculated first)