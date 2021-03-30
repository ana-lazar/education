from tkinter import *
from tkinter import ttk

class OperationFrame(Frame):
    def createWidgets(self):
        # operation
        operationLabel = Label(self, text="Operation: ")
        operationLabel.grid(column = 0, row = 0)
        self.comboBox = ttk.Combobox(self, values=["Add", "Subtract", "Multiply", "Divide"])
        self.comboBox.grid(column = 1, row = 0)
        self.comboBox.current(0)

        # operand 1
        operand1Label = Label(self, text="Operand 1:")
        operand1Label.grid(column = 0, row = 1)
        self.operand1 = StringVar()
        operand1Entry = Entry(self, textvariable=self.operand1)
        operand1Entry.grid(column = 1, row = 1)
        self.op1BaseComboBox = ttk.Combobox(self, values=[2, 3, 4, 5, 6, 7, 8, 9, 10, 16])
        self.op1BaseComboBox.grid(column=3, row=1)
        self.op1BaseComboBox.current(8)
        op1BaseLabel = Label(self, text="in base: ")
        op1BaseLabel.grid(column=2, row=1)

        # operand 2
        operand2Label = Label(self, text="Operand 2:")
        operand2Label.grid(column = 0, row = 2)
        self.operand2 = StringVar()
        operand2Entry = Entry(self, textvariable=self.operand2)
        operand2Entry.grid(column = 1, row = 2)
        self.op2BaseComboBox = ttk.Combobox(self, values=[2, 3, 4, 5, 6, 7, 8, 9, 10, 16])
        self.op2BaseComboBox.grid(column=3, row=2)
        self.op2BaseComboBox.current(8)
        op2BaseLabel = Label(self, text="in base: ")
        op2BaseLabel.grid(column=2, row=2)

        # result
        resultLabel = Label(self, text="Result:")
        resultLabel.grid(column=1, row=3)
        resultBaseLabel = Label(self, text="in base:")
        resultBaseLabel.grid(column=3, row=3)
        self.resultBaseComboBox = ttk.Combobox(self, values=[2, 3, 4, 5, 6, 7, 8, 9, 10, 16])
        self.resultBaseComboBox.grid(column=4, row=3)
        self.resultBaseComboBox.current(8)
        self.resultValueLabel = Label(self, text="")
        self.resultValueLabel.grid(column=2, row=3)
        executeButton = Button(self, text="Compute", command=self.compute)
        executeButton.grid(column=0, row=3)

    def digit_list(self, num):
        base16 = {10: 'A', 11: 'B', 12: 'C', 13: 'D', 14: 'E', 15: 'F'}
        for pos in range(0, len(num)):
            if num[pos] in base16:
                num[pos] = base16[num[pos]]
            else:
                num[pos] = str(num[pos])
        return num

    def string_to_digit_list(self, number):
        base16 = {"A": 10, "B": 11, "C": 12, "D": 13, "E": 14, "F": 15}
        digit_list = list(number)
        for pos in range(0, len(digit_list)):
            digit = digit_list[pos]
            if digit in base16:
                digit_list[pos] = base16[digit]
            else:
                digit_list[pos] = int(digit)
        return digit_list

    def compute(self):
        try:
            opd1Base = int(self.op1BaseComboBox.get())
            opd2Base = int(self.op2BaseComboBox.get())
            opd1 = self.string_to_digit_list(self.operand1.get())
            opd2 = self.string_to_digit_list(self.operand2.get())
            resultBase = int(self.resultBaseComboBox.get())
            operation = self.comboBox.get()
            command = self.__commands[operation]
            if command == self.__services.divide:
                result = command(opd1, opd2, opd1Base, opd2Base, resultBase)
                self.resultValueLabel["text"] = result
            else:
                result = self.digit_list(command(opd1, opd2, opd1Base, opd2Base, resultBase))
                self.resultValueLabel["text"] = "".join(result)
        except ValueError:
            self.resultValueLabel["text"] = "Invalid data"
        except KeyError:
            self.resultValueLabel["text"] = "Invalid command"

    def __init__(self, master, services):
            Frame.__init__(self, master)
            self.createWidgets()
            self.__services = services
            self.__commands = {"Add": self.__services.add, "Subtract": self.__services.subtract, "Multiply": self.__services.multiply, "Divide": self.__services.divide}
            self.pack()