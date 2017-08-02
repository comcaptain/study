#!/bin/bash

let "abc = ((a = 9, 15 / 3))"

echo "$abc" # print 5
echo "$a" # print 9