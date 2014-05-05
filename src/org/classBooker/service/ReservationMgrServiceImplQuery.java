/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import java.util.List;
import org.classBooker.dao.ReservationDAO;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTypeException;
import org.classBooker.dao.exception.IncorrectUserException;
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
    
    public List<Reservation> getReservations() throws Exception {
        return resDao.getAllReservation();
    }
    
    public List<Reservation> getReservations(String resBy) throws Exception {
        if(isBuilding(resBy)){
            return resDao.getAllReservationByBuilding(resBy);
        } else if(isRoom(resBy)){
            return resDao.getAllReservationByRoom(resBy);
        } else if(isNif(resBy)){
            return resDao.getAllReservationByUserNif(resBy);
        } else {
            return null;
        }
    }
    
    public Reservation getReservation(DateTime resDate, 
            String roomNb, 
            String buildingName) throws Exception {
        return resDao.getReservationByDateRoomBulding(resDate, roomNb, buildingName);
    }
    
    private boolean isBuilding (String resBy){
        String pattern = "[a-zA-Z]+*";
        return resBy.matches(pattern);
    }
    
    private boolean isRoom (String resBy){
        String pattern = "[0-9]//.[0-9]";
        return resBy.matches(pattern);
    }
    
    private boolean isNif (String resBy){
        String pattern = "(\\d{1,8})([a-zA-Z])";
        return resBy.matches(pattern);
    }

    public void setRes(Reservation res) {
        this.res = res;
    }

    public Reservation getRes() {
        return res;
    }

    public void setResDao(ReservationDAO resDao) {
        this.resDao = resDao;
    }

    public ReservationDAO getResDao() {
        return resDao;
    }

    public void setResUser(ReservationUser resUser) {
        this.resUser = resUser;
    }

    public ReservationUser getResUser() {
        return resUser;
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
    public List<Room> suggestionSpace(String roomNb, String building) throws IncorrectTypeException, IncorrectBuildingException, IncorrectRoomException {
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

    
}
