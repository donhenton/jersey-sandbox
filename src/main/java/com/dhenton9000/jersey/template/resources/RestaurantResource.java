/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.resources;

import com.dhenton9000.jersey.template.service.RestaurantService;
import com.dhenton9000.restaurant.model.Restaurant;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author dhenton
 */
@Path("/restaurant/")
@Api(value = "/restaurant", description = "Restaurant Api")
public class RestaurantResource {

    @Autowired
    private RestaurantService restaurantService;

    @Context
    private ResourceContext resourceContext;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Get a restaurant")
    public Restaurant getRestaurantById(@ApiParam(name = "id", required = true) @PathParam("id") Integer id) {
        return restaurantService.getById(id);

    }

    @Path("review/{restaurantId}/")
    
    public RestaurantReviewResource getReviewForRestaurant(@PathParam("restaurantId") Integer restaurantId
    ) {
        RestaurantReviewResource rResource = new RestaurantReviewResource();
        rResource.setRestaurantId(restaurantId);
        rResource.setRestaurantService(restaurantService);
        //resourceContext.getResource(RestaurantReviewResource.class);
        rResource.setRestaurantId(restaurantId);
        return rResource;
    }

}
