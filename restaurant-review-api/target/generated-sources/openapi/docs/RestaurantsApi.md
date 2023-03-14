# RestaurantsApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAllRestaurants**](RestaurantsApi.md#getAllRestaurants) | **GET** /restaurants | Get all restaurants |


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

