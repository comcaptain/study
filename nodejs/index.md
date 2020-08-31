### Index

- [nut.js](./nutjs.md) A library that has following features:
  - Control your keyboard
  - Control your mouse
  - Search for image on the screen
  
- [Puppeteer](./Puppeteer.md) A library that has following features:
  - Control Chromium like selenium
  - With the help of `connect`, it can control the Chromium browser you are using. e.g. edge
  
- [Windows 10 display scale](./Windows 10 display scale.md)
  
  - Tells you how to get correct pixel location when windows 10 display scale is not 100%
  
- [yarn add from github](https://stackoverflow.com/a/43636577)

  ```bash
  # branch/tag
  yarn add ssh://github.com/fancyapps/fancybox#3.0
  # commit
  yarn add https://github.com/fancyapps/fancybox#5cda5b529ce3fb6c167a55d42ee5a316e921d95f
  ```

- Enhance console to add timestamp and level

  ```javascript
  require('console-stamp')(console, { pattern: 'yyyy-MM-dd HH:MM:ss.l' });
  ```

- Get input from command line

  ```javascript
  const readline = require('readline').createInterface({
      input: process.stdin,
      output: process.stdout
  });
  function question(questionText) {
      return new Promise(resolve => readline.question(questionText + " > ", resolve));
  }
  const answer = await question("Is this search area good for you? Y/N");
  ```

  

