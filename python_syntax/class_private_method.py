class MyClass:
    def __init__(self):
        print("__init__() method")

    def method1(self):
        print("method1() ")

    def _method2(self):
        print("_method2() ")

    def __method3(self):
        print("__method3() ")

    def _method4_(self):
        print("_method4_() ")

    def __method5__(self):
        print("__method5__() ")

    def method6_(self):
        print("method6_() ")

    def method7__(self):
        print("method7__()")

if __name__=="__main__":
    cls = MyClass()
    cls.__init__()
    cls.method1()
    cls._method2()
    # cls.__method3()
    cls._method4_()
    cls.__method5__()
    cls.method6_()
    cls.method7__()
