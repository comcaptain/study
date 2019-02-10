<https://angular.io/guide/architecture>

### Modules

An NgModule declares a **compilation context** for a set of components that is dedicated to an application domain, a workflow, or a closely related set of capabilities.

Every Agunlar app has a root module, conventionally named AppModule, which provides the bootstrap mechanism that launches the application. An app typically contains many functional modules.

One module can import functionality from other module, and export its own functionality. e.g. to use the router service in your app, you import the `Router` module.

Angular libraries are a collection of modules. Each Angular library name begines with the `@angular` prefix.

### Components

Every Angular application has at least one component, the root component that connects a component hireachy with the page document object model.

Component consists of a ts class (similar to action class in Struts2), a css file and a template. BTW, to make this css only apply to current component, it uses tricks like below:
```html
<h3 _ngcontent-c2="">Top Heroes</h3>
```
```css
h3[_ngcontent-c2] {
    text-align: center;
    margin-bottom: 0;
}
```

### Templates, data binding

A template combines HTML with Angular markup that can modify HTML elements before they are displayed. There are two types of data binding:
- Event binding lets your app respond to user input the the target environment by updating your application data
- Property binding lets you interpolate values that are computed from your application data into the HTML

Pipes are annotated with `@Pipe`. e.g. You can use `date` pipe to format date

### Directive

A directive is a class with a `@Directive` decorator. And a component is techinically a directive. In fact `@Component` inherites from `@Directive`.

There are two kinds of directives: structural and attribute

#### Structural directives
Structural directives alter layout by adding, removing and replacing elements in the DOM. e.g. `*ngFor` and `*ngIf`

#### Attribute directives
Attribute directives alter the appearance or behavior of an existing element. e.g. `[(ngModel)]`

### Services

Service is a broad category encompassing any value, function or feature that an app needs. A service is typically a class with a narrow, well-defined purpose. It should do somethign specific and do it well.

For data or logic that isn't associated with a specific view, and that you want to share accross components, you create a service class. A service class defination is immediately preceded by the `@Injectable()` decorator. The decorator provides the metadata that allows your service to be injected into client components as a dependency.

### Dependency Injection

DI lets you keep you component classes lean and efficient. They don't fetch data from the server, validate user input, or log directly to the console; they delegate such tasks to services.

You can register providers globally like this: add `@Injectable({providedIn: 'root'})` to a class that this provider provides. Then this can be injected to any class.

Or you can register it into a module by adding class to providers of `@NgModule`: `@NgModule({providers: [HeroService] ...})`.

Or you can register it into a specific component:

```typescript
@Component({
  selector:    'app-hero-list',
  templateUrl: './hero-list.component.html',
  providers:  [ HeroService ]
})
```

### Routing

With it, when clicking an NgLink, url is changed and the page is refreshed by Angular as if navigating to a brand-new page. But in fact it's not, you do not even have to send HTTP request during this procedure.

This means that you can easily implement fast page navigation without any overhead caused by Internet latency. And you do not have to worry about how the resources for the new page will be loaded or how the old resources will be released, Angular will do this for you. I can already imagine how useful can it be in current project.
