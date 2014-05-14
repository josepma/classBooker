/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classBooker.service;

import java.util.ArrayList;
import java.util.List;
import org.classBooker.dao.ReservationDAO;
import org.classBooker.dao.SpaceDAO;
import org.classBooker.dao.UserDAO;
import org.classBooker.dao.exception.AlredyExistReservationException;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTimeException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;
import org.classBooker.util.ReservationResult;
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
     * Returns a boolean which indicates if the reservation exists in the database.
     * if the reservation exists in the database, it will return true, else will return false
     */
    
    /**
     * Returns a boolean which indicates if the reservation exists in the database.
     * if the reservation exists in the database, it will return true, else will return false
     */
    public boolean alreadyExistingReservation(DateTime datetime, Room room) {
        return reservationDao.getReservationByDateRoomBulding(datetime, room.getNumber(), room.getBuilding().getBuildingName())!=null;
    }

    @Override
    public List<Room> suggestionSpace(String roomNb, String building, DateTime date) throws IncorrectBuildingException, IncorrectRoomException {
        Room room = spaceDao.getRoomByNbAndBuilding(roomNb, building);
        List<Room> suggestedRoomsByTypeAndCapacity = spaceDao.getAllRoomsByTypeAndCapacity(room.getClass().toString(), room.getCapacity(), building);
        List<Room> finalSuggestedRooms = getNonReservedRooms(suggestedRoomsByTypeAndCapacity, date);
        return finalSuggestedRooms;
    }

    @Override
    public ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime) {
        Reservation res = reservationDao.getReservationByDateRoomBulding(datetime, roomNb, building);
        if (res == null) {
            return null;
        }
        return res.getrUser();
    }

    @Override
    public void acceptReservation(Reservation reservation) throws IncorrectReservationException, IncorrectUserException, IncorrectRoomException, AlredyExistReservationException {
        reservationDao.addReservation(reservation);
    }

    @Override
    public ReservationResult makeCompleteReservationBySpace(String nif, String roomNb, String buildingName, DateTime resDate) throws Exception {
        Room room = spaceDao.getRoomByNbAndBuilding(roomNb, buildingName);
        Reservation reservation = makeReservationBySpace(room.getRoomId(), nif, resDate);

        if (reservation != null) {
            acceptReservation(reservation);
            ReservationUser reservationUser = (ReservationUser) userDao.getUserByNif(nif);
            return new ReservationResult(reservation, reservationUser);
        }

        List<Room> suggestedRooms = suggestionSpace(roomNb, buildingName, resDate);
        return new ReservationResult(suggestedRooms);
    }

    /**
     * Returns a reservation for the roomId, the date initialTime and the user
     * nif. For making correctly the reservation, we should check that the user
     * and the room exist in database also the format of time should be correct.
     * This reservation should be accepted by the user before inserting it into
     * the database
     */
    @Override
    public Reservation makeReservationBySpace(long roomId, String nif, DateTime initialTime) throws Exception {
        Room room = spaceDao.getRoomById(roomId);
        User user = userDao.getUserByNif(nif);
        datetime = initialTime;
        if (room == null) {
            throw new IncorrectRoomException();
        }
        if (user == null || !(user instanceof ReservationUser)) {
            throw new IncorrectUserException();
        }
        if (datetime.isBeforeNow() || datetime.getMinuteOfHour() != 0) {
            throw new IncorrectTimeException();
        }
        if (alreadyExistingReservation(datetime, room)) {
            return null;
        } else {
            return new Reservation(datetime, (ReservationUser) user, room);
        }
    }

    /**
     * Delete an existing reservation if not found will throw
     * IncorrectReservationException.
     */
    @Override
    public void deleteReservation(long id) throws IncorrectReservationException {
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
     * Return an particular reservation by a concrete ReservationId
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

    

    @Override
    public Reservation findReservationById(String buildingName, String roomNumber, DateTime date) throws IncorrectBuildingException,
                                                                                                           IncorrectRoomException,
                                                                                                           IncorrectTimeException {
        Building building = spaceDao.getBuildingByName(buildingName);
        Room room = spaceDao.getRoomByNbAndBuilding(roomNumber, buildingName);
        datetime = date;
        if (room == null) {
            throw new IncorrectRoomException();
        }
        if (building == null) {
            throw new IncorrectBuildingException();
        }
        if (datetime.isBeforeNow() || datetime.getMinuteOfHour() != 0) {
            throw new IncorrectTimeException();
        }
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
    public Reservation makeReservationByType(String nif, String type, String buildingName, int capacity, DateTime date) throws IncorrectBuildingException{
        List<Room> sameTypeRooms = spaceDao.getAllRoomsByTypeAndCapacity(type, capacity, buildingName);
        if(sameTypeRooms.isEmpty()){
            return null;
        }
              
        List<Room> nonReservedRooms = getNonReservedRooms(sameTypeRooms, date);
        if(nonReservedRooms.isEmpty()){
            return null;
        }
        User reservationUser = userDao.getUserByNif(nif);
        if(reservationUser instanceof ReservationUser){
            return new Reservation(date, (ReservationUser)reservationUser, nonReservedRooms.get(0));
        }
        return null;
        
    }

    private List<Room> getNonReservedRooms(List<Room> rooms, DateTime date){
        Reservation res;
        List<Room> nonReservedRooms = new ArrayList<>();
        for (Room r : rooms) {
            res = reservationDao.getReservationByDateRoomBulding(date, r.getNumber(), r.getBuilding().getBuildingName());
            if (res == null) {
                nonReservedRooms.add(r);
            }
        }
        return nonReservedRooms;
    }
    

}
