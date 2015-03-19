package com.dhenton9000.jersey.template.resources;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
 
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.springframework.stereotype.Component;

 

/**
 * In this case the root is :
 * http://localhost:9090/jerseyrs/resources/
 * then add 'spring/myresource' because of the path
 * 
 * http://localhost:9090/jerseyrs/resources/spring/myresource
 * 
 * @author dhenton
 */
@Path("spring/myresource/")
 
public class MySpringResource {

    //use this to resolve ambigous resources
 
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;

    
    @GET
    @Produces("text/html")
    @ApiOperation(value = "Get Description")
    public String getIt() {
        return "<body><html><h1>Hi there from spring!  </h1></html></body>";
    }
    

    /**
     * nothing added here so as to delegate to the various methods in the
     * subresource class of TestChain
     * http://localhost:9090/jerseyrs/resources/spring/myresource/chain/55
     * @param id
     * @return 
     */
    @Path("chain/{id}/")
     
    public TestChain getChain(@PathParam("id") Integer id) {

        TestChain t = resourceContext.getResource(TestChain.class);
        t.setId(id);
        return t;
    }
    
    
    
 
    
}
