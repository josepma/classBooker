/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import org.classBooker.dao.ReservationDAO;
import org.classBooker.entity.Reservation;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.runner.RunWith; 
import org.jmock.Sequence;
import java.util.List;
import java.util.ArrayList;
import org.classBooker.dao.SpaceDAO;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTypeException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.*;
import org.joda.time.DateTime;
import org.junit.Before;

/**
 *
 * @author SantiH & Xurata
 */

@RunWith(JMock.class)
public class ReservationMgrServiceImplQueryTest {
    
    Mockery context = new JUnit4Mockery();
    ReservationUser rUser;
    ReservationDAO resDao;
    SpaceDAO spaDao;
    DateTime startDate;
    DateTime endDate;
    Reservation res1,res2,res3,res4,res5;
    Room room;
    Building building;
    Sequence seq;
    ReservationMgrServiceImplQuery rmsQ;
    List<Reservation> lres, lreser;
    
    private String nif;
    private DateTime startD;
    private DateTime endD;
    private String buildingName;
    private String roomNb;
    private int capacity;
    private String roomType;
   
    @Before
    public void setup(){
        lres = new ArrayList<>();
        lreser = new ArrayList<>();
        rmsQ = new ReservationMgrServiceImplQuery();
        startingMockObjects();       
        makingSomeReservations();
    }
    
    @Test 
    public void zeroReservationsByNif() throws Exception{    
      getStartExpectations("12345678",lreser);
      List <Reservation> tested = rmsQ.getReservationsByNif("12345678");
      assertEquals("0 reservations",0,tested.size());
    }
 
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

    private void startingMockObjects(){
        resDao = context.mock(ReservationDAO.class);
        spaDao = context.mock(SpaceDAO.class);
        rmsQ.setResDao(resDao);
        rmsQ.setSpaDao(spaDao);
        seq = context.sequence("seq");
    }
    
    private void makingSomeReservations(){
        rUser = new ProfessorPas("12345678","Ralph@aus.com","Ralph");
        building = new Building("Rectorate Building");
        room = new MeetingRoom (building,"2.3",30);
        startDate = new DateTime(2014,5,9,12,0);
        endDate = new DateTime(2014,5,9,13,0);
        res1 = new Reservation(startDate, rUser, room);
        building = new Building("Main Library");
        room = new ClassRoom (building,"2.3",40);
        res2 = new Reservation(startDate, rUser, room);
        building = new Building("Faculty");
        room = new LaboratoryRoom (building,"3.2",25);
        res3 = new Reservation(startDate, rUser, room);
        building = new Building("Faculty");
        room = new LaboratoryRoom (building,"2.3",45);
        res4 = new Reservation(startDate, rUser, room);
        building = new Building("Main Library");
        room = new ClassRoom (building,"4.4",50);
        res5 = new Reservation(startDate, rUser, room);
        
        lres.add(res1);
        lres.add(res2);
        lres.add(res3);
        lres.add(res4);
        lres.add(res5);
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
    
    private void getStartExpectations(final String nif, final List<Reservation> lRes) 
            throws IncorrectUserException{
        context.checking(new Expectations(){{
            oneOf(resDao).getAllReservationByUserNif(nif);
            will(returnValue(lRes));
        }});
    }
    
    private void getExpectationsWithRoomNbAndBuilding(
                                                final String roomNb, 
                                                final String building, 
                                                final Room roomId) 
                throws IncorrectUserException, 
                IncorrectBuildingException, 
                IncorrectRoomException{
        context.checking(new Expectations(){{
            oneOf(spaDao).getRoomByNbAndBuilding(with(equal(roomNb)),
                                                 with(equal(building)));
            will(returnValue(roomId));
        }});
    }
    
    private void getExpectationsWithRoomType(final String roomType,Reservation res) 
                throws IncorrectUserException,
                IncorrectRoomException,
                IncorrectTypeException{
        final List<Room> rooms = new ArrayList<>();
        rooms.add(res.getRoom());
        context.checking(new Expectations(){{
            oneOf(spaDao).getAllRoomsOfOneType(roomType);
            will(returnValue(rooms));
        }});
    }
    
}
