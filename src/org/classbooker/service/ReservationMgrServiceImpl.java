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
     * Returns a boolean which indicates if the reservationMade exists in the
     * database. if the reservationMade exists in the database, it will return
     * true, else will return false
     */
    /**
     * Returns a boolean which indicates if the reservationMade exists in the
     * database. if the reservationMade exists in the database, it will return
     * true, else will return false
     */
    public boolean alreadyExistingReservation(DateTime datetime, Room room) throws DAOException {
        return reservationDao.getReservationByDateRoomBulding(datetime, room.getNumber(), room.getBuilding().getBuildingName()) != null;
    }

    @Override
    public List<Room> suggestionSpace(String roomNb, String building, DateTime date) throws DAOException {
        Building b = spaceDao.getBuildingByName(building);
        Room room = spaceDao.getRoomByNbAndBuilding(roomNb, building);
        if (b == null) {
            throw new IncorrectBuildingException();
        }
        if (room == null) {
            throw new IncorrectRoomException();
        }
        List<Room> suggestedRoomsByTypeAndCapacity = spaceDao.getAllRoomsByTypeAndCapacity(room.getClass().getName(), room.getCapacity(), building);
        List<Room> finalSuggestedRooms = getNonReservedRooms(suggestedRoomsByTypeAndCapacity, date);
        return finalSuggestedRooms;
    }

    @Override
    public ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime) {
        Reservation res = null;
        res = reservationDao.getReservationByDateRoomBulding(datetime, roomNb, building);

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
        Building b = spaceDao.getBuildingByName(buildingName);
        Room room = spaceDao.getRoomByNbAndBuilding(roomNb, buildingName);
        if (b == null) {
            throw new IncorrectBuildingException();
        }
        if (room == null) {
            throw new IncorrectRoomException();
        }

        Reservation reservationMade = makeReservationBySpace(room.getRoomId(), nif, resDate);

        if (reservationMade != null) {
            return new ReservationResult(reservationMade);
        }

        List<Room> suggestedRooms = suggestionSpace(roomNb, buildingName, resDate);
        return new ReservationResult(suggestedRooms);
    }

    /**
     * Returns a reservationMade for the roomId, the date initialTime and the
     * user nif. For making correctly the reservationMade, we should check that
     * the user and the room exist in database also the format of time should be
     * correct. This reservationMade should be accepted by the user before
     * inserting it into the database
     */
    public Reservation makeReservationBySpace(long roomId, String nif, DateTime initialTime) throws DAOException {
        Room room = spaceDao.getRoomById(roomId);
        User user = userDao.getUserByNif(nif);
        datetime = initialTime;

        checkRoom(room);
        checkUser(user);
        checkDate(datetime);
        Reservation r = makeReservation(datetime, user, room);
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
     * Return a particular reservationMade by a concrete ReservationId If not
     * exists, will throwIncorrectReservationException.
     */
    @Override
    public Reservation findReservationById(long id) throws IncorrectReservationException {
        Reservation reser = reservationDao.getReservationById(id);
        if (reser == null) {
            throw new IncorrectReservationException("Can not find the reservation");
        }
        return reser;
    }

    @Override
    public List<Reservation> findReservationByBuildingAndRoomNb(String buildingName, String roomNumber) throws DAOException {
        List<Reservation> res = reservationDao.getAllReservationByBuilding(buildingName);
        List<Reservation> result = new ArrayList<>();
        for (Reservation reser : res) {
            if (reser.getRoom().getNumber().equals(roomNumber)) {
                result.add(reser);
            }
        }
        return result;
    }

    /**
     * Return a particular reservation Made by a concrete space and a concrete
     * date if exists some errors, it will throws exceptions
     */
    @Override
    public Reservation findReservationBySpaceAndDate(String buildingName, String roomNumber, DateTime date) throws DAOException {
        Building building = spaceDao.getBuildingByName(buildingName);
        Room room = spaceDao.getRoomByNbAndBuilding(roomNumber, buildingName);
        datetime = date;
        checkBuilding(building);
        checkRoom(room);
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
    public Reservation makeReservationByType(String nif, String type, String buildingName, int capacity, DateTime date) throws DAOException {
        List<Room> avaliableRooms = obtainAllRoomsWithSameFeatures(type, capacity, buildingName, date);
        if (avaliableRooms.isEmpty()) {
            return null;
        }
        User reservationUser = userDao.getUserByNif(nif);
        if (reservationUser instanceof ReservationUser) {
            return new Reservation(date, (ReservationUser) reservationUser, avaliableRooms.get(0));
        }
        return null;

    }

    private List<Room> getNonReservedRooms(List<Room> rooms, DateTime date) throws DAOException {
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
    public List<Room> obtainAllRoomsWithSameFeatures(String type, int capacity, String building, DateTime date) throws DAOException {
        List<Room> sameTypeRooms = spaceDao.getAllRoomsByTypeAndCapacity(type, capacity, building);
        if (sameTypeRooms.isEmpty()) {
            return new ArrayList();
        }

        List<Room> nonReservedRooms = getNonReservedRooms(sameTypeRooms, date);
        if (nonReservedRooms.isEmpty()) {
            return new ArrayList();
        }
        return nonReservedRooms;
    }

    @Override
    public List<Reservation> getReservationsByNif(String nif) {
        List<Reservation> lreser = reservationDao.getAllReservationByUserNif(nif);
        return lreser;
    }

    @Override
    public List<Reservation> getFilteredReservation(String nif,
            DateTime startDate,
            DateTime endDate,
            String buildingName,
            String roomNb,
            int capacity,
            String roomType)
            throws DAOException {
        if (validation(nif, startDate, endDate, buildingName, roomNb, capacity, roomType)) {
            System.out.println("DINS VAL");
            return new ArrayList<>();
        }
        List<Reservation> lfreser = getReservationsByNif(nif);

        if (lfreser == null) {
            System.out.println("DINS FRES");
            return new ArrayList<>();
        } else {
            lfreser = validateField(startDate, endDate, buildingName, roomNb, capacity, roomType, lfreser);
        }
        return lfreser;
    }

    private void checkRoom(Room room) throws DAOException {
        if (room == null) {
            throw new IncorrectRoomException("can not find the room");
        }
    }

    private void checkUser(User user) throws DAOException {
        if (user == null || !(user instanceof ReservationUser)) {
            throw new IncorrectUserException("user not exist");
        }
    }

    private void checkDate(DateTime datetime) throws DAOException {
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

    private void checkBuilding(Building building) throws DAOException {
        if (building == null) {
            throw new IncorrectBuildingException("can not find the building");
        }
    }

    private List<Reservation> getReservationAndDates(DateTime startDate,
            DateTime endDate, List<Reservation> lfreser) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation res : lfreser) {
            if ((res.getReservationDate().isEqual(startDate))) {
                result.add(res);
            }
        }
        return result;
    }

    private List<Reservation> getReservationAndBuilding(String buildingName, List<Reservation> lfreser)
            throws DAOException {
        List<Reservation> result = new ArrayList<>();
        for (Reservation res : lfreser) {
            if ((res.getRoom().getBuilding().getBuildingName()).equals(buildingName)) {
                result.add(res);
            }
        }
        return result;
    }

    private List<Reservation> getReservationAndRoom(String roomNb,
            String buildingName,
            List<Reservation> lfreser)
            throws DAOException {

        Room roomID = spaceDao.getRoomByNbAndBuilding(roomNb, buildingName);

        return getReservationAndRoom(roomID.getRoomId(), lfreser);
    }

    private List<Reservation> getReservationAndRoom(long roomID,
            List<Reservation> lfreser) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation res : lfreser) {
            if ((res.getRoom().getRoomId() == roomID)) {
                result.add(res);
            }
        }
        return result;
    }

    private List<Reservation> getReservationAndCapacity(int capacity,
            List<Reservation> lfreser) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation res : lfreser) {
            if ((res.getRoom().getCapacity() >= capacity)) {
                result.add(res);
            }
        }
        return result;
    }

    private List<Reservation> getReservationAndRoomType(String roomType,
            List<Reservation> lfreser)
            throws DAOException {

        List<Room> rooms = spaceDao.getAllRoomsOfOneType(roomType);
        List<Reservation> result = new ArrayList<>();
        for (Reservation res : lfreser) {
            for (Room room : rooms) {
                if ((res.getRoom() == room)) {
                    result.add(res);
                }
            }
        }
        return result;
    }

    private boolean validation(String nif,
            DateTime startDate,
            DateTime endDate,
            String buildingName,
            String roomNb,
            int capacity,
            String roomType) {

        if ((startDate == null & endDate != null)
                || (startDate != null & endDate == null)) {
            return true;
        }

        if (startDate != null & endDate != null) {
            if (startDate.isAfter(endDate)) {
                return true;
            }
        }

        return !testFields(nif, buildingName, roomNb, capacity, roomType);
    }

    private List<Reservation> validateField(DateTime startDate,
            DateTime endDate,
            String buildingName,
            String roomNb,
            int capacity,
            String roomType,
            List<Reservation> lfreser) throws DAOException {
        List<Reservation> aux = lfreser;

        if (startDate != null && endDate != null & lfreser.size() > 0) {
            aux = getReservationAndDates(startDate, endDate, lfreser);
        }
        if (buildingName != null & aux.size() > 0) {
            aux = getReservationAndBuilding(buildingName, aux);
        }
        if (roomNb != null & buildingName != null & aux.size() > 0) {

            aux = getReservationAndRoom(roomNb, buildingName, aux);
        }
        if (capacity > 0 & aux.size() > 0) {
            aux = getReservationAndCapacity(capacity, aux);
        }
        if (roomType != null & aux.size() > 0) {
            aux = getReservationAndRoomType(roomType, aux);
        }
        return aux;
    }

    private boolean testFields(String nif, String buildingName,
            String roomNb,
            int capacity,
            String roomType) {
        boolean validate1 = validateNif(nif) & validateBuilding(buildingName);
        boolean validate2 = validateRoomNb(roomNb) && validateCapacity(capacity);
        return validateRoomType(roomType) && validate1 && validate2;
    }

    private boolean validateNif(String nif) {
        return (nif == null || nif.matches("\\d{1,8}"));
    }

    private boolean validateBuilding(String buildingName) {
        return (buildingName == null || buildingName.matches("[A-Z][a-z].*"));
    }

    private boolean validateRoomNb(String roomNb) {
        return (roomNb == null || roomNb.matches("\\d\\.\\d"));
    }

    private boolean validateCapacity(int capacity) {
        return capacity >= 0;
    }

    private boolean validateRoomType(String roomType) {
        return (roomType == null || roomType.matches("[A-Z][a-z]+.*"));
    }

}
