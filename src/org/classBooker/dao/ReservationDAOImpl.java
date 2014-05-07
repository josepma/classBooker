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
    public long addReservation(Reservation reservation) 
                                        throws IncorrectReservationException, 
                                               IncorrectUserException, 
                                               IncorrectRoomException,
                                               AlredyExistReservationException{
        
        em.getTransaction().begin();    
        checkReservation(reservation);
        persistReservation(reservation);
        em.getTransaction().commit();
        return reservation.getReservationId();
    }
    
    @Override
    public Reservation addReservation(String userId, String roomNb, 
                                String buildingName, DateTime dateTime) 
                                        throws IncorrectReservationException, 
                                               IncorrectUserException, 
                                               IncorrectRoomException,
                                               AlredyExistReservationException{
        
        em.getTransaction().begin();
        ReservationUser user;
        Room room;
        if((user = (ReservationUser) em.find(User.class, userId)) == null){
            throw new IncorrectUserException();
        }
        try{
            room = (Room) em.createQuery("SELECT r "
                            + "FROM Room r "
                            + "WHERE r.building.name = :buildingName AND "
                            + "r.number = :roomNb ")
                            .setParameter("buildingName", buildingName)
                            .setParameter("roomNb", roomNb)
                            .getSingleResult();
        }catch(NoResultException ex){
            throw new IncorrectRoomException();
        }
        Reservation reservation = new Reservation(dateTime, user, room);
        checkExistingReservation(reservation);
        persistReservation(reservation);
        em.getTransaction().commit();
        return reservation;
        
    }
    
    private void persistReservation(Reservation reservation){
            em.persist(reservation);
            reservation.getrUser().getReservations().add(reservation);
            reservation.getRoom().getReservations().add(reservation);
    }

    @Override
    public Reservation getReservationById(long id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public Reservation getReservationByDateRoomBulding(DateTime dateTime, String roomNb, String buildingName) {
        return (Reservation) em.createQuery("SELECT r "
                + "FROM Reservation r "
                + "WHERE r.reservationDate = :rDate AND"
                        + "r.room.number = :roomNb AND"
                        + "r.room.building.name = :buildingName")
                .setParameter("rDate", dateTime.toCalendar(Locale.getDefault()))
                .setParameter("roomNb", roomNb)
                .setParameter("buildingName", buildingName)
                .getSingleResult();
    }
    
    @Override
    public List<Reservation> getAllReservation() {
        return em.createQuery("SELECT r "
                            + "FROM Reservation r ").getResultList();
    }


    @Override
    public List<Reservation> getAllReservationByBuilding(String name) throws IncorrectBuildingException {
        return em.createQuery("SELECT r "
                + "FROM Reservation r "
                + "WHERE r.room.building.name = :buildingName ")
                .setParameter("buildingName", name)
                .getResultList();
    }

    @Override
    public List<Reservation> getAllReservationByRoom(String id) throws IncorrectRoomException {
        return em.createQuery("SELECT r "
                + "FROM Reservation r "
                + "WHERE r.room.roomId = :roomID ")
                .setParameter("roomID", id)
                .getResultList();
    }

    @Override
    public void removeReservation(String id) throws IncorrectReservationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> getAllReservationByUserNif(String nif) throws IncorrectUserException {
        return em.createQuery("SELECT r "
                + "FROM Reservation r "
                + "WHERE r.rUser.nif = :nif ")
                .setParameter("nif", nif)
                .getResultList();
    }
    
    
    
    private void checkReservation(Reservation reservation) 
                                        throws IncorrectReservationException, 
                                                IncorrectRoomException, 
                                                IncorrectUserException, 
                                                AlredyExistReservationException{
        
        if(reservation == null || reservation.getReservationDate() == null)
            throw new IncorrectReservationException();
        
        checkRoom(reservation.getRoom());
        checkUser(reservation.getrUser());
        checkExistingReservation(reservation);
        

    }

    private void checkRoom(Room room) throws IncorrectRoomException {
        //modify
        if(room == null || room.getNumber() == null) 
            throw new IncorrectRoomException();
        
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

    private void checkExistingReservation(Reservation reservation) 
                                        throws AlredyExistReservationException{
        
        if(!em.createQuery("SELECT r "
                + "FROM Reservation r "
                + "WHERE r.reservationDate = :reservationdDate AND "
                + "r.room = :reservationRoom ")
                .setParameter("reservationdDate", 
                        reservation.getReservationDate().toCalendar(Locale.getDefault()))
                .setParameter("reservationRoom",  
                        reservation.getRoom())
                .getResultList().isEmpty()){
            throw new AlredyExistReservationException();
        }
    }

}
