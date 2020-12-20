# References:

- [Getting started - Chrome Developers](https://developer.chrome.com/docs/extensions/mv2/getstarted/)

# Create an empty plugin and import it

Create a file called `manifest.json`:

```json
{
  "name": "Getting Started Example",
  "version": "1.0",
  "description": "Build an Extension!",
  "manifest_version": 2
}
```

And put it in a directory.

Then import it:

1. Navigate to `chrome://extensions`
2. Enable developer mode
3. Click `Load Unpacked` to load the directory you created above

# Add a background js file and reload plugin

`manifest.json` is entry point of the plugin. So important components like background js file should be registered in `manifest.js`

## Create background.js

```javascript
// onInstalled is fired when the extension is first installed
chrome.runtime.onInstalled.addListener(function() {
  chrome.storage.sync.set({color: '#3aa757'}, function() {
    console.log("The color is green.");
  });
});
```

Place it in root of plugin directory

## Register in manifest.js

```json
{
  "name": "Getting Started Example",
  "version": "1.0",
  "description": "Build an Extension!",
  "permissions": ["storage"],
  "background": {
    "scripts": ["background.js"],
    "persistent": false
  },
  "manifest_version": 3
}
```

- Since storage API is used, you must declare for it in `permissions` entry

## Reload

1. Click the reload button in extensions page
2. Then you'll see a `background page` link. Click it would open a developer tool console printing `The color is green`

# Add a popup

## Create the popup.html

```html
<!DOCTYPE html>
<html>
  <head>
    <style>
      button {
        height: 30px;
        width: 30px;
        outline: none;
      }
    </style>
  </head>
  <body>
    <button id="changeColor"></button>
  </body>
</html>
```

## Register in manifest.json

```json
"page_action": {
	"default_popup": "popup.html",
	"default_icon": {
		"16": "images/get_started16.png",
		"32": "images/get_started32.png",
		"48": "images/get_started48.png",
		"128": "images/get_started128.png"
	}
},
```

And also specify the icon

# Only activate plugin for certain url or certain element matches

This needs [declarativeContent](https://developer.chrome.com/declarativeContent/) API

## Add permission in manifest.json

```json
"permissions": ["declarativeContent", "storage"],
```

## Add rules

```javascript
chrome.runtime.onInstalled.addListener(function ()
{
	// ....
	// Only enable the plugin for specific urls (you can also enable plugin only if a css selector has matched element)
	// removeRules(undefined, callback) is the standard way to set rules
	// this requires `declarativeContent` permission
	chrome.declarativeContent.onPageChanged.removeRules(undefined, function ()
	{
		const urlRule = {
			conditions: [new chrome.declarativeContent.PageStateMatcher({
				pageUrl: { hostEquals: 'developer.chrome.com' }
			})],
			actions: [new chrome.declarativeContent.ShowPageAction()]
		}
		chrome.declarativeContent.onPageChanged.addRules([urlRule]);
	})
});
```

# Add JS to popup.html

It's same as normal web page:

1. Add reference in popup.html

   ```html
   <script src="popup.js"></script>
   ```

2. Create popup.js

   ```javascript
   const changeColor = document.getElementById("changeColor");
   chrome.storage.sync.get('color', data =>
   {
   	changeColor.style.backgroundColor = data.color;
   	changeColor.setAttribute("value", data.color);
   })
   ```

# Inject js code to current page in popup.js

## Add permission to manifest.json

```json
"permissions": ["activeTab", "declarativeContent", "storage"],
```

## Use `chrome.tabs` API

```javascript
changeColor.addEventListener("click", e =>
{
	const color = e.target.value;
	chrome.tabs.query({ active: true, currentWindow: true }, tabs =>
	{
		const tab = tabs[0];
		chrome.tabs.executeScript(tab.id, { code: `document.body.style.background = "${color}";` });
	})
})
```

# Add configure options page

It's very similar to adding popup page

## Register in manifest.json

```json
"options_page": "options.html",
```

## Use `storage` API to store configurations

options.html

```html
<!DOCTYPE html>
<html>

<head>
	<style>
		button {
			height: 30px;
			width: 30px;
			outline: none;
			margin: 10px;
		}
	</style>
</head>

<body>
	<div id="buttonDiv">
	</div>
	<div>
		<p>Choose a different background color!</p>
	</div>
</body>
<script src="options.js"></script>

</html>
```

options.js

```javascript
let page = document.getElementById('buttonDiv');
const kButtonColors = ['#3aa757', '#e8453c', '#f9bb2d', '#4688f1'];
function constructOptions(kButtonColors)
{
	for (let item of kButtonColors)
	{
		let button = document.createElement('button');
		button.style.backgroundColor = item;
		button.addEventListener('click', function ()
		{
			chrome.storage.sync.set({ color: item }, function ()
			{
				console.log('color is ' + item);
			})
		});
		page.appendChild(button);
	}
}
constructOptions(kButtonColors);
```





























































