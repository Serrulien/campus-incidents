package io.swagger.client.api;

import io.swagger.client.CollectionFormats.*;

import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.*;

import io.swagger.client.model.Category;
import io.swagger.client.model.CategoryRenameDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CategoryControllerApi {
  /**
   * getAllCategories
   * Sync method
   * 
   * @param containing containing (optional)
   * @return List&lt;Category&gt;
   */
  @GET("/categories")
  List<Category> getAllCategoriesUsingGET(
    @retrofit.http.Query("containing") String containing
  );

  /**
   * getAllCategories
   * Async method
   * @param containing containing (optional)
   * @param cb callback method
   */
  @GET("/categories")
  void getAllCategoriesUsingGET(
    @retrofit.http.Query("containing") String containing, Callback<List<Category>> cb
  );
  /**
   * newCategory
   * Sync method
   * 
   * @param body newCat (required)
   * @return Category
   */
  @POST("/categories")
  Category newCategoryUsingPOST(
    @retrofit.http.Body Category body
  );

  /**
   * newCategory
   * Async method
   * @param body newCat (required)
   * @param cb callback method
   */
  @POST("/categories")
  void newCategoryUsingPOST(
    @retrofit.http.Body Category body, Callback<Category> cb
  );
  /**
   * renameCategory
   * Sync method
   * 
   * @param body renaming (required)
   * @return Category
   */
  @POST("/categories/rename")
  Category renameCategoryUsingPOST(
    @retrofit.http.Body CategoryRenameDto body
  );

  /**
   * renameCategory
   * Async method
   * @param body renaming (required)
   * @param cb callback method
   */
  @POST("/categories/rename")
  void renameCategoryUsingPOST(
    @retrofit.http.Body CategoryRenameDto body, Callback<Category> cb
  );
}
