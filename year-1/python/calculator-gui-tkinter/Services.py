class Services:

    def num_to_digit_list(self, number):
        """
        Converts a natural number to a digit list
        num - natural number
        Returns the number's digit list
        """
        digit_list = []
        while number:
            digit_list.insert(0, number % 10)
            number = int(number / 10)
        return digit_list

    def digit_list_to_num(self, digit_list):
        """
        Converts a digit list to a natural number
        digit_list - list of digits
        Returns a natural number
        """
        string = ""
        for digit in digit_list:
            base16 = {10: "A", 11: "B", 12: "C", 13: "D", 14: "E", 15: "F"}
            if (digit > 9) and (digit in base16):
                string += base16[digit]
            else:
                string += str(digit)
        return int(string)

    def delete_zeroes(self, lis):
        """
        Deletes all the zeroes at the start of a digit list
        lis - list of digits
        Returns a list
        """
        while lis[0] == 0:
            if lis == [0]:
                return lis
            del (lis[0])
        return lis

    def equate_length(self, num1, num2):
        """
        Makes 2 lists the same length by inserting zeroes in the beginning of the number
        num1, num2 - lists of digits
        Returns a natural number representing the length of both lists
        """
        while len(num1) != len(num2):
            if len(num1) > len(num2):
                num2.insert(0, 0)
            else:
                num1.insert(0, 0)
        return len(num1)

    def add_lists(self, num1, num2, base):
        """
        Adds two natural numbers
        num1, num2 - lists of digits
        base - natural number, 2 <= b <= 16
        Returns a list of digits representing the sum
        """
        l = self.equate_length(num1, num2)
        sum = []
        transport = 0
        for pos in range(1, l + 1):
            digit_sum = num1[l - pos] + num2[l - pos] + transport
            digit = digit_sum % base
            sum.insert(0, digit)
            transport = digit_sum // base
        if transport:
            sum.insert(0, transport)
        return sum

    def sub_lists(self, num1, num2, base):
        """
        Subtract two natural numbers
        num1, num2 - lists of digits
        base - natural number, 2 <= b <= 16
        Returns a list of digits representing the result of the operation
        """
        if self.digit_list_to_num(num1) < self.digit_list_to_num(num2):
            return []
        l = self.equate_length(num1, num2)
        sub = []
        transport = 0
        for pos in range(1, l + 1):
            if num1[l - pos] + transport >= num2[l - pos]:
                digit = num1[l - pos] + transport - num2[l - pos]
                transport = 0
            else:
                digit = base + num1[l - pos] + transport - num2[l - pos]
                transport = -1
            sub.insert(0, digit)
        return self.delete_zeroes(sub)

    def mult_lists(self, num1, num2, base):
        """
        Multiply a natural number with a digit
        num1 - lists of digits
        num2 - digit
        base - natural number, 2 <= b <= 16
        Returns a list of digits representing the result of the operation
        """
        transport = 0
        mult = []
        for pos in reversed(num1):
            digit_mult = int(pos * num2) + transport
            digit = digit_mult % base
            mult.insert(0, digit)
            transport = digit_mult // base
        if transport:
            mult.insert(0, transport)
        return mult

    def div_lists(self, num1, num2, base):
        """
        Divide a natural number by a digit
        num1 - lists of digits
        num2 - digit
        base - natural number, 2 <= b <= 16
        Returns a list of digits representing the result of the operation
        """
        div_integer = []
        transport = 0
        for pos in num1:
            digit_div = pos + int(transport * base)
            digit = digit_div // num2
            div_integer.append(digit)
            transport = (pos + int(transport * base)) % num2
        return {"cat": self.delete_zeroes(div_integer), "rest": transport}

    def validateOpd(self, opd, opdBase):
        for digit in opd:
            if digit >= opdBase:
                raise ValueError()

    def add(self, opd1List, opd2List, opd1Base, opd2Base, resultBase):
        self.validateOpd(opd1List, opd1Base)
        self.validateOpd(opd2List, opd2Base)
        opd1List = self.convert_simple(opd1List, opd1Base, resultBase)
        opd2List = self.convert_simple(opd2List, opd2Base, resultBase)
        result = self.add_lists(opd1List, opd2List, resultBase)
        return result

    def subtract(self, opd1List, opd2List, opd1Base, opd2Base, resultBase):
        self.validateOpd(opd1List, opd1Base)
        self.validateOpd(opd2List, opd2Base)
        opd1List = self.convert_simple(opd1List, opd1Base, resultBase)
        opd2List = self.convert_simple(opd2List, opd2Base, resultBase)
        result = self.sub_lists(opd1List, opd2List, resultBase)
        return result

    def multiply(self, opd1List, opd2, opd1Base, opd2Base, resultBase):
        self.validateOpd(opd1List, opd1Base)
        opd2 = self.digit_list_to_num(opd2)
        if opd2 >= opd2Base:
            raise ValueError()
        opd1List = self.convert_simple(opd1List, opd1Base, resultBase)
        result = self.mult_lists(opd1List, opd2, resultBase)
        return result

    def divide(self, opd1List, opd2, opd1Base, opd2Base, resultBase):
        self.validateOpd(opd1List, opd1Base)
        opd2 = self.digit_list_to_num(opd2)
        if opd2 >= opd2Base:
            raise ValueError()
        opd1List = self.convert_simple(opd1List, opd1Base, resultBase)
        result = self.div_lists(opd1List, opd2, resultBase)
        return str(self.digit_list_to_num(result["cat"])) + " rest " + str(result["rest"])

    def convert_division(self, num, base1, base2):
        """
        Converts a natural number from a base to another by division
        num - lists of digits
        base1, base2 - natural number, 2 <= b <= 16, base1 > base2
        Returns a list of digits representing the result of the operation
        """
        result = []
        while num != [0]:
            div_result = self.div_lists(num, base2, base1)
            num = div_result["cat"]
            r = div_result["rest"]
            result.insert(0, r)
        return result

    def convert_subst(self, num, base1, base2):
        """
        Converts a natural number from a base to another by substitution
        num - lists of digits
        base1, base2 - natural number, 2 <= b <= 16, base1 < base2
        Returns a list of digits representing the result of the operation
        """
        result = [0]
        for i in range(0, len(num)):
            pos = [num[i]]
            for j in range(1, len(num) - i):
                pos = self.mult_lists(pos, base1, base2)
            result = self.add_lists(result, pos, base2)
        return result

    def convert_simple(self, numList, initialBase, finalBase):
        """
        Chooses the best conversion algorithm
        """
        self.validateOpd(numList, initialBase)
        if initialBase < finalBase:
            return self.convert_division(numList, initialBase, finalBase)
        else:
            return self.convert_subst(numList, initialBase, finalBase)

    def convert_quickly(self, numList, initialBase, finalBase):
        self.validateOpd(numList, initialBase)
        newNumber = []
        if initialBase == 2:
            if finalBase == 4:
                table = {"00": 0, "01": 1, "10": 2, "11": 3}
                while len(numList) % 2 != 0:
                    numList.insert(0, 0)
                for i in range(0, len(numList), 2):
                    newNumber.append(table[str(numList[i]) + str(numList[i + 1])])
            elif finalBase == 8:
                table = {"000": 0, "001": 1, "010": 2, "011": 3, "100": 4, "101": 5, "110": 6, "111": 7}
                while len(numList) % 3 != 0:
                    numList.insert(0, 0)
                for i in range(0, len(numList), 3):
                    newNumber.append(table[str(numList[i]) + str(numList[i + 1]) + str(numList[i + 2])])
            elif finalBase == 16:
                table = {"0000": 0, "0001": 1, "0010": 2, "0011": 3, "0100": 4, "0101": 5, "0110": 6, "0111": 7, "1000": 8, "1001": 9, "1010": 10, "1011": 11, "1100": 12, "1101": 13, "1110": 14, "1111": 15}
                while len(numList) % 4 != 0:
                    numList.insert(0, 0)
                for i in range(0, len(numList), 4):
                    newNumber.append(table[str(numList[i]) + str(numList[i + 1]) + str(
                        numList[i + 2]) + str(numList[i + 3])])
        elif initialBase == 4:
            table = {"0": [0, 0], "1": [0, 1], "2": [1, 0], "3": [1, 1]}
            for i in range(0, len(numList)):
                newNumber += table[str(numList[i])]
        elif initialBase == 8:
            table = {"0": [0, 0, 0], "1": [0, 0, 1], "2": [0, 1, 0], "3": [0, 1, 1], "4": [1, 0, 0], "5": [1, 0, 1], "6": [1, 1, 0], "7": [1, 1, 1]}
            for i in range(0, len(numList)):
                newNumber += table[str(numList[i])]
        else:
            table = {"0": [0, 0, 0, 0], "1": [0, 0, 0, 1], "2": [0, 0, 1, 0], "3": [0, 0, 1, 1], "4": [0, 1, 0, 0], "5": [0, 1, 0, 1], "6": [0, 1, 1, 0], "7": [0, 1, 1, 1], "8": [1, 0, 0, 0], "9": [1, 0, 0, 1], "10": [1, 0, 1, 0], "11": [1, 0, 1, 1], "12": [1, 1, 0, 0], "13": [1, 1, 0, 1], "14": [1, 1, 1, 0], "15": [1, 1, 1, 1]}
            for i in range(0, len(numList)):
                newNumber += table[str(numList[i])]
        return newNumber

    def convert_intermediate(self, numList, initialBase, finalBase):
        self.validateOpd(numList, initialBase)
        base10 = self.convert_division(numList, initialBase, 10)
        if finalBase > 10:
            return self.convert_division(base10, 10, finalBase)
        else:
            return self.convert_subst(base10, 10, finalBase)