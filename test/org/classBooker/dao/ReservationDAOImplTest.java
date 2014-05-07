/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.classBooker.dao.exception.*;
import org.classBooker.entity.*;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author msf7
 */
public class ReservationDAOImplTest {
    
    Mockery context = new JUnit4Mockery();
    ReservationDAOImpl rDao;
    EntityManager em;
    Room room;
    Building building;
    ReservationUser user;
    Reservation reservation;

    
    
    public ReservationDAOImplTest() {
    }
    
    
    @Before
    public void setUp() throws Exception {
        rDao = new ReservationDAOImpl();
        
        // data base
        em = getEntityManager();
        rDao.setEm(em);   
        
        // entity objects
        building = new Building("testBuilding");
        room = new MeetingRoom();
        room.setBuilding(building);
        room.setNumber("10");
        user = new ProfessorPas("47658245M", "random@professor.ly", "Manolo");
        reservation = new Reservation(new DateTime(2014, 05, 11, 12, 00), user, room);
        
        addDB(user,room,building);
        
    }
    
    @After
    public void tearDown() throws Exception {
        
        em = rDao.getEm();
        if (em.isOpen()) em.close();
        
        em = getEntityManager();
        em.getTransaction().begin();
        
        Query query  = em.createQuery("DELETE FROM Reservation");
        Query query2 = em.createQuery("DELETE FROM User");
        Query query3 = em.createQuery("DELETE FROM Room");
        Query query4 = em.createQuery("DELETE FROM Building");
        int deleteRecords = query.executeUpdate();
        deleteRecords = query2.executeUpdate();
        deleteRecords = query3.executeUpdate();
        deleteRecords = query4.executeUpdate();
              
        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");
    }
    
    
    @Test
    public void testAddReservationByReservation() throws Exception {
        
        rDao.addReservation(reservation);
        
        Reservation reservationDB = getReservationFromDB(
                                            reservation.getReservationId());
        
        checkReservation(reservation, reservationDB);
        
    }
    
    @Test(expected = IncorrectUserException.class)
    public void testAddReservationNotExistUser() throws Exception {
        ReservationUser user1 = new ProfessorPas("4765665M", "random@professor.ly", "Manolo");
        Reservation reservation1 = new Reservation(new DateTime(2014, 05, 11, 13, 00), user1, room);
        rDao.addReservation(reservation1);
    }
    
    @Test(expected = IncorrectRoomException.class)
    public void testAddReservationNotExistRoom() throws Exception {
        Room room1 = new MeetingRoom();
        room1.setBuilding(building);
        room1.setNumber("10");
        Reservation reservation1 = new Reservation(new DateTime(2014, 05, 11, 13, 00), user, room1);
        rDao.addReservation(reservation1);
    }
    
    @Test
    public void testAddReservationRoomAndDateIsReservet() throws Exception {
        ReservationUser user1 = new ProfessorPas("4765665M", "random@professor.ly", "Manolo");
        Reservation reservation1 = new Reservation(new DateTime(2014, 05, 11, 12, 00), user1, room);
        addDB(user1);
        rDao.addReservation(reservation);
        rDao.addReservation(reservation1);
    }
    
    @Test
    public void testAddReservationByAttribute() throws Exception {
        
        Reservation rtest = rDao.addReservation("47658245M", "10", 
                            "testBuilding", new DateTime(2014, 05, 11, 12, 00));
                
        Reservation reservationDB = getReservationFromDB(
                                            rtest.getReservationId());
        
        checkReservation(rtest, reservationDB);
        
    }
        
    @Test(expected = IncorrectRoomException.class)
    public void testAddReservationByAttributeNotExistRoom() throws Exception {
        
        Reservation rtest = rDao.addReservation("47658245M", "11", 
                            "testBuilding", new DateTime(2014, 05, 11, 12, 00));

    }
    
    @Test(expected = IncorrectBuildingException.class)
    public void testAddReservationByAttributeNotExistBuilding() throws Exception {
        
        Reservation rtest = rDao.addReservation("47658245M", "10", 
                            "NotExistBuilding", new DateTime(2014, 05, 11, 12, 00));
        
    }
    
    @Test(expected = IncorrectUserException.class)
    public void testAddReservationByAttributeNotExistUser() throws Exception {
        
        Reservation rtest = rDao.addReservation("47658205M", "10", 
                            "testBuilding", new DateTime(2014, 05, 11, 12, 00));
        
    }

    /**
     * Test of getReservationById method, of class ReservationDAOImpl.
     */
    //@Test
    public void testGetReservationById() throws Exception {
        
        rDao.addReservation(reservation);
        
        assertEquals(reservation,
                    rDao.getReservationById(reservation.getReservationId()));
    }

    /**
     * Test of getAllReservation method, of class ReservationDAOImpl.
     */
    //@Test
    public void testGetAllReservation() {
        
        rDao.getAllReservation();
    }

    /**
     * Test of getAllReservationByBuilding method, of class ReservationDAOImpl.
     */
    //@Test
    public void testGetAllReservationByBuilding() throws Exception {

    }

    /**
     * Test of getAllReservationByRoom method, of class ReservationDAOImpl.
     */
    //@Test
    public void testGetAllReservationByRoom() throws Exception {

    }

    /**
     * Test of removeReservation method, of class ReservationDAOImpl.
     */
    //@Test
    public void testRemoveReservation() throws Exception {

    }

    /**
     * Test of getAllReservationByUserNif method, of class ReservationDAOImpl.
     */
    //@Test
    public void testGetAllReservationByUserNif() throws Exception {

    }
    
    private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBooker");
        return emf.createEntityManager();  
    }
    
    private Reservation getReservationFromDB(long id) throws Exception{        

        em = getEntityManager();
        em.getTransaction().begin();
        Reservation reservationDB = (Reservation) em.find(Reservation.class, id);
        em.getTransaction().commit();
        em.close();

        return reservationDB; 
    }
    
    private void addDB(Object... argv){
        em.getTransaction().begin();
        for (Object entity : argv) {
            em.persist(entity);
        }
        em.getTransaction().commit();
        
    }
    
    private void checkReservation(Reservation expected,Reservation actual){
        assertEquals(null, expected, actual);
        assertTrue(actual.getRoom().getReservations().contains(expected));
        assertTrue(actual.getrUser().getReservations().contains(expected));
    }
    
}
