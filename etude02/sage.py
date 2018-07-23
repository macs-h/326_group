golden_lookup = {}
silver_lookup = {}

def golden(d):
    if d in golden_lookup.keys():
        return golden_lookup.get(d)
    
    h = silver(d, d)
    if h % 12 == 0:
        h = 12
    else:
        h %= 12

    golden_lookup[d] = h
    return h

def silver(b, c):
    if c == 1:
        return 1

    if (b, c) in silver_lookup.keys():
        return silver_lookup.get((b, c))

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

while True:
    print(golden(int(input("Enter a day: "))))