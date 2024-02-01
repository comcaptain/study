```js
function getCircularReplacer() {
  const seen = new WeakSet();
  return (key, value) => {
    if (typeof value === "object" && value !== null) {
      if (seen.has(value)) {
        // Replace circular reference
        return;
      }
      seen.add(value);
    }
    return value; // Return value if it's not a circular reference
  };
}

const obj = { name: "Alice" };
obj.self = obj; // Circular reference

const safeStringify = JSON.stringify(obj, getCircularReplacer());
console.log(safeStringify); // {"name":"Alice","self":null}

```
