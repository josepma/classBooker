/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.classBooker.dao.exception.*;
import org.classBooker.entity.*;
import org.joda.time.DateTime;

/**
 *
 * @author msf7
 */
public class ReservationDAOImpl implements ReservationDAO{

    private EntityManager em;
    
    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Reservation addReservation(Reservation reservation) 
                                        throws IncorrectReservationException, 
                                               IncorrectUserException, 
                                               IncorrectRoomException {
        
        em.getTransaction().begin();    
        if(!checkReservation(reservation)){
            em.persist(reservation);
            reservation.getrUser().getReservations().add(reservation);
            reservation.getRoom().getReservations().add(reservation);
            em.getTransaction().commit();
            return reservation;
        }else{
            System.err.println("The room is reserved for this Date");
            em.getTransaction().rollback();
        }
        return null;
    }
    
    @Override
    public Reservation addReservation(String userId, String roomNb, 
                                String buildingName, DateTime dateTime) 
                                        throws IncorrectReservationException, 
                                               IncorrectUserException, 
                                               IncorrectRoomException,
                                               IncorrectBuildingException{
        em.getTransaction().begin();
        ReservationUser user;
        Building building;
        Room room;
        if((user = (ReservationUser) em.find(User.class, userId)) == null){
            throw new IncorrectUserException();
        }
        if((building = (Building) em.find(Building.class, buildingName)) == null){
            throw new IncorrectBuildingException();
        }
        try{
        room = (Room) em.createQuery("SELECT r "
                + "FROM Room r "
                + "WHERE r.building = :rbuilding AND "
                + "r.number = :roomNb ")
                .setParameter("rbuilding", building)
                .setParameter("roomNb", roomNb).getSingleResult();
        }catch(NoResultException ex){
            throw new IncorrectRoomException();
        }
        em.getTransaction().commit();
        return addReservation(new Reservation(dateTime, user, room));
        
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
    
    
    
    private boolean checkReservation(Reservation reservation) throws IncorrectReservationException, IncorrectRoomException, IncorrectUserException{
        
        if(reservation == null || reservation.getReservationDate() == null)
            throw new IncorrectReservationException();
        
        checkRoom(reservation.getRoom());
        checkUser(reservation.getrUser());
        
        return !em.createQuery("SELECT r "
                + "FROM Reservation r "
                + "WHERE r.reservationDate = :reservationdDate AND "
                + "r.room = :reservationRoom ")
                .setParameter("reservationdDate", 
                        reservation.getReservationDate().toCalendar(Locale.getDefault()))
                .setParameter("reservationRoom",  
                        reservation.getRoom())
                .getResultList().isEmpty();
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
