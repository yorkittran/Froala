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
public class Favorites implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Products layerId;
    private Products topId;
    private Products bottomId;
    private Users userEmail;

    public Favorites() {
    }

    public Favorites(Integer id) {
        this.id = id;
    }

    public Favorites(Integer id, Products topId, Products bottomId, Users userEmail) {
        this.id = id;
        this.topId = topId;
        this.bottomId = bottomId;
        this.userEmail = userEmail;
    }

    public Favorites(Integer id, Products layerId, Products topId, Products bottomId, Users userEmail) {
        this.id = id;
        this.layerId = layerId;
        this.topId = topId;
        this.bottomId = bottomId;
        this.userEmail = userEmail;
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public Products getLayerId() {
        return layerId;
    }

    public void setLayerId(Products layerId) {
        this.layerId = layerId;
    }

    @XmlElement
    public Products getTopId() {
        return topId;
    }

    public void setTopId(Products topId) {
        this.topId = topId;
    }

    @XmlElement
    public Products getBottomId() {
        return bottomId;
    }

    public void setBottomId(Products bottomId) {
        this.bottomId = bottomId;
    }

    @XmlElement
    public Users getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(Users userEmail) {
        this.userEmail = userEmail;
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
        if (!(object instanceof Favorites)) {
            return false;
        }
        Favorites other = (Favorites) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "anhtt.dtos.Favorites[ id=" + id + " ]";
    }
    
}
