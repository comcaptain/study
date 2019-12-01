## ECMAScript 6

Also known as ECMAScript 2015.

### Classes

```javascript
class SkinnedMesh extends Three.Mesh {
    constructor(geometry, materials) {
        super(geometry, materials);

        this.idMatrix = SkinnedMesh.defaultMatrix();
        this.bones = [];
        this.boneMatrices = []
    }

    update(camera) {
        super.update();
    }

    get boneCount() {
        return this.bones.length;
    }

    set maxtrixType(matrixType)  {
        this.idMatrix = SkinnedMesh[matrixType]();
    }

    static defaultMatrix() {
        return new THREE.Matrix4();
    }
}
```

### Enhanced Object Literals

```javascript
let obj = {
    __proto__: theProtoObj,
    // Shorthand for `handler: handler`
    handler,
    // Methods
    toString() {
        return 'd ' + super.toString();
    }
    // Dynamic property names
    ['prop_' + (() => 42)()]: 42
}
```
### Destructuring

It silently fails if matching does not work fine.

```javascript
// list matching
let [a, , b] = [1, 2, 3]

// object matching
let {op: a, lhs: {op: b}, rhs: c} = getASTNode();
let {op, lhs, rhs} = getASTNode();

// Use it in function parameter
function g({name: x}) {
    console.log(x);
}
g({name: 5});

// Fail-soft desctructuring
let [a] = []; // a = undefined

// Fail-soft destructuring with defaults
let [a = 1] = []; // a = 1
```

### Default parameters value and `...`

```javascript
function f(x, y = 12) {
    return x + y;
}
f(3) == 15

// Similar to `test(int a, int... others)` in java
function f(x, ...y) {
    // y is an array
    return x * y.length;
}

function(x, y, z) {
    return x + y + z;
}
f(...[1, 2, 3]) == 6
```

### `let` and `const`

Both are block-scoped binding constructs:
```javascript
function f() {
    let x;
    {
        // find, block scoped name
        const x = "sneaky"
        // error, constant
        x = "foo"
    }
    // error, x is already declared in current block
    let x = "inner"
}
```