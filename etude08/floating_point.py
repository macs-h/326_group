# E8 - Floating Point
#
# @author Tom Adams
# @author Max Huang
# @author Mitchie Maluschnig
# @author Asher Statham
#
# @since 30 July 2018

# CONSTANTS

from math import log, pow

EXP_BIAS = {"s": 127, "d": 1023}
EXP_SIZE = {"s": 8, "d": 11}
FRAC_SIZE = {"s": 23, "d": 52}

#Converting to floating point from decmal code
#----------------------------------------------
def toBinfrac(n):
    precision = 23
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

def toFloat(n):
    binaryString = ""
    if n >= 0:
        binaryString = binaryString + "0"
    else:
        binaryString = binaryString + "1"
    #####################
    ####find exponent####
    #####################
    n = abs(n)
    fraction = n
    exponent = 0
    if n < 2:
        while fraction < 1:
            exponent -= 1
            fraction = n/(2**(exponent))
        # print(exponent,count)
    else:
        while fraction >= 2:
            exponent += 1
            fraction= n/(2**(exponent))
        # print(exponent,count)
    fraction -= 1
    print(fraction, exponent,bin(exponent))
    binaryString = binaryString + "-" + bin(exponent+127)[2:].zfill(8)[:8]
    #####################
    ####find fraction####
    #####################
    binaryString = binaryString + "-" + toBinfrac(fraction)
    return binaryString
#-----------------------------------------------

#SUPPORT FUNCTIONS
def binToFrac(bin):
    fraction = 0.0
    for i in range(len(bin)):
        if(bin[i] =="1"):
            fraction += pow(2,(-1)*(i+1))
    return fraction

def frac(input_frac, input_p, output_p):
    size = FRAC_SIZE.get(output_p)

    hex_frac = binToFrac(input_frac)
    print(hex_frac)
    output_frac = bin(hex_frac)[2:].zfill(size)[:size]

    return output_frac

def exp(input_exp, input_p, output_p):
    size = EXP_SIZE.get(output_p)
    print("> 16^", int(input_exp, 2) - 64)
    hex_exp = pow(16, int(input_exp, 2) - 64)
    hex_exp = int(log(hex_exp,2))
    print("> 2^",hex_exp)
    output_exp = hex_exp + EXP_BIAS.get(output_p) - (int(input_exp, 2) - 64) # wee bit of a cheeky fix.
    output_exp = bin(output_exp)[2:].zfill(size)[:size]

    return output_exp

def convert(input_num, input_p, output_p, write_file):
    s = input_num[0]
    e = exp(input_num[1], input_p, output_p)
    f = frac(input_num[2], input_p, output_p)
    output_num = s + "-" + e + "-" + f + "\n"
    write_file.write(output_num)
    

# MAIN

input_file = input("Enter input filename: ")
input_p = input("Precision (s for single, d for double): ")
output_file = input("Enter output filename: ")
output_p = input("Precision (s for single, d for double): ")

with open(input_file) as numbers:
    write_file = open(output_file, 'w')
    for number in numbers:
        number_array = [number[0], number[1:8], number[8:]]
        convert(number_array, input_p, output_p, write_file)
    write_file.close()
