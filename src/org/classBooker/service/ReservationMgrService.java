/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import java.util.HashMap;
import java.util.List;
import org.classBooker.entity.Building;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;
import org.joda.time.DateTime;

/**
 *
 * @author josepma
 */
public interface ReservationMgrService {
    public Reservation makeReservationById(String nif, String buildingName, String roomNumber, DateTime date);
    public Reservation makeReservationById(String nif, String buildingName, String roomNumber, DateTime date, List<Integer> times);
    //public Reservation makeReservationByType(String nif, String type, int capacity, DateTime date);
    //public Reservation makeReservationByType(String nif, String type, int capacity, DateTime date, List<Integer> times);
    public Reservation modifyReservation(long id);
    public List<Reservation> findReservationByNif(String nif);
    public Reservation findReservationById(long id); //???? És el id d'una reserva?
    public List<Reservation> findReservationById(String buildingName, String roomNumber);
    public Reservation findReservationById(String buildingName, String roomNumber, DateTime date);
    public List<Reservation> findReservationByType(String type);
    public List<Reservation> findReservationByType(String type, DateTime date);
    public List<Reservation> getAllReservations();
    public void deleteReservation(long id);
    public HashMap<Building,Room> suggestionSpace(long id);
}
