## OnInit

```ts
import { Component, OnInit } from '@angular/core';
import Hero from './Hero';
import { HeroService } from '../hero.service';

@Component({
	selector: 'app-heroes',
	templateUrl: './heroes.component.html',
	styleUrls: ['./heroes.component.scss']
})
// Implement OnInit interface to implement ngOnInit
export class HeroesComponent implements OnInit
{
	heroes?: Hero[];
	selectedHero?: Hero;

	constructor(private heroService: HeroService)
	{
	}
  // This would be invoked when component initializes
	ngOnInit(): void
	{
		this.getHeroes();
	}

	getHeroes()
	{
		this.heroes = this.heroService.getHeroes();
	}

	onSelect(hero: Hero)
	{
		this.selectedHero = hero;
	}
}

```

