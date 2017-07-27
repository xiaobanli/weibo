package com.nowcoder.weibo;





import org.apache.commons.io.IOUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/26.
 */
public class BanWordsUtil {
    // public Logger logger = Logger.getLogger(this.getClass());
    public static final int WORDS_MAX_LENGTH = 10;


    //敏感词列表
    public static Map[] banWordsList = null;

    //敏感词索引
    public static Map<String, Integer> wordIndex = new HashMap<String, Integer>();

    /*
    * 初始化敏感词库
    */
    public static void initBanWordsList() throws IOException {
        if (banWordsList == null) {
            banWordsList = new Map[WORDS_MAX_LENGTH];

            for (int i = 0; i < banWordsList.length; i++) {
                banWordsList[i] = new HashMap<String, String>();
            }
        }

        //敏感词词库所在目录，这里为txt文本，一个敏感词一行


        InputStream is = new FileInputStream("G://敏感词词库2016-09-26.txt");
        List<String> words = IOUtils.readLines(is,"UTF-8");

        for (String w : words) {
            if (StringUtils.isNotBlank(w)) {
                //将敏感词按长度存入map
                banWordsList[w.length()].put(w.toLowerCase(), "");

                Integer index = wordIndex.get(w.substring(0, 1));

                //生成敏感词索引，存入map
                if (index == null) {
                    index = 0;
                }

                int x = (int) Math.pow(2, w.length());
                index = (index | x);
                wordIndex.put(w.substring(0, 1), index);
            }
        }
    }

    /**
     * 检索敏感词
     * @param content
     * @return
     */
    public static List<String> searchBanWords(String content) {
        if (banWordsList == null) {
            try {
                initBanWordsList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        List<String> result = new ArrayList<String>();

        for (int i = 0; i < content.length(); i++) {
            Integer index = wordIndex.get(content.substring(i, i + 1));
            int p = 0;

            while ((index != null) && (index > 0)) {
                p++;
                index = index >> 1;

                String sub = "";

                if ((i + p) < (content.length() - 1)) {
                    sub = content.substring(i, i + p);
                } else {
                    sub = content.substring(i);
                }

                if (((index % 2) == 1) && banWordsList[p].containsKey(sub)) {
                    result.add(content.substring(i, i + p));

                    // System.out.println("找到敏感词："+content.substring(i,i+p));
                }
            }
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        String content = "毒品冰毒苹果 ";
        BanWordsUtil.initBanWordsList();
        List<String> banWordList = BanWordsUtil.searchBanWords(content);
        for(String s : banWordList){
            System.out.println("找到敏感词："+s);
        }
    }
}