from tkinter import *
from tkinter import ttk

class ConversionFrame(Frame):
    def createWidgets(self):
        # operation
        conversionLabel = Label(self, text="Conversion type:")
        conversionLabel.grid(column=0, row=0)
        self.comboBox = ttk.Combobox(self, values=["Substitution/Division", "Quick (bases 2, 4, 8, 16)", "Using an intermediate base"])
        self.comboBox.grid(column=1, row=0)
        self.comboBox.current(0)
        # operand
        operandLabel = Label(self, text="Number: ")
        operandLabel.grid(column = 0, row = 1)
        self.operand = StringVar()
        operandEntry = Entry(self, textvariable=self.operand)
        operandEntry.grid(column = 1, row = 1)
        opdBaseLabel = Label(self, text="from base: ")
        opdBaseLabel.grid(column=0, row=2)
        self.opdBaseComboBox = ttk.Combobox(self, values=[2, 3, 4, 5, 6, 7, 8, 9, 10, 16])
        self.opdBaseComboBox.grid(column=1, row=2)
        self.opdBaseComboBox.current(8)
        opdBaseLabel = Label(self, text="to base: ")
        opdBaseLabel.grid(column=0, row=3)
        self.newBaseComboBox = ttk.Combobox(self, values=[2, 3, 4, 5, 6, 7, 8, 9, 10, 16])
        self.newBaseComboBox.grid(column=1, row=3)
        self.newBaseComboBox.current(8)
        # result
        executeButton = Button(self, text="Convert", command=self.convert)
        executeButton.grid(column=1, row=4)
        resultLabel = Label(self, text="Result:")
        resultLabel.grid(column=0, row=5)
        self.resultValueLabel = Label(self, text="")
        self.resultValueLabel.grid(column=1, row=5)

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

    def convert(self):
        try:
            opd = self.string_to_digit_list(self.operand.get())
            initialBase = int(self.opdBaseComboBox.get())
            finalBase = int(self.newBaseComboBox.get())
            command = self.__commands[self.comboBox.get()]
            result = self.digit_list(command(opd, initialBase, finalBase))
            self.resultValueLabel["text"] = "".join(result)
        except ValueError:
            self.resultValueLabel["text"] = "Invalid data"
        except KeyError:
            self.resultValueLabel["text"] = "Invalid command"

    def __init__(self, master, services):
            Frame.__init__(self, master)
            self.createWidgets()
            self.__services = services
            self.__commands = {"Substitution/Division": self.__services.convert_simple, "Quick (bases 2, 4, 8, 16)": self.__services.convert_quickly, "Using an intermediate base": self.__services.convert_intermediate}
            self.pack()