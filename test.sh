#!/bin/bash

# Check if input file is provided
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 filename"
    exit 1
fi

input_file="$1"
base_name="${input_file%.*}"

# Initialize the first output file for content before the first "# Built with:"
filecount=0
outfile="${base_name}-${filecount}.txt"

# Split the file using awk
awk -v base_name="$base_name" -v outfile="$outfile" '
    /^# Built with:$/ {
        close(outfile)
        outfile = base_name "-" ++filecount ".txt"
    }
    {
        print > outfile
    }
' "$input_file"
