/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import org.classbooker.service.ReservationMgrServiceImplQuery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.classbooker.dao.ReservationDAO;
import org.classbooker.dao.ReservationDAOImpl;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.SpaceDAOImpl;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectBuildingException;
import org.classbooker.entity.Building;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;
import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author sht1
 */
public class ReservationMgrServiceImplQueryIntegTest {
   
   ReservationUser rUser;
   ReservationDAOImpl resDao;
   SpaceDAOImpl spaDao;
   DateTime startDate;
   DateTime endDate;
   Reservation res1,res2,res3,res4,res5;
   Room room;
   Building building;
   List<Reservation>lres,lreser;
   ReservationMgrServiceImplQuery rmsQ;
   EntityManager em;
    
    private String nif;
    private DateTime startD;
    private DateTime endD;
    private String buildingName;
    private String roomNb;
    private int capacity;
    private String roomType;
    
    @Before
    public void setup() throws Exception{
        lres = new ArrayList<>();
        lreser = new ArrayList<>();
        rmsQ = new ReservationMgrServiceImplQuery();
        createEm();
    }
    
    @Test 
    public void zeroReservationsByNif() throws Exception{    
     
      List <Reservation> tested = rmsQ.getReservationsByNif("123456");
      
      assertEquals("0 reservations",0,tested.size());
    }
 
    @Test 
    public void ReservationsByNif() throws Exception{
       
      searchReservationsByFields("12345678",null,null,null,null,0,null);
      List <Reservation> tested = rmsQ.getReservationsByNif(nif);
      
      assertEquals("Same size",13,tested.size());
    }
   
    @Test 
    public void ReservationWithoutFieldsForFiltering() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,0,null);     
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",13,tested.size());
     
    }
     
    @Test 
    public void ReservationFilteredByDates() throws Exception{
     
      searchReservationsByFields("12345678",new DateTime(2014,6,10,9,0),
              new DateTime(2014,6,10,10,0),null,null,0,null);       
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      
      assertEquals("Same size",2,tested.size());
      
    }
    
    @Test 
    public void ReservationFilteredByBuildingName() throws Exception{
      searchReservationsByFields("12345678",null,null,"Rectorate Building",null,0,null); 
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",7,tested.size());
      //assertEquals("First reservation",res1,tested.get(0));
    }
    
    @Test 
    public void ReservationFilteredByRoomNb() throws Exception{
      searchReservationsByFields("12345678",null,null,"Rectorate Building",
                                 "1.0",0,null);
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",7,tested.size());
      
    }
    
    @Test 
    public void ReservationFilteredByCapacity() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,20,null);
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",13,tested.size());
      //assertEquals("Fifth reservation",res5,tested.get(0));
    }
    
    @Test 
    public void ReservationFilteredByRoomType() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,0,"MeetingRoom");
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",5,tested.size());
      
    }
    
    @Test 
    public void ReservationFilteredByAllFields() throws Exception{
      searchReservationsByFields("12345678",
                                  new DateTime(2014,6,10,9,0),
                                  new DateTime(2014,6,10,10,0),
                                  "Main Library","1.0",
                                  40,
                                  "MeetingRoom");
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",1,tested.size());
      //assertEquals("First reservation",res5,tested.get(0));
    }
    
    @Test 
    public void findReservationBySpace() throws IncorrectBuildingException, DAOException{
      
      List <Reservation> tested = rmsQ.findReservationByBuildingAndRoomNb("Rectorate Building","1.0");
      assertEquals("Same Size",10,tested.size());
      
    }
    
    @Test
    public void IncorrectFields() throws Exception{
       
       searchReservationsByFields("12345678",null,null,"2.04",null,0,null); 
       List<Reservation> result = new ArrayList<>();
       List<Reservation> tested = rmsQ.getFilteredReservation(nif, 
                                                              startDate, 
                                                              endDate, 
                                                              buildingName, 
                                                              roomNb, 
                                                              capacity, 
                                                              roomType);
       
       assertEquals("Non rerserves, building is incorrect",result,tested);
    }
    
    @Test 
    public void validateDatesNoInit() throws Exception{
     
      searchReservationsByFields("12345678",null,new DateTime(2014,5,9,13,0)
                                 ,null,null,0,null);
      List<Reservation> result = new ArrayList<>();
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
        
        assertEquals("Non reserves,StartDate incorrect",result,tested);
    }
    
    @Test 
    public void validateDatesNoEnd() throws Exception{
     
      searchReservationsByFields("12345678",new DateTime(2014,5,9,12,0),null
                                 ,null,null,0,null);
      List<Reservation> result = new ArrayList<>();
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
        
        assertEquals("Non reserves,EndDate incorrect",result,tested);
    }
    
    @Test 
    public void validateDateEndBeforeInit() throws Exception{
     
      searchReservationsByFields("12345678",new DateTime(2014,5,9,12,0),
                                  new DateTime(2014,5,9,11,0),
                                 null,null,0,null);
      List<Reservation> result = new ArrayList<>();
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
        
        assertEquals("Non reserves,EndDate is before than startDate",
                                                                result,tested);
    }
    
    private void createEm() throws Exception{
        resDao = new ReservationDAOImpl();
        spaDao = new SpaceDAOImpl();
        em = getEntityManager();
        resDao.setEm(em);
        spaDao.setEm(em);
        resDao.setsDao(spaDao);
        rmsQ.setResDao(resDao);
        rmsQ.setSpaDao(spaDao);
    }
    
    private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBookerIntegration");
        return emf.createEntityManager();  
    }

     private void searchReservationsByFields(String nif, 
                                          DateTime startDate,
                                          DateTime endDate, 
                                          String buildingName,
                                          String roomNb,
                                          int capacity,
                                          String roomType){
        this.nif = nif;
        this.startD = startDate;
        this.endD = endDate;
        this.buildingName = buildingName;
        this.roomNb = roomNb;
        this.capacity = capacity;
        this.roomType = roomType;    
    }
}
