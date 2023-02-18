## Basics

### Commands

```bash
# Create a new workspace
npx create-nx-workspace@latest
# Add a new app
npx nx g @nrwl/react:app app-name
# Add a new react lib
npx nx g @nrwl/react:lib common-ui
# Add a new js lib
npx nx g @nrwl/js:lib products
# Add a new component and export
npx nx g @nrwl/react:component banner --project=common-ui --export


# Check graph
nx graph
# Run test
nx test common-ui
# Run lint
nx lint common-ui
# Change something without commit, then check what's influenced
nx affected:graph --base master # base is to specify the trunck branch. Default value is main
# Run test on affected components
nx affected --target=test --base master # target can be any task, like build
```

### [Integrated Repos vs. Package-Based Repos | Nx](https://nx.dev/concepts/integrated-vs-package-based)

- Package-based repo is to make each underlying repo independent. It works like a folder containing many independent repos

### Internal Dependency

```typescript
// nx-demo is root package name in package.json
// common-ui is dependent library name in project.json
import { Banner } from '@nx-demo/common-ui';
```



### Task Caching

nx would cache task execution output according to input

- Input: any environment details and all the source code of the projects and dependencies affecting your project
- Output: the terminal output created by the task, as well as any files created by the task

The output would be replayed, including things like writing files. So when running build twice, the 2nd time would be way faster

## VS code setup

To make test case work, you should install [Jest Runner](https://marketplace.visualstudio.com/items?itemName=firsttris.vscode-jest-runner)
