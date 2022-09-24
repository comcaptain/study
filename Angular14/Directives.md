## [Angular - directives](https://angular.io/guide/built-in-directives)

Directives are classes that add additional behavior to elements in your Angular applications.

- Component is also a kind of directive

## [Built-in structural directives](https://angular.io/guide/built-in-directives#built-in-structural-directives)

Structural directives are responsible for HTML layout. They shape or reshape the DOM's structure, typically by adding, removing, and manipulating the host elements to which they are attached.

### [ngFor](https://angular.io/guide/built-in-directives#ngFor)

```html
<h2>My Heroes</h2>
<ul class="heroes">
	<li *ngFor="let hero of heroes">
		<button>
			<span class="badge">{{hero.id}}</span>
			<span class="name">{{hero.name}}</span>
		</button>
	</li>
</ul>
```

## [ngIf](https://angular.io/guide/built-in-directives#ngIf)

```html
<div *ngIf="selectedHero">
  <h2>{{selectedHero.name | uppercase}} Details</h2>
  <div>id: {{selectedHero.id}}</div>
  <div>
    <label for="hero-name">Hero name: </label>
    <input id="hero-name" [(ngModel)]="selectedHero.name" placeholder="name">
  </div>
</div>
```

## async

This is used to subscribe to `Observable` and return result:

**componet**

```ts
import { Component, OnInit } from '@angular/core';
import { Subject, Observable, debounceTime, distinctUntilChanged, switchMap } from 'rxjs';
import { HeroService } from '../hero.service';
import Hero from '../heroes/Hero';

@Component({
	selector: 'app-hero-search',
	templateUrl: './hero-search.component.html',
	styleUrls: ['./hero-search.component.scss']
})
export class HeroSearchComponent implements OnInit
{
  // $ suffix is naming convention Observable-typed property
	heroes$!: Observable<Hero[]>;

	private searchTerms = new Subject<string>();

	constructor(private heroService: HeroService) { }

	ngOnInit(): void
	{
		this.heroes$ = this.searchTerms.pipe(
			debounceTime(300),
			distinctUntilChanged(),
			switchMap(term => this.heroService.searchHeroes(term))
		)
	}

	search(term: string)
	{
		this.searchTerms.next(term);
	}

}
```

Then in template:

```html
<div id="search-component">
	<label for="searchBox">Hero Search</label>
	<input #searchBox type="text" id="search-box" (input)="search(searchBox.value)" />
	<ul class="search-result">
		<li *ngFor="let hero of heroes$ | async">
			<a routerLink="/detail/{{hero.id}}">{{hero.name}}</a>
		</li>
	</ul>
</div>
```

