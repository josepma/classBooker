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
import static org.jmock.Expectations.any;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.runner.RunWith; 
import org.jmock.Sequence;
import java.util.List;
import java.util.ArrayList;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.ClassRoom;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
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
        resDao = context.mock(ReservationDAO.class);
        rmsQ.setResDao(resDao);
        seq = context.sequence("seq");       
        makingSomeReservations();
    }
    
    @Test 
    public void noReservationsWithoutFiltered() throws Exception{
      
      context.checking(new Expectations(){{
        oneOf(resDao).getAllReservationByUserNif("12345678");
        will(returnValue(lres));
      }});
      List <Reservation> tested = rmsQ.getReservationsByNif("12345678");
      
      assertEquals("Error",0,tested.size());
    }
 
    @Test 
    public void allReservationsWithoutFiltered() throws Exception{
      lres.add(res1);
      lres.add(res2);
      lres.add(res3);
      lres.add(res4);
      lres.add(res5);
      
      context.checking(new Expectations(){{
        oneOf(resDao).getAllReservationByUserNif("12345678");
        will(returnValue(lres));
      }});
      List <Reservation> tested = rmsQ.getReservationsByNif("12345678");
      
      assertEquals("Error",res1,tested.get(0));
      assertEquals("Error",res2,tested.get(1));
      assertEquals("Error",res3,tested.get(2));
      assertEquals("Error",res4,tested.get(3));
      assertEquals("Error",res5,tested.get(4));
    }
    
    @Test 
    public void noReservationFilteredExist() throws Exception{
      
      final List <Reservation> lreser = new ArrayList<>();
      
      searchReservationsByFields("01234567",null,null,null,"0",0,null);
       
      context.checking(new Expectations(){{
        oneOf(resDao).getAllReservationByUserNif(nif);
        will(returnValue(lreser));
      }});
      
      List <Reservation> expected = rmsQ.getFilteredReservation(nif,
                                                                startD,
                                                                endD,
                                                                buildingName,
                                                                roomNb,
                                                                capacity,
                                                                roomType);
      assertEquals("Error",expected,lres);
    }
    
    //@Test 
    public void noReservationExistBis() throws Exception{
      
      final List <Reservation> lreser = new ArrayList<>();
      lreser.add(new Reservation());
      final String nif ="01234567";
       
      context.checking(new Expectations(){{
        oneOf(resDao).getAllReservationByUserNif(nif);
        will(returnValue(lreser));
      }});
      
      List <Reservation> expected = rmsQ.getReservationsByNif(nif);
      assertEquals("Error",expected.get(0),lres.get(0));
    }

    private void makingSomeReservations(){
        rUser = new ProfessorPas("12345678","Ralph@aus.com","Ralph");
        building = new Building("Rectorate Building");
        room = new ClassRoom (building,"2.3",30);
        startDate = new DateTime(1,2,3,4,0);
        endDate = new DateTime(1,2,3,5,0);
        res1 = new Reservation(startDate, rUser, room);
        building = new Building("Main Library");
        room = new ClassRoom (building,"2.3",40);
        res2 = new Reservation(startDate, rUser, room);
        building = new Building("Faculty of Arctic Engineering");
        room = new ClassRoom (building,"3.2",25);
        res3 = new Reservation(startDate, rUser, room);
        building = new Building("Faculty of Arctic Engineering");
        room = new ClassRoom (building,"2.3",45);
        res4 = new Reservation(startDate, rUser, room);
        building = new Building("Main Library");
        room = new ClassRoom (building,"4.4",50);
        res5 = new Reservation(startDate, rUser, room);
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
