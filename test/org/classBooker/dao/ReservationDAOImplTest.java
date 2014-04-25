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
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.classBooker.entity.Building;
import org.classBooker.entity.MeetingRoom;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
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
    EntityTransaction transaction;
    Query query;
    Room room;
    Building building;
    ReservationUser user;
    Reservation reservation;

    
    
    public ReservationDAOImplTest() {
    }
    
    
    @Before
    public void setUp() {
        rDao = new ReservationDAOImpl();
        
        em = context.mock(EntityManager.class);
        transaction = context.mock(EntityTransaction.class);
        query = context.mock(Query.class);
        
        building = new Building("testBuilding");
        room = new MeetingRoom();
        room.setBuilding(building);
        room.setNumber("10");
        user = new ProfessorPas("47658245M", "random@professor.ly", "Manolo");
        //reservation = new Reservation(new Time(11111), new Date(684), user, room);
        rDao.setEm(em);        
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEm method, of class ReservationDAOImpl.
     */
    @Test
    public void testGetEm() {
        System.out.println("getEm");
        ReservationDAOImpl instance = new ReservationDAOImpl();
        EntityManager expResult = null;
        EntityManager result = instance.getEm();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEm method, of class ReservationDAOImpl.
     */
    @Test
    public void testSetEm() {
        System.out.println("setEm");
        EntityManager em = null;
        ReservationDAOImpl instance = new ReservationDAOImpl();
        instance.setEm(em);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addReservation method, of class ReservationDAOImpl.
     */
    @Test
    public void testAddReservation() throws Exception {
        System.out.println("addReservation");
        Reservation reservation = null;
        ReservationDAOImpl instance = new ReservationDAOImpl();
        instance.addReservation(reservation);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReservationById method, of class ReservationDAOImpl.
     */
    @Test
    public void testGetReservationById() {
        System.out.println("getReservationById");
        String id = "";
        ReservationDAOImpl instance = new ReservationDAOImpl();
        Reservation expResult = null;
        Reservation result = instance.getReservationById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllReservation method, of class ReservationDAOImpl.
     */
    @Test
    public void testGetAllReservation() {
        System.out.println("getAllReservation");
        ReservationDAOImpl instance = new ReservationDAOImpl();
        List<Reservation> expResult = null;
        List<Reservation> result = instance.getAllReservation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllReservationByBuilding method, of class ReservationDAOImpl.
     */
    @Test
    public void testGetAllReservationByBuilding() throws Exception {
        System.out.println("getAllReservationByBuilding");
        String name = "";
        ReservationDAOImpl instance = new ReservationDAOImpl();
        List<Reservation> expResult = null;
        List<Reservation> result = instance.getAllReservationByBuilding(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllReservationByRoom method, of class ReservationDAOImpl.
     */
    @Test
    public void testGetAllReservationByRoom() throws Exception {
        System.out.println("getAllReservationByRoom");
        String id = "";
        ReservationDAOImpl instance = new ReservationDAOImpl();
        List<Reservation> expResult = null;
        List<Reservation> result = instance.getAllReservationByRoom(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeReservation method, of class ReservationDAOImpl.
     */
    @Test
    public void testRemoveReservation() throws Exception {
        System.out.println("removeReservation");
        String id = "";
        ReservationDAOImpl instance = new ReservationDAOImpl();
        instance.removeReservation(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllReservationByUserNif method, of class ReservationDAOImpl.
     */
    @Test
    public void testGetAllReservationByUserNif() throws Exception {
        System.out.println("getAllReservationByUserNif");
        String nif = "";
        ReservationDAOImpl instance = new ReservationDAOImpl();
        List<Reservation> expResult = null;
        List<Reservation> result = instance.getAllReservationByUserNif(nif);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
