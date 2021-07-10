# Boilerplate

## Basic commands

### Create a react react project

```bash
# Create a react app called my-app
npx create-react-app my-app --template typescript
cd my-app
```

### Start development server

```bash
# in app folder
npm start
# This would start a simple http server at http://localhost:3000/
# Page would reload automatically when code changes
```

### Build code

```
npm build
```

This would build do the compilation and output to `/build` folder

- All html/js/css files would be compressed and added hash suffix
- Entry point is index.html
- You can start a http server in index.html to verify

## Add support for scss

[Adding a Sass Stylesheet | Create React App (create-react-app.dev)](https://create-react-app.dev/docs/adding-a-sass-stylesheet/)

```bash
npm install node-sass --save
```

Then rename all css to scss and update references in tsx files

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

### Render a component

```tsx
// Attributes would be converted to properties attribute and passed to constructor/function of component
const element = <Welcome name="Sara" />;
```

### Properties is immutable

Components should **never** modify properties value
