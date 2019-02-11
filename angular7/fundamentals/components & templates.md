- Start: <https://angular.io/guide/displaying-data>
- Next: <https://angular.io/guide/template-syntax#attribute-class-and-style-bindings>

## Displaying Data

Nothing interesting, except for one thing, you may not write a separate template html file, instead, you can specifiy html string in component ts directly, like this:

```typescript
template: `
  <h1>{{title}}</h1>
  <h2>My favorite hero is: {{myHero}}</h2>
  `
```

## Template syntax

In Angular, the component plays the part of the controller/viewmodel, and the template represents the view.

### HTML in templates

Almost all HTML syntax is supported, but `<script>` tag is forbidden.

### Interpolation `{{...}}`

You can write any expression inside the block, e.g. `{{1 + 1 + getValue()}}`, getValue is a function defined in component class.

NOTE: in the expression, you cannot used js expression that have or promote side effects, including:
- Assignments (=, +=, ....)
- Operators such as new, typeof, instanceof, etc
- Chaining expression with `;` or `,`
- The increment and decrement operators ++ and --

The context of the expression is the component class. But if there is context variable with the same name as property of the component class (e.g. hero in `*ngFor="let hero of heroes"`), then context variable has higher priority.

The priciple for expressions:
- No visible side effects
- Quick execution. Since it would be executed every time after a change is done
- Simplicity

### Tempalte statements

A template statement responds to an event raised by a binding target, e.g. `(click)="tempalte statement`

Unlike template expression, template statement is used to create side effect. Like template expression, template statements use a language that looks like JavaScript. The template statement parser differs from the template expression parser and specifically supports both basic assignment and chaining expressions (`;` and `,`).

But, certain JavaScript syntax is not allowed:
- new
- increment and decrement operators, ++ and --
- operator assignment, such as += and -=
- bitwise operators like `|` and `&`
- template exression operators: pipe, `?.` and `!.`

### HTML attribute and DOM property are different!

Attributes initialize DOM properties and then they are done. Property values can change; attribute values cannot unless you manually set it.

e.g. `<input type="text" value="Bob" />`, if user enters 'Sally' into the input box, then `inputNode.value` is "Sally" but `inputNode.getAttribute("value")` is "Bob".

`disabled` is a special case, when you add/remove attribute of `disabled` attribute, the `disabled` property is automatically changed, hence changing button to disabled/enabled. i.e. It's property of DOM that controls whether the button is disabled or enabled.

#### Property binding: `[property]`

Write a template property binding to set a **property** (not attribute, so you cannot bind to an arbitrary string, e.g. `[abc]`) of a view element. The binding sets the property to the value of a `template expression`.

- A normal binding: `<img [src]="heroImageUrl" />`, `<button [disabled]="isUnchanged">save</button>`
- Bind to property registered by directive: `<div [ngClass]="classes"></div>`
- Set model property of a custom component: `<app-hero-detail [hero]="currentHero"></app-hero-detail> `

There is another way to bind property: `<img bind-src="heroImageUrl" />`

You may want to use traditionaly html attribute like this: `<input abc="def" />`. It works exactly the same way as in normal html. But you cannot use interpolation in the value like this: `<input abc="{{name}}" />`, since this is identical with `<input [abc]="name" />`.