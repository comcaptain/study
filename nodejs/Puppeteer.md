### References

- [github link](https://github.com/puppeteer/puppeteer)
- [API](https://github.com/puppeteer/puppeteer/blob/v5.2.1/docs/api.md)

## [puppeteer vs puppeteer-core](https://github.com/puppeteer/puppeteer/blob/main/docs/api.md#puppeteer-vs-puppeteer-core)

`puppeteer` includes Chromium, while `puppeteer-core` does not include browser, so you can use your own browser

And if you use `puppeteer`, you should use:

```javascript
const puppeteer = require('puppeteer-core');
```

rather than

```javascript
const puppeteer = require('puppeteer');
```



## Connect to your own browser

You can use [`puppeteer.launch`](https://github.com/puppeteer/puppeteer/blob/main/docs/api.md#puppeteerlaunchoptions)  to start a new browser instance, which would not include your daily-used browser's plugins, cookies, logged users... i.e. It's like a brand-new browser.

And if `puppeteer-core` is used, be sure to set `executablePath` option when invoking `launch` method.

To make puppeteer connect to your daily-used browser, with all your cookies, plugins... You should use `puppeteer.connect`:

```javascript
// Connect to existing browser
// To do this, you have to add --remote-debugging-port=9222 when starting up browser
// In mac, you can do this by executing following command:
// /Applications/Microsoft\ Edge.app/Contents/MacOS/Microsoft\ Edge --remote-debugging-port=9222 &
// Then visit url http://127.0.0.1:9222/json/version
// Get value of webSocketDebuggerUrl and then paste below:
const browserWSURL = "ws://127.0.0.1:9222/devtools/browser/099b8efb-9a60-4c4a-a76d-13e08bb141cf";
console.info(`Going to connect to browser ${browserWSURL}`)
const browser = await puppeteer.connect({
    browserWSEndpoint: browserWSURL,
    defaultViewport: null
});
```

## Open a page

```javascript
const page = await browser.newPage();
// This would wait until javascript's onload is fired
await page.goto(url);
```

## Select element(s) and execute some js code on it

```javascript
// `e.pause()` would be executed in browser
// Like querySelector, only 1st element would be selected
await page.$eval("video", e => e.pause());
// Like querySelectorAll, all elements would be selected
const videoNames = await page.$$eval(VIDEO_NAME_SELECTOR, nodes => nodes.map(n => n.textContent.trim()));
```

## Select element(s) without executing js code

```javascript
// Like querySelector, only 1st element would be selected
// $$ would select all elements
const nextButton = await page.$(NEXT_PAGE_SELECTOR);
await nextButton.click();
```

## Wait until certain condition can be fulfilled

```javascript
// selector is VIDEO_NAME_SELECTOR
// firstVideoName is videoNames[0]
await page.waitForFunction((selector, firstVideoName) => {
    // This lambda can also be marked as async, then you can use await inside it
    return document.querySelector(selector).textContent.trim() !== firstVideoName;
}, {}, VIDEO_NAME_SELECTOR, videoNames[0]);
```























