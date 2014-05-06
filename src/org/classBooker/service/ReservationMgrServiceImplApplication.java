package org.classBooker.service;

import java.util.HashMap;
import java.util.List;
import org.classBooker.dao.ReservationDAO;
import org.classBooker.dao.SpaceDAO;
import org.classBooker.dao.UserDAO;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTimeException;
import org.classBooker.dao.exception.IncorrectTypeException;
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
 * @author aba8
 */



public class ReservationMgrServiceImplApplication implements ReservationMgrService{
    private SpaceDAO sDao;
    private UserDAO uDao;
    private ReservationDAO rDao;
    private DateTime datetime;
 
    
          /**
           * Returns a reservation for the roomId, the date initialTime and the user nif.
           * For making correctly the reservation, we should check that the user and the room exist in database
           * also the format of time should be correct.
           * This reservation should be accepted by the user before inserting it into the database
           */
        @Override
        public Reservation makeReservationBySpace(long roomId, String nif, DateTime initialTime)throws Exception {
             Room room = sDao.getRoomById(roomId); 
             User user = uDao.getUserByNif(nif);
             datetime = initialTime;
            if(room == null){ 
                throw new IncorrectRoomException();
            }
            if(user == null || !(user instanceof ReservationUser)){
                throw new IncorrectUserException();
            }
            if(datetime.isBeforeNow() || datetime.getMinuteOfHour()!=0){
                throw new IncorrectTimeException();
            }
            if(alreadyExistingReservation(datetime,room)){
                return null;
            }
            else {
                return new Reservation(datetime, (ReservationUser)user, room);
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

    @Override
    public Reservation findReservationById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> findReservationById(String buildingName, String roomNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reservation findReservationById(String buildingName, String roomNumber, DateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> findReservationByType(String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> findReservationByType(String type, DateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> getAllReservations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteReservation(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public ReservationResult makeCompleteReservationBySpace(String nif, String roomNb, String buildingName, DateTime resDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   

    @Override
    public void acceptReservation(Reservation reservation) throws IncorrectReservationException, IncorrectUserException, IncorrectRoomException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime) throws IncorrectRoomException {
        return null;
    }
    
    
     /**
     * Set the UserDao.
     */
    public void setUserDao(UserDAO uDao){
        this.uDao = uDao;
    }
    
     /**
     * Set the ReservationDao.
     */
    public void setReservationDao(ReservationDAO rDao){
        this.rDao = rDao;
    }
    
     /**
     * Set the SpaceDao.
     */
    public void setSpaceDao(SpaceDAO sDao){
        this.sDao = sDao;
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
        return rDao.getReservationByDateRoomBulding(datetime, room.getNumber(), room.getBuilding().getBuildingName())!=null;
    }

    @Override
    public List<Room> suggestionSpace(String roomNb, String building, DateTime resDate) throws IncorrectTypeException, IncorrectBuildingException, IncorrectRoomException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
