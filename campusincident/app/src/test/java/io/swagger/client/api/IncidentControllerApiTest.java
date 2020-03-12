package io.swagger.client.api;

import io.swagger.client.ApiClient;
import io.swagger.client.model.Incident;
import io.swagger.client.model.IncidentDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for IncidentControllerApi
 */
public class IncidentControllerApiTest {

    private IncidentControllerApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(IncidentControllerApi.class);
    }

    /**
     * getAllIncidents
     *
     * 
     */
    @Test
    public void getAllIncidentsUsingGETTest() {
        // List<Incident> response = api.getAllIncidentsUsingGET();

        // TODO: test validations
    }
    /**
     * getLocation
     *
     * 
     */
    @Test
    public void getLocationUsingGETTest() {
        Long id = null;
        // Incident response = api.getLocationUsingGET(id);

        // TODO: test validations
    }
    /**
     * newIncident
     *
     * 
     */
    @Test
    public void newIncidentUsingPOSTTest() {
        IncidentDto body = null;
        // Incident response = api.newIncidentUsingPOST(body);

        // TODO: test validations
    }
}
