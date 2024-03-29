# [Angular - Get data from a server](https://angular.io/tutorial/toh-pt6)

## Hello World

First, you have to import http client module:

```ts
@NgModule({
	// Others
	imports: [
    // http client module
		HttpClientModule
	],
	// Others
})
export class AppModule { }
```

Then you can use it like below

### GET

```ts
function getHeroes(): Observable<Hero[]>
{
  // It returns an Observable
	return this.http.get<Hero[]>("/api/heroes");
}
```

### Error Handling

```ts
function getHeroes(): Observable<Hero[]>
{
	return this.http.get<Hero[]>("/api/heroes").pipe(
		tap(_ => console.log('fetched heroes')),
		catchError(handleError("getHeroes", []))
	);
}

function handleError<T>(operation: string, result?: T)
{
	return (error: any): Observable<T> =>
	{
		console.error(error);
		this.log(`${operation} failed: ${error.message}`);
		return result ? of(result) : of();
	}
}
```

### PUT

```ts
function updateHero(hero: Hero)
{
	return this.http.put("/api/heroes", hero, {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	}).pipe(
		tap(_ => console.log(`updated hero id=${hero.id}`)),
		catchError(handleError(`updateHero id=${hero.id}`))
	)
}
```

### POST

```ts
function addHero(hero: Hero): Observable<Hero>
{
	return this.http.post<Hero>("/api/heroes", hero, {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	}).pipe(
		tap(_ => console.log(`created new hero ${hero.name}`)),
		catchError(handleError<Hero>(`addHero id=${hero.id}`))
	)
}
```



### DELETE

```ts
function deleteHero(id: number): Observable<Hero>
{
	return this.http.delete<Hero>(`/api/heroes/${id}`, {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	}).pipe(
		tap(_ => console.log(`deleted hero id=${id}`)),
		catchError(handleError<Hero>(`deleteHero id=${id}`)))
}
```



## [Angular Mock HTTP Server](https://angular.io/tutorial/toh-pt6#simulate-a-data-server)

**NOTE:** Only angular client module is mocked. i.e. It's not mocking javascript's fetch, ajax classes/functions. So it would only work for angular client module

First, install dependency:

```bash
npm install angular-in-memory-web-api --save
```

Then prepare a data service:

```ts
import { Injectable } from '@angular/core';
import { InMemoryDbService, RequestInfo } from 'angular-in-memory-web-api';
import Hero from './heroes/Hero';

@Injectable({
	providedIn: 'root'
})
export class InMemoryDataService implements InMemoryDbService
{
	createDb()
	{
		const heroes = [
			{ id: 12, name: 'Dr. Nice' },
			{ id: 13, name: 'Bombasto' },
			{ id: 14, name: 'Celeritas' },
			{ id: 15, name: 'Magneta' },
			{ id: 16, name: 'RubberMan' },
			{ id: 17, name: 'Dynama' },
			{ id: 18, name: 'Dr. IQ' },
			{ id: 19, name: 'Magma' },
			{ id: 20, name: 'Tornado' }
		];
    // Key is heroes. So all API paths would begin with /api/heroes
		return { heroes };
	}
	// Overrides the genId method to ensure that a hero always has an id.
	// If the heroes array is empty,
	// the method below returns the initial number (11).
	// if the heroes array is not empty, the method below returns the highest
	// hero id + 1.
	genId(heroes: Hero[]): number
	{
		return heroes.length > 0 ? Math.max(...heroes.map(hero => hero.id)) + 1 : 11;
	}
}
```

Then include the mock module:

```ts
@NgModule({
	// Others...
	imports: [
		// Others...
		// The HttpClientInMemoryWebApiModule module intercepts HTTP requests
		// and returns simulated server responses.
		// Remove it when a real server is ready to receive requests.
		HttpClientInMemoryWebApiModule.forRoot(
      // InMemoryDataService is the service created above
			InMemoryDataService, { dataEncapsulation: false }
		)
	],
	// Others...
})
export class AppModule { }

```

Now following RESTful endpoints would be mocked:

- `GET /api/heroes`
- `GET /api/heroes/42`
- `PUT /api/heroes`
- `POST /api/heroes`
- `DELETE /api/heroes`
- You can also do search. e.g. search by name: `GET /api/heroes?name=<keyword>`

