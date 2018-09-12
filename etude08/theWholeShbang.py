# E8 - Floating Point
#
# @author Tom Adams
# @author Max Huang
# @author Mitchie Maluschnig
# @author Asher Statham
#
# @since 30 July 2018

# CONSTANTS

EXP_BIAS = {"s": 127, "d": 1023}
EXP_SIZE = {"s": 8, "d": 11}
FRAC_SIZE = {"s": 23, "d": 52}
MIN_SIZE = {"s" : (1.18 * 10**-38), "d" : (2.23 * 10**-308)}

#converts binary fraction to a decimal fraction
def binToFrac(bin):
    fraction = 0.0
    for i in range(len(bin)):
        if(bin[i] =="1"):
            fraction += pow(2,(-1)*(i+1))
    return fraction

#converts a IBM float to a decimal number
def fromFloat(binRep):
    s = binRep[0]
    e = binRep[1:8]
    f = binRep[8:]
    #print(s + "-" + e + "-" + f)
    exponent = int(e, 2) - 64
    fraction = binToFrac(f)
    if s == "1":
        number = fraction*(-16**exponent)
    else:
        number = fraction * (16 ** exponent)

    return number


#---------------------------------------------------
#converts a decimal fractoon to a binary fraction
def toBinfrac(n):
    global output_p
    precision = FRAC_SIZE.get(output_p)
    binString = ""
    for i in range(precision):
        n *= 2
        if int(n) == 1:
            binString = binString + "1"
            n -= 1
        else:
            binString = binString + "0"
    #print(n) #might need to change something to make this round ?? maybe
    return binString

#converts a dacimal number to a IEEE float
def toFloat(n):
    global output_p
    binaryString = ""
    if n >= 0:
        binaryString = binaryString + "0"
    else:
        binaryString = binaryString + "1"
    if abs(n) < (MIN_SIZE.get(output_p)):

        return binaryString + "0000000000000000000000000000000"

    ####find exponent####
    n = abs(n)
    fraction = n
    exponent = 0
    if n < 2:
        while fraction < 1:
            exponent -= 1
            fraction = n/(2**(exponent))
    else:
        while fraction >= 2:
            exponent += 1
            fraction= n/(2**(exponent))
    fraction -= 1

    binaryString = binaryString + bin(exponent+EXP_BIAS.get(output_p))[2:].zfill(EXP_SIZE.get(output_p))[:EXP_SIZE.get(output_p)]
    ####find fraction####
    binaryString = binaryString + toBinfrac(fraction)

    return binaryString

global input_p, output_p
input_file = input("Enter IBM input file: ")
input_p = input("Specify IBM precision: ")
output_file = input("Enter IEEE output file: ")
output_p = input("Specify IEEE precision: ")

#print(toFloat(fromFloat("01000011001001010111001000000000")))
#print(toFloat(fromFloat("0111111100000000000000000000000000000000000000000000000000000000")))
#print(toFloat(0.15625))

with open(input_file, 'rb') as numbers:
    write_file = open(output_file, 'wb')
    for number in numbers:
        number = number.decode('ascii')
        number = toFloat(fromFloat(number)).encode('ascii')
        write_file.write(number)
        write_file.write("\n".encode('ascii'))

    write_file.close()