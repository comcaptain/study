# References

- [How To Use Rsync to Sync Local and Remote Directories | DigitalOcean](https://www.digitalocean.com/community/tutorials/how-to-use-rsync-to-sync-local-and-remote-directories)

# Summary

- An enhanced scp

- It can find differences between two folders and only copy changed files

- Unluckily it's an ad-hoc tool and cannot auto-sync when there are file changes. But you can easily achieve it using [this way](https://stackoverflow.com/a/40525217)

  ```bash
  while inotifywait -r -e modify,create,delete,move /directory; do
      rsync -avz /directory /target
  done
  ```

# Examples

## Sync two folders locally

```bash
# the / behind dir1 is important. If it's removed, then dir1 would be copied under dir2
# You can add `-v` option to show detail
rsync -aP dir1/ dir2
```

## Sync one folder to another machine via ssh

```bash
rsync -aP ../git/study/ tony@192.168.162.118:~/tmp/study_mirror
```

## Keep two folders in Sync (single-direction)

```bash
#!/bin/bash

while inotifywait -r -e modify,create,delete,move ~/c/git/study/; do
	rsync -aP --delete ~/c/git/study/ ~/c/tmp/study_mirror
done
```

## Sync two folders with gitignore

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



# Options

- **-r, --recursive**  recurse into directories
- **-a**  
  - You should use this option in most cases, it's equivalent to `-rlptgoD`
  - It include recursive, copy symbol links, keep permissions...
- **-v** verbosely display the data
- **-P**
  - It combines the flags `--progress` and `--partial`
  - Progress bar would be displayed and interrupted transfer can be resumed
- **--delete**
  - By default rsync would not delete file from target folder
  - With this option, target folder's files would be deleted if local files are removed
- **--dry-run**