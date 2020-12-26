# What's difficult?

- There is no way to run flutter in iOS (simulator or real iPhone) on Windows
- My Macbook Air is too slow for coding smoothly

# Goal

- Code in Windows
- Run the code in my iPhone

# Plan A (NG)

## Idea

- Create a shared directory in Windows
- Open the shared directory in Mac
- Create a new flutter app in the shared directory under Mac using IntelliJ
- Connect to iPhone and run the app on iPhone using command line (keep IntelliJ closed)
- In windows, run IntelliJ to load the flutter project in shared directory
- Do some code changes in windows
- Use command line in Mac to see whether it works
- If it works, then use [External Tools—IntelliJ IDEA (jetbrains.com)](https://www.jetbrains.com/help/idea/settings-tools-external-tools.html) to bind a shortcut for a command line tool
- The command line tool would automatically update a file in shared folder
- In Mac, write a shell to keep monitoring the file updated above. Once it's updated, then do hot reload

## Experiment

1. Create `C:\git\flutter\ClearMoney` and share in Windows
2. In mac's command line, the shared folder can be visited under `/Volumes`
3. Started app in mac but there are two issues for now:
   - **Because it's in shared drive, the xcode build time is doubled**
   - **There is no standalone command to reload the app**
4. Now use Windows IntelliJ to open the project
   - There are compiler errors
   - **It seems that that's because flutter packages are not recognized, cannot figure out how to resolve it. Quit**

# Plan B

## Idea

- Use `rsync` to sync file between Mac and Windows
- Only sync files that matters (I can refer to flutter gitignore file about which files should be synced)