/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.client;

import com.dhenton9000.jersey.client.server.JettyServer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

/**
 *
 * @author dhenton
 */
public class ServerTest   {
    
    private static final int PORT = 4444;
    private static final String CONTEXT_PATH = "/app";
    private static final Logger LOG
            = LoggerFactory.getLogger(ServerTest.class);
    private static JettyServer localWebServer;
    
    @BeforeClass
    public static void start() throws Exception {
        localWebServer = new JettyServer(PORT, CONTEXT_PATH);
        

    }

    
    
    @Test
    public void shouldTalkToServer() throws Exception {
         
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet mockRequest = new HttpGet("http://localhost:"+PORT+CONTEXT_PATH);
         
        HttpResponse mockResponse = client.execute(mockRequest);
        BufferedReader rd = new BufferedReader
          (new InputStreamReader(mockResponse.getEntity().getContent()));    
         String theString = IOUtils.toString(rd);
         assertNotNull(theString);
         LOG.debug(theString);
         
    }

    @AfterClass
    public static void stop() throws Exception {
        localWebServer.stopJettyServer();
    }
    
    
}
