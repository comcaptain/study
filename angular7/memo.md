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
  - [Next: Show messages](https://angular.io/tutorial/toh-pt4)

### TODO list
- ES6 js features

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

### Dynamically change class

```html
<li [class.selected]="hero === selectedHero"></li>
```

### Use component inside component

The key point is how to inject data into the component. In invoker side:

```html
<!-- This is one way binding, and injects selectedHero into hero-detail component's `hero` property -->
<app-hero-detail [hero]="selectedHero"></app-hero-detail>
```

And in sub-component:
```typescript
import { Component, OnInit, Input } from '@angular/core';
//...
@Input() hero: Hero;
```

### Create new service

Component should focus on presenting the data, not focusing on how to access/update data. This kind of work should be done by service.

Use command line `ng generate service hero`:

```typescript
import { Injectable } from '@angular/core';
import { Hero } from './hero'
import { HEROES } from './mock-heroes'

// This makes this class injectable into other things, e.g. component
@Injectable({
  // With registeration on root, Angular would create a shared, single instance and inject is into whereever that asks for it
  providedIn: 'root'
})
export class HeroService {

  constructor() { }

  // This is how type script define return type and array type
  getHeroes(): Hero[] {
    return HEROES;
  }
}

```

### Inject service into component

```typescript
import { HeroService } from '../hero.service'
  //...
  heroes: Hero[];

  // This would create a private property. If you want to use this service outside, e.g. in component html, then you should declare it as public
  constructor(private heroService: HeroService) { }

  loadHeroes(): void {
    this.heroes = this.heroService.getHeroes();
  }

  // This will be invoked by Angular in a proper time after this compnent's instance is created
  ngOnInit() {
    this.loadHeroes();
  }
```

### Use `Observable` to get data in asynchronized way

`Observable` is similar to `Promise` but is more powerful. I do not go into details here.

```typescript
import { Observable, of } from 'rxjs'
  
  // You can use of to create an Observable instance. We use Observable instead of Promise here because Angular uses Observable in its ajax components
  getHeroes(): Observable<Hero[]> {
    return of(HEROES);
  }

// And you can use subscribe to listen to it
this.heroService.getHeroes().subscribe(heroes => this.heroes = heroes);
```

### How to implement routes

#### 1. Create an empty module

By convention, the module name should be 'app-routing'. And the command line is:

```bash
# --flat is to put source files in `/app` folder instead of in its own folder
# --module=app is to tell CLI to register this module into app module's `imports`. So that later, directives exported in app-routing can be used in tempalte html
ng generate module app-routing --flat --module=app
```

#### 2. Clean up default implemenation and export `RouterModule`

We do not define components inside route module, so there is no need to keep `declarations` (used to declare component) and `imports` (used to import things used in component template html).

We should also export `RouterModule`, so that useful directives exported by `RouterModule` can be used by app module's component html. Then the final version would be like this:

```typescript
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'
@NgModule({
  exports: [RouterModule]
})
export class AppRoutingModule {
}
```

### 3. Create a route

```typescript
const routes: Routes = [
  {path: "heroes", component: HeroesComponent}
]
```

### 4. Initialize the router and make it listen for browser location changes

```typescript
// `forRoot` method supplies the service providers and directives needed for routing, and performs the initial navigation based on the current browser URL
imports: [RouterModule.forRoot(routes)]
```

### 5. Apply routing to application

Replace `<app-heroes>` with `<router-outlet>` like this:

```html
<h1>{{title}}</h1>
<!-- This is a placeholder for routing to render content. This directive comes from `RouterModule`, which is exported by routing module. -->
<router-outlet></router-outlet>
<app-messages></app-messages>
```

### 6. Add a navigation link

```html
<!-- The routerLink attribute is selector for RouterLink directive, which is exported by RouterModule -->
<a routerLink="/heroes">Heroes</a>
```

### Add route redirection

```js
// If it's exactly root path, then redirect to /dashboard
{path: "", redirectTo: "/dashboard", pathMatch: "full"}
```

### Pass parameters in route

Add new route
```js
{path: "hero/:id", component: HeorDetailComponent}
```

And to use this new route:
```html
<a routerLink='/hero/{{hero.id}}'>{{hero.name}}</a>
```

And to get parameter value in target component:

```typescript
import { ActivatedRoute } from '@angular/router'
  // Inject current route
  constructor(
    private route: ActivatedRoute
  ) { }

  // Get value
  // `+` can be a unary operator to change other objects to number, this is supported by native JS, even on IE
  // snapshot is a static image of the route information shortly after the component is created.
  let id = +this.route.snapshot.paramMap.get('id');
```

### Do go back like what it is in browser

```typescript
import { Location } from '@angular/common'
  
  constructor(private location: Location) {}

  getBack(): void {
    this.location.back();
  }

```

### Simulate real HTTP server with `in-memory-web-api`

#### Step 1. Install package

```bash
# With this `--save` option, this new dependency is automatically added to `package.json`
npm install angular-in-memory-web-api --save
```

#### Step 2. Enable HTTP services

`import { HttpClientModule }    from '@angular/common/http';`, then add it to app module's `imports`.

Without it, anything requiring HTTPClient will not work

#### Step 3. Create a fake DB

```typescript
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Hero } from './hero';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const heroes = [
      { id: 11, name: 'Mr. Nice' },
      { id: 12, name: 'Narco' },
      { id: 13, name: 'Bombasto' },
      { id: 14, name: 'Celeritas' },
      { id: 15, name: 'Magneta' },
      { id: 16, name: 'RubberMan' },
      { id: 17, name: 'Dynama' },
      { id: 18, name: 'Dr IQ' },
      { id: 19, name: 'Magma' },
      { id: 20, name: 'Tornado' }
    ];
    // This is equivalent to {heroes: heroes}. Then you can get heroes from url: `/api/heroes`
    return {heroes};
  }

  // Overrides the genId method to ensure that a hero always has an id.
  // If the heroes array is empty,
  // the method below returns the initial number (11).
  // if the heroes array is not empty, the method below returns the highest
  // hero id + 1.
  genId(heroes: Hero[]): number {
    return heroes.length > 0 ? Math.max(...heroes.map(hero => hero.id)) + 1 : 11;
  }
}
```

#### Step 4. Use in-memory DB service to intercept real HTTP requests

Add following things to app module's imports:

```typescript
// The HttpClientInMemoryWebApiModule module intercepts HTTP requests
// and returns simulated server responses.
// Remove it when a real server is ready to receive requests.
HttpClientInMemoryWebApiModule.forRoot(
  InMemoryDataService, { dataEncapsulation: false }
)
```

### Send ajax request and get data

1. Add import: `import { HttpClient, HttpHeaders } from '@angular/common/http';`
2. Inject it: `constructor(private http: HttpClient)`
3. Use it: `this.http.get<Hero[]>("/api/heroes")`. This will return `Observable<Hero[]>`. This will only work if ajax returns data like this: `[{id: 1, name: "abc"}]`

### Some tricks in typescript

```typescript
// You can use default values in function parameter
// You can use generate type like java
// To make variable optional, use `?` as suffix
private handleError<T> (operation = 'operation', result?: T) {
  // Unlike java, you can assign return type in lambda expression
  return (error: any): Observable<T> => {
    console.error(error);
    this.log(`${operation} failed: ${error.message}`)
    return of(result);
  }
}
```

### How to catch errors in `Observable`

You have to import an operator: `import { catchError } from 'rxjs/operators'`

Then you can use it like this:

```typescript
abc.pipe(catchError((error: any) => {
  console.error(any);
  return of('default value when error happens')
}))
```