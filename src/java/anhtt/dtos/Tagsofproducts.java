/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhtt.dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yorkit Tran
 */
@XmlRootElement
public class Tagsofproducts implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Products productId;
    private Tags tagId;

    public Tagsofproducts() {
    }

    public Tagsofproducts(Integer id) {
        this.id = id;
    }

    public Tagsofproducts(Integer id, Products productId, Tags tagId) {
        this.id = id;
        this.productId = productId;
        this.tagId = tagId;
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    @XmlElement
    public Tags getTagId() {
        return tagId;
    }

    public void setTagId(Tags tagId) {
        this.tagId = tagId;
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
        if (!(object instanceof Tagsofproducts)) {
            return false;
        }
        Tagsofproducts other = (Tagsofproducts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "anhtt.dtos.Tagsofproducts[ id=" + id + " ]";
    }
    
}
