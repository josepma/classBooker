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
import javax.persistence.NonUniqueResultException;
import org.classBooker.dao.exception.*;
import org.classBooker.entity.*;
import org.joda.time.DateTime;

/**
 *
 * @author msf7
 */
public class ReservationDAOImpl implements ReservationDAO{

    private EntityManager em;
    private UserDAO uDao;
    private SpaceDAO sDao;
    
    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
        public UserDAO getuDao() {
        return uDao;
    }

    public void setuDao(UserDAO uDao) {
        this.uDao = uDao;
    }

    public SpaceDAO getsDao() {
        return sDao;
    }

    public void setsDao(SpaceDAO sDao) {
        this.sDao = sDao;
    }

    @Override
    public long addReservation(Reservation reservation) 
                                        throws IncorrectReservationException, 
                                               IncorrectUserException, 
                                               IncorrectRoomException,
                                               AlredyExistReservationException{
        em.getTransaction().begin();    
        
        if(checkReservationForUser(reservation)){
            em.getTransaction().rollback();
        }else{
            persistReservation(reservation);
            em.getTransaction().commit();
        }
        return reservation.getReservationId();
    }
    
    @Override
    public long addReservation(String userId, String roomNb, 
                                String buildingName, DateTime dateTime) 
                                        throws IncorrectReservationException, 
                                               IncorrectUserException, 
                                               IncorrectRoomException,
                                               AlredyExistReservationException,
                                               IncorrectBuildingException{
        
        return addReservation(new Reservation(dateTime, 
                                            getUser(userId), 
                                            getRoom(roomNb, buildingName)));
        
        /*
        em.getTransaction().begin();
        ReservationUser user= getUser(userId);
        Room room = getRoom(roomNb,buildingName);
        Reservation reservation = new Reservation(dateTime, user, room);
        checkExistingReservation(reservation);
        persistReservation(reservation);
        em.getTransaction().commit();
        return reservation.getReservationId();
        */
        
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
    public Reservation getReservationByDateRoomBulding(DateTime dateTime, 
                                                            String roomNb, 
                                                            String buildingName) 
                                              throws IncorrectBuildingException, 
                                                     IncorrectRoomException {
        
        Room room =  sDao.getRoomByNbAndBuilding(roomNb, buildingName);
        List<Reservation> reservations = room.getReservations();
        for (Reservation res : reservations) {
            if (res.getReservationDate().isEqual(dateTime)) {
                return res;
            }
        }
        return null;
    }
    
    @Override
    public List<Reservation> getAllReservation() {
        return em.createQuery("SELECT r "
                            + "FROM Reservation r ").getResultList();
    }


    @Override
    public List<Reservation> getAllReservationByBuilding(String name) 
                                            throws IncorrectBuildingException {
        return em.createQuery("SELECT r "
                + "FROM Reservation r "
                + "WHERE r.room.building.name = :buildingName ")
                .setParameter("buildingName", name)
                .getResultList();
    }

    @Override
    public List<Reservation> getAllReservationByRoom(long id) 
                                                throws IncorrectRoomException {
        return em.createQuery("SELECT r "
                + "FROM Reservation r "
                + "WHERE r.room.roomId = :roomID ")
                .setParameter("roomID", id)
                .getResultList();
    }

    @Override
    public void removeReservation(long id) throws IncorrectReservationException {
        removeReservation(getReservationById(id));
    }
    
    @Override
    public void removeReservation(DateTime datetime, String roomNb, String buildingName) 
                                        throws IncorrectReservationException, 
                                                IncorrectRoomException, 
                                                IncorrectBuildingException {

        removeReservation(getReservationByDateRoomBulding(datetime, 
                                                            roomNb, 
                                                            buildingName));
    }
    
    private void removeReservation(Reservation res)throws IncorrectReservationException {
        if(res == null )throw new IncorrectReservationException();
        em.getTransaction().begin();
        res.getrUser().getReservations().remove(res);
        res.getRoom().getReservations().remove(res);
        em.remove(res);
        em.getTransaction().commit();
    }

    @Override
    public List<Reservation> getAllReservationByUserNif(String nif) 
                                                throws IncorrectUserException {
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
        
    private boolean checkReservationForUser(Reservation reservation) 
                                        throws IncorrectReservationException, 
                                                IncorrectRoomException, 
                                                IncorrectUserException, 
                                                AlredyExistReservationException{
        
        if(reservation == null || reservation.getReservationDate() == null)
            throw new IncorrectReservationException();
        checkRoom(reservation.getRoom());
        checkUser(reservation.getrUser());
        return checkExistingReservationForOtherUser(reservation);
    }

    private void checkRoom(Room room) throws IncorrectRoomException {
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
                                reservation.getReservationDate()
                                .toCalendar(Locale.getDefault()))
                .setParameter("reservationRoom",  
                                reservation.getRoom())
                .getResultList().isEmpty()){
            throw new AlredyExistReservationException();
        }    
    }
    
    private boolean checkExistingReservationForOtherUser(Reservation reservation) 
                                        throws AlredyExistReservationException{
        User ur = null;
        try{
        ur = (User) em.createQuery("SELECT r.rUser "
                + "FROM Reservation r "
                + "WHERE r.reservationDate = :reservationdDate AND "
                + "r.room = :reservationRoom ")
                .setParameter("reservationdDate", 
                                reservation.getReservationDate()
                                .toCalendar(Locale.getDefault()))
                .setParameter("reservationRoom",  
                                reservation.getRoom()).getSingleResult();
        }catch(NonUniqueResultException ex ){
            throw new AlredyExistReservationException();
        }catch(NoResultException ex){
            return false;
        }
        if(ur != reservation.getrUser()){
            throw new AlredyExistReservationException();
        }
        return true;
    }

    private ReservationUser getUser(String userId) throws IncorrectUserException {
        User user = uDao.getUserByNif(userId);
        if(user == null){
            throw new IncorrectUserException();
        }else if (user instanceof ReservationUser) {
            return (ReservationUser) user;
        }else{
            throw new IncorrectUserException();
        }
        
    }

    private Room getRoom(String roomNb, String buildingName) 
                                                throws IncorrectRoomException, 
                                                IncorrectBuildingException {
        return sDao.getRoomByNbAndBuilding(roomNb, buildingName);
        
        
        
    }

}
