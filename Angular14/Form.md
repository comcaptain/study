## [Two-way binding](https://angular.io/tutorial/toh-pt1#two-way-binding)

- In angular, form can be achieved via "two-way binding", it works like react's controlled component
- It can bind a input element's value to a field in component class
- It's called two-way because:
  - When input value changes, then field value would also change
  - When field value changes, input value would also change
- NOTE: `FormsModule` has to be included to support this

```html
<div><input type="text" name="name" id="name" [(ngModel)] = "hero.name" /></div>
```



