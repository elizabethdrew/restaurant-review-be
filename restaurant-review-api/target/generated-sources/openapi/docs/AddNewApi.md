# AddNewApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addNewRestaurant**](AddNewApi.md#addNewRestaurant) | **POST** /restaurants | Add a new restaurant |
| [**addNewReview**](AddNewApi.md#addNewReview) | **POST** /reviews | Add a new review |
| [**addNewUser**](AddNewApi.md#addNewUser) | **POST** /users | Add a new user |


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
import org.openapitools.client.api.AddNewApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    AddNewApi apiInstance = new AddNewApi(defaultClient);
    RestaurantInput restaurantInput = new RestaurantInput(); // RestaurantInput | The restaurant to add.
    try {
      RestaurantResponse result = apiInstance.addNewRestaurant(restaurantInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AddNewApi#addNewRestaurant");
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

<a name="addNewReview"></a>
# **addNewReview**
> ReviewResponse addNewReview(reviewInput)

Add a new review

Adds a new review to the database.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AddNewApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    AddNewApi apiInstance = new AddNewApi(defaultClient);
    ReviewInput reviewInput = new ReviewInput(); // ReviewInput | The review to add.
    try {
      ReviewResponse result = apiInstance.addNewReview(reviewInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AddNewApi#addNewReview");
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
| **reviewInput** | [**ReviewInput**](ReviewInput.md)| The review to add. | |

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
| **201** | The new review |  -  |
| **400** | Invalid input |  -  |
| **500** | Internal server error |  -  |

<a name="addNewUser"></a>
# **addNewUser**
> User addNewUser(userInput)

Add a new user

Adds a new user to the database.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AddNewApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    AddNewApi apiInstance = new AddNewApi(defaultClient);
    UserInput userInput = new UserInput(); // UserInput | The user to add.
    try {
      User result = apiInstance.addNewUser(userInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AddNewApi#addNewUser");
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
| **userInput** | [**UserInput**](UserInput.md)| The user to add. | |

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | The newly created user. |  -  |
| **500** | Internal server error |  -  |

