package io.nanuvem.demo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json Utilities
 */
public class JsonUtil {

  /**
   * Empty constructor
   */
  private JsonUtil() {
    // SonarLint
  }

  /**
   * GSON instance
   * @see Gson
   */
  private static final Gson GSON = new GsonBuilder()
    .setDateFormat("yyyy-MM-dd HH:mm")
    .create();

  /**
   * Map an object to a JSON String
   * @param object Object to be mapped
   * @return JSON String
   */
  public static String toJson(Object object) {
    return GSON.toJson(object);
  }

  /**
   * Map a JSON String to an Object
   * @param json JSON String
   * @param dClass Class of the object
   * @param <D> Class of the Object
   * @return Object from JSON String
   */
  public static <D> D fromJson(String json, Class<D> dClass) {
    return GSON.fromJson(json, dClass);
  }
}
