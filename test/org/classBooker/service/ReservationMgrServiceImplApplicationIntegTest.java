/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.classBooker.dao.ReservationDAO;
import org.classBooker.dao.ReservationDAOImpl;
import org.classBooker.dao.SpaceDAO;
import org.classBooker.dao.SpaceDAOImpl;
import org.classBooker.dao.UserDAO;
import org.classBooker.dao.UserDAOImpl;
import org.classBooker.dao.exception.IncorrectBuildingException;
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
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
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

public class ReservationMgrServiceImplApplicationIntegTest {
    ReservationMgrServiceImpl rmgr;
    ReservationDAOImpl rDao;
    SpaceDAOImpl sDao;
    UserDAOImpl uDao;
    EntityManager em;
    
    String nif="12345";
    long roomId = 111;
    DateTime date = new DateTime().plusDays(2).withMinuteOfHour(0);
    DateTime dateBeforeNow = new DateTime(2014,3,2,12,0);
    DateTime dateIncorrectFormatOfMinutes = new DateTime(2015,3,2,12,12);
    Building building = new Building("nameBuilding");
    Room room = new ClassRoom(building, nif, 30);
    ReservationUser professor = new ProfessorPas();
    User staff = new StaffAdmin();
    Reservation reservation = new Reservation();
    long reservationId = 123;
    
    @Before
    public void setUp() throws Exception{
        
        rmgr = new ReservationMgrServiceImpl();  
        CreateAndSetObjects();
        
    }
    //Tests makeReservationBySpace
    @Test(expected = IncorrectRoomException.class)
    public void testIncorrectRoomId() throws Exception {
        rmgr.makeReservationBySpace(roomId, nif, date);
    }
    
    /*@Test(expected = IncorrectUserException.class)
    public void testIncorrectUserDontExist() throws Exception {
        //checkUserAndRoomExpectations(room,null);
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
        checkFindReservationByBuildingRoomDateExpectations(null,room);
        rmgr.findReservationById(building.getBuildingName(), room.getNumber(), date);
    }
    
    @Test(expected = IncorrectRoomException.class)
    public void testCannotFindReservationByRoom() throws Exception{
        checkFindReservationByBuildingRoomDateExpectations(building,null);
        rmgr.findReservationById(building.getBuildingName(), room.getNumber(), date);
    }
    
    @Test(expected = IncorrectTimeException.class)
    public void testCannotFindReservationByDateIsBeforeNow() throws Exception{
        checkFindReservationByBuildingRoomDateExpectations(building,room);
        rmgr.findReservationById(building.getBuildingName(), room.getNumber(), dateBeforeNow);
    }
    
    @Test(expected = IncorrectTimeException.class)
    public void testCannotFindReservationByDateIncorrectFormatOfMinutes() throws Exception{
        checkFindReservationByBuildingRoomDateExpectations(building,room);
        rmgr.findReservationById(building.getBuildingName(), room.getNumber(), dateIncorrectFormatOfMinutes);
    }
    
    @Test
    public void testFindReservationByBuildingRoomDate() throws Exception{
        checkFindReservationByBuildingRoomDateExpectations(building,room);
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
   

    private void checkFindReservationByBuildingRoomDateExpectations(final Building b, final Room r) 
                                                                                    throws Exception{
        context.checking(new Expectations(){{ 
            oneOf(sDao).getBuildingByName(building.getBuildingName());will(returnValue(b));
            allowing(sDao).getRoomByNbAndBuilding(room.getNumber(),building.getBuildingName());will(returnValue(r));
            allowing(rDao).getReservationByDateRoomBulding(date, room.getNumber(), building.getBuildingName());
        }});
    }*/
 
    private void CreateAndSetObjects() throws Exception {
        
        
        rDao = new ReservationDAOImpl();
        sDao = new SpaceDAOImpl();
        uDao = new UserDAOImpl();
        em = getEntityManager();
        
        sDao.setEm(em);
        uDao.setEntityManager(em);
        rDao.setEm(em);
        rDao.setsDao(sDao);
        rDao.setuDao(uDao);
        
        rmgr.setReservationDao(rDao);
        rmgr.setSpaceDao(sDao);
        rmgr.setUserDao(uDao);
    }
    
        private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBookerIntegration");
        return emf.createEntityManager();  
    }

    
}
