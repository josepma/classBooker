/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import org.classbooker.service.ReservationMgrServiceImpl;
import org.classbooker.dao.ReservationDAO;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.exception.IncorrectBuildingException;
import org.classbooker.dao.exception.IncorrectReservationException;
import org.classbooker.dao.exception.IncorrectRoomException;
import org.classbooker.dao.exception.IncorrectTimeException;
import org.classbooker.dao.exception.IncorrectUserException;
import org.classbooker.entity.Building;
import org.classbooker.entity.ClassRoom;
import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;
import org.classbooker.entity.StaffAdmin;
import org.classbooker.entity.User;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;
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
    ReservationMgrServiceImpl rmgr;
    ReservationDAO rDao;
    SpaceDAO sDao;
    UserDAO uDao;
    
    final String nif="12345";
    final long roomId = 111;
    final DateTime date = new DateTime().plusDays(2).withMinuteOfHour(0);
    final DateTime dateBeforeNow = new DateTime(2014,3,2,12,0);
    final DateTime dateIncorrectFormatOfMinutes = new DateTime(2015,3,2,12,12);
    final Building building = new Building("nameBuilding");
    final Room room = new ClassRoom(building, nif, 30);
    final ReservationUser professor = new ProfessorPas();
    final User staff = new StaffAdmin();
    final Reservation reservation = new Reservation();
    final long reservationId = 123;
    
    @Before
    public void setUp(){
        
        rmgr = new ReservationMgrServiceImpl();  
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
        checkUserAndRoomExpectations(room,professor); 
        rmgr.makeReservationBySpace(roomId, nif, dateBeforeNow);
        
        
    }
    
    @Test(expected = IncorrectTimeException.class)
    public void testIncorrectFormatOfMinute() throws Exception {
        checkUserAndRoomExpectations(room,professor); 
        rmgr.makeReservationBySpace(roomId, nif, dateIncorrectFormatOfMinutes);
        
        
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
    
    //find reservationById
    @Test(expected = IncorrectReservationException.class)
    public void testCannotFindReservationById()throws Exception{
        findReservationExpectations(null);
        rmgr.findReservationById(reservationId);
    }
    
    @Test
    public void testFindReservationById()throws Exception{
        findReservationExpectations(reservation);
        rmgr.findReservationById(reservationId);
    }
    
    //find reservationById(Space and Date)
    
    @Test (expected = IncorrectBuildingException.class)
    public void testCannotFindReservationByBuilding() throws Exception{
        checkFindReservationByBuildingRoomDateExpectations(null,room,null);
        rmgr.findReservationById(building.getBuildingName(), room.getNumber(), date);
    }
    
    @Test(expected = IncorrectRoomException.class)
    public void testCannotFindReservationByRoom() throws Exception{
        checkFindReservationByBuildingRoomDateExpectations(building,null,null);
        rmgr.findReservationById(building.getBuildingName(), room.getNumber(), date);
    }
    
    @Test(expected = IncorrectTimeException.class)
    public void testCannotFindReservationByDateIsBeforeNow() throws Exception{
        checkFindReservationByBuildingRoomDateExpectations(building,room,null);
        rmgr.findReservationById(building.getBuildingName(), room.getNumber(), dateBeforeNow);
    }
    
    @Test(expected = IncorrectTimeException.class)
    public void testCannotFindReservationByDateIncorrectFormatOfMinutes() throws Exception{
        checkFindReservationByBuildingRoomDateExpectations(building,room,null);
        rmgr.findReservationById(building.getBuildingName(), room.getNumber(), dateIncorrectFormatOfMinutes);
    }
    
    @Test
    public void testFindReservationByBuildingRoomDate() throws Exception{
        checkFindReservationByBuildingRoomDateExpectations(building,room,reservation);
        rmgr.findReservationById(building.getBuildingName(), room.getNumber(), date);
    }
    
    @Test
    public void testNotFindReservationByBuildingRoomDate() throws Exception{
        checkFindReservationByBuildingRoomDateExpectations(building,room,null);
        rmgr.findReservationById(building.getBuildingName(), room.getNumber(), date);
    }

    
    private void makeReservationExpectations(final Room room, final User user,final Reservation reservation) throws IncorrectBuildingException, IncorrectRoomException {
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

   private void findReservationExpectations(final Reservation reser) {
        context.checking(new Expectations(){{ 
            oneOf(rDao).getReservationById(reservationId);will(returnValue(reser));
         }});  
    }
   

    private void checkFindReservationByBuildingRoomDateExpectations(final Building b, final Room r, final Reservation res) 
                                                                                    throws Exception{
        context.checking(new Expectations(){{ 
            oneOf(sDao).getBuildingByName(building.getBuildingName());will(returnValue(b));
            allowing(sDao).getRoomByNbAndBuilding(room.getNumber(),building.getBuildingName());will(returnValue(r));
            allowing(rDao).getReservationByDateRoomBulding(date, room.getNumber(), building.getBuildingName());will(returnValue(res));
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
