/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhtt.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yorkit Tran
 */

@XmlRootElement(name = "products")
public class ListProducts implements Serializable {
    private List<Products> listProducts;

    public ListProducts() {
    }

    public ListProducts(List<Products> listProducts) {
        this.listProducts = listProducts;
    }

    @XmlElement(name = "product")
    public List<Products> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<Products> listProducts) {
        this.listProducts = listProducts;
    }

    public void add(Products product) {
        if (this.listProducts == null) {
            this.listProducts = new ArrayList<>();
        }
        this.listProducts.add(product);
    }
}
