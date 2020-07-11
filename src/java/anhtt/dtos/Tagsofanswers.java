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
public class Tagsofanswers implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Answers answerId;
    private Tags tagId;

    public Tagsofanswers() {
    }

    public Tagsofanswers(Integer id) {
        this.id = id;
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public Answers getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Answers answerId) {
        this.answerId = answerId;
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
        if (!(object instanceof Tagsofanswers)) {
            return false;
        }
        Tagsofanswers other = (Tagsofanswers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "anhtt.dtos.Tagsofanswers[ id=" + id + " ]";
    }
    
}
