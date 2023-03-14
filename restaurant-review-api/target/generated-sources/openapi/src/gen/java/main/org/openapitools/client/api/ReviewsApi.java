/*
 * Restaurant Review API
 * An API for managing restaurant reviews
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiCallback;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.Configuration;
import org.openapitools.client.Pair;
import org.openapitools.client.ProgressRequestBody;
import org.openapitools.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import org.openapitools.client.model.Error;
import org.openapitools.client.model.Review;
import org.openapitools.client.model.ReviewInput;
import org.openapitools.client.model.ReviewResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class ReviewsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public ReviewsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ReviewsApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    /**
     * Build call for addNewReview
     * @param reviewInput The review to add. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> The new review </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid input </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call addNewReviewCall(ReviewInput reviewInput, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = reviewInput;

        // create path and map variables
        String localVarPath = "/reviews";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call addNewReviewValidateBeforeCall(ReviewInput reviewInput, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'reviewInput' is set
        if (reviewInput == null) {
            throw new ApiException("Missing the required parameter 'reviewInput' when calling addNewReview(Async)");
        }

        return addNewReviewCall(reviewInput, _callback);

    }

    /**
     * Add a new review
     * Adds a new review to the database.
     * @param reviewInput The review to add. (required)
     * @return ReviewResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> The new review </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid input </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ReviewResponse addNewReview(ReviewInput reviewInput) throws ApiException {
        ApiResponse<ReviewResponse> localVarResp = addNewReviewWithHttpInfo(reviewInput);
        return localVarResp.getData();
    }

    /**
     * Add a new review
     * Adds a new review to the database.
     * @param reviewInput The review to add. (required)
     * @return ApiResponse&lt;ReviewResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> The new review </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid input </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ReviewResponse> addNewReviewWithHttpInfo(ReviewInput reviewInput) throws ApiException {
        okhttp3.Call localVarCall = addNewReviewValidateBeforeCall(reviewInput, null);
        Type localVarReturnType = new TypeToken<ReviewResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Add a new review (asynchronously)
     * Adds a new review to the database.
     * @param reviewInput The review to add. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> The new review </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid input </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call addNewReviewAsync(ReviewInput reviewInput, final ApiCallback<ReviewResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = addNewReviewValidateBeforeCall(reviewInput, _callback);
        Type localVarReturnType = new TypeToken<ReviewResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAllReviews
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of reviews </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAllReviewsCall(final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/reviews";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAllReviewsValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getAllReviewsCall(_callback);

    }

    /**
     * Get all reviews
     * Returns a list of all reviews.
     * @return List&lt;Review&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of reviews </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public List<Review> getAllReviews() throws ApiException {
        ApiResponse<List<Review>> localVarResp = getAllReviewsWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Get all reviews
     * Returns a list of all reviews.
     * @return ApiResponse&lt;List&lt;Review&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of reviews </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<Review>> getAllReviewsWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getAllReviewsValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<List<Review>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get all reviews (asynchronously)
     * Returns a list of all reviews.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of reviews </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAllReviewsAsync(final ApiCallback<List<Review>> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAllReviewsValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<List<Review>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getReviewById
     * @param reviewId The ID of the review to retrieve. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> The review </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Review not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getReviewByIdCall(Integer reviewId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/reviews/{reviewId}"
            .replace("{" + "reviewId" + "}", localVarApiClient.escapeString(reviewId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getReviewByIdValidateBeforeCall(Integer reviewId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'reviewId' is set
        if (reviewId == null) {
            throw new ApiException("Missing the required parameter 'reviewId' when calling getReviewById(Async)");
        }

        return getReviewByIdCall(reviewId, _callback);

    }

    /**
     * Get a review by ID
     * Returns a single review by ID.
     * @param reviewId The ID of the review to retrieve. (required)
     * @return ReviewResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> The review </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Review not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ReviewResponse getReviewById(Integer reviewId) throws ApiException {
        ApiResponse<ReviewResponse> localVarResp = getReviewByIdWithHttpInfo(reviewId);
        return localVarResp.getData();
    }

    /**
     * Get a review by ID
     * Returns a single review by ID.
     * @param reviewId The ID of the review to retrieve. (required)
     * @return ApiResponse&lt;ReviewResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> The review </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Review not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ReviewResponse> getReviewByIdWithHttpInfo(Integer reviewId) throws ApiException {
        okhttp3.Call localVarCall = getReviewByIdValidateBeforeCall(reviewId, null);
        Type localVarReturnType = new TypeToken<ReviewResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get a review by ID (asynchronously)
     * Returns a single review by ID.
     * @param reviewId The ID of the review to retrieve. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> The review </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Review not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getReviewByIdAsync(Integer reviewId, final ApiCallback<ReviewResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getReviewByIdValidateBeforeCall(reviewId, _callback);
        Type localVarReturnType = new TypeToken<ReviewResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for searchReviewByRating
     * @param rating The rating of the reviews to search for. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of reviews matching the search criteria. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call searchReviewByRatingCall(Integer rating, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/search/reviews";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (rating != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("rating", rating));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call searchReviewByRatingValidateBeforeCall(Integer rating, final ApiCallback _callback) throws ApiException {
        return searchReviewByRatingCall(rating, _callback);

    }

    /**
     * Search reviews by rating
     * Returns a list of reviews with the specified rating.
     * @param rating The rating of the reviews to search for. (optional)
     * @return List&lt;Review&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of reviews matching the search criteria. </td><td>  -  </td></tr>
     </table>
     */
    public List<Review> searchReviewByRating(Integer rating) throws ApiException {
        ApiResponse<List<Review>> localVarResp = searchReviewByRatingWithHttpInfo(rating);
        return localVarResp.getData();
    }

    /**
     * Search reviews by rating
     * Returns a list of reviews with the specified rating.
     * @param rating The rating of the reviews to search for. (optional)
     * @return ApiResponse&lt;List&lt;Review&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of reviews matching the search criteria. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<Review>> searchReviewByRatingWithHttpInfo(Integer rating) throws ApiException {
        okhttp3.Call localVarCall = searchReviewByRatingValidateBeforeCall(rating, null);
        Type localVarReturnType = new TypeToken<List<Review>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Search reviews by rating (asynchronously)
     * Returns a list of reviews with the specified rating.
     * @param rating The rating of the reviews to search for. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of reviews matching the search criteria. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call searchReviewByRatingAsync(Integer rating, final ApiCallback<List<Review>> _callback) throws ApiException {

        okhttp3.Call localVarCall = searchReviewByRatingValidateBeforeCall(rating, _callback);
        Type localVarReturnType = new TypeToken<List<Review>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateReviewById
     * @param reviewId The ID of the review to retrieve. (required)
     * @param reviewInput The updated review. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> The updated review </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid input </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Review not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateReviewByIdCall(Integer reviewId, ReviewInput reviewInput, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = reviewInput;

        // create path and map variables
        String localVarPath = "/reviews/{reviewId}"
            .replace("{" + "reviewId" + "}", localVarApiClient.escapeString(reviewId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateReviewByIdValidateBeforeCall(Integer reviewId, ReviewInput reviewInput, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'reviewId' is set
        if (reviewId == null) {
            throw new ApiException("Missing the required parameter 'reviewId' when calling updateReviewById(Async)");
        }

        // verify the required parameter 'reviewInput' is set
        if (reviewInput == null) {
            throw new ApiException("Missing the required parameter 'reviewInput' when calling updateReviewById(Async)");
        }

        return updateReviewByIdCall(reviewId, reviewInput, _callback);

    }

    /**
     * Update a review
     * Updates a review by ID.
     * @param reviewId The ID of the review to retrieve. (required)
     * @param reviewInput The updated review. (required)
     * @return ReviewResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> The updated review </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid input </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Review not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ReviewResponse updateReviewById(Integer reviewId, ReviewInput reviewInput) throws ApiException {
        ApiResponse<ReviewResponse> localVarResp = updateReviewByIdWithHttpInfo(reviewId, reviewInput);
        return localVarResp.getData();
    }

    /**
     * Update a review
     * Updates a review by ID.
     * @param reviewId The ID of the review to retrieve. (required)
     * @param reviewInput The updated review. (required)
     * @return ApiResponse&lt;ReviewResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> The updated review </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid input </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Review not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ReviewResponse> updateReviewByIdWithHttpInfo(Integer reviewId, ReviewInput reviewInput) throws ApiException {
        okhttp3.Call localVarCall = updateReviewByIdValidateBeforeCall(reviewId, reviewInput, null);
        Type localVarReturnType = new TypeToken<ReviewResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update a review (asynchronously)
     * Updates a review by ID.
     * @param reviewId The ID of the review to retrieve. (required)
     * @param reviewInput The updated review. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> The updated review </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid input </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Review not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateReviewByIdAsync(Integer reviewId, ReviewInput reviewInput, final ApiCallback<ReviewResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateReviewByIdValidateBeforeCall(reviewId, reviewInput, _callback);
        Type localVarReturnType = new TypeToken<ReviewResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
