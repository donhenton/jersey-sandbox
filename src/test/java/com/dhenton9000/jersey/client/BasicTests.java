/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * http://www.oracle.com/technetwork/articles/java/json-1973242.html
 * https://jersey.java.net/documentation/latest/user-guide.html#client
 * http://www.j-tricks.com/tutorials/java-rest-client-for-jira-using-jersey
 * http://www.hascode.com/2013/12/jax-rs-2-0-rest-client-features-by-example/
 *
 * @author dhenton
 */
public class BasicTests extends CodeBaseClass {

    private static Logger LOG = LogManager.getLogger(BasicTests.class);

    @Test
    public void testGetRestaurantWithReadOnlyJsonApi() {
        ClientConfig config = new ClientConfig();

        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(getBaseURI());

        JsonObject restaurantObject = this.getSingleRestaurant(4, target);
        JsonString restaurantName = restaurantObject.getJsonString("name");
        LOG.debug(restaurantName.getString());
        assertTrue(restaurantName.toString().toUpperCase().contains("ARBY"));
    }

   
    @Test
    public void testUpdateRestaurantWithFasterXML() throws IOException {

        String newName = "Elmo Leonards";

        JsonNode restaurant = getRestaurantJsonNode(4);
        final String fieldName = "name";
        String oldName = restaurant.get(fieldName).asText();
        assertTrue(oldName.contains("Arby"));

        updateRestaurant(restaurant, fieldName, newName);

        JsonNode newRestaurant = getRestaurantJsonNode(4);
        String foundNewName = newRestaurant.get(fieldName).asText();
        assertEquals(newName, foundNewName);
        
        //revert
        updateRestaurant(restaurant, fieldName, oldName);

    }

    @Test
    public void testConfigReponseFilter() {
        ClientConfig config = new ClientConfig();
        config.register(ResponseFilter.class);
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        Response response = target.path("restaurant").request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);
        if (response.getStatus() != 200) {
            fail("status problem for request " + response.getStatus());
        }

        InputStream jsonStringResponse = response.readEntity(InputStream.class);
        JsonReader myreader = Json.createReader(jsonStringResponse);
        JsonArray restaurants = myreader.readArray();
        assertTrue(restaurants.size() > 5);
        assertTrue(ResponseFilter.gotHit);
        ResponseFilter.gotHit = false;

    }
    
    /**
     * this demonstrates writing headers to the request
     */
     @Test
    public void testConfigRequestFilter() {
        ClientConfig config = new ClientConfig();
        config.register(RequestFilter.class);
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        Response response = target.path("restaurant").request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);
        if (response.getStatus() != 200) {
            fail("status problem for request " + response.getStatus());
        }

        InputStream jsonStringResponse = response.readEntity(InputStream.class);
        JsonReader myreader = Json.createReader(jsonStringResponse);
        JsonArray restaurants = myreader.readArray();
        assertTrue(restaurants.size() > 5);
        assertTrue(RequestFilter.gotHit);
        RequestFilter.gotHit = false;

    }
    
    

    @Provider
    static class ResponseFilter implements ClientResponseFilter {

        public ResponseFilter() {
        }

        static boolean gotHit = false;

        @Override
        public void filter(ClientRequestContext requestContext,
                ClientResponseContext responseContext) throws IOException {
            LOG.debug("filter hit");
            gotHit = true;

        }
    }
    
     @Provider
    static class RequestFilter implements ClientRequestFilter {

        public RequestFilter() {
        }

        static boolean gotHit = false;

        @Override
        public void filter(ClientRequestContext requestContext) throws IOException {
             
            List<Object> items = new ArrayList<>();
            items.add("bonzo");
            requestContext.getHeaders().put("bonzo", items);
            gotHit = true;
            
        }

         
    }
    

}
