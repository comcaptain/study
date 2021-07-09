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