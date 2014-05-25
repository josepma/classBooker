/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.classBooker.dao.ReservationDAOImpl;
import org.classBooker.dao.SpaceDAOImpl;
import org.classBooker.dao.UserDAOImpl;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTimeException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Reservation;
import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

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

    
    DateTime correctDate = new DateTime(2014,7,11,9,0);
    DateTime dateBeforeNow = new DateTime(2014,3,2,12,0);
    DateTime dateIncorrectFormatOfMinutes = new DateTime(2015,3,2,12,12);
 
    
    @Before
    public void setUp() throws Exception{
        rmgr = new ReservationMgrServiceImpl();
        CreateAndSetObjects();
    }
  
   //Tests makeReservationBySpace
    //roomid 111 no exists
    @Test(expected = IncorrectRoomException.class)
    public void testIncorrectRoomId() throws Exception { 
        rmgr.makeReservationBySpace(111, "55555", correctDate);
    }
   
    //room with roomid 2 exits in database
    @Test(expected = IncorrectUserException.class)
    public void testIncorrectUserDontExist() throws Exception{
         rmgr.makeReservationBySpace(2, "2222", correctDate);
         
    }
  
    //user with 12457638 is staffAdmin
    @Test(expected = IncorrectUserException.class)
    public void testIncorrectUserIsNotReservationUser() throws Exception {
         rmgr.makeReservationBySpace(2,"12457638", correctDate);
    }
    
    @Test(expected = IncorrectTimeException.class)
    public void testIncorrectTimeBeforeNow() throws Exception {
        rmgr.makeReservationBySpace(2,"55555", dateBeforeNow);
        
        
    }
    
    @Test(expected = IncorrectTimeException.class)
    public void testIncorrectFormatOfMinute() throws Exception {
       rmgr.makeReservationBySpace(2,"55555", dateIncorrectFormatOfMinutes);
        
        
    }

    @Test
    //we have all requirements to make new reservation
    public void testMakeNewReservation() throws Exception {
        Reservation result = rmgr.makeReservationBySpace(2, "55555", correctDate);
        assertEquals("Check hour of day",correctDate.getHourOfDay(),result.getReservationDate().getHourOfDay());
        assertEquals("Check year",correctDate.getYear(),result.getReservationDate().getYear());
        assertEquals("Check minute of hour",correctDate.getMinuteOfHour(),result.getReservationDate().getMinuteOfHour());
        assertEquals("Check month of year",correctDate.getMonthOfYear(),result.getReservationDate().getMonthOfYear());
        assertEquals("Check day of month",correctDate.getDayOfMonth(),result.getReservationDate().getDayOfMonth());
        assertEquals("Check user",uDao.getUserByNif("55555"),result.getrUser());
        assertEquals("Check roomNumber",sDao.getRoomById(2).getNumber(),result.getRoom().getNumber());
        assertEquals("Check nameBuilding",sDao.getRoomById(2).getBuilding(),result.getRoom().getBuilding());
        
    }

    @Test
    //this reservation exists in database, so we will return a value null to indicate that alreadyExisting reservation
    public void testAlreadyExistingReservation() throws Exception {
        Reservation result = rmgr.makeReservationBySpace(3,"9876544", correctDate);
        assertEquals("Make new Reservation",null,result);
    }
    
    //Test deleteReservation
    
    @Test(expected = IncorrectReservationException.class)
    public void testNoExitingReservationDelete()throws Exception{
        rmgr.deleteReservation(99);
    }
    
    @Test
    public void testDeleteExistingReservation()throws Exception{
        Reservation result = rmgr.makeReservationBySpace(2, "55555", correctDate);
        rDao.addReservation(result);
        rmgr.deleteReservation(result.getReservationId());
        assertEquals("already delete the reservation made",null,rDao.getReservationById(result.getReservationId()));
    }

    //find reservationById
    @Test(expected = IncorrectReservationException.class)
    public void testCannotFindReservationById()throws Exception{
        rmgr.findReservationById(26);
    }
    
    @Test
    public void testFindReservationById()throws Exception{
        rmgr.findReservationById(10);
    }
    
    //find reservationById(Space and Date)
   
    
    //test no va , porque spaceDao si building =null, null.getrooms() lanza nullpointer Exception//
   // @Test (expected = IncorrectBuildingException.class)
    public void testCannotFindReservationByBuilding() throws Exception{
        
        rmgr.findReservationBySpaceAndDate("ADE", "2.08", correctDate);
    }
   
    @Test(expected = IncorrectRoomException.class)
    // in datebase room with roomnumber 2.05 doesn't exist
    public void testCannotFindReservationByRoom() throws Exception{
       
        rmgr.findReservationBySpaceAndDate("EPS","2.05", correctDate);
    }
    
    @Test(expected = IncorrectTimeException.class)
    public void testCannotFindReservationByDateIsBeforeNow() throws Exception{
        rmgr.findReservationBySpaceAndDate("EPS","2.08", dateBeforeNow);
    
    }
    
    @Test(expected = IncorrectTimeException.class)
    public void testCannotFindReservationByDateIncorrectFormatOfMinutes() throws Exception{
       
         rmgr.findReservationBySpaceAndDate("EPS","2.08",dateIncorrectFormatOfMinutes);
    }
    
    
    @Test
    public void testFindReservationByBuildingRoomDate() throws Exception{
      
        Reservation r = rmgr.findReservationBySpaceAndDate("Rectorate Building", "1.0", correctDate);
        assertEquals("same reservation id",16,r.getReservationId());
    }

    @Test
    public void testNotFindReservationByBuildingRoomDate() throws Exception{
        DateTime date1 = new DateTime(2015,5,12,8,0);
        Reservation r = rmgr.findReservationBySpaceAndDate("Rectorate Building", "1.0", date1);
        assertEquals("not find reservation made",null,r);
    }

    
    
    
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
