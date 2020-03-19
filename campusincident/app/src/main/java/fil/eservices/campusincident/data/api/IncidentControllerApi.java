package fil.eservices.campusincident.data.api;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import fil.eservices.campusincident.data.ApiException;
import fil.eservices.campusincident.data.ApiInvoker;
import fil.eservices.campusincident.data.Pair;
import fil.eservices.campusincident.data.model.Incident;
import fil.eservices.campusincident.data.model.IncidentDto;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


public class IncidentControllerApi {
  String basePath = "http://54.38.242.184:8080";
  ApiInvoker apiInvoker = ApiInvoker.getInstance();

  public void addHeader(String key, String value) {
    getInvoker().addDefaultHeader(key, value);
  }

  public ApiInvoker getInvoker() {
    return apiInvoker;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public String getBasePath() {
    return basePath;
  }

  /**
   * getAllIncidents
   *
   * @return List<Incident>
   */
  public List<Incident> getAllIncidentsUsingGET () throws TimeoutException, ExecutionException, InterruptedException, ApiException {
    Object postBody = null;

    // create path and map variables
    String path = "/incidents";

    // query params
    List<Pair> queryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> headerParams = new HashMap<String, String>();
    // form params
    Map<String, String> formParams = new HashMap<String, String>();
    String[] contentTypes = {
    };
    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    if (contentType.startsWith("multipart/form-data")) {
      // file uploading
      MultipartEntityBuilder localVarBuilder = MultipartEntityBuilder.create();
      HttpEntity httpEntity = localVarBuilder.build();
      postBody = httpEntity;
    } else {
      // normal form params
    }

    String[] authNames = new String[] {  };

    try {
      String localVarResponse = apiInvoker.invokeAPI (basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType, authNames);
      if (localVarResponse != null) {
        return (List<Incident>) ApiInvoker.deserialize(localVarResponse, "array", Incident.class);
      } else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    } catch (InterruptedException ex) {
      throw ex;
    } catch (ExecutionException ex) {
      if (ex.getCause() instanceof VolleyError) {
        VolleyError volleyError = (VolleyError)ex.getCause();
        if (volleyError.networkResponse != null) {
          throw new ApiException(volleyError.networkResponse.statusCode, volleyError.getMessage());
        }
      }
      throw ex;
    } catch (TimeoutException ex) {
      throw ex;
    }
  }

  /**
   * getAllIncidents
   *

   */
  public void getAllIncidentsUsingGET (final Response.Listener<List<Incident>> responseListener, final Response.ErrorListener errorListener) {
    Object postBody = null;


    // create path and map variables
    String path = "/incidents".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> queryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> headerParams = new HashMap<String, String>();
    // form params
    Map<String, String> formParams = new HashMap<String, String>();



    String[] contentTypes = {

    };
    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    if (contentType.startsWith("multipart/form-data")) {
      // file uploading
      MultipartEntityBuilder localVarBuilder = MultipartEntityBuilder.create();


      HttpEntity httpEntity = localVarBuilder.build();
      postBody = httpEntity;
    } else {
      // normal form params
    }

    String[] authNames = new String[] {  };

    try {
      apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType, authNames,
              new Response.Listener<String>() {
                @Override
                public void onResponse(String localVarResponse) {
                  try {
                    responseListener.onResponse((List<Incident>) ApiInvoker.deserialize(localVarResponse,  "array", Incident.class));
                  } catch (ApiException exception) {
                    errorListener.onErrorResponse(new VolleyError(exception));
                  }
                }
              }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                  errorListener.onErrorResponse(error);
                }
              });
    } catch (ApiException ex) {
      errorListener.onErrorResponse(new VolleyError(ex));
    }
  }
  /**
   * getLocation
   *
   * @param id id
   * @return Incident
   */
  public Incident getLocationUsingGET (Long id) throws TimeoutException, ExecutionException, InterruptedException, ApiException {
    Object postBody = null;
    // verify the required parameter 'id' is set
    if (id == null) {
      VolleyError error = new VolleyError("Missing the required parameter 'id' when calling getLocationUsingGET",
              new ApiException(400, "Missing the required parameter 'id' when calling getLocationUsingGET"));
    }

    // create path and map variables
    String path = "/incidents/{id}".replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(Objects.requireNonNull(id).toString()));

    // query params
    List<Pair> queryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> headerParams = new HashMap<String, String>();
    // form params
    Map<String, String> formParams = new HashMap<String, String>();
    String[] contentTypes = {
    };
    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    if (contentType.startsWith("multipart/form-data")) {
      // file uploading
      MultipartEntityBuilder localVarBuilder = MultipartEntityBuilder.create();
      HttpEntity httpEntity = localVarBuilder.build();
      postBody = httpEntity;
    } else {
      // normal form params
    }

    String[] authNames = new String[] {  };

    try {
      String localVarResponse = apiInvoker.invokeAPI (basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType, authNames);
      if (localVarResponse != null) {
        return (Incident) ApiInvoker.deserialize(localVarResponse, "", Incident.class);
      } else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    } catch (InterruptedException ex) {
      throw ex;
    } catch (ExecutionException ex) {
      if (ex.getCause() instanceof VolleyError) {
        VolleyError volleyError = (VolleyError)ex.getCause();
        if (volleyError.networkResponse != null) {
          throw new ApiException(volleyError.networkResponse.statusCode, volleyError.getMessage());
        }
      }
      throw ex;
    } catch (TimeoutException ex) {
      throw ex;
    }
  }

  /**
   * getLocation
   *
   * @param id id
   */
  public void getLocationUsingGET (Long id, final Response.Listener<Incident> responseListener, final Response.ErrorListener errorListener) {
    Object postBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      VolleyError error = new VolleyError("Missing the required parameter 'id' when calling getLocationUsingGET",
              new ApiException(400, "Missing the required parameter 'id' when calling getLocationUsingGET"));
    }

    // create path and map variables
    String path = "/incidents/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(Objects.requireNonNull(id).toString()));

    // query params
    List<Pair> queryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> headerParams = new HashMap<String, String>();
    // form params
    Map<String, String> formParams = new HashMap<String, String>();



    String[] contentTypes = {

    };
    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    if (contentType.startsWith("multipart/form-data")) {
      // file uploading
      MultipartEntityBuilder localVarBuilder = MultipartEntityBuilder.create();


      HttpEntity httpEntity = localVarBuilder.build();
      postBody = httpEntity;
    } else {
      // normal form params
    }

    String[] authNames = new String[] {  };

    try {
      apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType, authNames,
              new Response.Listener<String>() {
                @Override
                public void onResponse(String localVarResponse) {
                  try {
                    responseListener.onResponse((Incident) ApiInvoker.deserialize(localVarResponse,  "", Incident.class));
                  } catch (ApiException exception) {
                    errorListener.onErrorResponse(new VolleyError(exception));
                  }
                }
              }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                  errorListener.onErrorResponse(error);
                }
              });
    } catch (ApiException ex) {
      errorListener.onErrorResponse(new VolleyError(ex));
    }
  }
  /**
   * newIncident
   *
   * @param newIncident newIncident
   * @return Incident
   */
  public Incident newIncidentUsingPOST (IncidentDto newIncident) throws TimeoutException, ExecutionException, InterruptedException, ApiException {
    Object postBody = newIncident;
    // verify the required parameter 'newIncident' is set
    if (newIncident == null) {
      VolleyError error = new VolleyError("Missing the required parameter 'newIncident' when calling newIncidentUsingPOST",
              new ApiException(400, "Missing the required parameter 'newIncident' when calling newIncidentUsingPOST"));
    }

    // create path and map variables
    String path = "/incidents";

    // query params
    List<Pair> queryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> headerParams = new HashMap<String, String>();
    // form params
    Map<String, String> formParams = new HashMap<String, String>();
    String[] contentTypes = {
            "application/json"
    };
    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    if (contentType.startsWith("multipart/form-data")) {
      // file uploading
      MultipartEntityBuilder localVarBuilder = MultipartEntityBuilder.create();
      HttpEntity httpEntity = localVarBuilder.build();
      postBody = httpEntity;
    } else {
      // normal form params
    }

    String[] authNames = new String[] {  };

    try {
      String localVarResponse = apiInvoker.invokeAPI (basePath, path, "POST", queryParams, postBody, headerParams, formParams, contentType, authNames);
      if (localVarResponse != null) {
        return (Incident) ApiInvoker.deserialize(localVarResponse, "", Incident.class);
      } else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    } catch (InterruptedException ex) {
      throw ex;
    } catch (ExecutionException ex) {
      if (ex.getCause() instanceof VolleyError) {
        VolleyError volleyError = (VolleyError)ex.getCause();
        if (volleyError.networkResponse != null) {
          throw new ApiException(volleyError.networkResponse.statusCode, volleyError.getMessage());
        }
      }
      throw ex;
    } catch (TimeoutException ex) {
      throw ex;
    }
  }

  /**
   * newIncident
   *
   * @param newIncident newIncident
   */
  public void newIncidentUsingPOST (IncidentDto newIncident, final Response.Listener<Incident> responseListener, final Response.ErrorListener errorListener) {
    Object postBody = newIncident;

    // verify the required parameter 'newIncident' is set
    if (newIncident == null) {
      VolleyError error = new VolleyError("Missing the required parameter 'newIncident' when calling newIncidentUsingPOST",
              new ApiException(400, "Missing the required parameter 'newIncident' when calling newIncidentUsingPOST"));
    }

    // create path and map variables
    String path = "/incidents".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> queryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> headerParams = new HashMap<String, String>();
    // form params
    Map<String, String> formParams = new HashMap<String, String>();



    String[] contentTypes = {
            "application/json"
    };
    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    if (contentType.startsWith("multipart/form-data")) {
      // file uploading
      MultipartEntityBuilder localVarBuilder = MultipartEntityBuilder.create();


      HttpEntity httpEntity = localVarBuilder.build();
      postBody = httpEntity;
    } else {
      // normal form params
    }

    String[] authNames = new String[] {  };

    try {
      apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, contentType, authNames,
              new Response.Listener<String>() {
                @Override
                public void onResponse(String localVarResponse) {
                  try {
                    responseListener.onResponse((Incident) ApiInvoker.deserialize(localVarResponse,  "", Incident.class));
                  } catch (ApiException exception) {
                    errorListener.onErrorResponse(new VolleyError(exception));
                  }
                }
              }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                  errorListener.onErrorResponse(error);
                }
              });
    } catch (ApiException ex) {
      errorListener.onErrorResponse(new VolleyError(ex));
    }
  }
}
