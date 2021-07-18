# Boilerplate

## Create a react react project

```bash
# Create a react app called my-app
npx create-react-app my-app --template typescript
cd my-app
```

## Start development server

```bash
# in app folder
npm start
# This would start a simple http server at http://localhost:3000/
# Page would reload automatically when code changes
```

## Add support for scss

[Adding a Sass Stylesheet | Create React App (create-react-app.dev)](https://create-react-app.dev/docs/adding-a-sass-stylesheet/)

```bash
npm install node-sass --save
```

Then rename all css to scss and update references in tsx files

## [Add proxy to backend server](https://create-react-app.dev/docs/proxying-api-requests-in-development/)

- Set up proxy so that when starting react development server, all API requests to development server can be proxied to backend server

- If your backend RESTful api url path all start with `/api`, then setup can be done by adding following entry to package.json

  ```json
  "proxy": "http://localhost:8080",
  ```

  `http://localhost:8080` is backend server url

# Build

## Build code

```
npm build
```

This would build do the compilation and output to `/build` folder

- All html/js/css files would be compressed and added hash suffix
- Entry point is index.html
- You can start a http server in index.html to verify

## Make build target support IE11

### How to

```bash
npm install react-app-polyfill
```

Then add following line as **first line** of index.tsx

```typescript
import 'react-app-polyfill/ie11';
```

### Verification

Verify whether following features work in IE11:

- [x] Async/await
- [x] `2 ** 3`
- [x] `let { x, y, ...z } = { x: 1, y: 2, a: 3, b: 4 }`
- [x] `let n = { x, y, ...z }`

## React App Folder Structure

```
my-app/
  README.md
  node_modules/
  package.json
  public/
    index.html
    favicon.ico
  src/
    App.css
    App.js
    App.test.js
    index.css
    index.js
    logo.svg
```

- `public`
  - Only files inside this folder can be referenced by `public/index.html`
- `src`
  - webpack would only preprocess files inside this directory
  - so all scss/ts/tsx files would be stored here

# VS code setup

https://create-react-app.dev/docs/setting-up-your-editor#visual-studio-code

## Debug in VS code

1. Install plugin [Chrome Debugger Extension](https://marketplace.visualstudio.com/items?itemName=msjsdiag.debugger-for-chrome)

2. Create `.vscode` directory in your app root folder

3. Create `launch.json` inside `.vscode` directory. And its content is:

   ```json
   {
     "version": "0.2.0",
     "configurations": [
       {
         "name": "Chrome",
         "type": "chrome",
         "request": "launch",
         "url": "http://localhost:3000",
         "webRoot": "${workspaceFolder}/src",
         "sourceMapPathOverrides": {
           "webpack:///src/*": "${webRoot}/*"
         }
       }
     ]
   }
   ```

4. Press F5 to open a new Chrome tab. Happy debugging :stuck_out_tongue_winking_eye:

# Main Concepts

[TypeScript: Documentation - JSX (typescriptlang.org)](https://www.typescriptlang.org/docs/handbook/jsx.html)

[Hello World – React (reactjs.org)](https://reactjs.org/docs/hello-world.html)

## Hello World

```tsx
ReactDOM.render(
  <h1>Hello, world!</h1>,
  // The DOM node inside which the h1 above would be rendered
  document.getElementById('root')
);
```

## JSX/TSX

### Declare tsx template variable

```tsx
const element = <h1>Hello, {name}</h1>;
// When putting tempalte into multiple lines, surround it with ()
const element2 = (
  <h1>
    Hello, {formatName(user)}!
  </h1>
);
```

### Run ts code in tsx template

```tsx
function sum(num1: number, num2: number): string {
	return `${num1} + ${num2} = ${num1 + num2}`;
}
ReactDOM.render(
  <p>{sum(2, 3)}</p>,
  document.getElementById('root')
);
```

**NOTE:** Result of expression inside `{}` would be escaped. So you do no need to worry about code injection

### TSX template would be compiled to...

```tsx
const element = (
  <h1 className="greeting">
    Hello, world!
  </h1>
);
```

would be compiled to following statement by babel:

```tsx
const element = React.createElement(
  'h1',
  {className: 'greeting'},
  'Hello, world!'
);
```

## React Element

### What is it

Each template is a react element. It's similar to DOM node in plain html. But there are following differences:

- It's a plain js object. So unlike HTML DOM node, it's very cheap to create
- It's **immutable**
- Its type is `JSX.Element` in typescript

### How to render it?

Use `ReactDOM.render` like below:

```tsx
ReactDOM.render(
  <h1>Hello, world!</h1>, // React Element
  // The DOM node inside which the h1 above would be rendered
  document.getElementById('root')
);
```

## Components and properties

- A component is an isolated collection of css, js and html
- It's a class that extends `React.Component`

### Define a component

- There are 2 ways to declare components: function & class
- They are identical and there is no way to distinguish whether a component is declared via function or class
- All components' first character must be **capitalized**. This is to differentiate from html tags like `div`
- **It's encoraged to create small component**

#### Function Components

```tsx
// The 1st parameter should always be props, an object-typed parameter
// And its return type should be JSX.Element
function Welcome(props: { name: string }): JSX.Element {
	return <p>Welcome {props.name}</p>
}
// parameter name does not have props and type can also be any type, like an interface
interface User {
	name: string
}
function Welcome2(user: User): JSX.Element {
	return <p>Welcome {user.name}</p>
}
```

#### Class Components

```tsx
class Welcome extends React.Component<{ name: string }> {
	render() {
		return <h1>Hello, {this.props.name}</h1>;
	}
}
```

### Conditionally do not render the component itself

```tsx
function WarningBanner(props: { warningText?: string }) {
	if (!props.warningText) {
		return null; // Returnning null would not render the component
	}

	return (
		<div className="warning">{props.warningText}</div>
	);
}
```



### Render a component

```tsx
// Attributes would be converted to properties attribute and passed to constructor/function of component
const element = <Welcome name="Sara" />;
```

### Properties is immutable

Components should **never** modify properties value

## State and Lifecycle

### A running clock

```ts
// Second generic type is for state
class Clock extends React.Component<{}, { currentTime: Date }> {
	// An optional property
	timerID?: NodeJS.Timeout;

	constructor(props: {}) {
		super(props);
        // Set initial state. Unlike properties, state is mutable
		this.state = { currentTime: new Date() };
		console.info("Step 1")
	}

    // Overridden method, this would be invoked immediately after this component is mounted 
	componentDidMount() {
        // NOTE: You should invoke setState to notify React that state is changed
        // this.state.currentTime = new Date() would not work
		this.timerID = setInterval(() => this.setState({ currentTime: new Date() }), 1000);
		console.info("Step 3")
	}

    // Overridden mothod, this would be called immediately before this component is destroyed
    // So we can do some cleanup work here
	componentWillUnmount() {
		console.info("Step 5")
		if (this.timerID) clearInterval(this.timerID)
	}

	render() {
		console.info(`Step ${this.timerID ? 4 : 2}`)
        // Use state rather than properties
		return <p>{this.state.currentTime.toLocaleTimeString()}</p>;
	}
}
```

### Using State Correctly

#### Treat `this.state` as if it were immutable

```ts
// Wrong
this.state.comment = 'Hello';
// Correct
this.setState({comment: 'Hello'});
```

According to [this doc](https://reactjs.org/docs/react-component.html#setstate):

> Never mutate this.state directly, as calling setState() afterwards may replace the mutation you made. **Treat this.state as if it were immutable**.

#### setState is asynchronous

After `setState` is executed, the state may not have been updated. So following code is wrong:

```tsx
// Wrong
this.setState({
  counter: this.state.counter + this.props.increment,
});
```

You can fix it like this:

```tsx
// Correct
this.setState((state, props) => ({
  counter: state.counter + props.increment
}));
```

#### Only need to update changed state property

```tsx
constructor(props) {
    super(props);
    this.state = {
        posts: [],
        comments: []
    };
}
componentDidMount() {
    fetchPosts().then(response => {
        this.setState({
            posts: response.posts // You do not have to add comments property here because it's not changed
        });
    });

    fetchComments().then(response => {
        this.setState({
            comments: response.comments // You do not have to add comments property here because it's not changed
        });
    });
}
```

## Event Handling

### Button Click Demo

```tsx
class ButtonDemo extends React.Component {

	clicked = (e: MouseEvent<HTMLButtonElement>) => {
		alert(`Clicked button ${e.currentTarget.textContent}`)
	}

	render() {
		return <button onClick={this.clicked}>Click Me</button>
	}
}
```

- Event type
  - All events in ReactJS is are children of `SyntheticEvent`
  - React defines `SyntheticEvent` according to W3C spec. So we can refer to MDN doc
- Why did I use `currentTarget` rather than `target`
  - Because `currentTarget` points to the element that binds the listener while `target` points to the element that triggers the event
  - So type of `target`  might not be the 
- clicked function
  - The function is defined in a way called [public class fields syntax](https://babeljs.io/docs/plugins/transform-class-properties/)
  - If we define it in normal way, then inside the function, value of `this` would be undefined
  - If you insist on using traditional function-declaration way, there are 2 workarounds for it:
    - Option1: In constructor, add `this.clicked = this.clicked.bind(this);`
    - Option 2: Bind this in `onClick` like this: `<button onClick={this.clicked.bind(this)}>Click Me2</button>`

### DOM node type hierarchy

```
Element
   |
   |------ HTMLElement
              |
              |------ HTMLButtonElement
              |
              |------ HTMLSelectElement
   
```

### Pass parameter into listener

```tsx
<button onClick={(e) => this.deleteRow(id, e)}>Delete Row</button>
<button onClick={this.deleteRow.bind(this, id)}>Delete Row</button>
```

## `key` in list

```tsx
function ToDos(props: { todos: Array<{ name: string, id: number }> }) {
	return (
		<ul>
			{props.todos.map(todo =>
				// By specifiying key, react can efficiently update changed elements only
				<li key={todo.id}>{todo.name}</li>
			)}
		</ul>
	)
}
```

## SCSS in component

There a `QButton` component example:

```tsx
import React, { MouseEventHandler } from "react";
import './QButton.scss'

type QButtonProps = {
	children: string,
	onClick?: MouseEventHandler<HTMLButtonElement>
}

class QButton extends React.Component<QButtonProps> {

	constructor(props: QButtonProps) {
		super(props);
	}

	render() {
		return <button className="q-button" onClick={this.props.onClick}>{this.props.children}</button>
	}
}

export default QButton
```

```scss
.q-button {
	height: 30px;
	padding-left: 10px;
	padding-right: 10px;
	border-radius: 3px;
	border: 1px solid silver;

	&:active {
		position: relative;
		top: 1px;
	}
}
```

- You can use `QButton` component multiple times on the screen, the scss file would not be referenced multiple times
- In fact, when compiling, all scss files would be compiled into a big one. So you can create as many scss files are you like and there is no need to worry about performance

## Form

### Why can't I change input value?

```tsx
function FormDemo(props: {}) {
	return <input type="text" value="abc" />
}
```

This looks like a normal input node whose initial value is "abc". But you'll find that:

- It's editable on the screen
- But whenever you change its value, it automatically restores to "abc"

The reason is:

- React would render the DOM node in exactly the same way as you told it
- So if you tell react that the input box's value is abc, then its value would always be abc. Even user cannot change it

You can fix it by using `defaultValue` attribute instead of `value` attribute:

```tsx
function FormDemo(props: {}) {
	return <input type="text" defaultValue="abc" />
}
```

Now you have changed the input node from **controlled**  to **uncontrolled**

### Controlled Components

Make component state single source of truth:

- Form nodes' value comes from state
- When nodes' value changes, update state accordingly

#### Checkbox example

```tsx
import React, { ChangeEvent } from "react";

type QCheckboxProps = {
	name: string,
	label: string,
	value: string,
	checkedValues: readonly string[],
	onChange: (event: ChangeEvent<HTMLInputElement>, newCheckedValues: readonly string[]) => void
}

function QCheckbox(props: QCheckboxProps) {

	function onChange(event: ChangeEvent<HTMLInputElement>) {
		let newCheckedValues;
		if (event.target.checked) {
			newCheckedValues = [...props.checkedValues, props.value]
		}
		else {
			newCheckedValues = props.checkedValues.filter(v => props.value !== v)
		}
		props.onChange(event, newCheckedValues);
	}

	return (
		<label>{props.label}<input type="checkbox"
			name={props.name}
			value={props.value}
			checked={props.checkedValues.includes(props.value)}
			onChange={onChange}
		/></label>
	)
}

export default QCheckbox
```

#### Form with various fields example

```tsx

import React, { ChangeEvent, FormEvent } from 'react';
import './FormDemo.scss'
import QSubmitButton from './QSubmitButton';
import QCheckbox from './QCheckbox';

type FormData = {
	input?: string,
	numericInput?: number,
	select?: string,
	multiSelect?: readonly string[], // This has to be a immutable array, otherwise multiple select tag would not accept it as value
	checkboxes: readonly string[],
	radiobox: string,
	textarea: string
}

function FormRow(props: { children: Array<JSX.Element> | JSX.Element }) {
	return <div className="form-row">{props.children}</div>
}

class ControlledFormDemo extends React.Component<FormData, FormData> {

	constructor(props: FormData) {
		super(props)
		this.state = { ...props };
		this.onChange = this.onChange.bind(this);
	}

	onChange(event: ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>, newValue?: any) {
		const target = event.currentTarget as any;
		let value;
		if (newValue) {
			value = newValue;
		}
		else if (target.multiple) {
			value = [];
			const options = (target as HTMLSelectElement).options;
			for (let i = 0; i < options.length; i++) {
				const option = options[i];
				option.selected && value.push(option.value);
			}
		}
		else {
			value = target.value
		}
		this.setState({
			[target.name]: value
		} as any);
	}

	onSubmit = (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		console.info(this.state)
	}

	render() {
		return (
			<form onSubmit={this.onSubmit}>
				<FormRow>
					<input type="text" name="input" value={this.state.input} onChange={this.onChange} />
					<input name="numericInput" type="number" value={this.state.numericInput} onChange={this.onChange} />
				</FormRow>
				<FormRow>
					<select name="select" value={this.state.select} onChange={this.onChange}>
						<option value="a">a</option>
						<option value="b">b</option>
						<option value="c">c</option>
					</select>
					<select multiple={true} name="multiSelect" value={this.state.multiSelect} onChange={this.onChange}>
						<option value="a">a</option>
						<option value="b">b</option>
						<option value="c">c</option>
						<option value="d">d</option>
						<option value="e">e</option>
						<option value="f">f</option>
					</select>
				</FormRow>
				<FormRow>
					<QCheckbox name="checkboxes" value="a" label="a: " checkedValues={this.state.checkboxes} onChange={this.onChange} />
					<QCheckbox name="checkboxes" value="b" label="b: " checkedValues={this.state.checkboxes} onChange={this.onChange} />
					<QCheckbox name="checkboxes" value="c" label="c: " checkedValues={this.state.checkboxes} onChange={this.onChange} />
				</FormRow>
				<FormRow>
					<label>1: <input type="radio" name="radiobox" checked={this.state.radiobox === "radio1"} onChange={this.onChange} value="radio1" /></label>
					<label>2: <input type="radio" name="radiobox" checked={this.state.radiobox === "radio2"} onChange={this.onChange} value="radio2" /></label>
					<label>3: <input type="radio" name="radiobox" checked={this.state.radiobox === "radio3"} onChange={this.onChange} value="radio3" /></label>
				</FormRow>
				<FormRow>
					<textarea name="textarea" onChange={this.onChange}>{this.state.textarea}</textarea>
				</FormRow>
				<QSubmitButton>Submit Me</QSubmitButton>
			</form>
		)
	}
}

export default ControlledFormDemo
```

#### Use [formik](https://formik.org/)

- Frankly speaking, it's not very easy to use controlled component. Especailly for some special ones like checkbox
- [formik](https://formik.org/) would do those verbose things for you. e.g.
  - It provides implementation of `onChange`  for all kinds of form fields
  - It provides convenient API to do form validation

### Uncontrolled component

- `<input type="file" />` is born to be uncontrolled because it's readonly
- DOM node value is single source of truth. State would not keep form nodes' latest value
- You can access DOM node directly like vanilla js

```tsx
class FormDemo extends React.Component {

	input: React.RefObject<HTMLInputElement>;

	constructor(props: {}) {
		super(props);
		this.input = React.createRef();
	}

	onChange = (event: ChangeEvent<HTMLInputElement>) => {
		this.setState({
			value: event.currentTarget.value
		})
	}

	onSubmit = (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		console.info(this.input.current?.value)
	}

	render() {
		return (
			<form onSubmit={this.onSubmit}>
				<input type="text" defaultValue="freedom" ref={this.input} />
				<input type="submit" />
			</form>
		)
	}
}
```

# Advanced Concepts

## Load component lazily

### Real world example

- In order list screen, when we select an order, order detail screen would popup
- In this case, we can lazily load order detail component

### What's behind it

Behind it is dynamic import, which is similar to requirejs, except it `import` method would return a promise

**math.ts**

```typescript
export function add(num1: number, num2: number): number {
	return num1 + num2;
}
```

**test.ts**

```typescript
async function test() {
    // This would load math js file dynamically like requirejs
    // So webpack would not package math.ts & test.ts into the same js file when compiling
	const math = await import("./math");
	console.info(math.add(1, 2))
}

test();
```

### How to

```tsx
const QButton = React.lazy(() => import('./QButton'));
function TestComponent() {
	return (
		<Suspense fallback={<div>Loading....</div>}>
			<QButton>Test</QButton>
		</Suspense>
	)
}
```

### Lazy Loaded Component should ONLY export default component

If in lazy loaded file, multiple components are exported. Then other exported components would also be loaded. You can check [this](https://stackoverflow.com/a/62862177/2334320) for more detail

## Routing

This is done via [React Router](https://reactrouter.com/web)

### Install

```bash
npm install react-router-dom
# This is for typescript
npm i --save-dev @types/react-router-dom
```

### Basic Routing with lazy loading

```tsx
import './App.scss';
import { lazy, Suspense } from 'react';
import { BrowserRouter as Router, Link, Route, Switch } from 'react-router-dom';

const Home = lazy(() => import('./Home'));
const About = lazy(() => import('./About'));
const Demo = lazy(() => import('./Demo'));

function App() {
	return (
		<div className="App">
			<Router>
				<ul>
					<li>
						<Link to="/">Home</Link>
					</li>
					<li>
						<Link to="/about">About</Link>
					</li>
					<li>
						<Link to="/demo">Demo</Link>
					</li>
				</ul>
				<Suspense fallback={<div>Loading...</div>}>
					<Switch>
						<Route exact path="/" component={Home} />
						<Route path="/about" component={About} />
						<Route path="/demo" component={Demo} />
					</Switch>
				</Suspense>
			</Router>
		</div>
	);
}

export default App;
```

- `Router` tag is a wrapper. All routing related tags should be in it. e.g. `Link` tag
- `<Switch>` & `<Route>`
  - Only if current url matches the path specified in `Route`, the Route's component would be rendered
  - `exact` is added to the first path. Because without it, all other paths like `/about` would be matched by it
- Because of the usage of lazy loading:
  - Home/About/Demo would only be loaded when the url navigates to it
  - This would also cause code split. i.e. Home/About/Demo components would not be bundled into the main js file. Instead, they would all have separate JS files

But if you intends to load Home/About/Demo in one shot when the main page opens, you can:

```tsx
import './App.scss';
import { BrowserRouter as Router, Link, Route, Switch } from 'react-router-dom';
import Home from './Home';
import About from './About';
import Demo from './Demo';

function App() {
	return (
		<div className="App">
			<Router>
				<ul>
					<li>
						<Link to="/">Home</Link>
					</li>
					<li>
						<Link to="/about">About</Link>
					</li>
					<li>
						<Link to="/demo">Demo</Link>
					</li>
				</ul>
				<Switch>
					<Route exact path="/" component={Home} />
					<Route path="/about" component={About} />
					<Route path="/demo" component={Demo} />
				</Switch>
			</Router>
		</div>
	);
}

export default App;
```

