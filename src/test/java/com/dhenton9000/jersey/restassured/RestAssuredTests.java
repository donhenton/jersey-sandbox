/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.restassured;

import com.dhenton9000.jersey.client.server.JettyServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.jayway.restassured.RestAssured.*;
//import static com.jayway.restassured.matcher.RestAssuredMatchers.;
import static org.hamcrest.Matchers.*;

/**
 * Rest assured test demonstration.
 * http://www.hascode.com/2011/10/testing-restful-web-services-made-easy-using-the-rest-assured-framework/
 * @author dhenton
 */
public class RestAssuredTests {
    
    private static final int PORT = 4444;
    private static final String CONTEXT_PATH = "/app";
    private static final Logger LOG
            = LoggerFactory.getLogger(RestAssuredTests.class);
    private static JettyServer localWebServer;
    private static final String APP_URL = "http://localhost:" 
            + PORT + CONTEXT_PATH;

    @BeforeClass
    public static void start() throws Exception {
        localWebServer = new JettyServer(PORT, CONTEXT_PATH);

    }
    
    @AfterClass
    public static void stop() throws Exception {
        localWebServer.stopJettyServer();
    }


    @Test
    public void testSimpleCall() throws Exception {
      when() 
        .get(APP_URL+"/webapi/restaurant/1")
        .then().body("zipCode",is("53073"));
              
      
      

    }
    
    @Test
    public void testReviewListing() throws Exception {
      when() 
        .get(APP_URL+"/webapi/restaurant/1")
        .then()
        .body("reviewListing.startrating",hasItems(5,2,22,2)) ;
              
      
      

    }
    
    
}
