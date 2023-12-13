#!/bin/bash

# Check if at least one file is provided
if [ "$#" -lt 1 ]; then
    echo "Usage: $0 file1 [file2 ...]"
    exit 1
fi

# Merge the files
awk '
    {
        # Append lines from each file
        a[FNR] = (FNR in a ? a[FNR] OFS : "") $0
    }
    END {
        # Output the combined lines
        for (i = 1; i <= FNR; i++) {
            print a[i]
        }
    }
' "$@"
