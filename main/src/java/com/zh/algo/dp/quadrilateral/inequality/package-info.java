package com.zh.algo.dp.quadrilateral.inequality;

/**
 * todo-zh 总结
 * 每个位置上的答案：存在不回退
 * ans = 最优 { 最差 {左指标，右指标}}
 * 或
 * ans = 最差 { 最优 {左指标，右指标}}
 *
 * 特征：
 * 指标与区间有单调性关系，例如非负数组中，区间越大，值单调不减
 *
 * 四边形不等式技巧特征
 * 1，两个可变参数的区间划分问题
 * 2，每个格子有枚举行为
 * 3，当两个可变参数固定一个，另一个参数和答案之间存在单调性关系
 * 4，而且两组单调关系是反向的：(升 升，降 降)  (升 降，降 升)
 * 5，能否获得指导枚举优化的位置对：上+右（上，枚举下限，右，枚举上限），或者，左+下（左，枚举下限，下，枚举上限）
 */