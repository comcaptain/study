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
- I planned to run `flutter run`  in  ssh from Windows to Mac but it doesn't work
- So the plan for now is to use `flutter run` in Mac and when it's necessary to do hot-reload, just press the key in Mac
- Future plan is:
  - In IntelliJ, use  [External Tools—IntelliJ IDEA (jetbrains.com)](https://www.jetbrains.com/help/idea/settings-tools-external-tools.html) to bind a shortcut for a command line tool
  - In the command line tool, update a file's content (which would be synced to mac)
  - Then mac has a watch program, which would monitor change of the file mentioned in previous step
  - Once it's changed, then send its content to terminal that runs flutter

# Create a new project

1. Create a new flutter project in Windows IntelliJ, then add into git

   - By default `core.autocrlf` would be true, which would convert \n to \r\n automatically
   - To fix it: `git config --global core.autocrlf input`

2. Create a new flutter project in Mac IntelliJ, then add into git

3. In mac, run app in iPhone

   - Connect iPhone to Mac and run the project in IntelliJ. It would fail with some errors
   - Start Xcode and open the `ios` folder in project root directory
   - In Xcode, run the project and you would find following error:
     - "Signing for Runner requires a development team". You can fix it in this way: on the left side, select `Runner project`. Then in right side, select `Signing & Capbabilities`, then choose a team for the project
   - Fix the error and run again, now it would complain it cannot launch to iPhone
   - Then in iPhone: `设置-通用-描述文件与设备管理`, trust my certificate
   - Run in Xcode again and it works now

4. Exit IntelliJ and Xcode in mac

5. Commit all changes in mac and push to origin

6. Then in Windows, configure the same git remote. Then do pull. There should be nearly no differences except for some iOS config changes

7. Create following sync file and run it in win10 built-in linux:

   ```bash
   #!/bin/bash
   
   function doSync()
   {	
   	# The idea is stolen from https://stackoverflow.com/a/50059607
   	# Since .gitignore is not fully compatible with rsync, so the idea is to list all ignored files using git command, and then feed them into rsync
   
   	# Get remote ignored files, so that when doing rsync those files are not deleted
   	ssh tony@192.168.162.118 "git -C ~/workspace/flutter/clear_money ls-files --exclude-standard -oi --directory" > ignores.tmp
   
   	# Get local ignored files
   	git -C /home/tony/c/git/flutter/clear_money ls-files --exclude-standard -oi --directory >> ignores.tmp
   
   	# Do sync
   	rsync -ahP --delete \
   	    --exclude .git --exclude-from="ignores.tmp" \
   	    /home/tony/c/git/flutter/clear_money/ tony@192.168.162.118:~/workspace/flutter/clear_money
   }
   
   echo "Doing initial sync..."
   doSync
   echo "Finished initial sync"
   while inotifywait -r -e modify,create,delete,move /home/tony/c/git/flutter/clear_money; do	
   	echo "Changes detected, doing sync..."
   	doSync
   	echo "Sync finished"
   done
   ```

# Preparations before starting development

- Start up the sync shell
- Connect iPhone to Mac
- `flutter run`

# How to verify rsync works well?

1. Git clone the repo from remote to a temp folder. This copy should only contain not-ignored files
2. Remove .gitignore and create a commit
3. Do rsync from workspace to this temp repo. If there is any diff, then there is bug



























