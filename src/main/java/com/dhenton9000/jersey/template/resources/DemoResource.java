/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.resources;

//import com.dhenton9000.jersey.template.model.TemplateModel;
//import com.dhenton9000.jersey.template.service.SpringService;
import com.dhenton9000.jersey.template.model.TemplateModel;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;

/**
 *
 * @author dhenton
 */
//@Component
@Path("demo")
public class DemoResource {

    //@Autowired
   // private SpringService springService;

    @GET
    @Path("/model")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})

    public TemplateModel getModel() {
        return new TemplateModel();
        
    }

}
