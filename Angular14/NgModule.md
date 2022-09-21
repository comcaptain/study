## [Basics](https://angular.io/tutorial/toh-pt1#appmodule)

```ts
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeroesComponent } from './heroes/heroes.component';

@NgModule({
  declarations: [
    AppComponent,
    HeroesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

This is configuration of the whole project and would be used in `main.ts`:

```ts
import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
```

- `bootstrap` defines the entry app
- `imports` defines modules that would be used in the project
  - e.g. `FormModule` is needed for form manipulation
- `declarations` lists all the components in the project
  - This would be updated automatically when running `ng generate component` to create component

## Create a module

```bash
ng generate module app-routing --flat --module=app
```

- `--flat` would put the file in `src/app` instead of its own directory
- `--module=app`: register the new module in the `imports` array of the `AppModule`