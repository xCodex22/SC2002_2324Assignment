#!/bin/bash

# Define source file
src="ScheduleDB/12346/2024/01/01.csv"

# Loop from 1 to 31
for i in $(seq -w 1 31); do
    # Construct the destination filename with leading zero
    dest="ScheduleDB/12346/2024/01/$i.csv"
    
    # Copy the source file to the destination
    cp "$src" "$dest"
    
    # Check if the copy was successful
    if [ $? -eq 0 ]; then
        echo "Successfully copied $src to $dest"
    else
        echo "Failed to copy $src to $dest"
    fi
done

