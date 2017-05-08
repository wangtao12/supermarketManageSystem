package com.softwareprocess.sms.tools;

import com.google.gson.Gson;

/**
 * JSON工具类
 */
public class JsonUtil {

    private static Gson gson = new Gson();
    /**
     * 对象转换为字符串
     * @param object 对象
     * @return JSON字符串
     */
    public static String toJSON(Object object){
        return gson.toJson(object);
    }
    /**
     * 字符串转换为对象
     * @param json JSON字符串
     * @param classType Java对象类型
     * @return
     * @return object对象
     */
    public static <Object> Object toObject(String json,Class<Object> classType){
        return gson.fromJson(json,classType);
    }
}
