# Guide for the impatient

references:

- [TypeScript: Documentation - TypeScript for JavaScript Programmers (typescriptlang.org)](https://www.typescriptlang.org/docs/handbook/typescript-in-5-minutes.html)

# Detail

## Basic Types

### Boolean

```
let isDone: boolean = false;
```

### Number

As in JavaScript, all numbers in TypeScript are floating point values.

```
let decimal: number = 6;
let hex: number = 0xf00d;
let binary: number = 0b1010;
let octal: number = 0o744;
```

### String

```
let color: string = "blue"
color = 'red'
let sentence: string = `Hello, my name is ${color}`
```

### Array

```
let list: number[] = [1, 2, 3];
let list: Array<number> = [1, 2, 3];
```

### Tuple

Tuple type allows you to express an aray where the type of a fixed number of elements is known
```
// Declare variable x, whose type is tuple
let x: [string, number];
x = ["hello", 10];
// Error will be reported
x = ["hello", "world"]
```

When accessing an element outside the set of known indicies, a union type is used

```
x[3] = "world"; // OK, 'string' can be assigned to 'string 
 number'
console.log(x[5].toString()); // OK, 'string' and 'number' both have 'toString'

x[6] = true; // Error, 'boolean' isn't 'string | number'
```

### Enum

```
enum Color {Red, Green, Blue}
let c: Color = Color.Green;

enum Color {Red = 1, Green, Blue}
enum Color {Red = 1, Green = 5, Blue = 7}

let colorName: string = Color[2];
alert(colorName);
```

### Any

```
let notSure: any = 4;
notSure = "maybe a string instead";
notSure = false;

let strValue: string = notSure; // ok because notSure can be any type, including string. Although actually it's not

notSure.ifNotExists(); // ok, ifItExists might exist at runtime

let prettySure: Object = 4;
prettySure.toFixed(); // error because Object.toFixed does not exist. As you can see, any is more general than Object

```

### Void

`void` typed variable can only be assigned `undefined` or `null`. So it's only useful when used as function's return type

### Null and Undefined

Type used for value `null` and and `undefined` respectively.

By default type `null` and `undefined` are subtypes of all other types, so you can assign `null` and `undefined` value to variable of any type

However, when using the `--strictNullChecks` flag, `null` and `undefined` value can only assigned to `void` and `null`/`undefined` typed variable.

In case you want to pass in either a `string` or `undefined` or `null`, you can use union type `string | undefined | null`

### Never

Normally, no value can be assigned to `never` typed variable. So it can only be used in two cases:

- As function's return type when that function will never return (e.g. always throws an exception or has an infinite loop)
- When condition-statements has covered all possible types:
  ```
  enum Color {Red, Green, Blue}
  function test(c: Color) {
      switch (c) {
          case Color.Red:
          case Color.Green:
          case Color.Blue:
              return "abc";
      }
      let d: never = c;
  }
  ```
  Then value d's type is `never` and can be used to invoke function that accepts never-typed parameter

### Object

Like java, `object` is ancestor of all non-primitive types. Primitive types contain `number`, `string`, `boolean`, `symbol`, `null` or `undefined`

### Type assertion (or type cast)

It looks like type casting in java, but in fact it's different: it does not throw any error even if type casting is wrong (e.g. the value is a number but you cast it as string). It's only used by compiler to do type checking.

This does not work at runtime because the compiled js does not support type at all...

```
let someValue: any = "abc"
let strLength: number = (<string>someValue).length;
strLength = (someValue as string).length;
```