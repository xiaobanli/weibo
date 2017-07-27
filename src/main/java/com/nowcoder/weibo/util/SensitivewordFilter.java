package com.nowcoder.weibo.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



public class SensitivewordFilter {

	public Map sensitiveWordMap = null;

	


	public SensitivewordFilter(){
		sensitiveWordMap = new SensitiveWordInit().initKeyWord();
	}
	

	public boolean isContaintSensitiveWord(String txt,int matchType){
		boolean flag = false;
		for(int i = 0 ; i < txt.length() ; i++){
			int matchFlag = this.CheckSensitiveWord(txt, i);
			if(matchFlag > 0){
				flag = true;
			}
		}
		return flag;
	}
	

	public Set<String> getSensitiveWord(String txt){
		Set<String> sensitiveWordList = new HashSet<String>();
		
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i);
			if(length > 0){
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;
			}
		}
		
		return sensitiveWordList;
	}
	

	public String replaceSensitiveWord(String txt,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt);
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		
		return resultTxt;
	}
	

	private String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}
		
		return resultReplace;
	}

	public int CheckSensitiveWord(String txt,int beginIndex){
		boolean  flag = false;
		int matchFlag = 0;
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);
			if(nowMap != null){
				matchFlag++;
				if("1".equals(nowMap.get("isEnd"))){
					flag = true;

				}
			}
			else{
				break;
			}
		}
		if(matchFlag < 2 || !flag){
			matchFlag = 0;
		}
		return matchFlag;
	}
	

}
