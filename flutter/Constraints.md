# References

- [Understanding constraints - Flutter](https://flutter.dev/docs/development/ui/layout/constraints)

# Summary

**Contraints go down. Sizes go up. Parents set position**

1. A widget gets its own constraints from its parent (i.e. Constraints go **down** to the children)
   - A constraint is just a set of 4 double values: minimum and maximum width, minimum and maximum height

2. One by one, parent tells its chilren what their constraints are (can be different for each child), then then ask each child for their size (should be within constraint, of course)
3. Parent positions its children (x and y for horizontal and vertial axis) one by one
4. Parent tells its parent about its own size