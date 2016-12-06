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

def test_forward_ad():
    """
    测试 forward mode求偏导数, forward_ad() 一次只能求函数对一个自变量的偏导数,
    要想求梯度,需要调用n(自变量数)次forward_ad()
    :return:
    """

    x = myad.Variable("x")
    y = myad.Variable("y")
    point = {"x": 3, "y": 2}

    assert (x + y).forward_ad(point, {"x": 1, "y": 0}) == 1
    assert (x + y).forward_ad(point, {"x": 0, "y": 1}) == 1
    assert (x * y).forward_ad(point, {"x": 0, "y": 1}) == 3
    assert (x * y * x + y * y - y + x / y).forward_ad(point, {"x": 0, "y": 1}) == 11.25

test_forward_ad()


def test_reverse_ad():
    """
    测试 reverse mode 求梯度值
    :return:
    """

    x = myad.Variable("x")
    y = myad.Variable("y")
    point = {"x": 3, "y": 2}

    assert (x + y).reverse_ad(point) == {"x": 1, "y": 1}
    assert (x - y).reverse_ad(point) == {"x": 1, "y": -1}
    assert (x * y).reverse_ad(point) == {"x": 2, "y": 3}
    assert (x * y * x + y * y - y + x / y).reverse_ad(point) == {"x": 12.5, "y": 11.25}

test_reverse_ad()
