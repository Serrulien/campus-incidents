package io.swagger.client.api;

import io.swagger.client.CollectionFormats.*;

import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.*;

import io.swagger.client.model.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LocationControllerApi {
  /**
   * getAllLocations
   * Sync method
   * 
   * @return List&lt;Location&gt;
   */
  @GET("/locations")
  List<Location> getAllLocationsUsingGET();
    

  /**
   * getAllLocations
   * Async method
   * @param cb callback method
   */
  @GET("/locations")
  void getAllLocationsUsingGET(
    Callback<List<Location>> cb
  );
  /**
   * getLocation
   * Sync method
   * 
   * @param id id (required)
   * @return Location
   */
  @GET("/locations/{id}")
  Location getLocationUsingGET1(
    @retrofit.http.Path("id") Long id
  );

  /**
   * getLocation
   * Async method
   * @param id id (required)
   * @param cb callback method
   */
  @GET("/locations/{id}")
  void getLocationUsingGET1(
    @retrofit.http.Path("id") Long id, Callback<Location> cb
  );
}
