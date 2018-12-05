package com.aliwh.android.quickyicha.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;
import jodd.typeconverter.TypeConverterManager;

public class JsonUtil {

	/**
	 * JsonObject转bean
	 * @param jo
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(JSONObject jo,Class<T> cls) {
		Iterator<String> it=jo.keys();
		T obj=null;
		try {
			obj=cls.newInstance();
		} catch (Exception e) {}
		while(it.hasNext()){
			String name=it.next();
			Object o = jo.opt(name);
			if(obj instanceof Map){
				((Map<String, Object>)obj).put(it.next(), o);
			}else{
				BeanUtil.setPropertyForcedSilent(obj, name, o);
			}
		}
		return obj;
	}
	/**
	 * JsonArray转List
	 * @param jr
	 * @param cls
	 * @return
	 */
	public static <T> List<T> toList(JSONArray jr,Class<T> cls) {
		List<T> list = new LinkedList<T>();
		if(jr == null){
			return list;
		}
		final int length = jr.length();
		for (int i = 0; i < length; i++) {
			try {
				JSONObject jsonObject = jr.optJSONObject(i);
				if(jsonObject != null){
					list.add(toBean(jsonObject,cls));
				}else{
					Object value = jr.get(i);
					list.add(TypeConverterManager.convertType(value, cls));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
