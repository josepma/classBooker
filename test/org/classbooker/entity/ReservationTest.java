/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.entity;

import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.MeetingRoom;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rds2
 */
public class ReservationTest {

    Reservation instance;
    DateTime dateTime;
    ReservationUser rUser;
    Room room;

    public ReservationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        dateTime = new DateTime(2014, 4, 25, 10, 25);
        rUser = new ProfessorPas();
        room = new MeetingRoom();

        instance = new Reservation(dateTime, rUser, room);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getReservationId method, of class Reservation.
     */
    @Test
    public void testGetReservationId() {

        long expResult = 0L;
        long result = instance.getReservationId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReservationTime method, of class Reservation.
     */
    //@Test
    public void testGetReservationDateTime() {
        DateTime expResult = dateTime;
        DateTime result = instance.getReservationDate();
       // assertEquals(expResult, result);
        
    }

    /**
     * Test of setReservationTime method, of class Reservation.
     */
    //@Test
    public void testSetReservationDateTime() {
        DateTime dt = new DateTime();
        instance.setReservationDate(dt);
        assertEquals(dt, instance.getReservationDate());

    }

    /**
     * Test of getrUser method, of class Reservation.
     */
    @Test
    public void testGetrUser() {

        ReservationUser expResult = rUser;
        ReservationUser result = instance.getrUser();
        assertEquals(expResult, result);
    }

    /**
     * Test of setrUser method, of class Reservation.
     */
    @Test
    public void testSetrUser() {

        ReservationUser expResult = new ProfessorPas("45654565k", "asdfsad@gmail.com", "pep");
        instance.setrUser(expResult);
        assertEquals(expResult, instance.getrUser());

    }

    /**
     * Test of getRoom method, of class Reservation.
     */
    @Test
    public void testGetRoom() {

        Room expResult = room;
        Room result = instance.getRoom();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRoom method, of class Reservation.
     */
    @Test
    public void testSetRoom() {
        Room expResult = new MeetingRoom();
        instance.setRoom(expResult);
        assertEquals(expResult, instance.getRoom());
    }

}
