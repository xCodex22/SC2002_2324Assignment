#!/bin/bash

# Define source directory and file pattern
src_dir="ScheduleDB/12346/2024/01"

# Loop from 2 to 12 for the month directories
for month in $(seq -w 2 12); do
    # Loop from 1 to 31 for the day files
    for day in $(seq -w 1 31); do
        # Construct the source file path
        src_file="$src_dir/$day.csv"
        
        # Construct the destination directory and file name
        dest_dir="ScheduleDB/12346/2024/$month"
        dest_file="$dest_dir/$day.csv"

        # Create the destination directory if it doesn't exist
        mkdir -p "$dest_dir"

        # Check if the source file exists before copying
        if [[ -f "$src_file" ]]; then
            cp "$src_file" "$dest_file"
            # Check if the copy was successful
            if [ $? -eq 0 ]; then
                echo "Successfully copied $src_file to $dest_file"
            else
                echo "Failed to copy $src_file to $dest_file"
            fi
        else
            echo "Source file $src_file does not exist, skipping."
        fi
    done
done

