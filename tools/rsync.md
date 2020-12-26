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