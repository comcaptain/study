### Progress

- [Getting started](https://angular.io/guide/quickstart)
  - Install node js
  - Fix openssl error when executing `npm -v`: [solution](https://github.com/npm/npm/issues/17261#issuecomment-310797098)
  - Instal Angular CLI (command line interface) globally
  - Use `ng new my-app` to create my first project. Choose default for all choices
  - Under `my-app` folder, use `ng serve --open` to start server
- [Tutorial: Tour of Heros](https://angular.io/tutorial)
  - Use `ng new angular-tour-of-heroes` to create a new app. Using default options. Then start the server
  - The page you see is the applciation shell. The shell is controller by an Angular component named AppComponent. Componetns are the fundamental building blocks of Angular applications. They display data on the screen, listen for user input, and take action based on that input
  - Encountered one issue: node is not found in command line even after regiestering it PATH. I finally fixed it by running the installer and click the repair button
  - Use `ng generate component heroes` to create new component: heroes
  - [Next: Display a Heroes List](https://angular.io/tutorial/toh-pt2)

### `Component` annotation

```typescript
import { Component, OnInit } from '@angular/core';

@Component({
  // The component's css selector
  selector: 'app-heroes',
  templateUrl: './heroes.component.html',
  styleUrls: ['./heroes.component.css']
})
// Always `export` the component class so that you can import elsewhere, like `app.module.ts`
export class HeroesComponent implements OnInit {

  constructor() { }
  // Is a lifecyle hook. Angular calls ngOnInit shortly after creating a component. It's a good place to put initialization logic
  ngOnInit() {
  }

}

```

### Bean ts class

```typescript
export class Hero {
    id: number;
    name: string;
}
```

And to use this bean class in component, you should import it first: `import { Hero } from '../hero'`. And you can create an instance like this: `{id: 1, name: 'Windstorm'}`

### Two-way binding

In the component html:
```html
<!-- hero is a property in corresponding component class -->
<input [(ngModel)]="hero.name" />
```

To make this work, you need to import `FormsModule` in `/app/app.module.ts`, like this:

```ts
import { FormsModule } from '@angular/forms';

//...
imports: [
  BrowserModule,
  // new one
  FormsModule
],
```

This top module class defines things used in html

### for loop

```html
<li *ngFor="let hero of heroes">
  <span class="badge">{{hero.id}}</span> {{hero.name}}
</li>
```

### bind listener

```html
<!-- onSelect is a function in component ts file -->
<li *ngFor="let hero of heroes" (click)="onSelect(hero)" >
  <span class="badge">{{hero.id}}</span> {{hero.name}}
</li>
```

```typescript
selectedHero: Hero;

onSelect(hero: Hero): void {
  this.selectedHero = hero;
}
```

### Use if-statement

```html
<div *ngIf="selectedHero">
  ...
</div>
```