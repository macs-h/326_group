#!/bin/bash

if [ $1 == "s" ]
then
    {
        echo "input_files/input_$1.txt"
        echo $1
        echo "output.txt"
        echo $2
<<<<<<< HEAD
    } | python floating_point.py #> /dev/null
=======
    } | python theWholeShbang.py > /dev/null
>>>>>>> 53ed6caf0e7efc8afa624a91af11de2e2271ad4b
else
    {
        echo "input_files/input_$1.txt"
        echo $1
        echo "output.txt"
        echo $2
<<<<<<< HEAD
    } | python floating_point.py #> /dev/null
=======
    } | python theWholeShbang.py > /dev/null
>>>>>>> 53ed6caf0e7efc8afa624a91af11de2e2271ad4b
fi

printf "Calling diff on ACTUAL vs EXPECTED output...\n"

diff output.txt expected_outputs/output_s.txt

echo ""
