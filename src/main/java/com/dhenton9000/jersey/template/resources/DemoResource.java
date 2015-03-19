/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.resources;

import com.dhenton9000.jersey.template.model.TemplateModel;
import com.dhenton9000.jersey.template.service.SpringService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
 
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * see readme.md for url samples
 *
 * @author dhenton
 */
//@Component
@Path("demo")
@Api(value = "/demo", description = "Demo Api")
public class DemoResource {

    @Autowired
    private SpringService springService;

    @GET
    @Path("/model")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Get sample model")
    public TemplateModel getModel() {
        return springService.getTemplateModel();

    }

    public enum ERROR_TYPE {

        notfound, client, runtime
    }

    @GET
    @Path("/error/{error}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Test error throwing path")
      @ApiResponses({
         @ApiResponse(code = 500, message = "general error"),
         @ApiResponse(code = 404, message = "not found"),
         @ApiResponse(code = 405, message = "client exception")})
    public Object getError(@PathParam("error") ERROR_TYPE type) {

        switch (type) {
            
            case notfound:
                throw new NotFoundException("Not found");
                
            case client:
                throw new ClientErrorException("client exception",
                        Response.Status.METHOD_NOT_ALLOWED);
            default:
                throw new RuntimeException("runtime error");
                
                
        }

         

    }

}
