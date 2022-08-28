def mySelectionSort(list, *, key = lambda x: x, reversed = False, cmp = None):
    """
    Selection Sort
    """
    for i in range(0, len(list) - 1):
        pos = i
        for j in range(i + 1, len(list)):
            if reversed == False:
                if cmp != None:
                    lessThan = cmp(list[j], list[pos]) < 0
                else:
                    lessThan = key(list[j]) < key(list[pos])
                if lessThan:
                    list[i], list[j] = list[j], list[i]
            else:
                if cmp != None:
                    greaterThan = cmp(list[j], list[pos]) >= 0
                else:
                    greaterThan = key(list[j]) > key(list[pos])
                if greaterThan:
                    list[i], list[j] = list[j], list[i]
    return list

def myShakeSort(list, *, key = lambda x: x, reversed = False):
    """
    Shake Sort
    """
    beg = 0
    fin = len(list) - 1
    noSwap = False
    while (not noSwap and fin - beg > 1):
        noSwap = True
        for j in range(beg, fin):
            if reversed == False:
                if key(list[j + 1]) < key(list[j]):
                    list[j], list[j + 1] = list[j + 1], list[j]
                    noSwap = False
            elif key(list[j + 1]) > key(list[j]):
                list[j], list[j + 1] = list[j + 1], list[j]
                noSwap = False
        fin = fin - 1
        for j in range(fin, beg, -1):
            if reversed == False:
                if key(list[j - 1]) > key(list[j]):
                    list[j], list[j - 1] = list[j - 1], list[j]
                    noSwap = False
            elif key(list[j - 1]) < key(list[j]):
                list[j], list[j + 1] = list[j + 1], list[j]
                noSwap = False
        beg = beg + 1
    return list