# ReviewApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteReviewByID**](ReviewApi.md#deleteReviewByID) | **DELETE** /reviews/{reviewId} | Delete a review by ID |


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
import org.openapitools.client.api.ReviewApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ReviewApi apiInstance = new ReviewApi(defaultClient);
    Integer reviewId = 56; // Integer | The ID of the review to retrieve.
    try {
      apiInstance.deleteReviewByID(reviewId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewApi#deleteReviewByID");
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

