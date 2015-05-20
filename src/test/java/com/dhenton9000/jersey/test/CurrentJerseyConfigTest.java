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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.WebConfig;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author dhenton
 */
public class CurrentJerseyConfigTest extends JerseyTest {

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    protected DeploymentContext configureDeployment() {

        ServletContainer servletContainer = new ServletContainer(new JerseyConfig());

        Map<String, String> initParams = new HashMap<>();
        // initParams.put("javax.ws.rs.Application", "com.dhenton9000.jersey.template.config.JerseyConfig");
        return ServletDeploymentContext.builder(initParams)
                .servlet(servletContainer)
                .contextParam("contextConfigLocation", "classpath:applicationContext.xml")
                .addListener(org.springframework.web.context.ContextLoaderListener.class).build();

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
