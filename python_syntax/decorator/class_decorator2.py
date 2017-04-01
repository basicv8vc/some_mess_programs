
class entryExit:

    def __init__(self, f):
        self.f = f

    def __call__(self):
        print("Entering", self.f.__name__)
        self.f()
        print("Exited", self.f.__name__)

@entryExit
def func1():
    print("inside function func1()")

@entryExit
def func2():
    print("inside function func2()")

func1()
func2()




