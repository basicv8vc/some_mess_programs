#-*- coding:utf-8 -*-

from collections import namedtuple

class Expr(object):
    def evaluation(self, point):
        """
        计算表达式在点point的值
        :param point: dict, 形如{"x": 1, "y": 2}
        :return:
        """
        return self._evaluation(point)

    def _evaluation(self, point):
        """
        真正进行表达式求值的方法, 每个Expr的子类都必须实现这个方法
        :param point:
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
        return self._forward_ad(point, direction)

    def _forward_ad(self, point, direction):
        """

        :param point:
        :param direction:
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
    def _evaluation(self, point):
        """"
        求Constant在点point的值, 返回Constant的值
        """
        return self.value

    def _forward_ad(self, point, direction):
        """
        求Constant在点point对direction变量的偏导数值
        :param point:
        :param direction: 形如 {"x": 1, "y": 0} 表示求x的偏导数值
        :return:
        """
        return 0

class Variable(Expr, namedtuple("Variable", "name")):
    """
    Variable表示一个变量,这里是占位符,它的值由用户提供的point{}决定
    """
    def _evaluation(self, point):
        """
        求变量Variable在点point的值,当然是返回point[self.name], 意思是如果Variable是'x', 返回point {"x": 2, "y": 1}中的2,
        如果Variable是'y', 返回1
        :param point:
        :return:
        """
        return point[self.name]

    def _forward_ad(self, point, direction):
        """
        求Variable在点point对direction变量的偏导数值
        :param point:
        :param direction: 形如 {"x": 1, "y": 0} 表示求x的偏导数值
        :return:
        """
        return direction[self.name]

class Add(Expr, namedtuple("Add", "expr1 expr2")):
    def _evaluation(self, point):
        """
        求expr1 + expr2 在点point的值
        :param point:
        :return:
        """
        left = self.expr1.evaluation(point)
        right = self.expr2.evaluation(point)

        return left + right

    def _forward_ad(self, point, direction):
        """
        求 expr1 + expr2 在点point对direction变量的偏导数值
        d(expr1+expr2) = d(expr1) + d(expr2)
        :param point: 形如 {"x": var1, "y": var2}, 表示点(var1, var2)
        :param direction: 形如 {"x": 1, "y": 0} 表示求x的偏导数值
        :return:
        """

        left = self.expr1.forward_ad(point, direction)
        right = self.expr2.forward_ad(point, direction)
        return left + right


class Sub(Expr, namedtuple("Sub", "expr1 expr2")):
    def _evaluation(self, point):
        """
        求 expr1 - expr2 在点point的值
        :param point:
        :return:
        """
        left = self.expr1.evaluation(point)
        right = self.expr2.evaluation(point)

        return left - right

    def _forward_ad(self, point, direction):
        """
        求 expr1 - expr2 在点point对direction变量的偏导数值
        d(expr1-expr2) = d(expr1) - d(expr2)
        :param point:
        :param direction:
        :return:
        """
        left = self.expr1.forward_ad(point, direction)
        right = self.expr2.forward_ad(point, direction)

        return left - right


class Mul(Expr, namedtuple("Mul", "expr1 expr2")):
    def _evaluation(self, point):
        """
        求 expr1 * expr2 在点point的值
        :param point:
        :return:
        """
        left = self.expr1.evaluation(point)
        right = self.expr2.evaluation(point)

        return left * right

    def _forward_ad(self, point, direction):
        """
        求 expr1 * expr2 在点point对direction变量的偏导数值
        d(expr1*expr2) = expr2 * d(expr1) + expr1 * d(expr2)
        :param point:
        :param direction:
        :return:
        """
        left = self.expr1.forward_ad(point, direction) * self.expr2.evaluation(point)
        right = self.expr2.forward_ad(point, direction) * self.expr1.evaluation(point)

        return left + right

class Div(Expr, namedtuple("Div", "expr1 expr2")):
    def _evaluation(self, point):
        """
        :param point:
        :return:
        """

        left = self.expr1.evaluation(point)
        right = self.expr2.evaluation(point)

        return left / right

    def _forward_ad(self, point, direction):
        """
        求 expr1 / expr2 在点point对direction变量的偏导数值
        d(expr1/expr2) = (d(expr1) * expr2 - expr1 * d(expr2))/(expr2 * expr2)
        :param point:
        :param direction:
        :return:
        """
        top = self.expr1.forward_ad(point, direction) * self.expr2.evaluation(point) - self.expr1.evaluation(point) * self.expr2.forward_ad(point, direction)
        bottom = self.expr2.evaluation(point) * self.expr2.evaluation(point) # 这里就重复计算了,看出cache的好处了

        return  top / bottom








