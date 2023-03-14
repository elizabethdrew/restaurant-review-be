/*
 * Restaurant Review API
 * An API for managing restaurant reviews
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.openapitools.client.model.Error;
import org.openapitools.client.model.Restaurant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openapitools.client.JSON;

/**
 * RestaurantResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-03-14T13:54:35.293849Z[Europe/London]")
public class RestaurantResponse {
  public static final String SERIALIZED_NAME_SUCCESS = "success";
  @SerializedName(SERIALIZED_NAME_SUCCESS)
  private Boolean success;

  public static final String SERIALIZED_NAME_RESTAURANT = "restaurant";
  @SerializedName(SERIALIZED_NAME_RESTAURANT)
  private Restaurant restaurant;

  public static final String SERIALIZED_NAME_ERROR = "error";
  @SerializedName(SERIALIZED_NAME_ERROR)
  private Error error;

  public RestaurantResponse() {
  }

  public RestaurantResponse success(Boolean success) {
    
    this.success = success;
    return this;
  }

   /**
   * Get success
   * @return success
  **/
  @javax.annotation.Nullable

  public Boolean getSuccess() {
    return success;
  }


  public void setSuccess(Boolean success) {
    this.success = success;
  }


  public RestaurantResponse restaurant(Restaurant restaurant) {
    
    this.restaurant = restaurant;
    return this;
  }

   /**
   * Get restaurant
   * @return restaurant
  **/
  @javax.annotation.Nullable

  public Restaurant getRestaurant() {
    return restaurant;
  }


  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }


  public RestaurantResponse error(Error error) {
    
    this.error = error;
    return this;
  }

   /**
   * Get error
   * @return error
  **/
  @javax.annotation.Nullable

  public Error getError() {
    return error;
  }


  public void setError(Error error) {
    this.error = error;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestaurantResponse restaurantResponse = (RestaurantResponse) o;
    return Objects.equals(this.success, restaurantResponse.success) &&
        Objects.equals(this.restaurant, restaurantResponse.restaurant) &&
        Objects.equals(this.error, restaurantResponse.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, restaurant, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestaurantResponse {\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    restaurant: ").append(toIndentedString(restaurant)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("success");
    openapiFields.add("restaurant");
    openapiFields.add("error");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to RestaurantResponse
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (!RestaurantResponse.openapiRequiredFields.isEmpty()) { // has required fields but JSON object is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in RestaurantResponse is not found in the empty JSON string", RestaurantResponse.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!RestaurantResponse.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `RestaurantResponse` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      // validate the optional field `restaurant`
      if (jsonObj.get("restaurant") != null && !jsonObj.get("restaurant").isJsonNull()) {
        Restaurant.validateJsonObject(jsonObj.getAsJsonObject("restaurant"));
      }
      // validate the optional field `error`
      if (jsonObj.get("error") != null && !jsonObj.get("error").isJsonNull()) {
        Error.validateJsonObject(jsonObj.getAsJsonObject("error"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!RestaurantResponse.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'RestaurantResponse' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<RestaurantResponse> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(RestaurantResponse.class));

       return (TypeAdapter<T>) new TypeAdapter<RestaurantResponse>() {
           @Override
           public void write(JsonWriter out, RestaurantResponse value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public RestaurantResponse read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of RestaurantResponse given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of RestaurantResponse
  * @throws IOException if the JSON string is invalid with respect to RestaurantResponse
  */
  public static RestaurantResponse fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, RestaurantResponse.class);
  }

 /**
  * Convert an instance of RestaurantResponse to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

