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