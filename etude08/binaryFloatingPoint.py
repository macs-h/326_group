

def checkFirstByte(byte):
    if byte[0] > 127:
        return (byte[0] - 128) * (-1)
    return byte[0]

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
        if number[:1] == '1':
            sign = -1
        else:
            sign = 1
        print("sign:", sign)
        exponent = number[1:8]
        print("exponent:", exponent)
        fraction = number[8:]
        print("fraction:", fraction)
        print("")

        #Do things with our binary strings
        newExponent = ((int(exponent, 2) - 64) * 4) + 125  # multiplying by 4 means we are in base 2 not 16
        newExponent = bin(newExponent)[2:].zfill(8)
        print(newExponent)


    write_file.close()