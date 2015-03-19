/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.restaurant.persistence;

import com.dhenton9000.restaurant.model.Restaurant;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.dhenton9000.restaurant.model.Reviews;
import com.dhenton9000.restaurant.persistence.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
 

/**
 *
 * @author dhenton
 */
public class RestaurantJpaController implements Serializable {

    public RestaurantJpaController(EntityManagerFactory emf) {
        
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Restaurant restaurant) {
        if (restaurant.getReviewsCollection() == null) {
            restaurant.setReviewsCollection(new ArrayList<Reviews>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Reviews> attachedReviewsCollection = new ArrayList<Reviews>();
            for (Reviews reviewsCollectionReviewsToAttach : restaurant.getReviewsCollection()) {
                reviewsCollectionReviewsToAttach = em.getReference(reviewsCollectionReviewsToAttach.getClass(), reviewsCollectionReviewsToAttach.getId());
                attachedReviewsCollection.add(reviewsCollectionReviewsToAttach);
            }
            restaurant.setReviewsCollection(attachedReviewsCollection);
            em.persist(restaurant);
            for (Reviews reviewsCollectionReviews : restaurant.getReviewsCollection()) {
                Restaurant oldRestaurantIdOfReviewsCollectionReviews = reviewsCollectionReviews.getRestaurantId();
                reviewsCollectionReviews.setRestaurantId(restaurant);
                reviewsCollectionReviews = em.merge(reviewsCollectionReviews);
                if (oldRestaurantIdOfReviewsCollectionReviews != null) {
                    oldRestaurantIdOfReviewsCollectionReviews.getReviewsCollection().remove(reviewsCollectionReviews);
                    oldRestaurantIdOfReviewsCollectionReviews = em.merge(oldRestaurantIdOfReviewsCollectionReviews);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Restaurant restaurant) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Restaurant persistentRestaurant = em.find(Restaurant.class, restaurant.getId());
            Collection<Reviews> reviewsCollectionOld = persistentRestaurant.getReviewsCollection();
            Collection<Reviews> reviewsCollectionNew = restaurant.getReviewsCollection();
            Collection<Reviews> attachedReviewsCollectionNew = new ArrayList<Reviews>();
            for (Reviews reviewsCollectionNewReviewsToAttach : reviewsCollectionNew) {
                reviewsCollectionNewReviewsToAttach = em.getReference(reviewsCollectionNewReviewsToAttach.getClass(), reviewsCollectionNewReviewsToAttach.getId());
                attachedReviewsCollectionNew.add(reviewsCollectionNewReviewsToAttach);
            }
            reviewsCollectionNew = attachedReviewsCollectionNew;
            restaurant.setReviewsCollection(reviewsCollectionNew);
            restaurant = em.merge(restaurant);
            for (Reviews reviewsCollectionOldReviews : reviewsCollectionOld) {
                if (!reviewsCollectionNew.contains(reviewsCollectionOldReviews)) {
                    reviewsCollectionOldReviews.setRestaurantId(null);
                    reviewsCollectionOldReviews = em.merge(reviewsCollectionOldReviews);
                }
            }
            for (Reviews reviewsCollectionNewReviews : reviewsCollectionNew) {
                if (!reviewsCollectionOld.contains(reviewsCollectionNewReviews)) {
                    Restaurant oldRestaurantIdOfReviewsCollectionNewReviews = reviewsCollectionNewReviews.getRestaurantId();
                    reviewsCollectionNewReviews.setRestaurantId(restaurant);
                    reviewsCollectionNewReviews = em.merge(reviewsCollectionNewReviews);
                    if (oldRestaurantIdOfReviewsCollectionNewReviews != null && !oldRestaurantIdOfReviewsCollectionNewReviews.equals(restaurant)) {
                        oldRestaurantIdOfReviewsCollectionNewReviews.getReviewsCollection().remove(reviewsCollectionNewReviews);
                        oldRestaurantIdOfReviewsCollectionNewReviews = em.merge(oldRestaurantIdOfReviewsCollectionNewReviews);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = restaurant.getId();
                if (findRestaurant(id) == null) {
                    throw new NonexistentEntityException("The restaurant with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Restaurant restaurant;
            try {
                restaurant = em.getReference(Restaurant.class, id);
                restaurant.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The restaurant with id " + id + " no longer exists.", enfe);
            }
            Collection<Reviews> reviewsCollection = restaurant.getReviewsCollection();
            for (Reviews reviewsCollectionReviews : reviewsCollection) {
                reviewsCollectionReviews.setRestaurantId(null);
                reviewsCollectionReviews = em.merge(reviewsCollectionReviews);
            }
            em.remove(restaurant);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Restaurant> findRestaurantEntities() {
        return findRestaurantEntities(true, -1, -1);
    }

    public List<Restaurant> findRestaurantEntities(int maxResults, int firstResult) {
        return findRestaurantEntities(false, maxResults, firstResult);
    }

    private List<Restaurant> findRestaurantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Restaurant.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Restaurant findRestaurant(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Restaurant.class, id);
        } finally {
            em.close();
        }
    }

    public int getRestaurantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Restaurant> rt = cq.from(Restaurant.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
