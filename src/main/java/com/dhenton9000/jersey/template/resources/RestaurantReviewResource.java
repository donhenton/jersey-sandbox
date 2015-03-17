/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.resources;

import com.dhenton9000.jersey.template.service.RestaurantService;
import com.dhenton9000.restaurant.model.Reviews;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author dhenton
 */
@Api(value = "reviewSubResources", description = "Review Resources", hidden = true)
public class RestaurantReviewResource {

    private Integer restaurantId = null;

    private RestaurantService restaurantService;

    /**
     * @return the restaurantId
     */
    public Integer getRestaurantId() {
        return restaurantId;
    }

    /**
     * @param restaurantId the restaurantId to set
     */
    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @GET
    @Produces("text/html")
    @Path("test")
    @ApiOperation(value = "Get stuff")
    public String getTest(@PathParam("reviewId") Integer reviewId) {
        return "get a job";
    }

    //https://deepintojee.wordpress.com/2011/12/04/testing-error-handling-in-restful-application-with-jersey-and-jbehave/

    @GET
    @Produces("application/json")
    @Path("reviewid/{reviewId}")
    @ApiOperation(value = "Get a restaurant review")
    public Response getReview(@PathParam("reviewId") Integer reviewId) {
        //http://localhost:8090/jersey-sandbox/restaurant/review/3/reviewid/44
        if (this.getRestaurantService() == null) {
            throw new RuntimeException("Null service");
        }
        Reviews r = getRestaurantService().getReviewForRestaurant(getRestaurantId(), reviewId);

       // if (CollectionUtils.isEmpty(results)) throw new NotFoundException(new Object[] { reference });
        if (r == null) {
            String info = String.format("No review {%d} for Restaurant {%d}", reviewId, getRestaurantId());
            throw new NotFoundException(info);
        }
        return Response.ok(r).build();

    }

    /**
     * @return the restaurantService
     */
    public RestaurantService getRestaurantService() {
        return restaurantService;
    }

    /**
     * @param restaurantService the restaurantService to set
     */
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

}
