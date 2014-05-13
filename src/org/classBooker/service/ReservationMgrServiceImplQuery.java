/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import org.classBooker.dao.ReservationDAO;
import org.classBooker.dao.SpaceDAO;
import org.classBooker.dao.SpaceDAOImpl;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTypeException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.classBooker.util.ReservationResult;
import org.joda.time.DateTime;

/**
 *
 * @author SantiH & Xurata
 */
public class ReservationMgrServiceImplQuery implements ReservationMgrService {
    
    private ReservationDAO resDao;
    private SpaceDAO spaDao;

    /**
     * Get a list of reservations for the user
     * @param nif
     * @return 
     * @throws IncorrectUserException 
     */
    public List <Reservation> getReservationsByNif(String nif) 
                              throws IncorrectUserException{
        
        List<Reservation> lreser= new ArrayList<>();
        lreser=resDao.getAllReservationByUserNif(nif);
        
        return lreser;
    }
    /**
     * Get a list of reservation using different filtered
     * @param nif
     * @param startDate
     * @param endDate
     * @param buildingName
     * @param roomNb
     * @param capacity
     * @param roomType
     * @return
     * @throws IncorrectUserException
     * @throws IncorrectBuildingException 
     */
    public List <Reservation> getFilteredReservation(String nif, 
                                                  DateTime startDate,
                                                  DateTime endDate, 
                                                  String buildingName,
                                                  String roomNb,
                                                  int capacity,
                                                  String roomType) 
            throws Exception{
        
        List<Reservation> lfreser= new ArrayList<Reservation>();
        lfreser = getReservationsByNif(nif);
   
        if(lfreser == null){
            return new ArrayList<Reservation>();
        }else{
            if(startDate != null && endDate != null & lfreser.size()>0){
                lfreser = getReservationAndDates(startDate, endDate,lfreser);
            }
            if(buildingName !=null & lfreser.size()>0){
                lfreser = getReservationAndBuilding(buildingName,lfreser);
            }
            if(roomNb !=null & buildingName !=null & lfreser.size()>0){
                lfreser = getReservationAndRoom(roomNb,buildingName,lfreser);
            }
            if(capacity >0 & lfreser.size()>0){
                lfreser = getReservationAndCapacity(capacity,lfreser);
            }
            if(roomType != null & lfreser.size()>0){
                lfreser = getReservationAndRoomType(roomType,lfreser);
            }
        }           
        return lfreser;
    }
    
    //////// PRIVATE OPS //////
    
    private List <Reservation> getReservationAndDates(DateTime startDate,
                               DateTime endDate,List<Reservation>lfreser){
        
            for (Reservation res: lfreser){
                    if(!(res.getReservationDate().isBefore(endDate))){
                           lfreser.remove(res); 
                    }          
            }
        
        return lfreser;
    }
    private List <Reservation> getReservationAndBuilding(String buildingName
                                ,List<Reservation>lfreser) 
                                throws IncorrectBuildingException{
        
        for (Reservation res: lfreser){
            if(!(res.getRoom().getBuilding().getBuildingName()).equals(buildingName)){
                lfreser.remove(res);
            }
        }
        return lfreser;
    }
    private List <Reservation> getReservationAndRoom(String buildingName,
                               String roomNb,List<Reservation>lfreser) 
            throws IncorrectBuildingException, IncorrectRoomException{
        
//        for(Reservation res: lfreser){
//            if((!res.getRoom().getBuilding().getBuildingName().equals(buildingName)) &&
//                    (!res.getRoom().getNumber().equals(roomNb))){
//                lfreser.remove(res);
//            }
//        }
//        SpaceDAOImpl spaceDao=new SpaceDAOImpl();
        Room roomID = spaDao.getRoomByNbAndBuilding(roomNb,buildingName);
        return getReservationAndRoom(roomID.getRoomId(),lfreser);
    }
    private List <Reservation> getReservationAndRoom(long roomID,
                               List<Reservation>lfreser){
        
        for(Reservation res: lfreser){
            if((res.getRoom().getRoomId()!=roomID)){
                lfreser.remove(res);
            }
        }
        return lfreser;
    }
    private List <Reservation> getReservationAndCapacity(int capacity,
                               List<Reservation>lfreser){
        
        for(Reservation res: lfreser){
            if((res.getRoom().getCapacity()>=capacity)){
                lfreser.remove(res);
            }
        }
        return lfreser;
    }
    private List <Reservation> getReservationAndRoomType(String roomType,
                               List<Reservation>lfreser) 
            throws IncorrectTypeException{
        
        List<Room> rooms;
        List<Reservation> result;
        rooms = new ArrayList<Room>();
        result = new ArrayList<Reservation>();
//        SpaceDAOImpl spaceDao = new SpaceDAOImpl();
        rooms = spaDao.getAllRoomsOfOneType(roomType);
        
        for(Reservation res: lfreser){
            for (Room r : rooms){
                if((res.getRoom().getRoomId()== r.getRoomId())){
                    result.add(res);
                }
            }
        }
        return result;
    }
    
    public void setResDao(ReservationDAO resDao) {
        this.resDao = resDao;
    }

    public ReservationDAO getResDao() {
        return resDao;
    }

    public SpaceDAO getSpaDao() {
        return spaDao;
    }

    public void setSpaDao(SpaceDAO spaDao) {
        this.spaDao = spaDao;
    }
  
    @Override
    public ReservationResult makeCompleteReservationBySpace(String nif, String roomNb, String buildingName, DateTime resDate) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reservation makeReservationBySpace(long roomld, String nif, DateTime initialTime) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void acceptReservation(Reservation reservation) throws IncorrectReservationException, IncorrectUserException, IncorrectRoomException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Room> suggestionSpace(String roomNb, String building, DateTime resDate) throws IncorrectTypeException, IncorrectBuildingException, IncorrectRoomException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reservation makeReservationByType(String nif, String type, int capacity, DateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
