#-*- coding:utf-8 -*-
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

''' 包含了抽样出来的一定量用户在一个月时间(11.18~12.18)之内的移动端行为数据，
 输出用户在接下来一天对商品子集(P)购买行为的预测结果
'''
def feature_values(tablename):
    '''
    查看table表某属性的取值
    :param tablename: 表名
    :return:
    '''
    time = set()
    item_id = set()
    user_id = set()
    with open(tablename,'r') as fi:
        next(fi)
        for eachline in fi:
            features = eachline.strip().split(',')
            if features[5] not in time:
                time.add(features[5])
            if features[1] not in item_id:
                item_id.add(features[1])
            if features[0] not in user_id:
                user_id.add(features[0])

    '''查看time属性的取值，
    从2014-11-18 00 到 2014-12-18 23'''
    time = sorted(time) #744
    print len(time)
    # for value in time:
    #     print value

    '''查看item_id的取值 '''
    print len(item_id) #4758484 商品

    '''查看user_id的取值'''
    print len(user_id) #20000用户

feature_values('../data/tianchi_fresh_comp_train_user.csv')




