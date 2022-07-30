## Reference links
- [Official website](https://www.crankuptheamps.com/)
- [Dashboard in official website](https://eval.crankuptheamps.com/)
	- Enter your email to register an account. It's free

## Installation
- The download link can be found in [Dashboard in official website](https://eval.crankuptheamps.com/)
- Only linux is supported
- You can see readme in downloaded zip file to learn how to start it up
	- There is no need to do any installation, just generate a config and run it

## Admin GUI
After starting up the server, you can visit port 8085 to access its admin website.

8085 is retrieved from following part of config file:
```xml
<Admin>
	<InetAddr>localhost:8085</InetAddr>
</Admin>
```

The "SQL" is not clickable and it would give instructions about how to set it up:
