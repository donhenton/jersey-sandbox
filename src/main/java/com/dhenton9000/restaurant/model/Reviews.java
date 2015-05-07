/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.restaurant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 *
 * @author dhenton
 */
@Entity
@ApiModel("The entity model for reviews of a restaurant")
@Table(name = "REVIEWS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reviews.findAll", query = "SELECT r FROM Reviews r"),
    @NamedQuery(name = "Reviews.findById", query = "SELECT r FROM Reviews r WHERE r.id = :id"),
    @NamedQuery(name = "Reviews.findByReviewlisting", query = "SELECT r FROM Reviews r WHERE r.reviewlisting = :reviewlisting"),
    @NamedQuery(name = "Reviews.findByStampdate", query = "SELECT r FROM Reviews r WHERE r.stampdate = :stampdate"),
    @NamedQuery(name = "Reviews.findByStartrating", query = "SELECT r FROM Reviews r WHERE r.startrating = :startrating")})
public class Reviews implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    @ApiModelProperty(example ="1",dataType = "Integer",required =true)
    private Integer id;
    @Size(max = 255)
    @Column(name = "REVIEWLISTING")
    @ApiModelProperty(example ="geta  job",notes="a review of the restaurant",dataType = "String",required =true)
    private String reviewlisting;
    @Column(name = "STAMPDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(example ="3/12/2014", notes = "tracking date",dataType = "Date",required =true)
    private Date stampdate;
    @Column(name = "STARTRATING")
    @ApiModelProperty(example ="4",dataType = "String",required =true)
    private Integer startrating;
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID")
    @ManyToOne
    @JsonBackReference
    private Restaurant restaurantId;

    public Reviews() {
    }

    public Reviews(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReviewlisting() {
        return reviewlisting;
    }

    public void setReviewlisting(String reviewlisting) {
        this.reviewlisting = reviewlisting;
    }

    public Date getStampdate() {
        return stampdate;
    }

    public void setStampdate(Date stampdate) {
        this.stampdate = stampdate;
    }

    public Integer getStartrating() {
        return startrating;
    }

    public void setStartrating(Integer startrating) {
        this.startrating = startrating;
    }

    public Restaurant getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Restaurant restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reviews)) {
            return false;
        }
        Reviews other = (Reviews) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dhenton9000.jersey.restaurant.Reviews[ id=" + id + " ]";
    }
    
}
