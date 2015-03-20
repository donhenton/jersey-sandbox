/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.restaurant.persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.dhenton9000.restaurant.model.Restaurant;
import com.dhenton9000.restaurant.model.Reviews;
import com.dhenton9000.restaurant.persistence.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author dhenton
 */
public class ReviewsJpaController implements Serializable {

    public ReviewsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reviews reviews) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Restaurant restaurantId = reviews.getRestaurantId();
            if (restaurantId != null) {
                restaurantId = em.getReference(restaurantId.getClass(), restaurantId.getId());
                reviews.setRestaurantId(restaurantId);
            }
            em.persist(reviews);
            if (restaurantId != null) {
                restaurantId.getReviewCollection().add(reviews);
                restaurantId = em.merge(restaurantId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reviews reviews) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reviews persistentReviews = em.find(Reviews.class, reviews.getId());
            Restaurant restaurantIdOld = persistentReviews.getRestaurantId();
            Restaurant restaurantIdNew = reviews.getRestaurantId();
            if (restaurantIdNew != null) {
                restaurantIdNew = em.getReference(restaurantIdNew.getClass(), restaurantIdNew.getId());
                reviews.setRestaurantId(restaurantIdNew);
            }
            reviews = em.merge(reviews);
            if (restaurantIdOld != null && !restaurantIdOld.equals(restaurantIdNew)) {
                restaurantIdOld.getReviewCollection().remove(reviews);
                restaurantIdOld = em.merge(restaurantIdOld);
            }
            if (restaurantIdNew != null && !restaurantIdNew.equals(restaurantIdOld)) {
                restaurantIdNew.getReviewCollection().add(reviews);
                restaurantIdNew = em.merge(restaurantIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reviews.getId();
                if (findReviews(id) == null) {
                    throw new NonexistentEntityException("The reviews with id " + id + " no longer exists.");
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
            Reviews reviews;
            try {
                reviews = em.getReference(Reviews.class, id);
                reviews.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reviews with id " + id + " no longer exists.", enfe);
            }
            Restaurant restaurantId = reviews.getRestaurantId();
            if (restaurantId != null) {
                restaurantId.getReviewCollection().remove(reviews);
                restaurantId = em.merge(restaurantId);
            }
            em.remove(reviews);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reviews> findReviewsEntities() {
        return findReviewsEntities(true, -1, -1);
    }

    public List<Reviews> findReviewsEntities(int maxResults, int firstResult) {
        return findReviewsEntities(false, maxResults, firstResult);
    }

    private List<Reviews> findReviewsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reviews.class));
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

    public Reviews findReviews(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reviews.class, id);
        } finally {
            em.close();
        }
    }

    public int getReviewsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reviews> rt = cq.from(Reviews.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Reviews getReviewsForRestaurant(Integer restaurantId, Integer reviewId) {
        EntityManager em = getEntityManager();

        TypedQuery<Reviews> q = em.createQuery(
                "SELECT reviewObj FROM Restaurant restaurantObj "
                + "JOIN restaurantObj.reviewListing reviewObj "
                + "WHERE restaurantObj.id = :restaurantId AND "
                + "reviewObj.id = :reviewId", Reviews.class);

        q.setParameter("restaurantId", restaurantId);
        q.setParameter("reviewId", reviewId);
        try {
            return q.getSingleResult();
        } catch (javax.persistence.NoResultException ne) {
            return null;
        }

    }

}
