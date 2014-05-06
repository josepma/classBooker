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
 * @author sht1, Xurat@
 */
public class ReservationMgrServiceImplQuery implements ReservationMgrService {
    
    private Reservation res;
    private ReservationDAO resDao;
    private ReservationUser resUser;
    private SpaceDAO spaDao;
    private DateTime dateIni, dateFi;
 
    
    
    /**
     * 
     * @param nif
     * @return
     * @throws IncorrectUserException 
     */
    public List <Reservation> getReservationsByNif(String nif) 
                              throws IncorrectUserException{
        
        List<Reservation> lfreser= new ArrayList<>();
        lfreser=resDao.getAllReservationByUserNif(nif);
        
        return lfreser;
    }
    
    public List <Reservation> getFilteredReservation(String nif, 
                              DateTime dataIni,DateTime dataFi, 
                              String buildingName,long roomNb,int capacity,
                              String roomType) throws IncorrectUserException, IncorrectBuildingException{
        
        List<Reservation> lfreser= new ArrayList<Reservation>();
        lfreser = getReservationsByNif(nif);
        // Que pasa si no hay nada en la lista?
        if(lfreser == null){
            return null;
        }else{
            if(dataIni != null && dataFi !=null & lfreser.size()>0){
                lfreser = getReservationAndDates(dataIni, dataFi,lfreser);
            }
            if(buildingName !=null & lfreser.size()>0){
                lfreser = getReservationAndBuilding(buildingName,lfreser);
            }
            if(roomNb <0 & lfreser.size()>0){
                lfreser = getReservationAndRoom(roomNb,lfreser);
            }
            if(capacity <0 & lfreser.size()>0){
                lfreser = getReservationAndCapacity(capacity,lfreser);
            }
            if(roomType !=null & lfreser.size()>0){
                lfreser = getReservationAndRoomType(roomType,lfreser);
            }
        }           
        return lfreser;
    }
    
    public void setRes(Reservation res) {
        this.res = res;
    }

    public void setResDao(ReservationDAO resDao) {
        this.resDao = resDao;
    }

    public void setResUser(ReservationUser resUser) {
        this.resUser = resUser;
    }
    
    //////// PRIVATE OPS //////
    
    private List <Reservation> getReservationAndDates(DateTime dataIni,
                               DateTime dataFi,List<Reservation>lfreser){
        
            for (Reservation res: lfreser){
                    if(res.getReservationDate().isBefore(dataFi)){
                            
                    }else
                        lfreser.remove(res);
            }
        
        return lfreser;
    }
    private List <Reservation> getReservationAndBuilding(String buildingName
                                ,List<Reservation>lfreser) 
                                throws IncorrectBuildingException{
        
        for ( Reservation res: lfreser){
            if(!(res.getRoom().getBuilding().getBuildingName()).equals(buildingName)){
                lfreser.remove(res);
            }
        }
        return lfreser;
    }
    private List <Reservation> getReservationAndRoom(String buildingName,
                               long roomNb,List<Reservation>lfreser){
        
        return null;
    }
    private List <Reservation> getReservationAndRoom(long roomID,
                               List<Reservation>lfreser){
        return null;
    }
    private List <Reservation> getReservationAndCapacity(int capacity,
                               List<Reservation>lfreser){
        return null;
    }
    private List <Reservation> getReservationAndRoomType(String roomType,
                               List<Reservation>lfreser){
        return null;
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
    public ReservationUser getCurrentUserOfDemandedRoom(String roomNb, String building, DateTime datetime) throws IncorrectRoomException {
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

    
}
