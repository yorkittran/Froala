/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhtt.dtos;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Yorkit Tran
 */
@XmlRootElement
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String colour;
    private String description;
    private String image;
    private String url;
    private int recommend;
    private Collection<Tagsofproducts> tagsofproductsCollection;
    private Categories categoryId;

    public Products() {
    }

    public Products(Integer id) {
        this.id = id;
    }

    public Products(Integer id, String name, String colour, String description, String image, String url, int recommend) {
        this.id = id;
        this.name = name;
        this.colour = colour;
        this.description = description;
        this.image = image;
        this.url = url;
        this.recommend = recommend;
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @XmlElement
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlElement
    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    @XmlTransient
    public Collection<Tagsofproducts> getTagsofproductsCollection() {
        return tagsofproductsCollection;
    }

    public void setTagsofproductsCollection(Collection<Tagsofproducts> tagsofproductsCollection) {
        this.tagsofproductsCollection = tagsofproductsCollection;
    }

    public Categories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Categories categoryId) {
        this.categoryId = categoryId;
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
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "anhtt.dtos.Products[ id=" + id + " ]";
    }
    
}
