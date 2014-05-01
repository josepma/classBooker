/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.entity.Building;
import org.classBooker.entity.MeetingRoom;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    //EntityTransaction transaction;
    //Query query;
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
        
        em.getTransaction().begin();
        em.persist(user);
        em.persist(room);
        em.persist(building);
        em.getTransaction().commit();
        
    }
    
    @After
    public void tearDown() throws Exception {
        
        em = rDao.getEm();
        if (em.isOpen()) em.close();
        
        em = getEntityManager();
        em.getTransaction().begin();
        
        Query query  = em.createQuery("DELETE FROM RESERVATION");
        Query query2 = em.createQuery("DELETE FROM USER");
        Query query3 = em.createQuery("DELETE FROM ROOM");
        Query query4 = em.createQuery("DELETE FROM BUILDING");
        int deleteRecords = query.executeUpdate();
        deleteRecords = query2.executeUpdate();
        deleteRecords = query3.executeUpdate();
        deleteRecords = query4.executeUpdate();
              
        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");
    }

    /**
     * Test of addReservation method, of class ReservationDAOImpl.
     */
    @Test
    public void testAddReservationOfReservation() throws Exception {
        
        rDao.addReservation(reservation);
        
        Reservation reservationDB = getReservationFromDB(
                                            reservation.getReservationId());
        
        assertEquals(reservation, reservationDB);
        
    }
    
    //@Test
    public void testAddReservationByAttribute() throws Exception {
        
        rDao.addReservation("47658245M", "10", 
                            "testBuilding", new DateTime(2014, 05, 11, 12, 00));
                
        Reservation reservationDB = getReservationFromDB(
                                            reservation.getReservationId());
        
        assertEquals(reservation, reservationDB);
        
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
    
}
