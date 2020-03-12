package io.swagger.client.api;

import io.swagger.client.CollectionFormats.*;

import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.*;

import io.swagger.client.model.ModelAndView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BasicErrorControllerApi {
  /**
   * errorHtml
   * Sync method
   * 
   * @return ModelAndView
   */
  @DELETE("/error")
  ModelAndView errorHtmlUsingDELETE();
    

  /**
   * errorHtml
   * Async method
   * @param cb callback method
   */
  @DELETE("/error")
  void errorHtmlUsingDELETE(
    Callback<ModelAndView> cb
  );
  /**
   * errorHtml
   * Sync method
   * 
   * @return ModelAndView
   */
  @GET("/error")
  ModelAndView errorHtmlUsingGET();
    

  /**
   * errorHtml
   * Async method
   * @param cb callback method
   */
  @GET("/error")
  void errorHtmlUsingGET(
    Callback<ModelAndView> cb
  );
  /**
   * errorHtml
   * Sync method
   * 
   * @return ModelAndView
   */
  @HEAD("/error")
  ModelAndView errorHtmlUsingHEAD();
    

  /**
   * errorHtml
   * Async method
   * @param cb callback method
   */
  @HEAD("/error")
  void errorHtmlUsingHEAD(
    Callback<ModelAndView> cb
  );
  /**
   * errorHtml
   * Sync method
   * 
   * @return ModelAndView
   */
  @OPTIONS("/error")
  ModelAndView errorHtmlUsingOPTIONS();
    

  /**
   * errorHtml
   * Async method
   * @param cb callback method
   */
  @OPTIONS("/error")
  void errorHtmlUsingOPTIONS(
    Callback<ModelAndView> cb
  );
  /**
   * errorHtml
   * Sync method
   * 
   * @return ModelAndView
   */
  @PATCH("/error")
  ModelAndView errorHtmlUsingPATCH();
    

  /**
   * errorHtml
   * Async method
   * @param cb callback method
   */
  @PATCH("/error")
  void errorHtmlUsingPATCH(
    Callback<ModelAndView> cb
  );
  /**
   * errorHtml
   * Sync method
   * 
   * @return ModelAndView
   */
  @POST("/error")
  ModelAndView errorHtmlUsingPOST();
    

  /**
   * errorHtml
   * Async method
   * @param cb callback method
   */
  @POST("/error")
  void errorHtmlUsingPOST(
    Callback<ModelAndView> cb
  );
  /**
   * errorHtml
   * Sync method
   * 
   * @return ModelAndView
   */
  @PUT("/error")
  ModelAndView errorHtmlUsingPUT();
    

  /**
   * errorHtml
   * Async method
   * @param cb callback method
   */
  @PUT("/error")
  void errorHtmlUsingPUT(
    Callback<ModelAndView> cb
  );
}
