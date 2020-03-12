package io.swagger.client.api;

import io.swagger.client.ApiClient;
import io.swagger.client.model.Category;
import io.swagger.client.model.CategoryRenameDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for CategoryControllerApi
 */
public class CategoryControllerApiTest {

    private CategoryControllerApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(CategoryControllerApi.class);
    }

    /**
     * getAllCategories
     *
     * 
     */
    @Test
    public void getAllCategoriesUsingGETTest() {
        String containing = null;
        // List<Category> response = api.getAllCategoriesUsingGET(containing);

        // TODO: test validations
    }
    /**
     * newCategory
     *
     * 
     */
    @Test
    public void newCategoryUsingPOSTTest() {
        Category body = null;
        // Category response = api.newCategoryUsingPOST(body);

        // TODO: test validations
    }
    /**
     * renameCategory
     *
     * 
     */
    @Test
    public void renameCategoryUsingPOSTTest() {
        CategoryRenameDto body = null;
        // Category response = api.renameCategoryUsingPOST(body);

        // TODO: test validations
    }
}
