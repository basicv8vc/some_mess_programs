#-*- coding:utf-8 -*-
import myad

def test_const_operation():
    x = myad.Constant(3)
    y = myad.Constant(2)

    assert x.evaluation({}) == 3
    assert y.evaluation({}) == 2

    assert (x + y).evaluation({}) == 5
    assert (x - y).evaluation({}) == 1
    assert (x * y).evaluation({}) == 6
    assert (x / y).evaluation({}) == 1.5

    assert (x + x + y * y + x * y).evaluation({}) == 16

test_const_operation()

def test_variable_operation():
    x = myad.Variable("x")
    y = myad.Variable("y")

    point = {"x": 3, "y": 2}

    assert x.evaluation(point) == 3
    assert y.evaluation(point) == 2

    assert (x + y).evaluation(point) == 5
    assert (x - y).evaluation(point) == 1
    assert (x * y).evaluation(point) == 6
    assert (x / y).evaluation(point) == 1.5

    assert (x + x + y * y + x * y).evaluation(point) == 16


test_variable_operation()

def test_forward():
    """
    测试 forward mode求偏导数
    :return:
    """

    x = myad.Variable("x")
    y = myad.Variable("y")
    point = {"x": 3, "y": 2}

    assert (x + y).forward_ad(point, {"x": 1, "y": 0}) == 1
    assert (x + y).forward_ad(point, {"x": 0, "y": 1}) == 1
    assert (x * y).forward_ad(point, {"x": 0, "y": 1}) == 3
    assert (x * y * x + y * y - y + x / y).forward_ad(point, {"x": 0, "y": 1}) == 11.25
