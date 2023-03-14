# UserApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteUserById**](UserApi.md#deleteUserById) | **DELETE** /users/{userId} | Delete a user by ID |


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
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    UserApi apiInstance = new UserApi(defaultClient);
    String userId = "userId_example"; // String | The ID or name of the user to retrieve.
    try {
      apiInstance.deleteUserById(userId);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#deleteUserById");
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

