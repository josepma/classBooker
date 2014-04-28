/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.dateTime;
import java.sql.Time;
import java.util.List;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;
import static org.joda.time.format.ISODateTimeFormat.dateTime;

/**
 *
 * @author josepma, Marc Solé, Carles Mònico
 */
public interface ReservationDAO {
    
    /**
     *
     * @param reservation
     * @throws IncorrectReservationException
     * @throws IncorrectUserException
     * @throws IncorrectRoomException
     */
    void addReservation(Reservation reservation)
                                           throws IncorrectReservationException,
                                           IncorrectUserException,
                                           IncorrectRoomException;
    
    void addReservation (String userId, String roomNb, 
                          String buildingName, Time dateTime)
                                           throws IncorrectReservationException,
                                           IncorrectUserException,
                                           IncorrectRoomException;

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
    Reservation getReservationById(String id);
    
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
