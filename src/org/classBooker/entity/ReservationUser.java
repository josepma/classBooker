/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author josepma
 */
@Entity
    public abstract class ReservationUser extends User{
        @OneToMany(mappedBy="rUser")
        private List<Reservation> reservations;

    public ReservationUser() {
        super();
        this.reservations = new ArrayList<>();
    }

    public ReservationUser(String nif, String email, String name) {
        super(nif, email, name);
        this.reservations = new ArrayList<>();
        
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    

    
    
}
