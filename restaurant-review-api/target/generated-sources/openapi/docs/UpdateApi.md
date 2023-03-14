# UpdateApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**updateRestaurantById**](UpdateApi.md#updateRestaurantById) | **PUT** /restaurants/{restaurantId} | Update a restaurant |
| [**updateReviewById**](UpdateApi.md#updateReviewById) | **PUT** /reviews/{reviewId} | Update a review |
| [**updateUserById**](UpdateApi.md#updateUserById) | **PUT** /users/{userId} | Update a user |


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
import org.openapitools.client.api.UpdateApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    UpdateApi apiInstance = new UpdateApi(defaultClient);
    Integer restaurantId = 56; // Integer | The ID of the restaurant to retrieve.
    RestaurantInput restaurantInput = new RestaurantInput(); // RestaurantInput | The updated restaurant.
    try {
      RestaurantResponse result = apiInstance.updateRestaurantById(restaurantId, restaurantInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UpdateApi#updateRestaurantById");
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

<a name="updateReviewById"></a>
# **updateReviewById**
> ReviewResponse updateReviewById(reviewId, reviewInput)

Update a review

Updates a review by ID.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UpdateApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    UpdateApi apiInstance = new UpdateApi(defaultClient);
    Integer reviewId = 56; // Integer | The ID of the review to retrieve.
    ReviewInput reviewInput = new ReviewInput(); // ReviewInput | The updated review.
    try {
      ReviewResponse result = apiInstance.updateReviewById(reviewId, reviewInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UpdateApi#updateReviewById");
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
| **reviewInput** | [**ReviewInput**](ReviewInput.md)| The updated review. | |

### Return type

[**ReviewResponse**](ReviewResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | The updated review |  -  |
| **400** | Invalid input |  -  |
| **404** | Review not found |  -  |
| **500** | Internal server error |  -  |

<a name="updateUserById"></a>
# **updateUserById**
> UserResponse updateUserById(userId, userInput)

Update a user

Updates a user by ID.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UpdateApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    UpdateApi apiInstance = new UpdateApi(defaultClient);
    String userId = "userId_example"; // String | The ID or name of the user to retrieve.
    UserInput userInput = new UserInput(); // UserInput | The updated user.
    try {
      UserResponse result = apiInstance.updateUserById(userId, userInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UpdateApi#updateUserById");
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
| **userInput** | [**UserInput**](UserInput.md)| The updated user. | |

### Return type

[**UserResponse**](UserResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | The updated user |  -  |
| **400** | Invalid input |  -  |
| **404** | User not found |  -  |
| **500** | Internal server error |  -  |

