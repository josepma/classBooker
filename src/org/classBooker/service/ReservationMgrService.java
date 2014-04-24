/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import org.classBooker.entity.Reservation;
import org.classBooker.entity.User;

/**
 *
 * @author josepma
 */
public interface ReservationMgrService {
    public Reservation makeReservation(User user);
    public Reservation makeReservationByPeriod(User user);
    public void deleteReservation(Reservation r);
    public void suggestionSpace();
}
