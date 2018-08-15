#!/bin/bash

if [ $1 == "s" ]
then
    {
        echo "input_files/input_$1.txt"
        echo $1
        echo "output.txt"
        echo $2
    } | python floating_point.py #> /dev/null
else
    {
        echo "input_files/input_$1.txt"
        echo $1
        echo "output.txt"
        echo $2
    } | python floating_point.py #> /dev/null
fi

printf "Calling diff on ACTUAL vs EXPECTED output...\n"

diff output.txt expected_outputs/output_s.txt

echo ""
