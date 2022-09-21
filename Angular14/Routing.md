# [Routing](https://angular.io/tutorial/toh-pt5)

## Hello World

The routing module should already be there in the new project. But you can create it like below:

```bash
ng generate module app-routing --flat --module=app
```

And then replace content of generated `app-routing.module.ts` with:

```ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HeroesComponent } from './heroes/heroes.component';

const routes: Routes = [
  // This is default path
  { path: '', redirectTo: '/heroes', pathMatch: 'full' },
  // This is an example path
  { path: 'heroes', component: HeroesComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  // This makes the RouterModule available throughout the application
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

Then you can should tell app where to render the routed component:

```html
<h1>{{title}}</h1>
<router-outlet></router-outlet>
```

And you can create an `a` link like below:

```html
<h1>{{title}}</h1>
<nav>
  <a routerLink="/heroes">Heroes</a>
</nav>
```

## URL parameter

In routing module:

```ts
const routes: Routes = [
	{ path: "detail/:id", component: HeroDetailComponent }
];
```

Then in target component:

```ts
// Other code
export class HeroDetailComponent implements OnInit
{
	constructor(
		private route: ActivatedRoute,
		private heroService: HeroService
	){}
	ngOnInit(): void
	{
    // Get id parameter from activated route
		const id = Number(this.route.snapshot.paramMap.get("id"));
    // Load hero when the component initializes
		this.heroService.getHero(id).subscribe(hero => this.hero = hero);
	}

	@Input() hero?: Hero;
}

```



