/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.util;

import java.util.List;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;

/**
 *
 * @author abg7
 */
public class ReservationResult {
    private Reservation reservation;
    private List<Room> suggestions;
    private ReservationUser rUser;

    public ReservationResult(Reservation reservation, ReservationUser rUser) {
        this.reservation = reservation;
        this.rUser = rUser;
    }

    public ReservationResult(List<Room> suggestions) {
        this.suggestions = suggestions;
    }
    
    public Reservation getReservation() {
        return reservation;
    }

    public List<Room> getSuggestions() {
        return suggestions;
    }

    public ReservationUser getrUser() {
        return rUser;
    }
    
    
}
