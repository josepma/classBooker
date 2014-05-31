/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.service;

import org.classbooker.service.ReservationMgrServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.classbooker.dao.ReservationDAO;
import org.classbooker.dao.ReservationDAOImpl;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.SpaceDAOImpl;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.UserDAOImpl;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectRoomException;
import org.classbooker.entity.Building;
import org.classbooker.entity.ClassRoom;
import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;
import org.classbooker.entity.StaffAdmin;
import org.classbooker.entity.User;
import org.classbooker.util.ReservationResult;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author abg7
 */
public class ReservationMgrServiceImplAcceptationIntegTest {

    ReservationMgrServiceImpl rms;
    SpaceDAO sDao;
    UserDAO uDao;
    ReservationDAO rDao;
    EntityManager em;
    Building building;
    DateTime dateTime;
    ReservationUser rUser;
    Reservation reservation;
    List<Room> lRooms;
    ReservationResult rResult;
    String nif;
    int capacity;

    @Before
    public void setUp() throws Exception {
        em = getEntityManager();
        rms = new ReservationMgrServiceImpl();
        sDao = new SpaceDAOImpl();
        uDao = new UserDAOImpl();
        rDao = new ReservationDAOImpl();

        sDao.setEm(em);
        uDao.setEntityManager(em);
        rDao.setEm(em);
        rDao.setsDao(sDao);
        rDao.setuDao(uDao);
        rms.setSpaceDao(sDao);
        rms.setReservationDao(rDao);
        rms.setUserDao(uDao);

        nif = "55555";
        building = sDao.getBuildingByName("EPS");

    }

    @After
    public void tearDown() throws DAOException {

    }

    @Test
    public void suggestedSpacesAssertRequirements() throws Exception {
        Room room = sDao.getRoomByNbAndBuilding("0.20", "EPS");

        dateTime = new DateTime(2014, 5, 26, 9, 0);

        List<Room> suggestedRooms = rms.suggestionSpace("0.20", "EPS", dateTime);
        if (suggestedRooms.isEmpty()) {
            fail("No spaces suggested.");
        }
        assertSuggestedSpacesRequirements(suggestedRooms, room);
    }

    @Test
    public void notSuggestedSpacesIfRoomsWhichAssertRequirementsAreReserved() throws Exception {
        dateTime = new DateTime(2014, 5, 28, 9, 0);

        List<Room> suggestedRooms = rms.suggestionSpace("0.20", "EPS", dateTime);
        assertTrue(suggestedRooms.isEmpty());
    }

    @Test
    public void notSuggestedSpacesIfNoRoomsAssertRequirements() throws Exception {
        dateTime = new DateTime(2014, 5, 29, 9, 0);

        List<Room> suggestedRooms = rms.suggestionSpace("0.25", "EPS", dateTime);
        assertTrue(suggestedRooms.isEmpty());
    }

    @Test
    public void testGetCurrentUserOfDemandedRoom() throws Exception {

        dateTime = new DateTime(2014, 5, 29, 9, 0);

        ReservationUser ru = rms.getCurrentUserOfDemandedRoom("0.25", "EPS", dateTime);
        Reservation r = rDao.getReservationByDateRoomBulding(dateTime, "0.25", "EPS");

        assertEquals(r.getrUser(), ru);
    }

    @Test
    public void testGetCurrentUserOfIncorrectRoom() throws Exception {

        dateTime = new DateTime(2014, 5, 31, 9, 0);

        ReservationUser ru = rms.getCurrentUserOfDemandedRoom("0.25", "EPS", dateTime);
        assertNull(ru);
    }

    @Test
    public void testCompleteReservation() throws Exception {

        dateTime = new DateTime(2015, 5, 30, 9, 0);

        ReservationResult rr = rms.makeCompleteReservationBySpace(nif, "0.25", "EPS", dateTime);
        User u = uDao.getUserByNif(nif);

        assertReservation(rr.getReservation(), "EPS", "0.25", dateTime, u);
        assertNull("Incorrect reservation result", rr.getSuggestions());
    }

    @Test
    public void testCompleteReservationOfReservedRoom() throws Exception {

        dateTime = new DateTime(2015, 5, 26, 9, 0);

        ReservationResult rr = rms.makeCompleteReservationBySpace(nif, "0.20", "EPS", dateTime);
        assertNull("Incorrect reservation result", rr.getReservation());
        assertSuggestedSpacesRequirements(rr.getSuggestions(), sDao.getRoomByNbAndBuilding("0.20", "EPS"));
    }

    @Test
    public void testMakeReservationByType() throws Exception {
        dateTime = new DateTime(2015, 5, 25, 9, 0);
        Reservation r = rms.makeReservationByType(nif, "ClassRoom", "EPS",
                20, dateTime);
        User u = uDao.getUserByNif(nif);
        assertReservation(r, "EPS", r.getRoom().getNumber(), dateTime, u);
    }

    @Test
    public void testMakeReservationByTypeNoEmptyRooms() throws Exception {
        dateTime = new DateTime(2015, 5, 26, 9, 0);
        Reservation r = rms.makeReservationByType(nif, "LaboratoryRoom", "EPS",
                20, dateTime);
        assertNull(r);

    }

    @Test
    public void testMakeReservationByTypeNoReservationUser() throws Exception {
        User staffAdm = new StaffAdmin("454654", "name@hotmail.com", "StaffName");
        dateTime = new DateTime(2014, 5, 26, 10, 0);

        Reservation r = rms.makeReservationByType(staffAdm.getNif(), "ClassRoom", "EPS",
                20, dateTime);
        assertNull(r);
        assertFalse(staffAdm instanceof ReservationUser);

    }

    @Test
    public void testMakeReservationByTypeNoSameTypeRooms() throws Exception {

        dateTime = new DateTime(2014, 5, 26, 9, 0);
        Reservation r = rms.makeReservationByType(nif, "MeetingRoom", "EPS",
                20, dateTime);

        assertNull(r);
    }

    private void assertReservation(Reservation r, String building, String room, DateTime dateTime, User u) {
        assertEquals(0, dateTime.compareTo(r.getReservationDate()));
        assertEquals(room, r.getRoom().getNumber());
        assertEquals(building, r.getRoom().getBuilding().getBuildingName());
        assertEquals(u, r.getrUser());
    }

    private void checkNotReservedRoom(Room room) {
        List<Reservation> reservations = room.getReservations();
        for (Reservation r : reservations) {
            assertFalse("Already reserved room in the dateTime.",
                    dateTime.equals(r.getReservationDate()));
        }
    }

    private void assertSuggestedSpacesRequirements(List<Room> rooms, Room room) {
        for (Room r : rooms) {
            assertFalse("Is the same room", r.getNumber().equals(room.getNumber())
                    && r.getBuilding().equals(room.getBuilding()));
            assertEquals("Different room types.", r.getClass(), room.getClass());
            assertTrue("Less capacity than room intended to reserve.",
                    r.getCapacity() >= room.getCapacity());
            checkNotReservedRoom(r);
        }
    }

    private EntityManager getEntityManager() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBookerIntegration");
        return emf.createEntityManager();
    }
}
