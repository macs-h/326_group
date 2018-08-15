# E2 - Sage and Trelawney
#
# @author Tom Adams
# @author Max Huang
# @author Mitchie Maluschnig
# @author Asher Statham
#
# @since 23 July 2018

golden_lookup = {}
silver_lookup = {}

# Finds the golden hour based on Trelawney's rules.
#
# Input:    d - the day
# Returns:  the value of the golden hour
def golden(d):
    
    # If the golden hour for this day has already been found, get the value
    if d in golden_lookup.keys():
        return golden_lookup.get(d)
    
    h = silver(d, d)

    # Ensure that the hour is in the afternoon (1 - 12)
    if h % 12 == 0:
        h = 12
    else:
        h %= 12

    golden_lookup[d] = h
    return h

# Finds the silver hour based on Trelawney's rules.
#
# Input:    b - the bronze of the day
#           c - the copper of the day
# Returns:  the value of the silver hour
def silver(b, c):
    if c == 1:
        return 1

    # If the silver hour for this day has already been found, get the value
    if (b, c) in silver_lookup.keys():
        return silver_lookup.get((b, c))

    # Otherwise, check each rule and see if it has been checked before or not
    if b == c:
        if (b, c - 1) in silver_lookup.keys():
            x = silver_lookup.get((b, c - 1))
        else:
            x = silver(b, c - 1)
            silver_lookup[(b, c - 1)] = x
        return x + 1

    if b < c:
        if b in golden_lookup.keys():
            silver_lookup[(b, c)] = golden_lookup.get(b)
        return golden(b)

    if b > c:
        if (b - c, c) in silver_lookup.keys():
            x = silver_lookup.get((b - c, c))
        else:
            x = silver(b - c, c)
            silver_lookup[(b - c, c)] = x
        
        if (b, c - 1) in silver_lookup.keys():
            y = silver_lookup.get((b, c - 1))
        else:
            y = silver(b, c - 1)
            silver_lookup[(b, c - 1)] = y
        
        return x + y

    return 0

# MAIN

while True:
    print(golden(int(input("Enter a day: "))))