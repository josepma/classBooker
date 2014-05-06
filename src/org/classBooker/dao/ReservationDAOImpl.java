/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;


import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import org.classBooker.dao.exception.AlreadyExistingBuildingException;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

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
    public Reservation confirmAndAddReservation(Reservation reservation) throws IncorrectReservationException, IncorrectUserException, IncorrectRoomException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public long addReservation(Reservation reservation) 
                                        throws IncorrectReservationException, 
                                               IncorrectUserException, 
                                               IncorrectRoomException,
                                               AlreadyExistingBuildingException {
        
        em.getTransaction().begin();       
        checkReservation(reservation);
        em.persist(reservation);
        reservation.getrUser().getReservations().add(reservation);
        
        em.getTransaction().commit();
        return reservation.getReservationId();
    }
    
    @Override
    public void addReservation( String userId, 
                                String roomNb, 
                                String buildingName, 
                                DateTime dateTime) 
                                throws  IncorrectReservationException, 
                                        IncorrectUserException, 
                                        IncorrectRoomException,
                                        AlreadyExistingBuildingException{
        
         
                
        
        
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
    
    private void checkReservation(Reservation reservation) 
                                        throws  IncorrectReservationException, 
                                                IncorrectRoomException, 
                                                IncorrectUserException, 
                                                AlreadyExistingBuildingException{
        
        checkExistingReservation(reservation);
        checkRoom(reservation);
        checkUser(reservation);
        
    }

    private void checkRoom(Reservation reservation) throws IncorrectRoomException {
        
        Room room = reservation.getRoom();
        //modify
        if(room == null || room.getNumber() == null) 
            throw new IncorrectRoomException();
       
            if(em.find(Room.class, room.getRoomId())== null)
            throw new IncorrectRoomException();
        
        
    }

    private void checkUser(Reservation reservation) throws IncorrectUserException {
        User user = reservation.getrUser();
        
        if(user == null || user.getNif() == null) 
            throw new IncorrectUserException();
        
        if(em.find(User.class, user.getNif())== null)
            throw new IncorrectUserException();
        
    }
    
    private void checkBuilding(Building building) throws IncorrectBuildingException {
        
        
        if(building == null || building.getBuildingName() == null) 
            throw new IncorrectBuildingException();
        
        if(em.find(Room.class, building)== null)
            throw new IncorrectBuildingException();
        
    }

    private void checkExistingReservation(Reservation reservation) 
                                        throws AlreadyExistingBuildingException{
        
        if(em.find(Reservation.class, reservation.getReservationId())!= null){
            throw new AlreadyExistingBuildingException();
        }
        DateTime data = reservation.getReservationDate();
        /*        String month = String.valueOf(data.monthOfYear().getAsShortText());
        String year = String.valueOf(data.year().get());
        String day = String.valueOf(data.dayOfMonth().get());
        String firstDate = day+"-"+month+"-"+year;*/
        String query = "SELECT r FROM RESERVATION r"
                     + "WHERE r.DATE  = ?1";
        //em.createQuery(query).setParameter(1, data.toCalendar(Locale.getDefault()),TemporalType.DATE);
    }



}
