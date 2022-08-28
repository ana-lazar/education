from tkinter import *
from OperationFrame import OperationFrame
from ConversionFrame import ConversionFrame
from Services import Services

class MainFrame(Frame):
    def __init__(self, master):
        Frame.__init__(self, master)
        self.__services = Services()
        button1 = Button(self, text = 'Operations', command = self.openOperations)
        button1.grid(row=0, column=0, padx=5, pady=5)
        button2 = Button(self, text = 'Conversions', command = self.openConversions)
        button2.grid(row=1, column=0, padx=5, pady=5)
        self.pack()

    def openOperations(self):
        newWindow = Toplevel(self)
        operationFrame = OperationFrame(newWindow, self.__services)

    def openConversions(self):
        newWindow = Toplevel(self)
        conversionFrame = ConversionFrame(newWindow, self.__services)

root = Tk()
root.title('Tema')
root.minsize(200, 75)
app = MainFrame(root)
root.mainloop()
