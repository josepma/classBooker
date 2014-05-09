/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import org.classBooker.dao.ReservationDAO;
import org.classBooker.dao.SpaceDAO;
import org.classBooker.dao.UserDAO;
import org.classBooker.dao.exception.IncorrectReservationException;
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
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
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
    final DateTime date = new DateTime(2015,5,12,13,0);
    final Building b = new Building("nameBuilding");
    final Room room = new ClassRoom(b, nif, 30);
    final ReservationUser professor = new ProfessorPas();
    final User staff = new StaffAdmin();
    final Reservation reservation = new Reservation();
    
    final long reservationId = 123;
    @Before
    public void setUp(){
        
        rmgr = new ReservationMgrServiceImplApplication();  
        createAndSetMockObjects();
        
    }
    //Tests makeReservationBySpace
    @Test(expected = IncorrectRoomException.class)
    public void testIncorrectRoomId() throws Exception {
        checkUserAndRoomExpectations(null,null);
        rmgr.makeReservationBySpace(roomId, nif, date);
    }
    
     @Test(expected = IncorrectUserException.class)
    public void testIncorrectUserDontExist() throws Exception {
        checkUserAndRoomExpectations(room,null);
        rmgr.makeReservationBySpace(roomId, nif, date);
    }
    
     @Test(expected = IncorrectUserException.class)
    public void testIncorrectUserIsNotReservationUser() throws Exception {
        checkUserAndRoomExpectations(room,staff);
        rmgr.makeReservationBySpace(roomId, nif, date);
    }
    
    @Test(expected = IncorrectTimeException.class)
    public void testIncorrectTimeBeforeNow() throws Exception {
        final DateTime date = new DateTime(2014,3,2,12,0);
        checkUserAndRoomExpectations(room,professor); 
        rmgr.makeReservationBySpace(roomId, nif, date);
        
        
    }
    
    @Test(expected = IncorrectTimeException.class)
    public void testIncorrectFormatOfMinute() throws Exception {
        final DateTime date = new DateTime(2015,3,2,12,12);
        checkUserAndRoomExpectations(room,professor); 
        rmgr.makeReservationBySpace(roomId, nif, date);
        
        
    }
    
    @Test
    public void testMakeNewReservation() throws Exception {
        makeReservationExpectations(room,professor,null);
        Reservation result = rmgr.makeReservationBySpace(roomId, nif, date);
        assertEquals("Check hour of day",date.getHourOfDay(),result.getReservationDate().getHourOfDay());
        assertEquals("Check year",date.getYear(),result.getReservationDate().getYear());
        assertEquals("Check minute of hour",date.getMinuteOfHour(),result.getReservationDate().getMinuteOfHour());
        assertEquals("Check month of year",date.getMonthOfYear(),result.getReservationDate().getMonthOfYear());
        assertEquals("Check day of month",date.getDayOfMonth(),result.getReservationDate().getDayOfMonth());
        assertEquals("Check user",professor,result.getrUser());
        assertEquals("Check roomNumber",room.getNumber(),result.getRoom().getNumber());
        assertEquals("Check nameBuilding",room.getBuilding(),result.getRoom().getBuilding());
    }

    @Test
    public void testAlreadyExistingReservation() throws Exception {
        makeReservationExpectations(room,professor,reservation);
        Reservation result = rmgr.makeReservationBySpace(roomId, nif, date);
        assertEquals("Make new Reservation",null,result);
    }
    
    //Test deleteReservation
    
    @Test(expected = IncorrectReservationException.class)
    public void testNoExitingReservationDelete()throws Exception{
        checkDeleteReservationExpectations(null);
        rmgr.deleteReservation(reservationId);
    }
    
    @Test
    public void testDeleteExistingReservation()throws Exception{
        checkDeleteReservationExpectations(reservation);
        rmgr.deleteReservation(reservationId);
    }
    
    
    
    private void makeReservationExpectations(final Room room, final User user,final Reservation reservation) {
        context.checking(new Expectations(){{ 
            oneOf(sDao).getRoomById(roomId);will(returnValue(room));
            oneOf(uDao).getUserByNif(nif);will(returnValue(user));
            oneOf(rDao).getReservationByDateRoomBulding(date, "12345", "nameBuilding");will(returnValue(reservation));
            
         }});    
    }

    
   
   private void checkUserAndRoomExpectations(final Room room,final User user){
       context.checking(new Expectations(){{ 
            oneOf(sDao).getRoomById(roomId);will(returnValue(room));
            oneOf(uDao).getUserByNif(nif);will(returnValue(user)); 
         }});    
   }
   
   private void checkDeleteReservationExpectations(final Reservation reser) 
                                                    throws IncorrectReservationException{
       context.checking(new Expectations(){{ 
            oneOf(rDao).getReservationById(reservationId);will(returnValue(reser));
            allowing(rDao).removeReservation(reservationId);
         }});    
   }

   
    private void createAndSetMockObjects() {
        rDao = context.mock(ReservationDAO.class);
        sDao = context.mock(SpaceDAO.class);
        uDao = context.mock(UserDAO.class);
        
        rmgr.setReservationDao(rDao);
        rmgr.setSpaceDao(sDao);
        rmgr.setUserDao(uDao);
    }
    
}
