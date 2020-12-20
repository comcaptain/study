# References

[Content scripts - Chrome Developers](https://developer.chrome.com/docs/extensions/mv2/content_scripts/)

# When should it be used

When you want to access the DOM nodes of the page that user is visiting

# How is it different from normal js file referenced in web page?

It's run in an isolated sandbox. So it cannot interact with web page's existing js

# How can it be injected?

## Inject in manifest.json

```json
 "content_scripts": [
   {
     "matches": ["http://*.nytimes.com/*"],
     "css": ["myStyles.css"],
     "js": ["contentScript.js"]
   }
 ],
```

## Inject js code or js file in extension js code

```javascript
chrome.runtime.onMessage.addListener(
  function(message, callback) {
    if (message == “changeColor”){
      chrome.tabs.executeScript({
        code: 'document.body.style.backgroundColor="orange"'
      });
    }
  });
```

```javascript
chrome.runtime.onMessage.addListener(
  function(message, callback) {
    if (message == “runContentScript”){
      chrome.tabs.executeScript({
        file: 'contentScript.js'
      });
    }
  });
```

