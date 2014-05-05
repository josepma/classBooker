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
 * @author Santiago Hijazo i Mauro Churata
 */

@RunWith(JMock.class)
public class ReservationMgrServiceImplQueryTest {
    
    Mockery context = new JUnit4Mockery();
    ReservationUser rUser;
    ReservationDAO resDao;
    DateTime dateIni;
    DateTime dateFi;
    Reservation res;
    Room roomNb;
    Building buildingName;
    Sequence seq;
    ReservationMgrServiceImplQuery rmsQ;
   
   
   
    @Before
    public void setup(){
        
        resDao = context.mock(ReservationDAO.class);
        rUser = new ProfessorPas("12345678","Pepe@gmail.com","Pepe");
        res = new Reservation();
        dateIni = new DateTime(1,2,3,4,5);
        dateFi = new DateTime(1,2,3,4,5);
        buildingName = new Building("EPS");
        roomNb = new ClassRoom (buildingName,"2",50);
        seq = context.sequence("seq");
        rmsQ = new ReservationMgrServiceImplQuery();
        
        rmsQ.setRes(res);
        rmsQ.setResDao(resDao);
        rmsQ.setBuildingName(buildingName);
        rmsQ.setRoomNb(roomNb);
        rmsQ.setResUser(rUser);
    }
    
    @Test 
    public void noReservationbyUser() throws Exception{
      
      final List <Reservation> lreser = new ArrayList<Reservation>();
       
      context.checking(new Expectations(){{
        oneOf(rUser).getReservations();
        will(returnValue(lreser));
      }});
      
      List <Reservation> expected = rmsQ.getReservationsByNif(rUser.getNif());
      assertEquals("The Same Reservations",lreser,expected);
    }
   /*
    //@Test // Pendiente con Ribó
    public void oneReservationByUserID(){
       
       final long id = 123;
       
       context.checking(new Expectations(){{
        oneOf(resDao).getReservationById(id);
        will (returnValue(res));
       }});
       
    }
    
    @Test
    public void oneReservationByDateRoomBuilding() throws Exception{
       
       final String roomId = "2.4";
       final String building = "EPS";
       
       context.checking(new Expectations(){{
        //oneOf(resDao).getReservationByDateRoomBulding(dateTime, roomId, building);
        will (returnValue(res));
       }});
       
       Reservation expected = rmsQ.getReservation(dateTime, roomId, building);
       assertEquals("Reservation already done",expected,res);
    }
    
    //@Test
    public void noReservationByUserNif() throws Exception{
     
      final String id = "Josep";  
       
      context.checking(new Expectations(){{
        oneOf(resDao).getAllReservationByUserNif(id);
        will(returnValue(null));
      }});
     rmsQ.getReservations(id);
     
//     assertEquals("Error conversion", null, rmsQ.getReservations(id));
    }
           
    @Test
    public void MoreReservationByUserNif() throws Exception {
       
        final List <Reservation> resList = new ArrayList<Reservation>();
        final String building = "EPS";
        context.checking(new Expectations(){{
            oneOf(resDao).getAllReservationByBuilding(building);
            will(returnValue(resList));
        }});
        List<Reservation> expected = rmsQ.getReservations(building);
        assertEquals("Reservation already done",expected,resList);
    }
    */
}
