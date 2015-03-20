/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.restaurant.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import com.fasterxml.jackson.annotation.JsonProperty; 

/**
 *
 * @author dhenton
 */
@Entity
@Table(name = "RESTAURANT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Restaurant.findAll", query = "SELECT r FROM Restaurant r"),
    @NamedQuery(name = "Restaurant.findById", query = "SELECT r FROM Restaurant r WHERE r.id = :id"),
    @NamedQuery(name = "Restaurant.findByName", query = "SELECT r FROM Restaurant r WHERE r.name = :name"),
    @NamedQuery(name = "Restaurant.findByVersion", query = "SELECT r FROM Restaurant r WHERE r.version = :version"),
    @NamedQuery(name = "Restaurant.findByZipCode", query = "SELECT r FROM Restaurant r WHERE r.zipCode = :zipCode"),
    @NamedQuery(name = "Restaurant.findByCity", query = "SELECT r FROM Restaurant r WHERE r.city = :city"),
    @NamedQuery(name = "Restaurant.findByState", query = "SELECT r FROM Restaurant r WHERE r.state = :state")})
public class Restaurant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 75)
    @Column(name = "NAME")
    private String name;
    @Column(name = "VERSION")
    private Integer version;
    @Size(max = 10)
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @Size(max = 75)
    @Column(name = "CITY")
    private String city;
    @Size(max = 3)
    @Column(name = "STATE")
    private String state;
    @OneToMany(mappedBy = "restaurantId",fetch=FetchType.EAGER)
    @JsonProperty("reviewListing") 
    private Collection<Reviews> reviewCollection;

    public Restaurant() {
    }

    public Restaurant(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlTransient
    public Collection<Reviews> getReviewCollection() {
        return reviewCollection;
    }

    public void setReviewCollection(Collection<Reviews> reviewListing) {
        this.reviewCollection = reviewListing;
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
        if (!(object instanceof Restaurant)) {
            return false;
        }
        Restaurant other = (Restaurant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dhenton9000.jersey.restaurant.Restaurant[ id=" + id + " ]";
    }
    
}
