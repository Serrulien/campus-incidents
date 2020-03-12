package io.swagger.client.api;

import io.swagger.client.ApiClient;
import io.swagger.client.model.Location;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for LocationControllerApi
 */
public class LocationControllerApiTest {

    private LocationControllerApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(LocationControllerApi.class);
    }

    /**
     * getAllLocations
     *
     * 
     */
    @Test
    public void getAllLocationsUsingGETTest() {
        // List<Location> response = api.getAllLocationsUsingGET();

        // TODO: test validations
    }
    /**
     * getLocation
     *
     * 
     */
    @Test
    public void getLocationUsingGET1Test() {
        Long id = null;
        // Location response = api.getLocationUsingGET1(id);

        // TODO: test validations
    }
}
