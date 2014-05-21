/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import org.classbooker.service.ReservationMgrServiceImplQuery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.classbooker.dao.ReservationDAO;
import org.classbooker.dao.ReservationDAOImpl;
import org.classbooker.dao.SpaceDAO;
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
   SpaceDAO spaDao;
   DateTime startDate;
   DateTime endDate;
   Reservation res1,res2,res3,res4,res5;
   Room room;
   Building building;
   List<Reservation>lres,lreser;
   ReservationMgrServiceImplQuery rmsQ;
   EntityManager eM;
    
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
      //getStartExpectations("12345678",lreser);
      searchReservationsByFields("12345678",null,null,null,null,0,null);
//      List<Reservation>result=resDao.getAllReservationByUserNif(nif);
      List <Reservation> tested = rmsQ.getReservationsByNif("12345678");
      
      assertEquals("0 reservations",10,tested.size());
    }
 /*
    @Test 
    public void ReservationsByNif() throws Exception{
      getStartExpectations("12345678",lres);
      List <Reservation> tested = rmsQ.getReservationsByNif("12345678");
      assertEquals("5 reservations",5,tested.size());
      assertEquals("First reservation",res1,tested.get(0));
      assertEquals("Second reservation",res2,tested.get(1));
      assertEquals("Third reservation",res3,tested.get(2));
      assertEquals("Fourth reservation",res4,tested.get(3));
      assertEquals("Fifth reservation",res5,tested.get(4));
    }
    
    @Test 
    public void ReservationWithoutFieldsForFiltering() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,0,null);     
      getStartExpectations("12345678",lres);    
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",5,tested.size());
      assertEquals("First reservation",res1,tested.get(0));
      assertEquals("Second reservation",res2,tested.get(1));
      assertEquals("Third reservation",res3,tested.get(2));
      assertEquals("Fourth reservation",res4,tested.get(3));
      assertEquals("Fifth reservation",res5,tested.get(4));
    }
    
    @Test 
    public void ReservationFilteredByDates() throws Exception{
     
      searchReservationsByFields("12345678",new DateTime(2014,5,9,12,0),
              new DateTime(2014,5,9,13,0),null,null,0,null);       
      getStartExpectations("12345678",lres);     
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",5,tested.size());
      assertEquals("First reservation",res1,tested.get(0));
      assertEquals("Second reservation",res2,tested.get(1));
      assertEquals("Third reservation",res3,tested.get(2));
      assertEquals("Fourth reservation",res4,tested.get(3));
      assertEquals("Fifth reservation",res5,tested.get(4));
    }
    
    @Test 
    public void ReservationFilteredByBuildingName() throws Exception{
      searchReservationsByFields("12345678",null,null,"Rectorate Building",null,0,null); 
      getStartExpectations("12345678",lres);
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",1,tested.size());
      assertEquals("First reservation",res1,tested.get(0));
    }
    
    @Test 
    public void ReservationFilteredByRoomNb() throws Exception{
      searchReservationsByFields("12345678",null,null,"Rectorate Building",
                                 "2.3",0,null);
      getStartExpectations("12345678",lres);
      getExpectationsWithRoomNbAndBuilding("2.3","Rectorate Building",
                                            res1.getRoom());
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",1,tested.size());
      assertEquals("First reservation",res1,tested.get(0));
    }
    
    @Test 
    public void ReservationFilteredByCapacity() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,50,null);
      getStartExpectations("12345678",lres);
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",1,tested.size());
      assertEquals("Fifth reservation",res5,tested.get(0));
    }
    
    @Test 
    public void ReservationFilteredByRoomType() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,0,"MeetingRoom");
      getStartExpectations("12345678",lres);
      getExpectationsWithRoomType("MeetingRoom",res1);
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",1,tested.size());
      assertEquals("First reservation",res1,tested.get(0));
    }
    
    @Test 
    public void ReservationFilteredByAllFields() throws Exception{
      searchReservationsByFields("12345678",
                                  new DateTime(2014,5,9,12,0),
                                  new DateTime(2014,5,9,13,0),
                                  "Main Library","4.4",
                                  50,
                                  "ClassRoom");
      getStartExpectations("12345678",lres);
      getExpectationsWithRoomNbAndBuilding("4.4","Main Library",
                                            res5.getRoom());
      getExpectationsWithRoomType("ClassRoom",res5);
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      assertEquals("Same size",1,tested.size());
      assertEquals("First reservation",res5,tested.get(0));
    }
    
    @Test 
    public void findReservationBySpace() throws IncorrectBuildingException{
      final List<Reservation> returnlres = new ArrayList<>();
      returnlres.add(res1);
      context.checking(new Expectations(){{
            oneOf(resDao).getAllReservationByBuilding("Rectorate Building");
            will(returnValue(returnlres));
        }});
      List <Reservation> tested = rmsQ.findReservationByBuildingAndRoomNb("Rectorate Building","2.3");
      assertEquals("Same Size",1,tested.size());
      assertEquals("First reservation",res1,tested.get(0));
    }
    
    @Test 
    public void finsReservationBySpaceBis() throws IncorrectBuildingException{
      final List<Reservation> returnlres = new ArrayList<>();
      returnlres.add(res3);
      returnlres.add(res4);
      context.checking(new Expectations(){{
            oneOf(resDao).getAllReservationByBuilding("Faculty");
            will(returnValue(returnlres));
        }});
      List <Reservation> tested = rmsQ.findReservationByBuildingAndRoomNb("Faculty","2.3");
      assertEquals("Same Size",1,tested.size());
      assertEquals("Only one reservation",res4,tested.get(0));
    }
    
    @Test
    public void IncorrectFields() throws Exception{
       
       searchReservationsByFields("12345678",null,null,"2.04",null,0,null); 
       
       List<Reservation> tested = rmsQ.getFilteredReservation(nif, 
                                                              startDate, 
                                                              endDate, 
                                                              buildingName, 
                                                              roomNb, 
                                                              capacity, 
                                                              roomType);
       
       assertEquals("Non rerserves, building is incorrect",lreser,tested);
    }
    
    @Test 
    public void validateDatesNoInit() throws Exception{
     
      searchReservationsByFields("12345678",null,new DateTime(2014,5,9,13,0)
                                 ,null,null,0,null);
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
        
        assertEquals("Non reserves,StartDate incorrect",lreser,tested);
    }
    
    @Test 
    public void validateDatesNoEnd() throws Exception{
     
      searchReservationsByFields("12345678",new DateTime(2014,5,9,12,0),null
                                 ,null,null,0,null);
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
        
        assertEquals("Non reserves,EndDate incorrect",lreser,tested);
    }
    
    @Test 
    public void validateDateEndBeforeInit() throws Exception{
     
      searchReservationsByFields("12345678",new DateTime(2014,5,9,12,0),
                                  new DateTime(2014,5,9,11,0),
                                 null,null,0,null);
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
        
        assertEquals("Non reserves,EndDate is before than startDate",
                                                                lreser,tested);
    }
   */
    
    private void createEm() throws Exception{
        resDao = new ReservationDAOImpl();
        EntityManager eM = getEntityManager();
        resDao.setEm(eM);
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
