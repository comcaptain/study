## General

Similar to react, angular has component and has 3 parts:

- Component class code (typescript)
- Component template (html)
- Component css

## Basic Example

app.component.ts

```ts
import { Component } from '@angular/core';

@Component({
	selector: 'app-root', // This component would be rendered *inside* a html element whose selector is app-root
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.scss']
})
export class AppComponent
{
  // Fields of the class, it would be injected into template
	title = 'Tour of Heros';
}

```

app.component.html

```html
<h1 id="title">{{title}}</h1>
```

## Create a new component

```bash
# e.g. ng generate component heroes
# This would put generated code into folder app/heroes
ng generate component <component name>
```

## Pass property to component

```ts
import { Component, Input, OnInit } from '@angular/core';
import Hero from '../heroes/Hero';

@Component({
	selector: 'app-hero-detail',
	templateUrl: './hero-detail.component.html',
	styleUrls: ['./hero-detail.component.scss']
})
export class HeroDetailComponent
{
  // hero is input property
	@Input() hero?: Hero;
}
```

Then to use it:

```html
<app-hero-detail [hero]="selectedHero"></app-hero-detail>
```

`[hero]` is also a one way binding
