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
     * Test of addReservation method, of class ReservationDAOImpl.
     */
    @Test
    public void testAddReservation() throws Exception {

    }
    
    public void testSearchAndAddReservation() throws Exception {

    }

    /**
     * Test of getReservationById method, of class ReservationDAOImpl.
     */
    @Test
    public void testGetReservationById() {

    }

    /**
     * Test of getAllReservation method, of class ReservationDAOImpl.
     */
    @Test
    public void testGetAllReservation() {

    }

    /**
     * Test of getAllReservationByBuilding method, of class ReservationDAOImpl.
     */
    @Test
    public void testGetAllReservationByBuilding() throws Exception {

    }

    /**
     * Test of getAllReservationByRoom method, of class ReservationDAOImpl.
     */
    @Test
    public void testGetAllReservationByRoom() throws Exception {

    }

    /**
     * Test of removeReservation method, of class ReservationDAOImpl.
     */
    @Test
    public void testRemoveReservation() throws Exception {

    }

    /**
     * Test of getAllReservationByUserNif method, of class ReservationDAOImpl.
     */
    @Test
    public void testGetAllReservationByUserNif() throws Exception {

    }
    
}
