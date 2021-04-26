# Conditional statements

## Most compact syntax of `if`

```bash
# CONSEQUENT-COMMANDS would be executed TEST-COMMAND return status is 0
if TEST-COMMANDS; then CONSEQUENT-COMMANDS; fi
```

- `TEST-COMMANDS` can be any command that may exit with 0

## Test expressions

### Test expressions for file

| Expression                | Meaning                                                      |
| ------------------------- | ------------------------------------------------------------ |
| [ `-a` `FILE` ]           | True if `FILE` exists.                                       |
| [ `-b` `FILE` ]           | True if `FILE` exists and is a block-special file.           |
| [ `-c` `FILE` ]           | True if `FILE` exists and is a character-special file.       |
| [ `-d` `FILE` ]           | True if `FILE` exists and is a directory.                    |
| [ `-e` `FILE` ]           | True if `FILE` exists.                                       |
| [ `-f` `FILE` ]           | True if `FILE` exists and is a regular file.                 |
| [ `-g` `FILE` ]           | True if `FILE` exists and its SGID bit is set.               |
| [ `-h` `FILE` ]           | True if `FILE` exists and is a symbolic link.                |
| [ `-k` `FILE` ]           | True if `FILE` exists and its sticky bit is set.             |
| [ `-p` `FILE` ]           | True if `FILE` exists and is a named pipe (FIFO).            |
| [ `-r` `FILE` ]           | True if `FILE` exists and is readable.                       |
| [ `-s` `FILE` ]           | True if `FILE` exists and has a size greater than zero.      |
| [ `-t` `FD` ]             | True if file descriptor `FD` is open and refers to a terminal. |
| [ `-u` `FILE` ]           | True if `FILE` exists and its SUID (set user ID) bit is set. |
| [ `-w` `FILE` ]           | True if `FILE` exists and is writable.                       |
| [ `-x` `FILE` ]           | True if `FILE` exists and is executable.                     |
| [ `-O` `FILE` ]           | True if `FILE` exists and is owned by the effective user ID. |
| [ `-G` `FILE` ]           | True if `FILE` exists and is owned by the effective group ID. |
| [ `-L` `FILE` ]           | True if `FILE` exists and is a symbolic link.                |
| [ `-N` `FILE` ]           | True if `FILE` exists and has been modified since it was last read. |
| [ `-S` `FILE` ]           | True if `FILE` exists and is a socket.                       |
| [ `FILE1` `-nt` `FILE2` ] | True if `FILE1` has been changed more recently than `FILE2`, or if `FILE1` exists and `FILE2` does not. |
| [ `FILE1` `-ot` `FILE2` ] | True if `FILE1` is older than `FILE2`, or is `FILE2` exists and `FILE1` does not. |
| [ `FILE1` `-ef` `FILE2` ] | True if `FILE1` and `FILE2` refer to the same device and inode numbers. |

### Test expressions for `String`

| Expression                    | Meaning                                                      |
| ----------------------------- | ------------------------------------------------------------ |
| `[ -z` STRING ]               | True of the length if "STRING" is zero.                      |
| `[ -n` STRING ] or [ STRING ] | True if the length of "STRING" is non-zero.                  |
| [ STRING1 == STRING2 ]        | True if the strings are equal. "=" may be used instead of "==" for strict POSIX compliance. |
| [ STRING1 != STRING2 ]        | True if the strings are not equal.                           |
| [ STRING1 < STRING2 ]         | True if "STRING1" sorts before "STRING2" lexicographically in the current locale. |
| [ STRING1 > STRING2 ]         | True if "STRING1" sorts after "STRING2" lexicographically in the current locale. |

### Test expressions for `Integer`

- Basic format is `[ int1 operator int2 ]`
- Operator can be one of `-eq`, `-ne`, `-lt`, `-le`, `-gt` or `-ge`

```bash
#!/bin/bash

threshold=$1
num=`ls | wc -l`
# You can remove double quotes, it would still work
if [ "$num" -gt "$threshold" ]; then
	echo "$num is greater than $threshold"
else
	echo "$num is less than $threshold"
fi
```

### Combining Test expressions

| Operation          | Effect                                                       |
| ------------------ | ------------------------------------------------------------ |
| [ ! EXPR ]         | True if **EXPR** is false.                                   |
| [ ( EXPR ) ]       | Returns the value of **EXPR**. This may be used to override the normal precedence of operators. |
| [ EXPR1 -a EXPR2 ] | True if both **EXPR1** and **EXPR2** are true.               |
| [ EXPR1 -o EXPR2 ] | True if either **EXPR1** or **EXPR2** is true.               |

## Use command as condition

```bash
# This works because when nothing is matched, fgrep exit status would be 1
# And if condition would only passes if command's exit status is 0
[tony:~/c/tmp]$ if ls | fgrep abc; then echo "There is *abc* file"; else echo "There is no *abc* file"; fi
There is no *abc* file
[tony:~/c/tmp]$ if ls | fgrep BBI; then echo "There is *abc* file"; else echo "There is no *abc* file"; fi
BBI-179
[FHD]BBI-186
There is *abc* file
```

## `&&` and `||`

Basic format is:

- **CONDITIONAL-EXPRESSION && COMMANDS**:  commands would only be run if conditional expression is true
- **CONDITIONAL-EXPRESSION || COMMANDS**:  commands would only be run if conditional expression is false

```bash
[tony:~/c/tmp]$ [ `ls | wc -l` -gt 3 ] && echo "There are more than 3 files"
There are more than 3 files
```

## `test` built-in command

It is equivalent to `[]`

```bash
[tony:~/c/tmp]$ test `ls | wc -l` -gt 3 && echo "There are more than 3 files"
There are more than 3 files
```

## `[]` V.S. `[[]]`

references

- [BashFAQ/031 - Greg's Wiki (wooledge.org)](http://mywiki.wooledge.org/BashFAQ/031#np2)
- [More advanced if usage (tldp.org)](https://tldp.org/LDP/Bash-Beginners-Guide/html/sect_07_02.html)

Differences

- `[]` is supported by more shells, while `[[]]` is only supported by bash, zsh...

- `[[]]` is an extension of `[]`

- world splitting of variable values would not happen in `[[]]`

  - e.g. `a="hello world"`
  - `[ $a == "hello world" ]` NG
    - Because word splitting works and there is space in value of `a`
  - `[ "$a" == "hello world" ]` OK
  - `[[ $a == "hello world" ]]` OK

- In `[[]]`, `==` and `!=` interpret strings to the right as shell glob patterns to be matched against the value to the left

  - ```bash
    [tony:~/c/tmp]$ a="hello world"
    # This would not work if we change `hello*` to `"hello*"`
    [tony:~/c/tmp]$ [[ $a == hello* ]] && echo "true"
    true
    ```

- You can use `&&` and `||` directly in `[[]]`

  - e.g. `[[ 3 > 2 && 4 > 3 ]]`

- You can do regex match using `=~` in `[[]]`

  - ```bash
    # Regex matching would not work if you surround right side regex with double quotes
    [tony:~/c/tmp]$ [[ "hello d123" =~ hello\ [0-9]+ ]] && echo "true" || echo "false"
    false
    [tony:~/c/tmp]$ [[ "hello 123" =~ hello\ [0-9]+ ]] && echo "true" || echo "false"
    true
    ```

## Arithmetic expression

We can use `(())` for arithmetic expression

```bash
[tony:~/c/tmp]$ (( 2 * 3 > 5 && 100 % 4 < 3 )) && echo "true"
true
```



