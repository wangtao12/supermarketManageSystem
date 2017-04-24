package com.softwareprocess.sms.tools;

import java.util.List;
import java.util.Map;

public class StringUtil {
	
	/**
	 * List<Map<String, Object>>特定字段转换为指定分隔符字符串
	 * @param list 输入列表
	 * @param field 字段名
	 * @param separator 分隔符
	 * @return
	 */
	public String convertListMapToString(List<Map<String, Object>> list,String field,String separator) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> temp = list.get(i);
			if(temp.containsKey(field)) {
				if(i == (list.size() -1)){
					result.append(temp.get(field));
					break;
				}
				result.append(temp.get(field) + separator);
			}
		}
		return result.toString();
	}

}
