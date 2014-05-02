/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;
import org.joda.time.DateTime;

/**
 *
 * @author msf7
 */
public class ReservationDAOImpl implements ReservationDAO{

    EntityManager em;
    
    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addReservation(Reservation reservation) 
                                        throws IncorrectReservationException, 
                                               IncorrectUserException, 
                                               IncorrectRoomException {
        
        em.getTransaction().begin();       
        checkReservation(reservation);
        em.persist(reservation);
        reservation.getrUser().getReservations().add(reservation);
        reservation.getRoom().getReservations().add(reservation);
        em.getTransaction().commit();
    }
    
    @Override
    public void addReservation(String userId, String roomNb, String buildingName, DateTime dateTime) throws IncorrectReservationException, IncorrectUserException, IncorrectRoomException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reservation getReservationById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reservation getReservationByDateRoomBulding(DateTime dateTime, String roomNb, String buildingName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Reservation> getAllReservation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public List<Reservation> getAllReservationByBuilding(String name) throws IncorrectBuildingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> getAllReservationByRoom(String id) throws IncorrectRoomException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeReservation(String id) throws IncorrectReservationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> getAllReservationByUserNif(String nif) throws IncorrectUserException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void checkReservation(Reservation reservation) throws IncorrectReservationException, IncorrectRoomException, IncorrectUserException{
        
        checkRoom(reservation.getRoom());
        checkUser(reservation.getrUser());
        
    }

    private void checkRoom(Room room) throws IncorrectRoomException {
        //modify
        if(room == null || room.getNumber() == null) 
            throw new IncorrectRoomException();
        
        //System.err.println(em.find(Room.class, room));
        if(!em.contains(room))
            throw new IncorrectRoomException();
        
        
    }

    private void checkUser(User user) throws IncorrectUserException {
        
        if(user == null || user.getNif() == null) 
            throw new IncorrectUserException();
        
        if(!em.contains(user))
            throw new IncorrectUserException();
        
    }
    
    private void checkBuilding(Building building) throws IncorrectBuildingException {
        
        
        if(building == null || building.getBuildingName() == null) 
            throw new IncorrectBuildingException();
        
        if(!em.contains(building))
            throw new IncorrectBuildingException();
        
    }

}
