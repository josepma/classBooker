/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.entity;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import org.joda.time.DateTime;

/**
 *
 * @author josepma
 */
@Entity
@Table (name="RESERVATION")
public class Reservation {
    @Id
    @Column (name="IDENTIFIER")
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long reservationId;
        
    @Column (name="DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private DateTime reservationDate;
    
    @ManyToOne
    @JoinColumn(name = "USER", referencedColumnName = "name")
    private ReservationUser rUser;
    
    @ManyToOne
    @JoinColumn(name = "ROOM", referencedColumnName = "ID")
    private Room room;

    public Reservation() {
    }

    public Reservation(DateTime reservationDate, ReservationUser rUser, Room room) {
        this.reservationDate = reservationDate;
        this.rUser = rUser;
        this.room = room;
    }

    public long getReservationId() {
        return reservationId;
    }

    public DateTime getReservationDateTime() {
        return reservationDate;
    }

    public void setReservationDateTime(DateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public ReservationUser getrUser() {
        return rUser;
    }

    public void setrUser(ReservationUser rUser) {
        this.rUser = rUser;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (int) (this.reservationId ^ (this.reservationId >>> 32));
        hash = 31 * hash + Objects.hashCode(this.reservationDate);
        hash = 31 * hash + Objects.hashCode(this.rUser);
        hash = 31 * hash + Objects.hashCode(this.room);
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
        final Reservation other = (Reservation) obj;
        if (this.reservationId != other.reservationId) {
            return false;
        }
 
        if (!Objects.equals(this.reservationDate, other.reservationDate)) {
            return false;
        }
        if (!Objects.equals(this.rUser, other.rUser)) {
            return false;
        }
        if (!Objects.equals(this.room, other.room)) {
            return false;
        }
        return true;
    }
}
