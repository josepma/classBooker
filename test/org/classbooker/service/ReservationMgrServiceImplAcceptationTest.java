/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.service;

import org.classbooker.service.ReservationMgrServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.classbooker.dao.ReservationDAO;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.exception.IncorrectBuildingException;
import org.classbooker.dao.exception.IncorrectReservationException;
import org.classbooker.dao.exception.IncorrectRoomException;
import org.classbooker.dao.exception.IncorrectTypeException;
import org.classbooker.dao.exception.IncorrectUserException;
import org.classbooker.entity.Building;
import org.classbooker.entity.ClassRoom;
import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;
import org.classbooker.entity.StaffAdmin;
import org.classbooker.entity.User;
import org.classbooker.util.ReservationResult;
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
    ReservationMgrServiceImpl rms;
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
    String nif;
    int capacity;

    public ReservationMgrServiceImplAcceptationTest() {
    }

    @Before
    public void setUp() {
        rms = new ReservationMgrServiceImpl();
        sDao = context.mock(SpaceDAO.class, "sDao");
        uDao = context.mock(UserDAO.class, "uDao");
        rDao = context.mock(ReservationDAO.class, "rDao");

        nif = "123";
        building = new Building("B1");
        capacity = 100;
        room = new ClassRoom(building, "2.10", capacity);
        dateTime = new DateTime(2015, 2, 3, 4, 0);
        rUser = new ProfessorPas();
        reservation = new Reservation(dateTime, rUser, room);
        rResult = new ReservationResult(reservation);
        room.setReservation(reservation);

        rms.setSpaceDao(sDao);
        rms.setReservationDao(rDao);
        rms.setUserDao(uDao);

        lRooms = new ArrayList<>();
        lRooms.add(room);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void suggestedSpacesAssertRequirements() throws Exception {

        checkSuggestionSpacesExpectations(room, lRooms, null);

        List<Room> suggestedRooms = rms.suggestionSpace(room.getNumber(), building.getBuildingName(), dateTime);
        System.out.println(suggestedRooms);
        if (suggestedRooms.isEmpty()) {
            fail("No spaces suggested.");
        }
        assertSuggestedSpacesRequirements(suggestedRooms);
    }

    @Test
    public void notSuggestedSpacesIfRoomsWhichAssertRequirementsAreReserved() throws Exception {

        checkSuggestionSpacesExpectations(room, lRooms, reservation);

        List<Room> suggestedRooms = rms.suggestionSpace(room.getNumber(), building.getBuildingName(), dateTime);
        assertTrue(suggestedRooms.isEmpty());
    }

    @Test
    public void notSuggestedSpacesIfNoRoomsAssertRequirements() throws Exception {

        checkSuggestionSpacesExpectations(room, new ArrayList<Room>(), null);

        List<Room> suggestedRooms = rms.suggestionSpace(room.getNumber(), building.getBuildingName(), dateTime);
        assertTrue(suggestedRooms.isEmpty());
    }

    @Test
    public void testAcceptReservation() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(rDao).addReservation(reservation);
            }
        });
        rms.acceptReservation(reservation);
    }

    @Test
    public void testGetCurrentUserOfDemandedRoom() throws Exception {
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
    public void testGetCurrentUserOfIncorrectRoom() throws Exception{
        context.checking(new Expectations() {
            {
                oneOf(rDao).getReservationByDateRoomBulding(dateTime, room.getNumber(), building.getBuildingName());
                will(returnValue(null));
            }
        });
        ReservationUser ru = rms.getCurrentUserOfDemandedRoom(room.getNumber(), building.getBuildingName(), dateTime);
        assertNull(ru);
    }

    @Test
    public void testCompleteReservation() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(sDao).getBuildingByName(building.getBuildingName());
                will(returnValue(building));
                oneOf(sDao).getRoomByNbAndBuilding(room.getNumber(), building.getBuildingName());
                will(returnValue(room));
                oneOf(sDao).getRoomById(room.getRoomId());
                will(returnValue(room));
                allowing(uDao).getUserByNif(nif);
                will(returnValue(rUser));
                oneOf(rDao).getReservationByDateRoomBulding(dateTime, room.getNumber(), building.getBuildingName());
                will(returnValue(null));
            }
        });
        ReservationResult rr = rms.makeCompleteReservationBySpace(nif, room.getNumber(), building.getBuildingName(), dateTime);

        assertReservation(rr.getReservation());
        assertNull("Incorrect reservation result", rr.getSuggestions());
    }

    @Test
    public void testCompleteReservationOfReservedRoom() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(sDao).getBuildingByName(building.getBuildingName());
                will(returnValue(building));
                oneOf(sDao).getRoomByNbAndBuilding(room.getNumber(), building.getBuildingName());
                will(returnValue(room));
                oneOf(sDao).getRoomById(room.getRoomId());
                will(returnValue(room));
                allowing(uDao).getUserByNif(nif);
                will(returnValue(rUser));
                oneOf(rDao).getReservationByDateRoomBulding(dateTime, room.getNumber(), building.getBuildingName());
                will(returnValue(reservation));
            }
        });
        checkSuggestionSpacesExpectations(room, lRooms, null);
        ReservationResult rr = rms.makeCompleteReservationBySpace(nif, room.getNumber(), building.getBuildingName(), dateTime);
        assertNull("Incorrect reservation result", rr.getReservation());
        assertSuggestedSpacesRequirements(rr.getSuggestions());
    }

    @Test
    public void testMakeReservationByType() throws Exception {
        checkReservationByTypeExpectations(lRooms, null, rUser);
        Reservation r = rms.makeReservationByType(nif, room.getClass().getName(), building.getBuildingName(),
                capacity, dateTime);
        assertReservation(r);
    }

    @Test
    public void testMakeReservationByTypeNoEmptyRooms() throws Exception {
        checkReservationByTypeExpectations(lRooms, reservation, rUser);
        Reservation r = rms.makeReservationByType(nif, room.getClass().getName(), building.getBuildingName(),
                capacity, dateTime);
        assertNull(r);

    }

    @Test
    public void testMakeReservationByTypeNoReservationUser() throws Exception {
        User staffAdm = new StaffAdmin();

        checkReservationByTypeExpectations(lRooms, reservation, staffAdm);
        Reservation r = rms.makeReservationByType(nif, room.getClass().getName(), building.getBuildingName(),
                capacity, dateTime);
        assertNull(r);
        assertFalse(staffAdm instanceof ReservationUser);

    }

    @Test
    public void testMakeReservationByTypeNoSameTypeRooms() throws Exception {

        context.checking(new Expectations() {
            {
                oneOf(sDao).getAllRoomsByTypeAndCapacity(room.getClass().getName(), capacity, building.getBuildingName());
                will(returnValue(new ArrayList()));
            }
        });
        Reservation r = rms.makeReservationByType(nif, room.getClass().getName(), building.getBuildingName(),
                capacity, dateTime);
        assertNull(r);
    }

    private void checkReservationByTypeExpectations(final List<Room> rooms, final Reservation res, final User user) throws Exception{
        context.checking(new Expectations() {
            {
                oneOf(sDao).getAllRoomsByTypeAndCapacity(room.getClass().getName(), capacity, building.getBuildingName());
                will(returnValue(rooms));
                oneOf(rDao).getReservationByDateRoomBulding(dateTime, room.getNumber(), building.getBuildingName());
                will(returnValue(res));
                allowing(uDao).getUserByNif(nif);
                will(returnValue(user));
            }
        });
    }

    private void assertReservation(Reservation r) {
        assertEquals(reservation.getReservationDate(), r.getReservationDate());
        assertEquals(reservation.getRoom(), r.getRoom());
        assertEquals(reservation.getrUser(), r.getrUser());
    }

    private void checkSuggestionSpacesExpectations(final Room room, final List<Room> lRooms, final Reservation r) throws Exception {
        context.checking(new Expectations() {
            {   
                oneOf(sDao).getBuildingByName(building.getBuildingName());
                will(returnValue(building));
                oneOf(sDao).getRoomByNbAndBuilding(room.getNumber(), building.getBuildingName());
                will(returnValue(room));
                oneOf(sDao).getAllRoomsByTypeAndCapacity(room.getClass().getName(), room.getCapacity(), building.getBuildingName());
                will(returnValue(lRooms));
                allowing(rDao).getReservationByDateRoomBulding(dateTime, room.getNumber(), building.getBuildingName());
                will(returnValue(r));
            }
        });
    }

    private void checkNotReservedRoom(Room room) {
        List<Reservation> reservations = room.getReservations();
        for (Reservation r : reservations) {
            assertFalse("Already reserved room in the dateTime.",
                    dateTime.equals(r.getReservationDate()));
        }
    }

    private void assertSuggestedSpacesRequirements(List<Room> rooms) {
        for (Room r : rooms) {
            assertEquals("Different room types.", r.getClass(), room.getClass());
            assertTrue("Less capacity than room intended to reserve.",
                    r.getCapacity() >= room.getCapacity());
            checkNotReservedRoom(r);
        }
    }
}
