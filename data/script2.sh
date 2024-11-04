#!/bin/bash

# Define source file
src="ScheduleDB/12346/2024/01/01.csv"

# Loop from 2 to 12 for the month directories
for month in $(seq -w 2 12); do
    # Construct the destination directory and file name
    dest_dir="ScheduleDB/12346/2024/$month"
    dest_file="$dest_dir/01.csv"

    # Create the destination directory if it doesn't exist
    
    # Copy the source file to the destination
    cp "$src" "$dest_file"

    # Check if the copy was successful
    if [ $? -eq 0 ]; then
        echo "Successfully copied $src to $dest_file"
    else
        echo "Failed to copy $src to $dest_file"
    fi
done

