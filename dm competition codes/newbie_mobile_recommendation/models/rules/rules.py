#-*- coding:utf-8 -*-
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

def rule1_last_buy():
    '''
    规则1：所有商品，不论类别，上次购买是一周以内的，就不买了；此规则不考虑地理位置信息，所以只需要处理user表，
           可以细化，根据不同category不同处理,
    :return:
    '''
