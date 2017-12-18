package com.eedu.diagnosis.common.utils;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/21
 * Time: 14:55
 * Describe:
 */
public class GsonUtils {

	public GsonUtils() {
	}

	private static class GsonFactory{
		private static Gson gson = new Gson();
	}

	public static Gson getGson(){
		return GsonFactory.gson;
	}

	public Object readResolve(){
		return getGson();
	}
}
