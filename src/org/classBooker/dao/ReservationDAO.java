/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.util.List;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;

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

    /**
     *
     * @param reservation
     * @throws IncorrectReservationException
     * @throws IncorrectUserException
     * @throws IncorrectRoomException
     */
    void modifyReservation(Reservation reservation)
                                           throws IncorrectReservationException,
                                           IncorrectUserException,
                                           IncorrectRoomException;
    
    /**
     *
     * @param reservation
     * @throws IncorrectReservationException
     */
    void removeReservation(Reservation reservation) 
                                           throws IncorrectReservationException;
    
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
     * @param user
     * @return
     * @throws IncorrectUserException
     */
    List<Reservation> getAllReservationByUser(User user)
                                            throws IncorrectUserException;

    /**
     *
     * @param nif
     * @return
     * @throws IncorrectUserException
     */
    List<Reservation> getAllReservationByUser(String nif)
                                            throws IncorrectUserException;

    /**
     *
     * @param building
     * @return
     * @throws IncorrectBuildingException
     */
    List<Reservation> getAllReservationByBuilding(Building building) 
                                            throws IncorrectBuildingException;

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
     * @param room
     * @return
     * @throws IncorrectRoomException
     */
    List<Reservation> getAllReservationByRoom(Room room) 
                                            throws IncorrectRoomException;

    /**
     *
     * @param id
     * @return
     * @throws IncorrectRoomException
     */
    List<Reservation> getAllReservationByRoom(String id) 
                                            throws IncorrectRoomException;
                                            
}
