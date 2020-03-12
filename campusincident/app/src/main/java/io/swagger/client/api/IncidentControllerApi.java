package io.swagger.client.api;

import io.swagger.client.CollectionFormats.*;

import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.*;

import io.swagger.client.model.Incident;
import io.swagger.client.model.IncidentDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IncidentControllerApi {
  /**
   * getAllIncidents
   * Sync method
   * 
   * @return List&lt;Incident&gt;
   */
  @GET("/incidents")
  List<Incident> getAllIncidentsUsingGET();
    

  /**
   * getAllIncidents
   * Async method
   * @param cb callback method
   */
  @GET("/incidents")
  void getAllIncidentsUsingGET(
    Callback<List<Incident>> cb
  );
  /**
   * getLocation
   * Sync method
   * 
   * @param id id (required)
   * @return Incident
   */
  @GET("/incidents/{id}")
  Incident getLocationUsingGET(
    @retrofit.http.Path("id") Long id
  );

  /**
   * getLocation
   * Async method
   * @param id id (required)
   * @param cb callback method
   */
  @GET("/incidents/{id}")
  void getLocationUsingGET(
    @retrofit.http.Path("id") Long id, Callback<Incident> cb
  );
  /**
   * newIncident
   * Sync method
   * 
   * @param body newIncident (required)
   * @return Incident
   */
  @POST("/incidents")
  Incident newIncidentUsingPOST(
    @retrofit.http.Body IncidentDto body
  );

  /**
   * newIncident
   * Async method
   * @param body newIncident (required)
   * @param cb callback method
   */
  @POST("/incidents")
  void newIncidentUsingPOST(
    @retrofit.http.Body IncidentDto body, Callback<Incident> cb
  );
}
