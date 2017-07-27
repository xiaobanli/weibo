package com.nowcoder.weibo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SensitiveWordInit {
	private String ENCODING = "GBK";

	public HashMap sensitiveWordMap;
	
	public SensitiveWordInit(){
		super();
	}


	public Map initKeyWord(){
		try {

			Set<String> keyWordSet = readSensitiveWordFile();

			addSensitiveWordToHashMap(keyWordSet);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sensitiveWordMap;
	}


	private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
		sensitiveWordMap = new HashMap(keyWordSet.size());
		String key = null;  
		Map nowMap = null;
		Map<String, String> newWorMap = null;

		Iterator<String> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			key = iterator.next();
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);
				Object wordMap = nowMap.get(keyChar);
				
				if(wordMap != null){
					nowMap = (Map) wordMap;
				}
				else{
					newWorMap = new HashMap<String,String>();
					newWorMap.put("isEnd", "0");
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}
				
				if(i == key.length() - 1){
					nowMap.put("isEnd", "1");
				}
			}
		}
	}


	private Set<String> readSensitiveWordFile() throws Exception{
		Set<String> set = null;
		
		File file = new File("D:\\SensitiveWord.txt");
		InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
		try {
			if(file.isFile() && file.exists()){
				set = new HashSet<String>();
				BufferedReader bufferedReader = new BufferedReader(read);
				String txt = null;
				while((txt = bufferedReader.readLine()) != null){
					set.add(txt);
			    }
			}
			else{
				throw new Exception("敏感词文件异常");
			}
		} catch (Exception e) {
			throw e;
		}finally{
			read.close();
		}
		return set;
	}
}
