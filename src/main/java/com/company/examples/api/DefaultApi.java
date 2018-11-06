package com.company.examples.api;

import com.aisera.connector.client.invoker.*;
import com.company.examples.invoker.*;
import com.google.gson.reflect.TypeToken;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultApi {
  private ApiClient apiClient;

  public DefaultApi() {
    this(Configuration.getDefaultApiClient());
  }

  public DefaultApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Build call for createIncident
   *
   * @param tenantId                (required)
   * @param dataSourceId            (required)
   * @param type                    (required)
   * @param parameters              (required)
   * @param data                    (required)
   * @param progressListener        Progress listener
   * @param progressRequestListener Progress request listener
   * @return Call to execute
   * @throws ApiException If fail to serialize the request body object
   */
  public Call createIncidentCall(String tenantId, String dataSourceId, String type, String parameters, String data, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/incident";

    List<Pair> localVarQueryParams = new ArrayList<Pair>();

    Map<String, String> localVarHeaderParams = new HashMap<String, String>();

    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    if (tenantId != null)
      localVarFormParams.put("tenantId", tenantId);
    if (dataSourceId != null)
      localVarFormParams.put("dataSourceId", dataSourceId);
    if (type != null)
      localVarFormParams.put("type", type);
    if (parameters != null)
      localVarFormParams.put("parameters", parameters);
    if (data != null)
      localVarFormParams.put("data", data);

    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

    final String[] localVarContentTypes = {
      "application/x-www-form-urlencoded"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);

    if (progressListener != null) {
      apiClient.getHttpClient().networkInterceptors().add(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
          Response originalResponse = chain.proceed(chain.request());
          return originalResponse.newBuilder()
            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
            .build();
        }
      });
    }

    String[] localVarAuthNames = new String[]{};
    return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
  }

  @SuppressWarnings("rawtypes")
  private Call createIncidentValidateBeforeCall(String tenantId, String dataSourceId, String type, String parameters, String data, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

    // verify the required parameter 'tenantId' is set
    if (tenantId == null) {
      throw new ApiException("Missing the required parameter 'tenantId' when calling createIncident(Async)");
    }

    // verify the required parameter 'dataSourceId' is set
    if (dataSourceId == null) {
      throw new ApiException("Missing the required parameter 'dataSourceId' when calling createIncident(Async)");
    }

    // verify the required parameter 'type' is set
    if (type == null) {
      throw new ApiException("Missing the required parameter 'type' when calling createIncident(Async)");
    }

    // verify the required parameter 'parameters' is set
    if (parameters == null) {
      throw new ApiException("Missing the required parameter 'parameters' when calling createIncident(Async)");
    }

    // verify the required parameter 'data' is set
    if (data == null) {
      throw new ApiException("Missing the required parameter 'data' when calling createIncident(Async)");
    }

    Call call = createIncidentCall(tenantId, dataSourceId, type, parameters, data, progressListener, progressRequestListener);
    return call;
  }

  /**
   * Create a new incident on the target connector system
   * Create a new incident using the ticket framework
   *
   * @param tenantId     (required)
   * @param dataSourceId (required)
   * @param type         (required)
   * @param parameters   (required)
   * @param data         (required)
   * @return String
   * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
   */
  public String createIncident(String tenantId, String dataSourceId, String type, String parameters, String data) throws ApiException {
    ApiResponse<String> resp = createIncidentWithHttpInfo(tenantId, dataSourceId, type, parameters, data);
    return resp.getData();
  }

  /**
   * Create a new incident on the target connector system
   * Create a new incident using the ticket framework
   *
   * @param tenantId     (required)
   * @param dataSourceId (required)
   * @param type         (required)
   * @param parameters   (required)
   * @param data         (required)
   * @return ApiResponse&lt;String&gt;
   * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
   */
  public ApiResponse<String> createIncidentWithHttpInfo(String tenantId, String dataSourceId, String type, String parameters, String data) throws ApiException {
    Call call = createIncidentValidateBeforeCall(tenantId, dataSourceId, type, parameters, data, null, null);
    Type localVarReturnType = new TypeToken<String>() {
    }.getType();
    return apiClient.execute(call, localVarReturnType);
  }

  /**
   * Create a new incident on the target connector system (asynchronously)
   * Create a new incident using the ticket framework
   *
   * @param tenantId     (required)
   * @param dataSourceId (required)
   * @param type         (required)
   * @param parameters   (required)
   * @param data         (required)
   * @param callback     The callback to be executed when the API call finishes
   * @return The request call
   * @throws ApiException If fail to process the API call, e.g. serializing the request body object
   */
  public Call createIncidentAsync(String tenantId, String dataSourceId, String type, String parameters, String data, final ApiCallback<String> callback) throws ApiException {

    ProgressResponseBody.ProgressListener progressListener = null;
    ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

    if (callback != null) {
      progressListener = new ProgressResponseBody.ProgressListener() {
        @Override
        public void update(long bytesRead, long contentLength, boolean done) {
          callback.onDownloadProgress(bytesRead, contentLength, done);
        }
      };

      progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
        @Override
        public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
          callback.onUploadProgress(bytesWritten, contentLength, done);
        }
      };
    }

    Call call = createIncidentValidateBeforeCall(tenantId, dataSourceId, type, parameters, data, progressListener, progressRequestListener);
    Type localVarReturnType = new TypeToken<String>() {
    }.getType();
    apiClient.executeAsync(call, localVarReturnType, callback);
    return call;
  }
}
