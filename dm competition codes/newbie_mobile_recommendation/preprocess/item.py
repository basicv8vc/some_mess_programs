#-*- coding:utf-8 -*-
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

'''item表仅是部分商品子集(P)，'''

def feature_values(tablename):
    '''
    查看table表某属性的取值
    :param tablename: 表名
    :return:
    '''

    item_id = set()
    with open(tablename,'r') as fi:
        next(fi)
        for eachline in fi:
            features = eachline.strip().split(',')
            if features[0] not in item_id:
                item_id.add(features[0])



    '''查看item_id的取值 '''
    print len(item_id) # 422858


feature_values('../data/tianchi_fresh_comp_train_item.csv')




