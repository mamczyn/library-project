package pl.edu.pjwstk.s32410.library.shared.json;

import com.google.gson.Gson;

public class JsonUtility {
	private static Gson gson = new Gson();
	
	public static String toJson(Object data) {
		return gson.toJson(data);
	}
	
	public static <D> D fromJson(String json, Class<D> type) {
		return gson.fromJson(json, type);
	}
}
