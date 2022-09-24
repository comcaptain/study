## DOM node reference

```html
<div>
	<label for="new-hero">Hero name: </label>
  <!-- heroName is name of the reference. You can use heroName as the DOM node variable in the template -->
	<input id="new-hero" #heroName />

	<!-- (click) passes input value to add() and then clears the input -->
	<button type="button" class="add-button" (click)="add(heroName.value); heroName.value=''">
		Add hero
	</button>
</div>
```

