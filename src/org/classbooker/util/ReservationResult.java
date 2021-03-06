/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.util;

import java.util.List;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.Room;

/**
 *
 * @author abg7
 */
public class ReservationResult {
    private Reservation reservation;
    private List<Room> suggestions;

    public ReservationResult(Reservation reservation) {
        this.reservation = reservation;
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
    
}
