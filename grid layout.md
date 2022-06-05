### References

- [A Complete Guide to Grid | CSS-Tricks](https://css-tricks.com/snippets/css/complete-guide-grid/)

## Terms

- Grid Container

  - The element on which `display: grid` is applied

- Grid Item

  - The direct children of the grid container

- Grid Line

  - The dividing lines that make up the structure of the grid

  - They can be either vertical (“column grid lines”) or horizontal (“row grid lines”) and reside on either side of a row or column

    <img src="/Users/tony/Library/Application Support/typora-user-images/image-20211116151257379.png" alt="image-20211116151257379" style="zoom:50%;" />

- Grid Cell

  - The space between 2 adjacent row and 2 adjacent column grid lines

    <img src="/Users/tony/Library/Application Support/typora-user-images/image-20211116151339319.png" alt="image-20211116151339319" style="zoom:50%;" />

- Grid Track

  - The space between two adjacent grid lines

  - i.e. column or row of the grid

    <img src="/Users/tony/Library/Application Support/typora-user-images/image-20211116151422427.png" alt="image-20211116151422427" style="zoom:50%;" />

- Grid Area

  - The total space surrounded by four grid lines

    <img src="/Users/tony/Library/Application Support/typora-user-images/image-20211116151507034.png" alt="image-20211116151507034" style="zoom:50%;" />

## Details

### Declare a grid container

Use `display: grid` or `display: inline-grid`