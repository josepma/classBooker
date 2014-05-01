/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import java.util.HashMap;
import java.util.List;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTypeException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;
import org.classBooker.util.ReservationResult;
import org.joda.time.DateTime;

/**
 *
 * @author josepma
 */
public interface ReservationMgrService {
    public ReservationResult makeCompleteReservationBySpace(String nif, String roomNb, String buildingName, DateTime resDate);
    public Reservation makeReservationBySpace(long roomld, String nif, DateTime initialTime)throws Exception;
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
    public List<Room> suggestionSpace(String roomNb, String building) throws IncorrectTypeException;
    public ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime) throws IncorrectRoomException;
    public void acceptReservation(Reservation reservation) throws IncorrectReservationException, IncorrectUserException, IncorrectRoomException ;
   
}
