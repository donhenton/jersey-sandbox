/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.resources;

import com.dhenton9000.jersey.template.model.TemplateModel;
import com.dhenton9000.jersey.template.service.RestaurantService;
import com.dhenton9000.restaurant.model.Restaurant;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author dhenton
 */
@Path("demo")
@Api(value = "/restaurant", description = "Restaurant Api")
public class RestaurantResource {

    @Autowired
    private RestaurantService restaurantService;

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Get sample model")
    public Restaurant getById(@PathParam("id") Integer id) {
        return restaurantService.getById(id);

    }

}
