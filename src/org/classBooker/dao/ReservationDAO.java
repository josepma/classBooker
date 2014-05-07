/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.util.List;
import org.classBooker.dao.exception.*;
import org.classBooker.entity.Reservation;
import org.joda.time.DateTime;

/**
 *
 * @author josepma, Marc Solé, Carles Mònico
 */
public interface ReservationDAO {
    
    /**
     *
     * @param reservation
     * @return long
     * @throws IncorrectReservationException
     * @throws IncorrectUserException
     * @throws IncorrectRoomException
     * @throws AlredyExistReservationException
     */
    long addReservation(Reservation reservation)
                                           throws IncorrectReservationException,
                                           IncorrectUserException,
                                           IncorrectRoomException,
                                           AlredyExistReservationException ;
    
    /**
     *
     * @param userId
     * @param roomNb
     * @param buildingName
     * @param dateTime
     * @return 
     * @throws IncorrectReservationException
     * @throws IncorrectUserException
     * @throws IncorrectRoomException
     * @throws AlredyExistReservationException
     */
    Reservation addReservation (String userId, String roomNb, 
                          String buildingName, DateTime dateTime)
                                           throws IncorrectReservationException,
                                           IncorrectUserException,
                                           IncorrectRoomException,
                                           AlredyExistReservationException;

    /**
     *
     * @param reservation
     * @throws IncorrectReservationException
     * @throws IncorrectUserException
     * @throws IncorrectRoomException
     *//*
    void modifyReservation(Reservation reservation)
                                           throws IncorrectReservationException,
                                           IncorrectUserException,
                                           IncorrectRoomException;
    */
    /**
     *
     * @param id
     * @throws IncorrectReservationException
     */

    void removeReservation(String id) throws IncorrectReservationException;
    
    /**
     *
     * @param id
     * @return
     */
    Reservation getReservationById(long id);
    
    /**
     *
     * @param dateTime
     * @param roomNb
     * @param buildingName
     * @return
     */
    Reservation getReservationByDateRoomBulding(DateTime dateTime, 
                                                    String roomNb, 
                                                    String buildingName);
    
    /**
     *
     * @return
     */
    List<Reservation> getAllReservation();
    
    /**
     *
     * @param nif
     * @return
     * @throws IncorrectUserException
     */
    List<Reservation> getAllReservationByUserNif(String nif)
                                            throws IncorrectUserException;

    /**
     *
     * @param name
     * @return
     * @throws IncorrectBuildingException
     */
    List<Reservation> getAllReservationByBuilding(String name) 
                                            throws IncorrectBuildingException;

    /**
     *
     * @param id
     * @return
     * @throws IncorrectRoomException
     */
    List<Reservation> getAllReservationByRoom(String id) 
                                            throws IncorrectRoomException;
                                            
}
