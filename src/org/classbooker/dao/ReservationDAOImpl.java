/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao;

import org.classbooker.entity.Building;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.Room;
import org.classbooker.entity.User;
import org.classbooker.dao.exception.IncorrectBuildingException;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectUserException;
import org.classbooker.dao.exception.AlredyExistReservationException;
import org.classbooker.dao.exception.IncorrectReservationException;
import org.classbooker.dao.exception.IncorrectRoomException;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

/**
 *
 * @author msf7
 */
public class ReservationDAOImpl implements ReservationDAO{

    private EntityManager em;
    private UserDAO uDao;
    private SpaceDAO sDao;
    
    private final Logger logger = Logger.getLogger("ReservationDAOImpl.log");
    

    public EntityManager getEm() {
        return em;
    }

    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void setuDao(UserDAO uDao) {
        this.uDao = uDao;
    }

    @Override
    public void setsDao(SpaceDAO sDao) {
        this.sDao = sDao;
    }

    @Override
    public long addReservation(Reservation reservation) 
                                        throws DAOException{
        em.getTransaction().begin();    
        
        if(checkReservationForUser(reservation)){
            em.getTransaction().rollback();
        }else{
            persistReservation(reservation);
            em.getTransaction().commit();
        }
        logger.info("Reservation added:"+reservation);
        return reservation.getReservationId();
    }
    
    @Override
    public long addReservation(String userId, String roomNb, 
                                String buildingName, DateTime dateTime) 
                                        throws DAOException{
        
        return addReservation(new Reservation(dateTime, 
                                            getUser(userId), 
                                            getRoom(roomNb, buildingName)));    
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
                                              throws DAOException {
        
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
        return em.createQuery("SELECT r FROM Reservation r ").getResultList();
    }


    @Override
    public List<Reservation> getAllReservationByBuilding(String name) 
                                            throws DAOException {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.room.building.name = :buildingName ")
                .setParameter("buildingName", name)
                .getResultList();
    }

    @Override
    public List<Reservation> getAllReservationByRoom(long id) 
                                                throws DAOException {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.room.roomId = :roomID ")
                .setParameter("roomID", id)
                .getResultList();
    }

    @Override
    public void removeReservation(long id) throws DAOException {
        removeReservation(getReservationById(id));
        logger.info("Reservation removed id:"+id);
    }
    
    @Override
    public void removeReservation(DateTime datetime, String roomNb, String buildingName) 
                                        throws DAOException {

        removeReservation(getReservationByDateRoomBulding(datetime, 
                                                            roomNb, 
                                                            buildingName));
    }
    
    private void removeReservation(Reservation res) throws DAOException{
        if(res == null ){
            throw new IncorrectReservationException();
        }
        em.getTransaction().begin();
        res.getrUser().getReservations().remove(res);
        res.getRoom().getReservations().remove(res);
        em.remove(res);
        em.getTransaction().commit();
    }

    @Override
    public List<Reservation> getAllReservationByUserNif(String nif) 
                                                throws DAOException{
        return em.createQuery("SELECT r FROM Reservation r WHERE r.rUser.nif = :nif ")
                .setParameter("nif", nif)
                .getResultList();
    }
    
    
    
    private void checkReservation(Reservation reservation) 
                                        throws DAOException{
        
        if(reservation == null || reservation.getReservationDate() == null){
            throw new IncorrectReservationException(logger,"checkReservation");
        }
        checkRoom(reservation.getRoom());
        checkUser(reservation.getrUser());
        checkExistingReservation(reservation);
    }
        
    private boolean checkReservationForUser(Reservation reservation) 
                                        throws DAOException{
        
        if(reservation == null || reservation.getReservationDate() == null){
            throw new IncorrectReservationException(logger,"checkReservationForUser");
        }
        checkRoom(reservation.getRoom());
        checkUser(reservation.getrUser());
        return checkExistingReservationForOtherUser(reservation);
    }

    private void checkRoom(Room room) throws IncorrectRoomException {
        if(room == null || room.getNumber() == null){
            throw new IncorrectRoomException(logger,"checkRoom");
        } 
            
        if(!em.contains(room)){
            throw new IncorrectRoomException(logger,"checkRoom");
        }
        
    }

    private void checkUser(User user) throws IncorrectUserException {
        if(user == null || user.getNif() == null){ 
            throw new IncorrectUserException(logger,"checkUser");
        }
        
        if(!em.contains(user)){
            throw new IncorrectUserException(logger,"checkUser");
        }
        
    }
    
    private void checkBuilding(Building building) throws IncorrectBuildingException {
        if(building == null || building.getBuildingName() == null){
            throw new IncorrectBuildingException(logger,"checkBuilding");
        }
        
        if(!em.contains(building)){
            throw new IncorrectBuildingException(logger,"checkBuilding");
        }
    }

    private void checkExistingReservation(Reservation reservation) 
                                        throws DAOException{     
        if(!em.createQuery("SELECT r FROM Reservation r WHERE r.reservationDate = :reservationdDate AND r.room = :reservationRoom ")
                .setParameter("reservationdDate", 
                                reservation.getReservationDate()
                                .toCalendar(Locale.getDefault()))
                .setParameter("reservationRoom",  
                                reservation.getRoom())
                .getResultList().isEmpty()){
            throw new AlredyExistReservationException(logger,"checkExistingReservation");
        }    
    }
    
    private boolean checkExistingReservationForOtherUser(Reservation reservation) 
                                        throws DAOException{
        User ur = null;
        try{
        ur = (User) em.createQuery("SELECT r.rUser FROM Reservation r WHERE r.reservationDate = :reservationdDate AND r.room = :reservationRoom ")
                .setParameter("reservationdDate", 
                                reservation.getReservationDate()
                                .toCalendar(Locale.getDefault()))
                .setParameter("reservationRoom",  
                                reservation.getRoom()).getSingleResult();

        }catch(NoResultException ex){
            return false;
        }
        if(ur != reservation.getrUser()){
            throw new AlredyExistReservationException(logger,"checkExistingReservationForOtherUser");
        }
        return true;
    }

    private ReservationUser getUser(String userId) throws DAOException {
        User user = uDao.getUserByNif(userId);
        if(user == null){
            throw new IncorrectUserException(logger,"getUser");
        }else if (user instanceof ReservationUser) {
            return (ReservationUser) user;
        }else{
            throw new IncorrectUserException(logger,"getUser");
        }
        
    }

    private Room getRoom(String roomNb, String buildingName) 
                                                throws DAOException {
        return sDao.getRoomByNbAndBuilding(roomNb, buildingName);
        
        
        
    }
    
    @Override
    public void tearDown(){
        em.getTransaction().begin();

        Query query = em.createQuery("DELETE FROM Reservation");

        query.executeUpdate();

        em.getTransaction().commit();
        em.close();
        logger.info("All records of reservation have been deleted.");
    }

}
