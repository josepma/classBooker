/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import java.util.List;
import java.util.ArrayList;
import org.classbooker.dao.ReservationDAO;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.exception.DAOException;
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
     * @throws org.classbooker.dao.exception.DAOException 
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
     * @throws org.classbooker.dao.exception.DAOException 
     */
    public List <Reservation> getFilteredReservation(String nif, 
                                                  DateTime startDate,
                                                  DateTime endDate, 
                                                  String buildingName,
                                                  String roomNb,
                                                  int capacity,
                                                  String roomType) 
            throws DAOException{
        if(validation(nif,startDate,endDate,buildingName,roomNb,capacity,roomType)){
            return new ArrayList<>(); 
        } 
        List<Reservation> lfreser= getReservationsByNif(nif);
        
        if(lfreser == null){
            return new ArrayList<>();
        }else{
            lfreser=validateField(startDate,endDate,buildingName,roomNb,capacity,roomType,lfreser);
        }           
        return lfreser;
    }
    //////// PRIVATE OPS //////
    
    private List <Reservation> getReservationAndDates(DateTime startDate,
                               DateTime endDate,List<Reservation>lfreser){
        List<Reservation> result = new ArrayList<>();
        for (Reservation res: lfreser){
                if((res.getReservationDate().isEqual(startDate))){
                        result.add(res); 
                }          
        } 
        return result;
    }
    private List <Reservation> getReservationAndBuilding(String buildingName
                                ,List<Reservation>lfreser) 
                                throws DAOException{
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
            throws DAOException{
       
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
            throws DAOException{
        
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
        
      return !testFields(nif,buildingName,roomNb,capacity,roomType);
    }
    private List <Reservation> validateField(DateTime startDate,
                              DateTime endDate, 
                              String buildingName,
                              String roomNb,
                              int capacity,
                              String roomType,
                              List<Reservation>lfreser) throws DAOException{
        List <Reservation> aux = lfreser;
        
        if(startDate != null && endDate != null & lfreser.size()>0){
                aux = getReservationAndDates(startDate, endDate,lfreser);
            }
            if(buildingName !=null & aux.size()>0){
                aux = getReservationAndBuilding(buildingName,aux);
            }
            if(roomNb !=null & buildingName != null & aux.size()>0){
               
                aux = getReservationAndRoom(roomNb,buildingName,aux);
            }
            if(capacity >0 & aux.size()>0){
                aux = getReservationAndCapacity(capacity,aux);
            }
            if(roomType != null & aux.size()>0){
                aux = getReservationAndRoomType(roomType,aux);
            }
         return aux;   
    }
    
    private boolean testFields(String nif,String buildingName,
                                     String roomNb,
                                     int capacity,
                                     String roomType){     
        boolean validate1 =validateNif(nif) & validateBuilding(buildingName);
        boolean validate2 = validateRoomNb(roomNb) && validateCapacity(capacity);
        return validateRoomType(roomType) && validate1 && validate2;          
    }
    
    private boolean validateNif(String nif){
        return (nif==null || nif.matches("\\d{1,8}"));
    }
    private boolean validateBuilding(String buildingName){
        return (buildingName==null || buildingName.matches("[A-Z][a-z].*"));
    }
    private boolean validateRoomNb(String roomNb){
        return (roomNb==null || roomNb.matches("\\d\\.\\d"));
    }
    private boolean validateCapacity(int capacity){
        return capacity>=0;
    }
    private boolean validateRoomType(String roomType){
        return (roomType==null || roomType.matches("[A-Z][a-z]+.*"));
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
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<Reservation> findReservationByNif(String nif) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Reservation findReservationById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); 
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
    public Reservation findReservationBySpaceAndDate(String buildingName, String roomNumber, DateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<Reservation> findReservationByType(String type) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<Reservation> findReservationByType(String type, DateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<Reservation> getAllReservations() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void deleteReservation(long id) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime){
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void acceptReservation(Reservation reservation) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<Room> suggestionSpace(String roomNb, String building, DateTime resDate) throws DAOException{
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Reservation makeReservationByType(String nif, String type, String buildingName, int capacity, DateTime date) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<Room> obtainAllRoomsWithSameFeatures(String type, int capacity, String building, DateTime date) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public ReservationResult makeCompleteReservationBySpace(String nif, String roomNb, String buildingName, DateTime resDate) throws DAOException{
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Reservation makeReservationBySpace(long roomld, String nif, DateTime initialTime) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
}
