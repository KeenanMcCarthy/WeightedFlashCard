#!/bin/bash

#############################################
## This script resets all of the flashcard ##
## weights to 1.0                          ##
#############################################

input="${PWD}/input.txt"
output="${PWD}/new_input.txt"
counter=1
while IFS= read -r line
do 
  if [ $counter -eq 3 ]
  then
    echo "1.0"
    counter=0
  else 
    echo "$line"
  fi
  counter=$((counter+1))
done < "$input" > "$output"
mv "$output" "$input"
