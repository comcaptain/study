### Sha-bang

The sharp-bang sign `#!` that appears in the first line indicates which program should be used to execute this program.
e.g. `#!/bin/bash` tells us to use Linux's bash to run it.

### Add permission to file

```bash
# gives everyone read/execute permission
chmod +rx scriptname

# gives only the script owner read/execute permission
chmod u+rx scriptname
```

### Make your script globally available for all users

Move your script to `/usr/local/bin`

### Semicolon

Permits putting two or more commands in one line

```bash
echo hello; echo there

if [ -x "$filename" ]; then
else
fi; echo "File test complete"
```

But for case option, we need double simicolon:

```
case "$variable" in
    abc) echo "\$variable = abc" ;;
    xyz) echo "\$variable = xyz" ;;
```

### dot as a component of file name

A leading dot in file name means it's hidden, i.e. it cannot be listed by `ls`by default, unless you use `-a` option

### comma operator

When used together with a series of arithmetic operations. All are evaluated, but only the last one is returned:

```bash
let "abc = ((a = 9, 15 / 3))"

echo "$abc" # print 5
echo "$a" # print 9
```

