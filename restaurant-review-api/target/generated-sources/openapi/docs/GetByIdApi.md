# GetByIdApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getRestaurantById**](GetByIdApi.md#getRestaurantById) | **GET** /restaurants/{restaurantId} | Get a restaurant by ID |
| [**getReviewById**](GetByIdApi.md#getReviewById) | **GET** /reviews/{reviewId} | Get a review by ID |
| [**getUserByIdOrName**](GetByIdApi.md#getUserByIdOrName) | **GET** /users/{userId} | Get a user by ID or name |


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
import org.openapitools.client.api.GetByIdApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    GetByIdApi apiInstance = new GetByIdApi(defaultClient);
    Integer restaurantId = 56; // Integer | The ID of the restaurant to retrieve.
    try {
      RestaurantResponse result = apiInstance.getRestaurantById(restaurantId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling GetByIdApi#getRestaurantById");
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

<a name="getReviewById"></a>
# **getReviewById**
> ReviewResponse getReviewById(reviewId)

Get a review by ID

Returns a single review by ID.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.GetByIdApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    GetByIdApi apiInstance = new GetByIdApi(defaultClient);
    Integer reviewId = 56; // Integer | The ID of the review to retrieve.
    try {
      ReviewResponse result = apiInstance.getReviewById(reviewId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling GetByIdApi#getReviewById");
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
| **reviewId** | **Integer**| The ID of the review to retrieve. | |

### Return type

[**ReviewResponse**](ReviewResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | The review |  -  |
| **404** | Review not found |  -  |
| **500** | Internal server error |  -  |

<a name="getUserByIdOrName"></a>
# **getUserByIdOrName**
> UserResponse getUserByIdOrName(userId)

Get a user by ID or name

Returns a single user by ID or name.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.GetByIdApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    GetByIdApi apiInstance = new GetByIdApi(defaultClient);
    String userId = "userId_example"; // String | The ID or name of the user to retrieve.
    try {
      UserResponse result = apiInstance.getUserByIdOrName(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling GetByIdApi#getUserByIdOrName");
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
| **userId** | **String**| The ID or name of the user to retrieve. | |

### Return type

[**UserResponse**](UserResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | The user |  -  |
| **404** | User not found |  -  |
| **500** | Internal server error |  -  |

