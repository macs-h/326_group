from array import *

# CONSTANTS

BYTE_SIZE = {"s":4, "d":8}
BIT_SIZE = {"s":32, "d":64}
EXP_BIAS = {"s": 127, "d": 1023}
EXP_MAX = {'s':255, "d":2047}
EXP_SIZE = {"s": 8, "d": 11}
FRAC_SIZE = {"s": 23, "d": 52}

# MAIN

# input_file = input("Enter input filename: ")
# input_p = input("Precision (s for single, d for double): ")
# output_file = input("Enter output filename: ")
# output_p = input("Precision (s for single, d for double): ")
input_file = 'input_d.txt'
input_p = 'd'
output_file = 'output.txt'
output_p = 's'

bytes = []
numberArray = []
with open(input_file,"rb") as numbers:
    write_file = open(output_file, 'wb')
    byte = numbers.read(BYTE_SIZE[input_p])
    # print("firstByte",checkFirstByte(byte))
    # byte = numbers.read(1)
    # fraction = numbers.read(3)


    # print(bin(int(fraction))[2:])
    while byte != b"":
        # for i in range(4):
        #     bytes.append(bin(byte[i]))

        fraction = int.from_bytes(byte, 'big')
        byte = numbers.read(BYTE_SIZE[input_p])
        number = bin(int(fraction))[2:].zfill(BIT_SIZE[input_p])

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
        # if input_p == 's':
        newExponent = ((int(exponent, 2) - 64) << 2) - count + EXP_BIAS[output_p]
        # else:
        #     print(int(exponent,2))
        #     newExponent = ((int(exponent, 2) - 512) << 2) - count + EXP_BIAS[output_p]

        if newExponent < 0:
            newExponent = "-" + bin(newExponent)[3:].zfill(EXP_SIZE[output_p])
        else:
            newExponent = bin(newExponent)[2:].zfill(EXP_SIZE[output_p])
        print("newExponent",newExponent)

        # fraction stuff
        newFraction = fraction[count:].ljust(FRAC_SIZE[output_p],'0')
        print("newFraction", newFraction)

        print("exp",int(newExponent,2),"frac",int(newFraction,2))

        if int(newExponent, 2) > EXP_MAX[output_p]:
            print("tooLarge caught: converting to Infinity")
            bin_array = array('B')
            if output_p == 's':
                newExponent = bin(255)[2:]
                newFraction = ''.ljust(23,'0')
                # each of these appends a byte to the array
                bin_array.append(int(sign + newExponent[:-1], 2))
                bin_array.append(int(newExponent[-1:] + '0000000', 2))
                bin_array.append(0)
                bin_array.append(0)
            else:
                newExponent = bin(2047)[2:]
                newFraction = ''.ljust(52,'0')

                bin_array.append(int(sign + newExponent[:-4], 2))
                bin_array.append(int(newExponent[-4:] + '0000', 2))
                bin_array.append(0)
                bin_array.append(0)
                bin_array.append(0)
                bin_array.append(0)
                bin_array.append(0)
                bin_array.append(0)

            bin_array.tofile(write_file)

        if int(newExponent, 2) < 0:
            print("tooSmall caught: converting to Zero")
            bin_array = array('B')
            if output_p == 's':
                newExponent = ''.ljust(8,'0')
                newFraction = ''.ljust(23, '0')

                bin_array.append(int(sign + '0000000', 2))
                bin_array.append(0)
                bin_array.append(0)
                bin_array.append(0)
            else:
                newExponent = ''.ljust(11, '0')
                newFraction = ''.ljust(52, '0')

                bin_array.append(int(sign + '0000000', 2))
                bin_array.append(0)
                bin_array.append(0)
                bin_array.append(0)
                bin_array.append(0)
                bin_array.append(0)
                bin_array.append(0)
                bin_array.append(0)
            bin_array.tofile(write_file)



        if int(newExponent,2) == EXP_MAX[output_p] and ('1' in newFraction):
            # exponent = 255, fraction != 0
            print("case 1: NaN")
            # no nan in etude? i think

        elif (int(newExponent,2) == EXP_MAX[output_p]) and (not '1' in newFraction):
            # exponent = 255, fraction = 0
            print("case 2: infinity")

        elif int(newExponent,2) < EXP_MAX[output_p] and int(newExponent,2) > 0:
            # 0 < exponent < 255
            print("case 3: floating point number")
            #write to file
            bin_array = array('B')
            if output_p == 's':
                # each of these appends a byte to the array
                bin_array.append(int(sign + newExponent[:-1],2))
                bin_array.append(int(newExponent[-1:]+newFraction[:7],2))
                bin_array.append(int(newFraction[7:15],2))
                bin_array.append(int(newFraction[15:], 2))

            else:
                bin_array.append(int(sign + newExponent[:-4], 2))
                bin_array.append(int(newExponent[-4:] + newFraction[:4], 2))
                bin_array.append(int(newFraction[4:12], 2))
                bin_array.append(int(newFraction[12:20], 2))
                bin_array.append(int(newFraction[20:28], 2))
                bin_array.append(int(newFraction[28:36], 2))
                bin_array.append(int(newFraction[36:44], 2))
                bin_array.append(int(newFraction[44:], 2))
            bin_array.tofile(write_file)

        elif (not '1' in newExponent) and ('1' in newFraction):
            # exponent = 0, fraction != 0
            print("case 4: denormalized")

        elif (not '1' in newExponent) and (not '1' in newFraction):
            # exponent = 0, fraction = 0
            print("case 5: zero")

        else:
            print("Somethiing went terribly wrong")



        print("")

    write_file.close()