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
public class Users implements Serializable {
 
    private String email, password, fullname, role;

    public Users() {
    }

    public Users(String email, String fullname, String role) {
        this.email = email;
        this.fullname = fullname;
        this.role = role;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElement
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @XmlElement
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
