#-*- coding:utf-8 -*-
from collections import namedtuple
from numpy import random
import myad

Pearson = namedtuple("Pearson", "value name")

Bob = Pearson(6, "Bob")

# print(Bob.value)
# print(Bob.name)

x = myad.Constant(3)
y = myad.Constant(2)

# print((x + y).evaluation({}))
# print((x * y).evaluation({}))
# print((x + x + y + y + x).evaluation({}))


var1 = myad.Variable("x")
var2 = myad.Variable("y")
point = {"x": 3, "y": 2}

# print((var1 + var2).evaluation(point))
# print((var1 * var2).evaluation(point))
# print((var1 + var1 + var2 + var2 + var1 * var2).evaluation(point))
# 求偏导数
print((var1 + var2).forward_ad(point, {"x": 1, "y": 0}))
print((var1 + var2).forward_ad(point, {"x": 0, "y": 1}))
print((var1 * var2).forward_ad(point, {"x": 0, "y": 1}))

print((var1 * var2 * var1 + var2 * var2 - var2 + var1 / var2).forward_ad(point, {"x": 0, "y": 1}))