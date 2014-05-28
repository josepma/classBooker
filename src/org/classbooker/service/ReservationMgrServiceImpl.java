/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.classbooker.dao.ReservationDAO;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.exception.AlredyExistReservationException;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectBuildingException;
import org.classbooker.dao.exception.IncorrectReservationException;
import org.classbooker.dao.exception.IncorrectRoomException;
import org.classbooker.dao.exception.IncorrectTimeException;
import org.classbooker.dao.exception.IncorrectUserException;
import org.classbooker.entity.Building;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;
import org.classbooker.entity.User;
import org.classbooker.util.ReservationResult;
import org.joda.time.DateTime;

/**
 *
 * @author abg7
 */
public class ReservationMgrServiceImpl implements ReservationMgrService {

    private Reservation reservation;
    private UserDAO userDao;
    private ReservationDAO reservationDao;
    private SpaceDAO spaceDao;
    private DateTime datetime;

    public void setSpaceDao(SpaceDAO spaceDao) {
        this.spaceDao = spaceDao;
    }

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void setReservationDao(ReservationDAO reservationDao) {
        this.reservationDao = reservationDao;
    }
    
    /**
     * Set the DateTime.
     */
    public void setDatetime(DateTime datetime) {
        this.datetime = datetime;
    }
    
    /**
     * Returns a boolean which indicates if the reservationMade exists in the database.
     * if the reservationMade exists in the database, it will return true, else will return false
     */
    
    /**
     * Returns a boolean which indicates if the reservationMade exists in the database.
     * if the reservationMade exists in the database, it will return true, else will return false
     */
    public boolean alreadyExistingReservation(DateTime datetime, Room room) throws  DAOException {
        return reservationDao.getReservationByDateRoomBulding(datetime, room.getNumber(), room.getBuilding().getBuildingName())!=null;
    }   

    @Override
    public List<Room> suggestionSpace(String roomNb, String building, DateTime date) throws DAOException {
        Room room = spaceDao.getRoomByNbAndBuilding(roomNb, building);
        if ( room == null){
            return new ArrayList();
        }
        List<Room> suggestedRoomsByTypeAndCapacity = spaceDao.getAllRoomsByTypeAndCapacity(room.getClass().getName(), room.getCapacity(), building);
        List<Room> finalSuggestedRooms = getNonReservedRooms(suggestedRoomsByTypeAndCapacity, date);
        return finalSuggestedRooms;
    }

    @Override
    public ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime){
        Reservation res = null;
        try{
            res = reservationDao.getReservationByDateRoomBulding(datetime, roomNb, building);
        }catch(IncorrectRoomException e ){
            
        } catch (IncorrectBuildingException ex) {
            Logger.getLogger(ReservationMgrServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            Logger.getLogger(ReservationMgrServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (res == null) {
            return null;
        }
        return res.getrUser();
    }

    @Override
    public void acceptReservation(Reservation reservation) throws DAOException {
        reservationDao.addReservation(reservation);
    }

    @Override
    public ReservationResult makeCompleteReservationBySpace(String nif, String roomNb, String buildingName, DateTime resDate) throws DAOException {
        Room room = spaceDao.getRoomByNbAndBuilding(roomNb, buildingName);
        Reservation reservationMade = makeReservationBySpace(room.getRoomId(), nif, resDate);

        if (reservationMade != null) {
            ReservationUser reservationUser = (ReservationUser) userDao.getUserByNif(nif);
            return new ReservationResult(reservationMade, reservationUser);
        }

        List<Room> suggestedRooms = suggestionSpace(roomNb, buildingName, resDate);
        return new ReservationResult(suggestedRooms);
    }

    /**
     * Returns a reservationMade for the roomId, the date initialTime and the user
     * nif. For making correctly the reservationMade, we should check that the user
     * and the room exist in database also the format of time should be correct.
     * This reservationMade should be accepted by the user before inserting it into
     * the database
     */
    @Override
    public Reservation makeReservationBySpace(long roomId, String nif, DateTime initialTime) throws DAOException {
        Room room = spaceDao.getRoomById(roomId);
        User user = userDao.getUserByNif(nif);
        datetime = initialTime;
        
        checkRoom(room);
        checkUser(user);
        checkDate(datetime);
        Reservation r= makeReservation(datetime,user,room); 
        return r;
    }

    /**
     * Delete an existing reservationMade if not found will throw
     * IncorrectReservationException.
     */
    @Override
    public void deleteReservation(long id) throws DAOException {
        Reservation reser = reservationDao.getReservationById(id);
        if (reser == null) {
            throw new IncorrectReservationException("Can not find the reservation");
        } else {
            reservationDao.removeReservation(id);
        }
    }

    @Override
    public Reservation modifyReservation(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> findReservationByNif(String nif) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Return a particular reservationMade by a concrete ReservationId
     * If not exists, will throwIncorrectReservationException.
     */
    @Override
    public Reservation findReservationById(long id)throws IncorrectReservationException {
        Reservation reser = reservationDao.getReservationById(id);
        if(reser == null){
            throw new IncorrectReservationException("Can not find the reservation");   
        }
        return reser;
    }

    @Override
    public List<Reservation> findReservationByBuildingAndRoomNb(String buildingName, String roomNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
     /**
     * Return a particular reservation Made by a concrete space and a concrete date
     * if exists some errors, it will throws exceptions
     */
    @Override
    public Reservation findReservationBySpaceAndDate(String buildingName, String roomNumber, DateTime date) throws DAOException {
        Building building = spaceDao.getBuildingByName(buildingName);
        Room room = spaceDao.getRoomByNbAndBuilding(roomNumber, buildingName);
        datetime = date;
        checkRoom(room);
        checkBuilding(building);
        checkDate(datetime);
        return reservationDao.getReservationByDateRoomBulding(datetime, roomNumber, buildingName);
    }


    @Override
    public List<Reservation> findReservationByType(String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> findReservationByType(String type, DateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Return a List of all reservations of Database
     */
    @Override
    public List<Reservation> getAllReservations() {
        return reservationDao.getAllReservation();
    }

    @Override
    public Reservation makeReservationByType(String nif, String type, String buildingName, int capacity, DateTime date) throws DAOException{
        List<Room> avaliableRooms = obtainAllRoomsWithSameFeatures(type, capacity, buildingName, date);
        if(avaliableRooms.isEmpty()){
            return null;
        }
        User reservationUser = userDao.getUserByNif(nif);
        if(reservationUser instanceof ReservationUser){
            return new Reservation(date, (ReservationUser)reservationUser, avaliableRooms.get(0));
        }
        return null;
        
    }

    private List<Room> getNonReservedRooms(List<Room> rooms, DateTime date) throws DAOException{
        Reservation res;
        List<Room> nonReservedRooms = new ArrayList<>();
        for (Room r : rooms) {
            System.out.println(r.getNumber());
            res = reservationDao.getReservationByDateRoomBulding(date, r.getNumber(), r.getBuilding().getBuildingName());
            if (res == null) {
                nonReservedRooms.add(r);
            }
        }
        return nonReservedRooms;
    }

    @Override
    public List<Room> obtainAllRoomsWithSameFeatures(String type, int capacity, String building, DateTime date)throws DAOException{
        List<Room> sameTypeRooms = spaceDao.getAllRoomsByTypeAndCapacity(type, capacity, building);
        if(sameTypeRooms.isEmpty()){
            return new ArrayList();
        }
              
        List<Room> nonReservedRooms = getNonReservedRooms(sameTypeRooms, date);
        if(nonReservedRooms.isEmpty()){
            return new ArrayList();
        }
        return nonReservedRooms;
    }

    private void checkRoom(Room room) throws DAOException{
        if (room == null) {
            throw new IncorrectRoomException("can not find the room");
        }
    }

    private void checkUser(User user) throws DAOException{
          if (user == null || !(user instanceof ReservationUser)) {
            throw new IncorrectUserException("user not exist");
        }
    }

    private void checkDate(DateTime datetime) throws DAOException{
        if (datetime.isBeforeNow() || datetime.getMinuteOfHour() != 0) {
            throw new IncorrectTimeException("incorrect date time format");
        }
    }

    private Reservation makeReservation(DateTime datetime, User user, Room room) throws DAOException {
       if (alreadyExistingReservation(datetime, room)) {
            return null;
        } else {
            return new Reservation(datetime, (ReservationUser) user, room);
        }
    }

    private void checkBuilding(Building building)throws DAOException {
       if (building == null) {
            throw new IncorrectBuildingException("can not find the building");
        }
    }
    

}
