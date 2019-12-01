### How electron works

The "main" method is written in nodejs, and the process that runs the "main" method is called **main process**. In the "main" method, Chromium window can be opened, and each Chromium window is run in its own process (NOT thread). This kind of process is called **renderer process**.

Open Chromium window example:

```javascript
win = new BrowserWindow({
	width: 800,
	height: 600,
	webPreferences: {
		nodeIntegration: true
	}
})

// You can load web page from webserver
win.loadURL('http://192.168.162.111:8080/index.html')
// Or you can load local html file in electron program
win.loadFile("index.html")
```

### JS referenced in loaded html page

In the js referenced in loaded html page, you can:
- Write whatever code that works in normal Chrome browser
- Write code that uses nodejs API (only if `webPreferences.nodeIntegration` option is true when window is opened). This means that you can use full power of nodejs and do things like visiting local file system, visiting local DB... In sum, you can do whatever a backend program can do
- Fully control the Chromium window by invoking API provided by electron. With the API, you can do things like below:
  - Lock the Chromium window and make it not moveable
  - Move the Chromium window to a specific position
  - Change Chromium window's width and height
  - Open a modal Chromium window
  - Open a frameless Chromium window: Remove all parts outside the html rendering window, including the Minimize, Maximize and Close button
  - ...
- Use some other electron APIs. e.g. Send notification to the user

### Communication between processes

With `ipcRenderer` and `ipcMain`, data can be transferred from main process to renderer process and renderer process to main process. So you can do things like below:\
- The main process is a nodejs program, i.e. it's not run in Chromium browser. So it does not have limitations like memory. And you can do heavy work inside main process and pass result to renderer process
- With main process as the bridge, one Chromium window can communicate with another Chromium window freely! You can do things like this: After a button is clicked in one window, close all opened Chromium windows

### Tabs, back button and other things that Chrome provides

Unluckily, electron does not support native implementation for any of them, even tabs. You have to implement them by yourself (of course, you can find some 3rd-party js widgets)

### Two main implementation routes

**1st route: Put front-end code in electron**

Put all html/js/css code in electron, electron would package them as installer and install them in user's PC

*Benefits:*
- html/js/css code is all in user's PC, so a page would be loaded more quickly
- No need to worry about issue like below:
  - Browser cache issue
  - Size of referenced js files
- Forces programmer to separate front-end and backend code: Webserver only provides RESTful JSON API and front-end code does all the rendering work

*Drawbacks:*
- Since html is in user's PC, so when electron loads it, it's similar to opening a local html file in Chrome. For the limitation of cross-origin security rules, ajax cannot to webserver directly. There are two ways to work around this:
  - Use electron/nodejs API to send HTTP request to webserver directly
  - Enable cross-origin visit in webserver, but this would bring security issues
- Front-end code is a bit harder to update. Of course, like normal PC software, electron provides way for you to set up an update server and allow user to do auto-update. But this is harder compared to normal web page

**2nd route: Put front-end code in electron**

Put all html/js/css in webserver. `main.js` is mainly used to start the Chromium windows, and its code does not need much update. In this case, if `main.js` is not updated,  client-side program only needs to be updated when Chromium needs to be updated.

*NOTE: Even if js code is in webserver, it can still invoke the nodejs and electron API*

### Chromium version

> Electron's version of Chromium is usually updated within one or two weeks after a new stable Chromium version is released, depending on the effort involved in the upgrade.
> When a new version of Node.js is released, Electron usually waits about a month before upgrading in order to bring in a more stable version.
> In Electron, Node.js and Chromium share a single V8 instance—usually the version that Chromium is using. Most of the time this just works but sometimes it means patching Node.js.

And with electron, user's browser version is very stable and we no longer ened to worry about the browser compatibility issue

### Use TypeScript and other modern web frameworks

Electron has a real browser inside it, so theoretically speaking, all js frameworks can be used.

With the help of TypeScript, js can have types like java and compiler/editor would be able to report compiler error for things like type-mismatch!
