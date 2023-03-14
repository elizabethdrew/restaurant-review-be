# ReviewsApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addNewReview**](ReviewsApi.md#addNewReview) | **POST** /reviews | Add a new review |
| [**getAllReviews**](ReviewsApi.md#getAllReviews) | **GET** /reviews | Get all reviews |
| [**getReviewById**](ReviewsApi.md#getReviewById) | **GET** /reviews/{reviewId} | Get a review by ID |
| [**searchReviewByRating**](ReviewsApi.md#searchReviewByRating) | **GET** /search/reviews | Search reviews by rating |
| [**updateReviewById**](ReviewsApi.md#updateReviewById) | **PUT** /reviews/{reviewId} | Update a review |


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
import org.openapitools.client.api.ReviewsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ReviewsApi apiInstance = new ReviewsApi(defaultClient);
    ReviewInput reviewInput = new ReviewInput(); // ReviewInput | The review to add.
    try {
      ReviewResponse result = apiInstance.addNewReview(reviewInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewsApi#addNewReview");
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
import org.openapitools.client.api.ReviewsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ReviewsApi apiInstance = new ReviewsApi(defaultClient);
    try {
      List<Review> result = apiInstance.getAllReviews();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewsApi#getAllReviews");
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
import org.openapitools.client.api.ReviewsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ReviewsApi apiInstance = new ReviewsApi(defaultClient);
    Integer reviewId = 56; // Integer | The ID of the review to retrieve.
    try {
      ReviewResponse result = apiInstance.getReviewById(reviewId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewsApi#getReviewById");
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

<a name="searchReviewByRating"></a>
# **searchReviewByRating**
> List&lt;Review&gt; searchReviewByRating(rating)

Search reviews by rating

Returns a list of reviews with the specified rating.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ReviewsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ReviewsApi apiInstance = new ReviewsApi(defaultClient);
    Integer rating = 56; // Integer | The rating of the reviews to search for.
    try {
      List<Review> result = apiInstance.searchReviewByRating(rating);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewsApi#searchReviewByRating");
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
| **rating** | **Integer**| The rating of the reviews to search for. | [optional] |

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
| **200** | A list of reviews matching the search criteria. |  -  |

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
import org.openapitools.client.api.ReviewsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ReviewsApi apiInstance = new ReviewsApi(defaultClient);
    Integer reviewId = 56; // Integer | The ID of the review to retrieve.
    ReviewInput reviewInput = new ReviewInput(); // ReviewInput | The updated review.
    try {
      ReviewResponse result = apiInstance.updateReviewById(reviewId, reviewInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewsApi#updateReviewById");
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

