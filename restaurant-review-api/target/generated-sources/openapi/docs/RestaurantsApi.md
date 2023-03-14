# RestaurantsApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addNewRestaurant**](RestaurantsApi.md#addNewRestaurant) | **POST** /restaurants | Add a new restaurant |
| [**deleteRestaurantById**](RestaurantsApi.md#deleteRestaurantById) | **DELETE** /restaurants/{restaurantId} | Delete a restaurant by ID |
| [**getAllRestaurants**](RestaurantsApi.md#getAllRestaurants) | **GET** /restaurants | Get all restaurants |
| [**getRestaurantById**](RestaurantsApi.md#getRestaurantById) | **GET** /restaurants/{restaurantId} | Get a restaurant by ID |
| [**searchRestaurantsByRatingOrCity**](RestaurantsApi.md#searchRestaurantsByRatingOrCity) | **GET** /search/restaurants | Search restaurants by review rating or city |
| [**updateRestaurantById**](RestaurantsApi.md#updateRestaurantById) | **PUT** /restaurants/{restaurantId} | Update a restaurant |


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

<a name="deleteRestaurantById"></a>
# **deleteRestaurantById**
> deleteRestaurantById(restaurantId)

Delete a restaurant by ID

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
    Integer restaurantId = 56; // Integer | The ID of the restaurant to retrieve.
    try {
      apiInstance.deleteRestaurantById(restaurantId);
    } catch (ApiException e) {
      System.err.println("Exception when calling RestaurantsApi#deleteRestaurantById");
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
| **restaurantId** | **Integer**| The ID of the restaurant to retrieve. | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Restaurant deleted |  -  |
| **404** | Restaurant not found |  -  |
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

<a name="getRestaurantById"></a>
# **getRestaurantById**
> RestaurantResponse getRestaurantById(restaurantId)

Get a restaurant by ID

Returns a single restaurant by ID.

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
    Integer restaurantId = 56; // Integer | The ID of the restaurant to retrieve.
    try {
      RestaurantResponse result = apiInstance.getRestaurantById(restaurantId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RestaurantsApi#getRestaurantById");
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
| **restaurantId** | **Integer**| The ID of the restaurant to retrieve. | |

### Return type

[**RestaurantResponse**](RestaurantResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | The restaurant |  -  |
| **404** | Restaurant not found |  -  |
| **500** | Internal server error |  -  |

<a name="searchRestaurantsByRatingOrCity"></a>
# **searchRestaurantsByRatingOrCity**
> List&lt;Restaurant&gt; searchRestaurantsByRatingOrCity(rating, city)

Search restaurants by review rating or city

Returns a list of restaurants matching the specified search criteria.

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
    Integer rating = 56; // Integer | The minimum rating of the restaurants to search for.
    String city = "city_example"; // String | The city of the restaurants to search for.
    try {
      List<Restaurant> result = apiInstance.searchRestaurantsByRatingOrCity(rating, city);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RestaurantsApi#searchRestaurantsByRatingOrCity");
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
| **rating** | **Integer**| The minimum rating of the restaurants to search for. | [optional] |
| **city** | **String**| The city of the restaurants to search for. | [optional] |

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
| **200** | A list of restaurants matching the search criteria. |  -  |

<a name="updateRestaurantById"></a>
# **updateRestaurantById**
> RestaurantResponse updateRestaurantById(restaurantId, restaurantInput)

Update a restaurant

Updates a restaurant by ID.

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
    Integer restaurantId = 56; // Integer | The ID of the restaurant to retrieve.
    RestaurantInput restaurantInput = new RestaurantInput(); // RestaurantInput | The updated restaurant.
    try {
      RestaurantResponse result = apiInstance.updateRestaurantById(restaurantId, restaurantInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RestaurantsApi#updateRestaurantById");
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
| **restaurantId** | **Integer**| The ID of the restaurant to retrieve. | |
| **restaurantInput** | [**RestaurantInput**](RestaurantInput.md)| The updated restaurant. | |

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
| **200** | The updated restaurant |  -  |
| **400** | Invalid input |  -  |
| **404** | Restaurant not found |  -  |
| **500** | Internal server error |  -  |

