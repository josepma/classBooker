/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classBooker.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.classBooker.dao.ReservationDAO;
import org.classBooker.dao.SpaceDAO;
import org.classBooker.dao.UserDAO;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectReservationException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTypeException;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.entity.Building;
import org.classBooker.entity.ClassRoom;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.classBooker.util.ReservationResult;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 *
 * @author abg7
 */
@RunWith(JMock.class)
public class ReservationMgrServiceImplAcceptationTest {

    Mockery context = new JUnit4Mockery();
    ReservationMgrServiceImplAcceptation rms;
    SpaceDAO sDao;
    UserDAO uDao;
    ReservationDAO rDao;
    Room room;
    Building building;
    DateTime dateTime;
    ReservationUser rUser;
    Reservation reservation;
    List<Room> lRooms;
    ReservationResult rResult;

    public ReservationMgrServiceImplAcceptationTest() {
    }

    @Before
    public void setUp() {
        rms = new ReservationMgrServiceImplAcceptation();
        sDao = context.mock(SpaceDAO.class, "sDao");
        uDao = context.mock(UserDAO.class, "uDao");
        rDao = context.mock(ReservationDAO.class, "rDao");

        building = new Building("B1");
        room = new ClassRoom(building, "2.10", 100);
        dateTime = new DateTime(1, 2, 3, 4, 5);
        rUser = new ProfessorPas();
        reservation = new Reservation(dateTime, rUser, room);
        rResult = new ReservationResult(reservation, rUser);
        room.setReservation(reservation);
        
        rms.setSpaceDao(sDao);
        rms.setReservationDao(rDao);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void suggestedSpacesAssertRequirements() throws Exception {

        lRooms = new ArrayList<>();
        lRooms.add(room);

        context.checking(new Expectations() {
            {
                oneOf(sDao).getRoomByNbAndBuilding(room.getNumber(), building.getBuildingName());
                will(returnValue(room));
                oneOf(sDao).getAllRoomsByTypeAndCapacity(room.getClass().toString(), room.getCapacity(), building.getBuildingName());
                will(returnValue(lRooms));
                allowing (rDao).getReservationByDateRoomBulding(dateTime, room.getNumber(), building.getBuildingName());
                will(returnValue(null));
            }
        });
        List<Room> suggestedRooms = rms.suggestionSpace(room.getNumber(), building.getBuildingName(), dateTime);
        System.out.println(suggestedRooms);
        if(suggestedRooms.isEmpty()) fail("No spaces suggested.");
        assertSuggestedSpacesRequirements(suggestedRooms);
    }
    
    @Test
    public void notSuggestedSpacesIfRoomsWhichAssertRequirementsAreReserved() throws Exception {

        lRooms = new ArrayList<>();
        lRooms.add(room);

        context.checking(new Expectations() {
            {
                oneOf(sDao).getRoomByNbAndBuilding(room.getNumber(), building.getBuildingName());
                will(returnValue(room));
                oneOf(sDao).getAllRoomsByTypeAndCapacity(room.getClass().toString(), room.getCapacity(), building.getBuildingName());
                will(returnValue(lRooms));
                allowing (rDao).getReservationByDateRoomBulding(dateTime, room.getNumber(), building.getBuildingName());
                will(returnValue(reservation));
            }
        });
        List<Room> suggestedRooms = rms.suggestionSpace(room.getNumber(), building.getBuildingName(), dateTime);
        assertTrue(suggestedRooms.isEmpty());
    }
    
    @Test
    public void notSuggestedSpacesIfNoRoomsAssertRequirements() throws Exception {

        lRooms = new ArrayList<>();
        lRooms.add(room);

        context.checking(new Expectations() {
            {
                oneOf(sDao).getRoomByNbAndBuilding(room.getNumber(), building.getBuildingName());
                will(returnValue(room));
                oneOf(sDao).getAllRoomsByTypeAndCapacity(room.getClass().toString(), room.getCapacity(), building.getBuildingName());
                will(returnValue(new ArrayList<Room>()));
            }
        });
        List<Room> suggestedRooms = rms.suggestionSpace(room.getNumber(), building.getBuildingName(), dateTime);
        assertTrue(suggestedRooms.isEmpty());
    }

    
    @Test
    public void testAcceptReservation() throws Exception{
        context.checking(new Expectations() {
            {
                oneOf(rDao).addReservation(reservation);
            }
        });        
        rms.acceptReservation(reservation);
    }
    @Test
    public void testGetCurrentUserOfDemandedRoom() throws Exception{
        context.checking(new Expectations() {
            {
                oneOf(rDao).getReservationByDateRoomBulding(dateTime, room.getNumber(), building.getBuildingName());
                will(returnValue(reservation));
            }
        });
        ReservationUser ru = rms.getCurrentUserOfDemandedRoom(room.getNumber(), building.getBuildingName(), dateTime);
        assertEquals(reservation.getrUser(), ru);
    }
    
    @Test 
    public void testGetCurrentUserOfIncorrectRoom() throws IncorrectRoomException{
        context.checking(new Expectations() {
            {
                oneOf(rDao).getReservationByDateRoomBulding(dateTime, room.getNumber(), building.getBuildingName());
                will(returnValue(null));
            }
        });
        ReservationUser ru = rms.getCurrentUserOfDemandedRoom(room.getNumber(), building.getBuildingName(), dateTime);
        assertNull(ru);
    }
    //@Test
    public void testCompleteReservation() throws Exception{
        context.checking(new Expectations() {
            {
                
            }
        });
        rms.makeCompleteReservationBySpace("", room.getNumber(), building.getBuildingName(), dateTime);
    }

    private void checkNotReservedRoom(Room room) {
        List<Reservation> reservations = room.getReservations();
        for(Reservation r: reservations){
            assertFalse("Already reserved room in the dateTime.", 
                    dateTime.equals(r.getReservationDate()));
        }
    }
    
    private void assertSuggestedSpacesRequirements(List<Room> rooms){
        for (Room r : rooms) {
            assertEquals("Different room types.", r.getClass(), room.getClass());
            assertTrue("Less capacity than room intended to reserve.",
                    r.getCapacity() >= room.getCapacity());
            checkNotReservedRoom(r);
        }
    }
}
