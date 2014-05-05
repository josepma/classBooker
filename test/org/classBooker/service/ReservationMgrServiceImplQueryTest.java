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
import org.joda.time.DateTime;

/**
 *
 * @author Santiago Hijazo i Mauro Churata
 */

@RunWith(JMock.class)
public class ReservationMgrServiceImplQueryTest {
    
    Mockery context = new JUnit4Mockery();
    //ReservationUser Ruser;
    ReservationDAO resDao;
    DateTime dateTime;
    Reservation reser;
    Sequence seq;
    ReservationMgrServiceImplQuery rmsQ;
   
   
   
    //@Before
    public void setup(){
        //Ruser = context.mock(ReservationUser.class);
        rmsQ = new ReservationMgrServiceImplQuery();
        resDao = context.mock(ReservationDAO.class);
        reser = context.mock(Reservation.class);
        dateTime = new DateTime(1,2,3,4,5);
        seq = context.sequence("seq");
    }
   
    //@Test
    
    // Pendiente con ribo
    public void noReservationbyUserID() throws Exception{
     
      final long id = 123;  
       
      context.checking(new Expectations(){{
        oneOf(resDao).getReservationById(id);
        will(returnValue(null));
      }});
      
     
    }
   
    //@Test
    public void oneReservationByUserID(){
       
       final long id = 123;
       
       context.checking(new Expectations(){{
        oneOf(resDao).getReservationById(id);
        will (returnValue(reser));
       }});
       
    }
    
    @Test
    public void oneReservationByDateRoomBuilding() throws Exception{
       
       final String roomId = "2.4";
       final String building = "EPS";
       
       context.checking(new Expectations(){{
        oneOf(resDao).getReservationByDateRoomBulding(dateTime, roomId, building);
        will (returnValue(reser));
       }});
       
       rmsQ.getReservations(roomId);
       
    }
    
    @Test
    public void noReservationByUserNif() throws Exception{
     
      final String id = "Josep";  
       
      context.checking(new Expectations(){{
        oneOf(resDao).getAllReservationByUserNif(id);
        will(returnValue(null));
      }});
     
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
