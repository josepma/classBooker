/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import org.classbooker.dao.exception.DAOException;
import java.util.HashMap;
import java.util.List;
import org.classbooker.dao.ReservationDAO;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.UserDAO;
import org.classbooker.entity.Building;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;
import org.classbooker.entity.User;
import org.classbooker.util.ReservationResult;
import org.joda.time.DateTime;

/**
 *
 * @author josepma
 */
public interface ReservationMgrService {

    /**
     * Returns an avaliable reservation according to the specified features or a list of suggested rooms.
     * @param nif Nif of the user who is doing the reserve.
     * @param roomNb Number of the room which is going to be reserved.
     * @param buildingName Name of the building where the room is.
     * @param resDate Date of the reserve.
     * @return A ReservationResult encapsulating the Reservation done or a list of suggested rooms.
     * @throws DAOException If the room number or building name is not correct.
     */
    ReservationResult makeCompleteReservationBySpace(String nif, String roomNb, String buildingName, DateTime resDate)throws DAOException;

    /**
     * Returns a reservation made according to the features of space and date:the roomid, the user's nif and the datetime
     * if exists the reservation will return null
     * @param roomld The id of the room
     * @param nif The user's nif
     * @param initialTime The datetime which user wants make the reservation
     * @return A reservation made according to the conditions of space and date
     * @throws DAOException if the building ,room or date are not correct
     */
    Reservation makeReservationBySpace(long roomld, String nif, DateTime initialTime) throws DAOException ;
    /**
     * Finds an avaliable reservation acording to the specified features.
     * @param nif Nif of the user who makes the reservation.
     * @param type Type of room.
     * @param buildingName The building where we want to make the reservation.
     * @param capacity Capacity of the room.
     * @param date Date of the reservation.
     * @return The reservation done.
     * @throws DAOException If the building name is not correct.
     */
    Reservation makeReservationByType(String nif, String type, String buildingName, int capacity, DateTime date) throws DAOException;

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
     * find one reservation by the reservation's id
     * @param id the id of reservation
     * @return an reservation found by the id of reservation 
     * @throws IncorrectReservationException if the reservation does not exist
     */
    Reservation findReservationById(long id)throws DAOException;

    /**
     *
     * @param buildingName
     * @param roomNumber
     * @return
     */
    public List<Reservation> findReservationByBuildingAndRoomNb(String buildingName, String roomNumber) throws DAOException;

    /**
     * find a reservation according to the space and date introduced
     * @param buildingName the building name of the reservation desired
     * @param roomNumber the number of number of the reservation wanted
     * @param date the date of the reservation made
     * @return an reservation which contains the building, the room and the date introduced
     * @throws DAOException if the building ,room or date are not correct
     */
     Reservation findReservationBySpaceAndDate(String buildingName, String roomNumber, DateTime date) throws DAOException ;


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
     * obtains a all reservations 
     * @return a list of all reservations existed of DB
     */
    List<Reservation> getAllReservations();

    /**
     * it will delete a reservation existed in db according to the id of reservation
     * @param id
     * @throws DAOException if the reservation can not be found
     */
    void deleteReservation(long id) throws DAOException;

    /**
     * Suggests alternative similar rooms when the specified Room is reserved in the date given.
     * @param roomNb Already reserved room number.
     * @param building Building where the already reserved room is.
     * @param resDate Date and time of the reserve.
     * @return A list of non reserved rooms similars to the given by parameters.
     * @throws DAOException If the room number or building name are incorrect.
     */
    List<Room> suggestionSpace(String roomNb, String building, DateTime resDate) throws DAOException;

    /**
     * Returns the user who have the reservation of a room in the specified date.
     * @param roomNb The room number.
     * @param building The building where the room is.
     * @param datetime The date and time of the reserve we are finding.
     * @return The user who have the reservation of that room.
     */
    ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime);

    /**
     * Accepts a reservation.
     * @param reservation The reservation to accept.
     * @throws DAOException If the reservation contains any incorrect value or can not be accepted.
     */
    void acceptReservation(Reservation reservation) throws DAOException;
   
    /**
     * Returns a list with all non reserved the rooms which asserts the specified features.
     * @param type The type of room.
     * @param capacity The minimum capacity.
     * @param building The building where the room must be.
     * @param date The date when the room must be not reserved.
     * @return A list of non reserved rooms which asserts the specified features.
     * @throws DAOException If the type or building name are incorrect.
     */
    List<Room> obtainAllRoomsWithSameFeatures(String type, int capacity, String building, DateTime date)throws DAOException;
    
    /**
     * Get a list of reservations from his own Nif
     * @param nif, this is the nif that we would use to search into the DB 
     * @return List of Reservation made by that Nif
     */
    public List <Reservation> getReservationsByNif(String nif) ;
    
    /**
     * Get a list of reservation using different fields for filtering 
     * the list that it obtained before by Nif
     * @param nif, this is the nif that we would use to search into the DB
     * @param startDate, this is the start date of a Reservation
     * @param endDate, this is the end date of a Reservation
     * @param buildingName, this is the building's name of a Reservation
     * @param roomNb, this is the room number of a Reservation
     * @param capacity, this is the capacity of a Reservation
     * @param roomType, this is the room type of a Reservation
     * @return List of Reservations filtered by some fields customized. 
     * @throws DAOException 
     */
    public List <Reservation> getFilteredReservation(String nif, 
                                                  DateTime startDate,
                                                  DateTime endDate, 
                                                  String buildingName,
                                                  String roomNb,
                                                  int capacity,
                                                  String roomType) 
            throws DAOException;
    /**
     * Set SpaceDAO
     * @param spaceDao 
     */
   public void setSpaceDao(SpaceDAO spaceDao) ;
   /**
    * Set UserDAO
    * @param userDao 
    */
   public void setUserDao(UserDAO userDao);
   /**
    * Set ReservationDAO
    * @param reservationDao 
    */
   public void setReservationDao(ReservationDAO reservationDao); 
}
