/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import java.util.HashMap;
import java.util.List;
import org.classBooker.dao.exception.*;
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

    /**
     * Makes a complete reservation of a concrete space.
     * @param nif Nif of the user who is doing the reserve.
     * @param roomNb Number of the room which is going to be reserved.
     * @param buildingName Name of the building where the room is.
     * @param resDate Date of the reserve.
     * @return A ReservationResult encapsulating the Reservation done or a list of suggested rooms.
     * @throws Exception If the roomNb or BuildingName doesn't match with a correct Room or Building.
     */
    public ReservationResult makeCompleteReservationBySpace(String nif, String roomNb, String buildingName, DateTime resDate) throws Exception;

    /**
     *
     * @param roomld
     * @param nif
     * @param initialTime
     * @return
     * @throws Exception
     */
    public Reservation makeReservationBySpace(long roomld, String nif, DateTime initialTime)throws Exception;
    /**
     * Makes a reservation by type of space.
     * @param nif Nif of the user who makes the reservation.
     * @param type Type of room.
     * @param buildingName The building where we want to make the reservation.
     * @param capacity Capacity of the room.
     * @param date Date of the reservation.
     * @throws IncorrectBuildingException If the building name is incorrect.
     * @return The reservation done.
     */
    public Reservation makeReservationByType(String nif, String type, String buildingName, int capacity, DateTime date) throws IncorrectBuildingException;

    /**
     *
     * @param id
     * @return
     */
        public Reservation modifyReservation(long id);

    /**
     *
     * @param nif
     * @return
     */
    public List<Reservation> findReservationByNif(String nif);

    /**
     *
     * @param id
     * @return
     */
    public Reservation findReservationById(long id)throws IncorrectReservationException; //???? És el id d'una reserva?

    /**
     *
     * @param buildingName
     * @param roomNumber
     * @return
     */
    public List<Reservation> findReservationById(String buildingName, String roomNumber) throws IncorrectBuildingException;

    /**
     *
     * @param buildingName
     * @param roomNumber
     * @param date
     * @return
     */
   
    public Reservation findReservationById(String buildingName, String roomNumber, DateTime date) throws IncorrectBuildingException,
                                                                                                           IncorrectRoomException,
                                                                                                           IncorrectTimeException ;


    /**
     *
     * @param type
     * @return
     */
    public List<Reservation> findReservationByType(String type);

    /**
     *
     * @param type
     * @param date
     * @return
     */
    public List<Reservation> findReservationByType(String type, DateTime date);

    /**
     *
     * @return
     */
    public List<Reservation> getAllReservations();

    /**
     *
     * @param id
     */
    public void deleteReservation(long id) throws IncorrectReservationException;

    /**
     * Suggests alternative similar rooms when the specified Room is reserved in the date given.
     * @param roomNb Already reserved room number.
     * @param building Building where the already reserved room is.
     * @param resDate Date and time of the reserve.
     * @return A list of non reserved rooms similars to the given by parameters.
     * @throws IncorrectTypeException If the room type is incorrect.
     * @throws IncorrectBuildingException If the building is incorrect.
     * @throws IncorrectRoomException If the Room number is incorrect.
     */
    public List<Room> suggestionSpace(String roomNb, String building, DateTime resDate) throws IncorrectTypeException, IncorrectBuildingException, IncorrectRoomException;

    /**
     * Returns the user who have the reservation of a room in a specific date.
     * @param roomNb The room number.
     * @param building The building where the room is.
     * @param datetime The date and time of the reserve we are finding.
     * @return The user who have the reservation of that room.
     */
    public ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime);

    /**
     * Accepts a reservation.
     * @param reservation The reservation to accept.
     * @throws IncorrectReservationException If the reservation is not correct.
     * @throws IncorrectUserException If the user is not correct.
     * @throws IncorrectRoomException If the room is not correct.
     * @throws AlredyExistReservationException If the reservation is already done.
     */
    public void acceptReservation(Reservation reservation) throws IncorrectReservationException, IncorrectUserException, IncorrectRoomException,AlredyExistReservationException;
   
}
