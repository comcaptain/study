### References

- [GitHub](https://github.com/nut-tree/nut.js)
- [Sample Code](https://github.com/nut-tree/trailmix)
- [API](https://nut-tree.github.io/nut.js/globals.html)
  - [Screen](https://nut-tree.github.io/nut.js/classes/screen.html)
  - [OptionalSearchParameters](https://nut-tree.github.io/nut.js/classes/optionalsearchparameters.html)

### Issue 1: Cannot type Chinese

Solution is to use robotjs. But its current latest version (0.6.0) does not support yet. So we have to install from github:

```bash
yarn add https://github.com/octalmage/robotjs.git#c9cbd98e
```

### Issue 2: Does not work when focus is in some program

The reason is: cmd does not have admin permission. So there are two permissions:

- Open a cmd with admin, then run the command inside it
- Start VS code with admin, then run the command inside its terminal