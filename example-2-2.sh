#!/bin/bash

LOG_DIR=/var/log

cd $LOG_DIR

# Use empty to cleanup its original content
cat /dev/null > messages
cat /dev/null > wtmp

echo "Logs cleaned up."

# A bare "exit"(no parameter) returns the exit status of the preceding command
exit