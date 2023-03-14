# GetAllApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAllRestaurants**](GetAllApi.md#getAllRestaurants) | **GET** /restaurants | Get all restaurants |
| [**getAllReviews**](GetAllApi.md#getAllReviews) | **GET** /reviews | Get all reviews |


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
import org.openapitools.client.api.GetAllApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    GetAllApi apiInstance = new GetAllApi(defaultClient);
    try {
      List<Restaurant> result = apiInstance.getAllRestaurants();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling GetAllApi#getAllRestaurants");
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

<a name="getAllReviews"></a>
# **getAllReviews**
> List&lt;Review&gt; getAllReviews()

Get all reviews

Returns a list of all reviews.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.GetAllApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    GetAllApi apiInstance = new GetAllApi(defaultClient);
    try {
      List<Review> result = apiInstance.getAllReviews();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling GetAllApi#getAllReviews");
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

[**List&lt;Review&gt;**](Review.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of reviews |  -  |
| **500** | Internal server error |  -  |

