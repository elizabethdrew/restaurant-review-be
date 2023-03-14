# SearchApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**searchRestaurantsByRatingOrCity**](SearchApi.md#searchRestaurantsByRatingOrCity) | **GET** /search/restaurants | Search restaurants by review rating or city |
| [**searchReviewByRating**](SearchApi.md#searchReviewByRating) | **GET** /search/reviews | Search reviews by rating |


<a name="searchRestaurantsByRatingOrCity"></a>
# **searchRestaurantsByRatingOrCity**
> List&lt;Restaurant&gt; searchRestaurantsByRatingOrCity(rating, city)

Search restaurants by review rating or city

Returns a list of restaurants matching the specified search criteria.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SearchApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SearchApi apiInstance = new SearchApi(defaultClient);
    Integer rating = 56; // Integer | The minimum rating of the restaurants to search for.
    String city = "city_example"; // String | The city of the restaurants to search for.
    try {
      List<Restaurant> result = apiInstance.searchRestaurantsByRatingOrCity(rating, city);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SearchApi#searchRestaurantsByRatingOrCity");
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
| **rating** | **Integer**| The minimum rating of the restaurants to search for. | [optional] |
| **city** | **String**| The city of the restaurants to search for. | [optional] |

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
| **200** | A list of restaurants matching the search criteria. |  -  |

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
import org.openapitools.client.api.SearchApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SearchApi apiInstance = new SearchApi(defaultClient);
    Integer rating = 56; // Integer | The rating of the reviews to search for.
    try {
      List<Review> result = apiInstance.searchReviewByRating(rating);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SearchApi#searchReviewByRating");
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

