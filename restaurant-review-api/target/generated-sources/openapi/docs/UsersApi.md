# UsersApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addNewUser**](UsersApi.md#addNewUser) | **POST** /users | Add a new user |
| [**deleteUserById**](UsersApi.md#deleteUserById) | **DELETE** /users/{userId} | Delete a user by ID |
| [**getUserByIdOrName**](UsersApi.md#getUserByIdOrName) | **GET** /users/{userId} | Get a user by ID or name |
| [**updateUserById**](UsersApi.md#updateUserById) | **PUT** /users/{userId} | Update a user |


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
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    UsersApi apiInstance = new UsersApi(defaultClient);
    UserInput userInput = new UserInput(); // UserInput | The user to add.
    try {
      User result = apiInstance.addNewUser(userInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#addNewUser");
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
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    UsersApi apiInstance = new UsersApi(defaultClient);
    String userId = "userId_example"; // String | The ID or name of the user to retrieve.
    try {
      apiInstance.deleteUserById(userId);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#deleteUserById");
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
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    UsersApi apiInstance = new UsersApi(defaultClient);
    String userId = "userId_example"; // String | The ID or name of the user to retrieve.
    try {
      UserResponse result = apiInstance.getUserByIdOrName(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#getUserByIdOrName");
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
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    UsersApi apiInstance = new UsersApi(defaultClient);
    String userId = "userId_example"; // String | The ID or name of the user to retrieve.
    UserInput userInput = new UserInput(); // UserInput | The updated user.
    try {
      UserResponse result = apiInstance.updateUserById(userId, userInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#updateUserById");
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

