/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.service;

import com.dhenton9000.restaurant.model.Restaurant;
import com.dhenton9000.restaurant.model.Reviews;
import com.dhenton9000.restaurant.persistence.RestaurantJpaController;
import com.dhenton9000.restaurant.persistence.ReviewsJpaController;
 

/**
 *
 * @author dhenton
 */
public class RestaurantService {
    
     
    private RestaurantJpaController restaurantController;
    private ReviewsJpaController reviewsController;

    

    /**
     * @param restaurantController the restaurantController to set
     */
    public void setRestaurantController(RestaurantJpaController restaurantController) {
        this.restaurantController = restaurantController;
    }

    

    /**
     * @param reviewsController the reviewsController to set
     */
    public void setReviewsController(ReviewsJpaController reviewsController) {
        this.reviewsController = reviewsController;
    }

    public Restaurant getById(Integer id) {
         return restaurantController.findRestaurant(id);
    }

    public Reviews getReviewForRestaurant(Integer restaurantId, Integer reviewId) {
        return new Reviews();
    }
    
}
