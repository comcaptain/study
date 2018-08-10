### References
- <https://basarat.gitbooks.io/typescript/content/docs/why-typescript.html>

### [Duck typing](https://www.wikiwand.com/en/Duck_typing)

If it walks like a duck and it quacks like a duck, then it must be a duck. 

With normal typing, suitability is determined by an object's type. In duck typing, an object's suitability is determined by the presence of certain methods and properties.

And type script uses duck typing

```
interface Point2D {
    x: number;
    y: number;
}
interface Point3D {
    x: number;
    y: number;
    z: number;
}
var point2D: Point2D = { x: 0, y: 10 }
var point3D: Point3D = { x: 0, y: 10, z: 20 }
function iTakePoint2D(point: Point2D) { /* do something */ }

iTakePoint2D(point2D); // exact match okay
iTakePoint2D(point3D); // extra information okay
iTakePoint2D({ x: 0 }); // Error: missing information `y`
```

### Type errors do not prevent JavaScript emit

Even if there is compiler error, compiler will still try its best to compile to correct js. e.g.
```
var foo = 123;
foo = '456'; // Error: cannot assign a `string` to a `number`
```
will emit the following js

```
var foo = 123;
foo = '456';
```

### Using javascript libraries

Note that definitions for most of the popular JavaScript libraries have already been written for you by the [DefinitelyTyped community](https://github.com/borisyankov/DefinitelyTyped).

But if one library does not have it, we still have workaround:

```
$('.awesome').show(); // Error: cannot find name `$`

// A quick fix
declare var $: any;
$('.awesome').show(); // Okay!

// Or you can provide more information to the compiler
declare var $: {
    (selector:string): any;
};
$('.awesome').show(); // Okay!
$(123).show(); // Error: selector needs to be a string
```
<https://basarat.gitbooks.io/typescript/content/docs/javascript/null-undefined.html>