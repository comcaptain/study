# [RxJS](https://rxjs.dev/guide/overview)

## Basics

- There is a standard called [reactive streams](https://www.reactive-streams.org/), it has several implementations, two of them are [Project Reactor](https://projectreactor.io/) and [RxJava](https://github.com/ReactiveX/RxJava)
- [RxJava](https://github.com/ReactiveX/RxJava) also implements another API called [ReactiveX](https://reactivex.io/)
- [ReactiveX](https://reactivex.io/) has implementations for different languages. Java version is RxJava and javascript version is RxJS

### [RxJS - Observable](https://rxjs.dev/guide/observable)

- Represents the idea of an invokable collection of future values or events.

- Same as Reactive Streams's publisher. But it does not have separate class for Flux and Mono
- Same as react stream's publisher, nothing would happen until subscription happens

```ts
import { Observable, of } from 'rxjs';

const observable = new Observable((subscriber) => {
  subscriber.next(1);
  subscriber.next(2);
  subscriber.next(3);
  setTimeout(() => {
    subscriber.next(4);
    subscriber.complete();
  }, 1000);
});
observable.subscribe(count => console.info(count));
// You can also use of
of(1, 2, 3).subscribe(count => console.info(count));
```

### [RxJS - Observer](https://rxjs.dev/guide/observer)

- A listener that listens to the events emitted by observable (publisher)
- Is a collection of callbacks that knows how to listen to values delivered by the Observable

- Similar to Reactive Streams's subscriber

```ts
const observer = {
  next: x => console.log('Observer got a next value: ' + x),
  error: err => console.error('Observer got an error: ' + err),
  complete: () => console.log('Observer got a complete notification'),
};
```

### [RxJS - Subscription](https://rxjs.dev/guide/subscription)

Represents the execution of an Observable, is primarily useful for cancelling the execution.

```ts
import { interval } from 'rxjs';

const observable = interval(1000);
const subscription = observable.subscribe(x => console.log(x));
// Later:
// This cancels the ongoing Observable execution which
// was started by calling subscribe with an Observer.
subscription.unsubscribe();
```

### [RxJS - RxJS Operators](https://rxjs.dev/guide/operators)

- Pure functions that enable a functional programming style of dealing with collections with operations like `map`, `filter`, `concat`, `reduce`, etc.
- It has 2 categores:
  - Pipeable Operators:
    - `observableInstance.pipe(operator())`
    - This is similar to Reactive Streams's operator
    - Invoking it would not change the original observable. Instead, it would create a new observable instance
  - Creation Operators:
    - Function that create to create a new observable. e.g. `of(1, 2, 3)`

```ts
import { of, map } from 'rxjs';

of(1, 2, 3)
  .pipe(map((x) => x * x))
  .subscribe((v) => console.log(`value: ${v}`));

// Logs:
// value: 1
// value: 4
// value: 9
```

### [RxJS - Subject](https://rxjs.dev/guide/subject)

- Is both an obersevable and observer
- Equivalent to an EventEmitter, and the only way of multicasting a value or event to multiple Observers.
- It's similar to host publisher `Sinks.Many` in project reactor

```ts
import { Subject } from 'rxjs';

const subject = new Subject<number>();

subject.subscribe({
  next: (v) => console.log(`observerA: ${v}`),
});
subject.subscribe({
  next: (v) => console.log(`observerB: ${v}`),
});

subject.next(1);
subject.next(2);

const observable = from([1, 2, 3]);
 
observable.subscribe(subject); // You can subscribe providing a Subject
```

### [RxJS - Scheduler](https://rxjs.dev/guide/scheduler)

- Are centralized dispatchers to control concurrency, allowing us to coordinate when computation happens on e.g. `setTimeout` or `requestAnimationFrame` or others.
- Similar to scheduler in project reactor. You can use `observeOn` operator to change scheduler (similar to `subscribeOn` in project reactor)

## Operators

### case: Debounce search ajax request

The requirement is to do ajax search when user types search keyword. We need to debounce to reduce ajax frequency:

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

