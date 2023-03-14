# DeleteApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteRestaurantById**](DeleteApi.md#deleteRestaurantById) | **DELETE** /restaurants/{restaurantId} | Delete a restaurant by ID |
| [**deleteReviewByID**](DeleteApi.md#deleteReviewByID) | **DELETE** /reviews/{reviewId} | Delete a review by ID |
| [**deleteUserById**](DeleteApi.md#deleteUserById) | **DELETE** /users/{userId} | Delete a user by ID |


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
import org.openapitools.client.api.DeleteApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DeleteApi apiInstance = new DeleteApi(defaultClient);
    Integer restaurantId = 56; // Integer | The ID of the restaurant to retrieve.
    try {
      apiInstance.deleteRestaurantById(restaurantId);
    } catch (ApiException e) {
      System.err.println("Exception when calling DeleteApi#deleteRestaurantById");
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

<a name="deleteReviewByID"></a>
# **deleteReviewByID**
> deleteReviewByID(reviewId)

Delete a review by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DeleteApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DeleteApi apiInstance = new DeleteApi(defaultClient);
    Integer reviewId = 56; // Integer | The ID of the review to retrieve.
    try {
      apiInstance.deleteReviewByID(reviewId);
    } catch (ApiException e) {
      System.err.println("Exception when calling DeleteApi#deleteReviewByID");
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

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Review deleted |  -  |
| **404** | Review not found |  -  |
| **500** | Internal server error |  -  |

<a name="deleteUserById"></a>
# **deleteUserById**
> deleteUserById(userId)

Delete a user by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DeleteApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DeleteApi apiInstance = new DeleteApi(defaultClient);
    String userId = "userId_example"; // String | The ID or name of the user to retrieve.
    try {
      apiInstance.deleteUserById(userId);
    } catch (ApiException e) {
      System.err.println("Exception when calling DeleteApi#deleteUserById");
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

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | User deleted |  -  |
| **404** | User not found |  -  |
| **500** | Internal server error |  -  |

