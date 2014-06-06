    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author josepma
 */
@Entity
@Table(name="USERT")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="USER_TYPE",
        discriminatorType=DiscriminatorType.STRING)
public abstract class User {
    @Id
    @Column(name="NIF")
    private String nif;
    
    @Column(name="email")
    private String email;
    
    @Column(name="name")
    private String name;
    
    @Column(name="password")
    private String password;
    
    private static final int HASH = 5;
    private static final int HASH_MULTI = 79;    
    
    
    public User(String nif, String email, String name, String password) {
        this.nif = nif;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User() {
        this.nif="";
        this.email="";
        this.name="";
        this.password="";
    }

    @Override
    public String toString() {
        return "User{" + "nif=" + nif + ", email=" + email + ", name=" + name + ", password=" + password + '}';
    }

    @Override
    public int hashCode() {
        int hash = HASH;
        hash = HASH_MULTI * hash + Objects.hashCode(this.nif);
        hash = HASH_MULTI * hash + Objects.hashCode(this.email);
        hash = HASH_MULTI * hash + Objects.hashCode(this.name);
        hash = HASH_MULTI * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.nif, other.nif)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
    
    public String getNif() {
        return nif;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }    
    
}
