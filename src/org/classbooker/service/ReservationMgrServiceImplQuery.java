/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import org.classbooker.dao.ReservationDAO;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectBuildingException;
import org.classbooker.dao.exception.IncorrectReservationException;
import org.classbooker.dao.exception.IncorrectRoomException;
import org.classbooker.dao.exception.IncorrectTimeException;
import org.classbooker.dao.exception.IncorrectTypeException;
import org.classbooker.dao.exception.IncorrectUserException;
import org.classbooker.entity.Building;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;
import org.classbooker.util.ReservationResult;
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
                              throws DAOException{
        List<Reservation> lreser = resDao.getAllReservationByUserNif(nif);
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
        if(validation(nif,startDate,endDate,buildingName,roomNb,capacity,roomType)){
            return new ArrayList<>(); 
        } 
        List<Reservation> lfreser= getReservationsByNif(nif);
   
        if(lfreser == null){
            return new ArrayList<>();
        }else{
            if(startDate != null && endDate != null & lfreser.size()>0){
                lfreser = getReservationAndDates(startDate, endDate,lfreser);
            }
            if(buildingName !=null & lfreser.size()>0){
                lfreser = getReservationAndBuilding(buildingName,lfreser);
            }
            if(roomNb !=null & buildingName != null & lfreser.size()>0){
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
        List<Reservation> result = new ArrayList<>();
        for (Reservation res: lfreser){
                if((res.getReservationDate().isBefore(endDate))){
                       result.add(res); 
                }          
        } 
        return result;
    }
    private List <Reservation> getReservationAndBuilding(String buildingName
                                ,List<Reservation>lfreser) 
                                throws IncorrectBuildingException{
        List<Reservation> result = new ArrayList<>();
        for (Reservation res: lfreser){
            if((res.getRoom().getBuilding().getBuildingName()).equals(buildingName)){
                result.add(res);
            }
        }
        return result;
    }
    private List <Reservation> getReservationAndRoom(String roomNb,
                                                    String buildingName,
                                                    List<Reservation>lfreser) 
            throws IncorrectBuildingException, IncorrectRoomException{
        Room roomID = spaDao.getRoomByNbAndBuilding(roomNb,buildingName);
        return getReservationAndRoom(roomID.getRoomId(),lfreser);
    }
    private List <Reservation> getReservationAndRoom(long roomID,
                               List<Reservation>lfreser){
        List<Reservation> result = new ArrayList<>();
        for(Reservation res: lfreser){
            if((res.getRoom().getRoomId()==roomID)){
                result.add(res);
            }
        }
        return result;
    }
    private List <Reservation> getReservationAndCapacity(int capacity,
                               List<Reservation>lfreser){
        List<Reservation> result = new ArrayList<>();
        for(Reservation res: lfreser){
            if((res.getRoom().getCapacity()>=capacity)){
                result.add(res);
            }
        }
        return result;
    }
    private List <Reservation> getReservationAndRoomType(String roomType,
                               List<Reservation>lfreser) 
            throws IncorrectTypeException{
        
        List<Room> rooms = spaDao.getAllRoomsOfOneType(roomType);
        List<Reservation> result = new ArrayList<>();
        for(Reservation res: lfreser){
            for (Room room : rooms){
                if((res.getRoom()== room)){
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
                              String roomType){
      
      if ((startDate == null & endDate != null) || 
             (startDate != null & endDate == null) ){
          return true;
      }  
      
      if (startDate != null & endDate != null){
          if (startDate.isAfter(endDate)){
              return true;
          }
      }
        
      return !((nif==null || nif.matches("\\d{1,8}")) &&
             (buildingName==null || buildingName.matches("[A-Z][a-z].*")) &&
             (roomNb==null || roomNb.matches("\\d\\.\\d")) &&
              capacity>=0 &&
             (roomType==null || roomType.matches("[A-Z][a-z]+[A-z][a-z]+")));
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
    public List<Reservation> findReservationByBuildingAndRoomNb(String buildingName, 
                                                 String roomNumber) 
                             throws DAOException{
        List <Reservation> res = resDao.getAllReservationByBuilding(buildingName);
        List <Reservation> result = new ArrayList<>();
        for(Reservation reser : res){
            if(reser.getRoom().getNumber().equals(roomNumber)){
                result.add(reser);
            }
        }
        return result;
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
    public Reservation makeReservationByType(String nif, String type, String buildingName, int capacity, DateTime date) throws IncorrectBuildingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Room> obtainAllRoomsWithSameFeatures(String type, int capacity, String building, DateTime date) throws IncorrectBuildingException, IncorrectRoomException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReservationResult makeCompleteReservationBySpace(String nif, String roomNb, String buildingName, DateTime resDate) throws IncorrectTimeException, IncorrectUserException, IncorrectRoomException, IncorrectBuildingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reservation makeReservationBySpace(long roomld, String nif, DateTime initialTime) throws IncorrectRoomException, IncorrectUserException, IncorrectTimeException, IncorrectBuildingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
