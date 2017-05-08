package com.softwareprocess.sms.tools;

import java.util.Map;

public class MapUtil {
	
	public static void putMapPara(Map map,String key,Object value){
		if(null != value){
			map.put(key, value);
		}
	}
	
	public static void putMapParaEmpty(Map map,String key,Object value){
		if(value != null){
			map.put(key, value);
		}else {
			map.put(key, "");
		}
	}
	
	public static void putMapParaNull(Map map,String key,Object value){
		if(value != null){
			map.put(key, value);
		}else{
			map.put(key, null);
		}
	}

	public static String convertObjectToString(Object value){
		if(value != null){
			return String.valueOf(value);
		}
		return "";
	}

}
