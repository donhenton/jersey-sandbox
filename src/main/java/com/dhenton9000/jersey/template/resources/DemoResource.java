/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.resources;

//import com.dhenton9000.jersey.template.model.TemplateModel;
//import com.dhenton9000.jersey.template.service.SpringService;
import com.dhenton9000.jersey.template.model.TemplateModel;
import com.dhenton9000.jersey.template.service.SpringService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;

/**
 * http://localhost:8090/resources/demo/model
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

}
