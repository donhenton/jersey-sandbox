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
import java.io.StringReader;
import java.net.URI;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author dhenton
 */
public class CodeBaseClass {

    private static Logger LOG = LogManager.getLogger(CodeBaseClass.class);

    protected URI getBaseURI() {

        //used for tcpmon set Listen port to 5555, 
        //Act as listener hostname: localhost port 8080 (local springmvc-app)
        //click add
        //   return UriBuilder.fromUri("http://localhost:5555/app/backbone").build();
        return UriBuilder.fromUri("http://localhost:8080/app/backbone").build();
        // return UriBuilder.fromUri("http://donhenton-springmvc3.herokuapp.com/app/backbone").build();

    }

    /**
     *
     * these two methods use the Java JSR JSON API which is READ-ONLY
     * @param response
     * @return 
     */
    protected JsonObject readInSingleRestaurant(Response response) {
        String jsonStringResponse = response.readEntity(String.class);
        LOG.debug(jsonStringResponse);
        StringReader sr = new StringReader(jsonStringResponse);
        JsonObject restaurantObject;
        try (JsonReader myreader = Json.createReader(sr)) {
            restaurantObject = myreader.readObject();
        }
        return restaurantObject;
    }

    public JsonObject getSingleRestaurant(int restaurantId, WebTarget target) {
        Response response = target.path("restaurant").path("" + restaurantId).request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);

        if (response.getStatus() != 200) {
            fail("status problem for request " + response.getStatus());
        }
        return readInSingleRestaurant(response);
    }

    /**
     * ****************************************************************
     * these classes use fasterxml jackson which is read/write to the
     * DOM-style JSON model
     * 
     */

    protected JsonNode getRestaurantJsonNode(int id) throws IOException {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        Response response = target.path("restaurant").path("" + 4).request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);

        if (response.getStatus() != 200) {
            fail("status problem for request " + response.getStatus());
        }

        String jsonStringResponse = response.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode sampleTree = mapper.readTree(jsonStringResponse);
        LOG.debug(jsonStringResponse);
        return sampleTree;
    }

    protected void updateRestaurant(JsonNode restaurant, String fieldName, String newValue) {

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());

        ObjectNode sampleObj = (ObjectNode) restaurant;
        sampleObj.put(fieldName, newValue);
        String newString = restaurant.toString();
        assertTrue(StringUtils.isNotEmpty(newString));

        Entity<String> itemToSend = Entity.json(newString);
        target.path("restaurant").path("4").request(MediaType.APPLICATION_JSON)
                .put(itemToSend);

    }

}
