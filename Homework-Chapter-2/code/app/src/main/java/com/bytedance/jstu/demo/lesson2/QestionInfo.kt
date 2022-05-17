package com.bytedance.jstu.demo.lesson2

val questionMap = mutableMapOf<Int, Triple<Pair<Int, Double>, Pair<String,String>, String>>(
    //题目序号、<<题解数、通过率>、<标题、难度>、详细信息>
    1 to Triple(
        Pair(1456,51.6),
        Pair("两数之和","简单"),
        "请计算两个数字的加和，可以使用计算器。"
    ),
    2 to Triple(
        Pair(1246,49.6),
        Pair("两数之积","中等"),
        "请计算两个数字的乘积，可以使用计算器。"
    ),
    3 to Triple(
        Pair(456,55.6),
        Pair("两数之差","简单"),
        "请计算两个数字的差，可以使用计算器。"
    ),
    4 to Triple(
        Pair(2316,45.6),
        Pair("两数之商","中等"),
        "请计算两个数字的商，可以使用计算器。"
    ),
    5 to Triple(
        Pair(2314,59.6),
        Pair("三角函数计算","简单"),
        "在直角三角形中，各边都扩大2倍，则锐角A的正弦值和余弦值会变化吗？"
    ),
    6 to Triple(
        Pair(1793,31.6),
        Pair("化学与生活","困难"),
        "用炉灶清洁剂（主要成分NaOH）清洗炉灶时，应带上橡胶手套，原因是？"
    ),
    7 to Triple(
        Pair(503,45.6),
        Pair("化学性质","中等"),
        "NaOH固体有吸水性是化学性质吗？"
    ),
    8 to Triple(
        Pair(2049,54.9),
        Pair("化学与生产","简单"),
        "经常用来改良酸性土壤的物质是什么？"
    )
)