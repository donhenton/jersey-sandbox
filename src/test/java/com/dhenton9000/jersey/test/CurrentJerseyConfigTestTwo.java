/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.test;

import com.dhenton9000.jersey.template.config.JerseyConfig;
import com.dhenton9000.jersey.template.resources.DemoResource;
import com.dhenton9000.restaurant.model.Restaurant;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * This test illustrates the default configuration of the JerseyTest
 * framework.
 * @author dhenton
 */
public class CurrentJerseyConfigTestTwo extends JerseyTest {
 
    /**
     * This is the Application object that is under test, included is Spring
     * injection
     * 
     * @return 
     */
    @Override
    public Application configure()
    {
        return new JerseyConfig();
    }
    
    
    @Test
    public void testErrorEndpoint() {
        WebTarget target = target("demo");

        Response s = target.path("error")
                .path(DemoResource.ERROR_TYPE.client.toString())
                .request().get(Response.class);

        Assert.assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), s.getStatus());
    }

    //derby database required here
    @Test
    public void testRestaurantGet() throws IOException {
        WebTarget target = target("restaurant");
        Response r
                = target.path("3").request().accept(MediaType.APPLICATION_JSON).get(Response.class);
        assertEquals(200, r.getStatus());
        String res = r.readEntity(String.class);
        assertTrue(res.contains("Subway"));

        ObjectMapper myreader = new ObjectMapper();
        Restaurant rr = myreader.readValue(res, Restaurant.class);
        assertTrue(rr.getName().contains("Subway"));
    }

}
