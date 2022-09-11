# [Angular - Understanding binding](https://angular.io/guide/binding-overview)

## [class binding](https://angular.io/guide/class-binding)

```html
<ul class="heroes">
	<li *ngFor="let hero of heroes">
		<button (click)="onSelect(hero)" [class.selected] = "hero === selectedHero">
			<span class="badge">{{hero.id}}</span>
			<span class="name">{{hero.name}}</span>
		</button>
	</li>
</ul>
```

The example above would add `selected` class to button when hero is selected