from math import log
def binToFrac(bin):
    fraction = 0.0
    for i in range(len(bin)):
        if(bin[i] =="1"):
            fraction += pow(2,(-1)*(i+1))
    return fraction

def fromFloat(binRep):
    s = binRep[0]
    e = binRep[1:8]
    f = binRep[8:]
    print(s + "-" + e + "-" + f)
    exponent = int(e, 2) - 64
    fraction = binToFrac(f)
    if s == "1":
        number = fraction*(-16**exponent)
    else:
        number = fraction * (16 ** exponent)

    return number


#---------------------------------------------------
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
    else:
        while fraction >= 2:
            exponent += 1
            fraction= n/(2**(exponent))
    fraction -= 1
    print(fraction, exponent,bin(exponent)[2:])
    binaryString = binaryString + "-" + bin(exponent+127)[2:].zfill(8)[:8]
    #####################
    ####find fraction####
    #####################
    binaryString = binaryString + "-" + toBinfrac(fraction)
    return binaryString

print(toFloat(fromFloat("11000010011101101010000000000000")))