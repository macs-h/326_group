# E8 - Floating Point
#
# @author Tom Adams
# @author Max Huang
# @author Mitchie Maluschnig
# @author Asher Statham
#
# @since 30 July 2018

def convert(input_num, input_p, output_p, write_file):
    output_num = input_num
    write_file.write(output_num)
    

input_file = input("Enter input filename: ")
input_p = input("Precision (s for single, d for double): ")
output_file = input("Enter output filename: ")
output_p = input("Precision (s for single, d for double): ")

with open(input_file) as numbers:
    write_file = open(output_file, 'w')
    for number in numbers:
        convert(number, input_p, output_p, write_file)
    write_file.close()