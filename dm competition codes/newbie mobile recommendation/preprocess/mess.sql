--sql 
--********************************************************************--
--author:
--create time:2016-03-19 22:50:34
--********************************************************************--

-- 统计 p_user中正负样本比例
-- select count(behavior_type == 1) from p_user; --215903066
-- select count(behavior_type != 1) from p_user; 215903066
-- select count(*) from p_user; 215903066
-- 统计p_user中点击记录次数
--select count(*) from p_user where behavior_type==1; --206491731 2亿条
-- 统计p_user 中收藏次数
--select count(*) from p_user where behavior_type == 2; --3208274 三百万
-- 统计p_user 添加购物车次数
--select count(*) from p_user where behavior_type == 3;  --4373197 四百万
-- 统计 p_user中购买次数
-- select count(*) from p_user where behavior_type==4; --1829864 一百万

-- 统计t_label_test_train的行数
--select count(*) from t_label_test_train; --33701

-- 统计t_label_valid_train的行数
--select count(*) from t_label_valid_train; --35220

--select count(*) from p_user where time>'2014-12-17' AND time<'2014-12-18' AND behavior_type=4; --46383

DROP TABLE IF EXISTS t_label_valid_train;
DROP TABLE IF EXISTS t_label_test_train;

CREATE TABLE t_label_valid_train AS
SELECT 
    user_id
    ,item_id
    ,1 AS label  
FROM 
    p_user 
WHERE 
    time>'2014-12-17' AND time<'2014-12-18' AND behavior_type=4
GROUP BY user_id,item_id;

CREATE TABLE t_label_test_train AS
SELECT 
    user_id
    ,item_id
    ,1 AS label
FROM 
    p_user
WHERE 
    time>'2014-12-18' AND time<'2014-12-19' AND behavior_type=4
GROUP BY user_id,item_id;


-- select count(*) from p_user; 215903066


--DROP TABLE IF EXISTS tfea_ui_count;
--CREATE TABLE tfea_ui_count(user_id STRING,item_id STRING, time STRING, feature STRING);

--INSERT INTO TABLE tfea_ui_count 
--SELECT 
--    myudf(user_id,item_id,behavior_type,time,'2014-12-19',1) AS (user_id,item_id,time,feature) 
--FROM 
--    (
--    SELECT * 
--	FROM p_user tb2 
--    WHERE time < '2014-12-19' 
--    DISTRIBUTE BY user_id,item_id
--    SORT BY user_id,item_id,time DESC    ) tb1;

--INSERT INTO TABLE tfea_ui_count 
--SELECT 
--    myudf(user_id,item_id,behavior_type,time,'2014-12-18',1) AS (user_id,item_id,time,feature) 
--FROM     ( SELECT *  FROM p_user tb2  WHERE  time < '2014-12-18' DISTRIBUTE BY user_id,item_id 
--    SORT BY user_id,item_id,time DESC  ) tb1;

--INSERT INTO TABLE tfea_ui_count 
--SELECT   myudf(user_id,item_id,behavior_type,time,'2014-12-17',1) AS (user_id,item_id,time,feature) 
--FROM   ( SELECT * FROM p_user tb2  WHERE time < '2014-12-17' DISTRIBUTE BY user_id,item_id 
--    SORT BY user_id,item_id,time DESC  ) tb1;

DROP TABLE IF EXISTS t_test_train_fea_neg;
