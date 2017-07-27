package com.nowcoder.weibo.util;

public class sensitiveTest {
    public static void main1(String[] args) {
        SensitivewordFilter filter = new SensitivewordFilter();
        System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());
        String string = "贩卖毒品、冰毒、看小日本是不好的";
       // System.out.println("待检测语句字数：" + string.length());
        //long beginTime = System.currentTimeMillis();
        //Set<String> set = filter.getSensitiveWord(string, 1);
        //long endTime = System.currentTimeMillis();
        //System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
        //System.out.println("总共消耗时间为：" + (endTime - beginTime));
        System.out.println(filter.replaceSensitiveWord(string,"*"));
    }
}
