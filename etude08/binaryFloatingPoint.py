from array import *

# MAIN

# input_file = input("Enter input filename: ")
# input_p = input("Precision (s for single, d for double): ")
# output_file = input("Enter output filename: ")
# output_p = input("Precision (s for single, d for double): ")
input_file = 'input.txt'
input_p = 's'
output_file = 'output.txt'
output_p = 's'

bytes = []
numberArray = []
with open(input_file,"rb") as numbers:
    write_file = open(output_file, 'wb')
    byte = numbers.read(4)
    # print("firstByte",checkFirstByte(byte))
    # byte = numbers.read(1)
    # fraction = numbers.read(3)


    # print(bin(int(fraction))[2:])
    while byte != b"":
        # for i in range(4):
        #     bytes.append(bin(byte[i]))

        fraction = int.from_bytes(byte, 'big')
        byte = numbers.read(4)
        number = bin(int(fraction))[2:].zfill(32)

        numberArray.append(number)
        # look! heres how you mask thing ( now lets never use it agan)
        # print(number)
        # print(bin(2**31))
        # sign = int(bin(2**31),2) & int(number,2)
        # print(int(bin(2**3),2) & int(bin(2**3),2))
        # print(bin(sign))
    for number in numberArray:
        # string masking !
        print("number", number)
        sign = number[:1]
        print("sign:", sign)
        exponent = number[1:8]
        print("exponent:", exponent)
        fraction = number[8:]
        print("fraction:", fraction)


        #Do things with our binary strings

        # exponent stuff
        # newExponent = ((int(exponent, 2) - 64) << 2)
        # finds the amount that the fraction needs to be shifted left
        count = 1
        for b in fraction:
            if b == '1':
                break
            count += 1
        print("count", count)
        newExponent = ((int(exponent, 2) - 64) << 2) - count + 127
        newExponent = bin(newExponent)[2:].zfill(8)
        print("newExponent",newExponent)

        # fraction stuff
        newFraction = fraction[count:].ljust(23,'0')
        print("newFraction", newFraction)


        #write to file
        signedExponent = sign + newExponent[:-1]
        print("first byte", bin(int(signedExponent,2)))
        print(signedExponent.encode())
        bin_array = array('B')
        bin_array.append(int(signedExponent,2))
        bin_array.append(int(newExponent[-1:]+newFraction[:7],2))
        bin_array.append(int(newFraction[7:15],2))
        bin_array.append(int(newFraction[15:], 2))
        bin_array.tofile(write_file)


        print("")

    write_file.close()