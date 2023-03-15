# RestaurantsApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addNewRestaurant**](RestaurantsApi.md#addNewRestaurant) | **POST** /restaurants | Add a new restaurant |
| [**getAllRestaurants**](RestaurantsApi.md#getAllRestaurants) | **GET** /restaurants | Get all restaurants |


<a name="addNewRestaurant"></a>
# **addNewRestaurant**
> RestaurantResponse addNewRestaurant(restaurantInput)

Add a new restaurant

Adds a new restaurant to the database.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RestaurantsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RestaurantsApi apiInstance = new RestaurantsApi(defaultClient);
    RestaurantInput restaurantInput = new RestaurantInput(); // RestaurantInput | The restaurant to add.
    try {
      RestaurantResponse result = apiInstance.addNewRestaurant(restaurantInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RestaurantsApi#addNewRestaurant");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **restaurantInput** | [**RestaurantInput**](RestaurantInput.md)| The restaurant to add. | |

### Return type

[**RestaurantResponse**](RestaurantResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | The new restaurant |  -  |
| **400** | Invalid input |  -  |
| **500** | Internal server error |  -  |

<a name="getAllRestaurants"></a>
# **getAllRestaurants**
> List&lt;Restaurant&gt; getAllRestaurants()

Get all restaurants

Returns a list of all restaurants.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RestaurantsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RestaurantsApi apiInstance = new RestaurantsApi(defaultClient);
    try {
      List<Restaurant> result = apiInstance.getAllRestaurants();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RestaurantsApi#getAllRestaurants");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Restaurant&gt;**](Restaurant.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of restaurants. |  -  |
| **500** | Internal server error |  -  |

