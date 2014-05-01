/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import org.classBooker.dao.ReservationDAO;
import org.classBooker.dao.SpaceDAO;
import org.classBooker.dao.UserDAO;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTimeException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.ClassRoom;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.classBooker.entity.StaffAdmin;
import org.classBooker.entity.User;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author aba8
 */
@RunWith(JMock.class)
public class ReservationMgrServiceImplApplicationTest {
    Mockery context = new JUnit4Mockery();
    ReservationMgrServiceImplApplication rmgr;
    ReservationDAO rDao;
    SpaceDAO sDao;
    UserDAO uDao;
    final String nif="12345";
    final long roomId = 111;
    final DateTime date = new DateTime();
    final Building b = new Building("nameBuilding");
    final Room room = new ClassRoom(b, nif, 30);
    final ReservationUser professor = new ProfessorPas();
    final User staff = new StaffAdmin();
    final Reservation reservation = new Reservation();
    
    @Before
    public void setUp(){
        rmgr = new ReservationMgrServiceImplApplication();
        rDao = context.mock(ReservationDAO.class);
        sDao = context.mock(SpaceDAO.class);
        uDao = context.mock(UserDAO.class);
        
        rmgr.setReservationDao(rDao);
        rmgr.setSpaceDao(sDao);
        rmgr.setUserDao(uDao);
      
    }
    
    @Test(expected = IncorrectRoomException.class)
    public void testIncorrectRoomId() throws Exception {
        makeReservationExpectations(null,null,null);
        rmgr.makeReservationBySpace(roomId, nif, date);
    }
    
     @Test(expected = IncorrectUserException.class)
    public void testIncorrectUserDontExist() throws Exception {
        makeReservationExpectations(room,null,null);
        rmgr.makeReservationBySpace(roomId, nif, date);
    }
    
     @Test(expected = IncorrectUserException.class)
    public void testIncorrectUserIsNotReservationUser() throws Exception {
        makeReservationExpectations(room,staff,null);
        rmgr.makeReservationBySpace(roomId, nif, date);
    }
    
    //@Test(expected = IncorrectTimeException.class)
    public void testIncorrectTimeBeforeNow() throws Exception {
       
        context.checking(new Expectations(){{ 
            allowing(sDao).getRoomById(roomId);will(returnValue(room));
            allowing(uDao).getUserByNif(nif);will(returnValue(professor));
            //oneOf(dTime).isBeforeNow();will(returnValue(true));
            
         }});    
        rmgr.makeReservationBySpace(roomId, nif, date);
    }
    
    
    
    @Test
    public void testReservationAlreadyDone() throws Exception {
        makeReservationExpectations(room,professor,reservation);
        Reservation result = rmgr.makeReservationBySpace(roomId, nif, date);
        assertEquals("Reservation already done",result,null);
    }
    
    @Test
    public void testMakeNewReservation() throws Exception {
        makeReservationExpectations(room,professor,null);
        Reservation result = rmgr.makeReservationBySpace(roomId, nif, date);
        assertEquals("Make new Reservation",result,new Reservation(date,professor,room));
    }

    private void makeReservationExpectations(final Room room, final User user,final Reservation reservation) {
        context.checking(new Expectations(){{ 
            allowing(sDao).getRoomById(roomId);will(returnValue(room));
            allowing(uDao).getUserByNif(nif);will(returnValue(user));
            allowing(rDao).getReservationByDateRoomBulding(date, "12345", "nameBuilding");will(returnValue(reservation));
            
         }});    
    }

   
    
}
