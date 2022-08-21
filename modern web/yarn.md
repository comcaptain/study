## Installation of latest yarn

It's explained [here](https://yarnpkg.com/getting-started/install) in official doc but is not very straightforward.

1. `sudo corepack enable`

   - Then yarn is already there, but if you run `yarn -v`, you'll find the version is 1.xxx

   - To install latest yarn, following step is needed

2. `yarn set version stable -g`

   - Not sure whether `-g` is enabled. But it works after running it

## PnP

- It's short for `Plug'n'Play`, i.e. plug and play
- You can find more detail in [official doc](https://yarnpkg.com/features/pnp)
- The idea is to replace `node_modules` with single `.pnp.cjs` file
- With it, the size of your dependent packages would be greatly reduced and duplicate depencies would be avoided
- It's natively support by [a lot of framework](https://yarnpkg.com/features/pnp#native-support), including `create-react-app`
  - For `create-react-app`, running `yarn create react-app xxx` would use PnP by default

### Make VS code support PnP

[Official manual](https://yarnpkg.com/getting-started/editor-sdks)