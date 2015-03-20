/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.client;

import com.dhenton9000.jersey.client.server.JettyServer;
import com.dhenton9000.jersey.template.exceptions.AppException;
import com.dhenton9000.restaurant.client.RestaurantClient;
import com.dhenton9000.restaurant.model.Restaurant;
import com.dhenton9000.restaurant.model.Reviews;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * derby needs to be turned on for this
 * 
 * @author dhenton
 */
public class ServerTest {

    private static final int PORT = 4444;
    private static final String CONTEXT_PATH = "/app";
    private static final Logger LOG
            = LoggerFactory.getLogger(ServerTest.class);
    private static JettyServer localWebServer;
    private static final String APP_URL = "http://localhost:" 
            + PORT + CONTEXT_PATH;

    @BeforeClass
    public static void start() throws Exception {
        localWebServer = new JettyServer(PORT, CONTEXT_PATH);

    }

    @Test
    public void testReadingSwaggerDoc() throws Exception {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet mockRequest = new HttpGet(APP_URL);

        HttpResponse mockResponse = client.execute(mockRequest);
        BufferedReader rd = new BufferedReader(new 
        InputStreamReader(mockResponse.getEntity().getContent()));
        String theString = IOUtils.toString(rd);
        assertNotNull(theString);
        assertTrue(theString.contains("swagger"));

    }
    
    
    @Test 
    public void testRESTCallForRestaurant() throws Exception  
    {
         HttpClient client = HttpClientBuilder.create().build();
        HttpGet mockRequest = new HttpGet(APP_URL+"/webapi/restaurant/4");

        HttpResponse mockResponse = client.execute(mockRequest);
        BufferedReader rd = new BufferedReader(new 
        InputStreamReader(mockResponse.getEntity().getContent()));
        String theString = IOUtils.toString(rd);
        //LOG.debug(theString);
         assertTrue(theString.contains("Arby"));
    }
    
    @Test(expected=AppException.class)
    public void testClientGetsRestaurantFail() throws AppException
    {
        RestaurantClient client = new RestaurantClient(APP_URL+"/webapi/");
        client.getRestaurant(-4444);
        
        
    }
    
    @Test 
    public void testClientGetsRestaurant() throws AppException
    {
        RestaurantClient client = new RestaurantClient(APP_URL+"/webapi/");
        Restaurant r = client.getRestaurant(1);
        assertEquals(new Integer(1),r.getId());
        
        
    }
    
    
    @Test
    public void testReview() throws AppException
    {
         RestaurantClient client = new RestaurantClient(APP_URL+"/webapi/");
        Reviews r = client.getReview(1,500);
        assertEquals(new Integer(500),r.getId());
    }
    
    @Test(expected=AppException.class)
    public void testReviewFail() throws AppException
    {
         RestaurantClient client = new RestaurantClient(APP_URL+"/webapi/");
        Reviews r = client.getReview(1,55500);
        assertEquals(new Integer(500),r.getId());
    }

    @AfterClass
    public static void stop() throws Exception {
        localWebServer.stopJettyServer();
    }

}
