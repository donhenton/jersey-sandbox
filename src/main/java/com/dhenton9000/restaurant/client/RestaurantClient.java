/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.restaurant.client;

import com.dhenton9000.jersey.template.exceptions.AppException;
import com.dhenton9000.restaurant.model.Restaurant;
import com.dhenton9000.restaurant.model.Reviews;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author dhenton
 */
public class RestaurantClient {

    private final String baseURL;
    private static Logger LOG = LogManager.getLogger(RestaurantClient.class);

    public RestaurantClient(String baseURL) {
        this.baseURL = baseURL;

    }

    private WebTarget getTarget() {

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        return client.target(getBaseURI());
    }

    private URI getBaseURI() {

        return UriBuilder.fromUri(baseURL).build();
        // http://localhost:8090/jersey-sandbox/webapi 
    }

    public Restaurant getRestaurant(int restaurantId) throws AppException {
        Restaurant r;
        Response response = getTarget().path("restaurant")
                .path("{restaurant}")
                .resolveTemplate("restaurant", restaurantId)
                .request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);
        
        r = readResponse(response,  Restaurant.class);

        return r;

    }
    
    public Reviews getReview(int restaurantId,int reviewId) throws AppException {
       Reviews r;
        
        
        Response response = getTarget().path("restaurant").path("review")
                .path("{restaurant}")
                .resolveTemplate("restaurant", restaurantId)
                .path("reviewid").path("{reviewid}")
                .resolveTemplate("reviewid", reviewId)
                .request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);
        
        
         
        
        
        
        r = readResponse(response,  Reviews.class);

        return r;

    }

    private <T> T  readResponse(Response response, Class<T> type) throws AppException {
        T item;
        String res = response.readEntity(String.class);
        ObjectMapper myreader = new ObjectMapper();
        if (response.getStatus() != 200) {
            AppException aa;
            try {
                aa = myreader.readValue(res, AppException.class);
            } catch (IOException ex) {
                aa = new AppException();
                aa.setDeveloperMessage(ex.getMessage() +" Cannot marshall error obj -- "+res);
            }

            throw aa;
        } else {
             
            AppException aa;
            try {
                item = myreader.readValue(res, type);
            } catch (IOException ex) {
                aa = new AppException();
                aa.setDeveloperMessage( ex.getMessage() +" Cannot marshall restaruant -- "+res);
                throw aa;
            }
        }
        return item;
    }

}
