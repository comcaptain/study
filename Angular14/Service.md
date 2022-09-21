# [Service](https://angular.io/guide/creating-injectable-service)

## Create a service

```bash
# e.g. ng generate service heroes/hero would creaet hero.service.ts in src/app/heroes
ng generate service <service name with path>
```

## Hello World

```ts
import { Injectable } from '@angular/core';
import { HEROES } from './heroes/mockHeroes';

@Injectable({
	providedIn: 'root'
})
export class HeroService
{
	getHeroes()
	{
		return HEROES;
	}
}
```

- Provider
  - A provider is something that can create or deliver a service
  - The `@Injectable` annotation defines a provider
- Injector
  - The *injector* is the object that chooses and injects the provider where the application requires it
  - `root` is an injector

To use the service, just include it into component class's constructor:

```ts
import { Component, OnInit } from '@angular/core';
import Hero from './Hero';
import { HeroService } from '../hero.service';

@Component({
	selector: 'app-heroes',
	templateUrl: './heroes.component.html',
	styleUrls: ['./heroes.component.scss']
})
export class HeroesComponent implements OnInit
{
  // Fields

	constructor(private heroService: HeroService)
	{
    // This is no need to explicitly define a heroService property. It has been created by the line above
	}

	// Others
}

```

It can also be injected into another service
