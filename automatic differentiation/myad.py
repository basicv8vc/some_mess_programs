#-*- coding:utf-8 -*-

from collections import namedtuple

class Expr(object):
    def evaluation(self, point):
        """
        计算表达式在点point的值
        :param point: dict, 形如{"x": 1, "y": 2}
        :return:
        """
        cache = {}
        return self._evaluation(point, cache)

    def _evaluation(self, point, cache):
        """
        真正进行表达式求值的方法, 每个Expr的子类都必须实现这个方法
        :param point:
        :param cache: 记录每次Expr的求值结果
        :return:
        """
        raise NotImplementedError

    def forward_ad(self, point, direction):
        """
        求函数对某个变量的偏导数值
        按照 forward mode求点point的对direction的偏导数值

        :param point: 点
        :param direction: dict, 形如 {"x: 1, "y": 0},表示求函数对x的偏导数值
        :return:
        """
        cache = {}
        self._evaluation(point, cache)
        return self._forward_ad(point, direction, cache)

    def _forward_ad(self, point, direction, cache):
        """
        :param point:
        :param direction:
        :param cache: 存储子Expr在点point的值
        :return:
        """
        raise NotImplementedError

    def __add__(self, other):
        return Add(self, other)

    def __sub__(self, other):
        return Sub(self, other)

    def __mul__(self, other):
        return Mul(self, other)

    def __truediv__(self, other):
        return Div(self, other)

class Constant(Expr, namedtuple("Constant", "value")):
    """
    Constant 表示一个常数,
    """
    def _evaluation(self, point, cache):
        """"
        求Constant在点point的值, 返回Constant的值
        :param point:
        :param cache:
        :return:
        """
        if id(self) not in cache:
            cache[id(self)] = self.value

        return cache[id(self)]

    def _forward_ad(self, point, direction, cache):
        """
        求Constant在点point对direction变量的偏导数值
        :param point:
        :param direction: 形如 {"x": 1, "y": 0} 表示求x的偏导数值
        :param cache:
        :return:
        """

        return 0

class Variable(Expr, namedtuple("Variable", "name")):
    """
    Variable表示一个变量,这里是占位符,它的值由用户提供的point{}决定
    """
    def _evaluation(self, point, cache):
        """
        求变量Variable在点point的值,当然是返回point[self.name], 意思是如果Variable是'x', 返回point {"x": 2, "y": 1}中的2,
        如果Variable是'y', 返回1
        :param point:
        :param cache:
        :return:
        """
        if id(self) not in cache:
            cache[id(self)] = point[self.name]

        return cache[id(self)]

    def _forward_ad(self, point, direction, cache):
        """
        求Variable在点point对direction变量的偏导数值
        :param point:
        :param direction: 形如 {"x": 1, "y": 0} 表示求x的偏导数值
        :param cache:
        :return:
        """
        return direction[self.name]

class Add(Expr, namedtuple("Add", "expr1 expr2")):
    def _evaluation(self, point, cache):
        """
        求expr1 + expr2 在点point的值
        :param point:
        :param cache:
        :return:
        """
        if id(self) not in cache:
            left = self.expr1._evaluation(point, cache)
            right = self.expr2._evaluation(point, cache)
            cache[id(self)] = left + right

        return cache[id(self)]

    def _forward_ad(self, point, direction, cache):
        """
        求 expr1 + expr2 在点point对direction变量的偏导数值
        d(expr1+expr2) = d(expr1) + d(expr2)
        :param point: 形如 {"x": var1, "y": var2}, 表示点(var1, var2)
        :param direction: 形如 {"x": 1, "y": 0} 表示求x的偏导数值
        :param cache:
        :return:
        """
        left = self.expr1._forward_ad(point, direction, cache)
        right = self.expr2._forward_ad(point, direction, cache)

        return left + right


class Sub(Expr, namedtuple("Sub", "expr1 expr2")):
    def _evaluation(self, point, cache):
        """
        求 expr1 - expr2 在点point的值
        :param point:
        :param cache:
        :return:
        """
        if id(self) not in cache:
            left = self.expr1._evaluation(point, cache)
            right = self.expr2._evaluation(point, cache)
            cache[id(self)] = left - right

        return cache[id(self)]

    def _forward_ad(self, point, direction, cache):
        """
        求 expr1 - expr2 在点point对direction变量的偏导数值
        d(expr1-expr2) = d(expr1) - d(expr2)
        :param point:
        :param direction:
        :param cache:
        :return:
        """
        left = self.expr1._forward_ad(point, direction, cache)
        right = self.expr2._forward_ad(point, direction, cache)

        return left - right


class Mul(Expr, namedtuple("Mul", "expr1 expr2")):
    def _evaluation(self, point, cache):
        """
        求 expr1 * expr2 在点point的值
        :param point:
        :param cache:
        :return:
        """
        if id(self) not in cache:
            left = self.expr1._evaluation(point, cache)
            right = self.expr2._evaluation(point, cache)
            cache[id(self)] = left * right

        return cache[id(self)]

    def _forward_ad(self, point, direction, cache):
        """
        求 expr1 * expr2 在点point对direction变量的偏导数值
        d(expr1*expr2) = expr2 * d(expr1) + expr1 * d(expr2)
        :param point:
        :param direction:
        :param cache:
        :return:
        """
        left = self.expr1._forward_ad(point, direction, cache) * cache[id(self.expr2)]
        right = self.expr2._forward_ad(point, direction, cache) * cache[id(self.expr1)]

        return left + right

class Div(Expr, namedtuple("Div", "expr1 expr2")):
    def _evaluation(self, point, cache):
        """
        :param point:
        :param cache:
        :return:
        """
        if id(self) not in cache:
            left = self.expr1._evaluation(point, cache)
            right = self.expr2._evaluation(point, cache)
            cache[id(self)] = left / right

        return cache[id(self)]

    def _forward_ad(self, point, direction, cache):
        """
        求 expr1 / expr2 在点point对direction变量的偏导数值
        d(expr1/expr2) = (d(expr1) * expr2 - expr1 * d(expr2))/(expr2 * expr2)
        :param point:
        :param direction:
        :param cache:
        :return:
        """
        top = self.expr1._forward_ad(point, direction, cache) * cache[id(self.expr2)] - cache[id(self.expr1)] * self.expr2._forward_ad(point, direction, cache)
        bottom = cache[id(self.expr2)] ** 2

        return  top / bottom








