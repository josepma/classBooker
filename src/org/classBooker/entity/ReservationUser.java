/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.entity;

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
    @OneToMany(mappedBy="reservationId")
    private List<Reservation> reservations;

}
