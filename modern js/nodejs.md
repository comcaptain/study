# Package Manager

## NPM or Yarn

There is a good comparison article: [Choosing Between NPM and Yarn | Engineering Education (EngEd) Program | Section](https://www.section.io/engineering-education/npm-vs-yarn-which-one-to-choose/)

For now. I'll choose NPM because it's kind of more popular and I'm not doing nodejs development

## NPX

A new tool in npm. With it, you can execute a nodejs without installing it

```bash
# Even though you didn't ever install create-react-app. This command would automatically install for u and execute it
# NOTE: Unlike npm install, this installation is not a permanent installation
npx create-react-app my-app
```

## Run 2 commands sequentially

e.g. Build react app and then serve build folder:

1. Add following line into `scripts` iof `package.json`:

   ```json
   "bs": "react-scripts build && npx serve -s build"
   ```

2. Then in app folder you can run `npm run bs` to do the build start serve build folder in a http server
