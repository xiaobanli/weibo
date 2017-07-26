package com.nowcoder.weibo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/26.
 */
public class SensitiveWorld {


    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        Map<String,String > sensitiveWordMap = new HashMap<>(keyWordSet.size());
        String key = null;
        Map nowMap = null;
        Map<String, String> newWordMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();    //关键字
            nowMap = sensitiveWordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if(wordMap != null){        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                }
                else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWordMap = new HashMap<String,String>();
                    newWordMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWordMap);
                    nowMap = newWordMap;
                }

                if(i == key.length() - 1){
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }
}
