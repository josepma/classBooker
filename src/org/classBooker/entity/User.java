    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.entity;

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
@Table(name="USUARI")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="USER_TYPE",
        discriminatorType=DiscriminatorType.STRING,length=5)
public abstract class User {
    @Id
    @Column(name="NIF")
    private String nif;
    
    @Column(name="email")
    private String email;
    
    @Column(name="name")
    private String name;

    @Override
    public String toString() {
        return "User{" + "nif=" + nif + ", email=" + email + ", name=" + name + '}';
    }

    public User(String nif, String email, String name) {
        this.nif = nif;
        this.email = email;
        this.name = name;
    }

    public User() {
        this.nif="";
        this.email="";
        this.name="";
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

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
