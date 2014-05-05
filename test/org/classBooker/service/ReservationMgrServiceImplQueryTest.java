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
import org.classBooker.entity.ReservationUser;
import org.joda.time.DateTime;
import org.junit.Before;

/**
 *
 * @author Santiago Hijazo i Mauro Churata
 */

@RunWith(JMock.class)
public class ReservationMgrServiceImplQueryTest {
    
    Mockery context = new JUnit4Mockery();
//    ReservationUser ser;
    ReservationDAO resDao;
    DateTime dateTime;
    Reservation res;
    Sequence seq;
    ReservationMgrServiceImplQuery rmsQ;
   
   
   
    @Before
    public void setup(){
        //Ruser = context.mock(ReservationUser.class);
        rmsQ = new ReservationMgrServiceImplQuery();
        resDao = context.mock(ReservationDAO.class);
        res = context.mock(Reservation.class);
        dateTime = new DateTime(1,2,3,4,5);
        seq = context.sequence("seq");
        rmsQ = new ReservationMgrServiceImplQuery();
        
        rmsQ.setRes(res);
        rmsQ.setResDao(resDao);
//        rmsQ.setResUser(null);
    }
   
    //@Test // Pendiente con Ribó
    public void noReservationbyUserID() throws Exception{
     
      final long id = 123;  
       
      context.checking(new Expectations(){{
        oneOf(resDao).getReservationById(id);
        will(returnValue(null));
      }});
      
     
    }
   
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
        oneOf(resDao).getReservationByDateRoomBulding(dateTime, roomId, building);
        will (returnValue(res));
       }});
       
       rmsQ.getReservations(roomId);
       
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
    public void MoreReservationByUserNif() throws IncorrectUserException {
       
        final List <Reservation> lreser = new ArrayList<Reservation>();
        final String name = "Pepe";
        
        context.checking(new Expectations(){{
            oneOf(resDao).getAllReservationByUserNif(name);
            will(returnValue(lreser));
            
        }});
        
    }
    
}
