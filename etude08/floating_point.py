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


#SUPPORT FUNCTIONS

def frac(input_frac, input_p, output_p):
    size = FRAC_SIZE.get(output_p)

    output_frac = input_frac.zfill(size)[:size]

    return output_frac

def exp(input_exp, input_p, output_p):
    size = EXP_SIZE.get(output_p)

    output_exp = int(input_exp, 2) - 64 + EXP_BIAS.get(output_p)
    output_exp = bin(output_exp)[2:].zfill(size)[:size]

    return output_exp

def convert(input_num, input_p, output_p, write_file):
    s = input_num[0]
    e = exp(input_num[1], input_p, output_p)
    f = frac(input_num[2], input_p, output_p)
    output_num = s + "-" + e + "-" + f + "\n"
    write_file.write(output_num)
    

# MAIN

# input_file = input("Enter input filename: ")
# input_p = input("Precision (s for single, d for double): ")
# output_file = input("Enter output filename: ")
# output_p = input("Precision (s for single, d for double): ")
input_file = 'input.txt'
input_p = 's'
output_file = 'output.txt'
output_p = 's'


with open(input_file) as numbers:
    write_file = open(output_file, 'w')
    for number in numbers:
        number_array = [number[0], number[1:8], number[9:]]
        convert(number_array, input_p, output_p, write_file)
    write_file.close()