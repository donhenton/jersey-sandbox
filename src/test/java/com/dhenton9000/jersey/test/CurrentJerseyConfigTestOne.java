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
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

/**
 *  This configures the test container as using a deployment context.
 *  This allows the full addition of filters and/or servlets (eg swagger)
 * 
 * @author dhenton
 */
public class CurrentJerseyConfigTestOne extends JerseyTest {
 
    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }
 
    @Override
    protected DeploymentContext configureDeployment() {

        ServletContainer servletContainer = new ServletContainer(new JerseyConfig());

        Map<String, String> initParams = new HashMap<>();        
        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("entityManagerFactoryBeanName", "myEmf");
      
        return ServletDeploymentContext.builder(initParams)
                .servlet(servletContainer)
                .addFilter(OpenEntityManagerInViewFilter.class, 
                        "openEntityManagerInViewFilter",filterParams)
                .contextParam("contextConfigLocation", 
                        "classpath:applicationContext.xml")
                .addListener(org.springframework.web.
                        context.ContextLoaderListener.class).build();

    }
 
    
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
