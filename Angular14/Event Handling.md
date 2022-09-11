## click

```html
<ul class="heroes">
	<li *ngFor="let hero of heroes">
		<button (click)="onSelect(hero)">
			<span class="badge">{{hero.id}}</span>
			<span class="name">{{hero.name}}</span>
		</button>
	</li>
</ul>
```

`onSelect` is a class method in component class:

```ts
import { Component, OnInit } from '@angular/core';
import Hero from './Hero';
import { HEROES } from './mockHeroes';

@Component({
	selector: 'app-heroes',
	templateUrl: './heroes.component.html',
	styleUrls: ['./heroes.component.scss']
})
export class HeroesComponent
{
	heroes = HEROES;
	selectedHero?: Hero;

	onSelect(hero: Hero)
	{
		this.selectedHero = hero;
	}
}
```

