
```q
// Example tables
a: flip `c1`c2`c3`c4`c5`c6!((1 2 3); ("a"; "b"; "c"); (10 20 30); (100 200 300); (1000 2000 3000); (10000 20000 30000))
b: flip `c1`c2`c3`c4`c5`c7!((1 2 3); ("a"; "b"; "c"); (10 20 30); (100 200 300); (1000 2000 3000); (100000 200000 300000))

keys: `c1`c2`c3`c4

// Anonymous function to perform left join and rename columns
result: {
  a: x;
  b: y;
  keys: z;
  
  // Perform left join
  joinedTable: lj[keys; a; b];

  // Rename columns from table b
  commonCols: keys;  // Columns to join on
  bCols: key b except commonCols;  // Columns from table b

  // Generate new names for columns from b
  renamedBCols: "x", upper each string each first each bCols, string each rest each bCols;

  // Create a dictionary for renaming
  renameDict: bCols!renamedBCols;

  // Rename columns in joined table and return the result
  update renameDict from joinedTable
}[a; b; keys]

// Display the result
result
```
