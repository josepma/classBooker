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
    Room room25, room30, room20;
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
        
        nif = "123";
        building = sDao.getBuildingByName("EPS");
        room25 = new ClassRoom(building, "25", 25);
        room20 = new ClassRoom(building, "20", 20);
        room30 = new ClassRoom(building, "30", 30);
        sDao.addRoom(room25);
        sDao.addRoom(room20);
        sDao.addRoom(room30);
        dateTime = new DateTime(2015, 5, 26, 9, 0);
        rUser = new ProfessorPas();
        uDao.addUser(rUser);
        reservation = new Reservation(dateTime, rUser, room25);
        rDao.addReservation(reservation);

    }

    @After
    public void tearDown() throws DAOException {
        sDao.removeRoom(room25);
        sDao.removeRoom(room30);
        sDao.removeRoom(room20);        
        uDao.removeUser(rUser);
        rDao.removeReservation(dateTime, "25", "EPS");
        
    }

    //@Test
    public void suggestedSpacesAssertRequirements() throws Exception {
        Room room = sDao.getRoomByNbAndBuilding("25", "EPS");
        List<Room> suggestedRooms = rms.suggestionSpace("25", "EPS", dateTime);
        if (suggestedRooms.isEmpty()) {
            fail("No spaces suggested.");
        }
        assertSuggestedSpacesRequirements(suggestedRooms, room);
    }

    //@Test
    public void notSuggestedSpacesIfRoomsWhichAssertRequirementsAreReserved() throws Exception {

        List<Room> suggestedRooms = rms.suggestionSpace(room25.getNumber(), building.getBuildingName(), dateTime);
        assertTrue(suggestedRooms.isEmpty());
    }

    //@Test
    public void notSuggestedSpacesIfNoRoomsAssertRequirements() throws Exception {

        List<Room> suggestedRooms = rms.suggestionSpace(room25.getNumber(), building.getBuildingName(), dateTime);
        assertTrue(suggestedRooms.isEmpty());
    }
/*
    @Test
    public void testGetCurrentUserOfDemandedRoom() throws Exception {

        ReservationUser ru = rms.getCurrentUserOfDemandedRoom(room.getNumber(), building.getBuildingName(), dateTime);
        assertEquals(reservation.getrUser(), ru);
    }

    @Test
    public void testGetCurrentUserOfIncorrectRoom() throws Exception {

        ReservationUser ru = rms.getCurrentUserOfDemandedRoom(room.getNumber(), building.getBuildingName(), dateTime);
        assertNull(ru);
    }

    @Test
    public void testCompleteReservation() throws Exception {

        ReservationResult rr = rms.makeCompleteReservationBySpace(nif, room.getNumber(), building.getBuildingName(), dateTime);

        assertReservation(rr.getReservation());
        assertEquals("Not same user", rUser, rr.getrUser());
        assertNull("Incorrect reservation result", rr.getSuggestions());
    }

    @Test
    public void testCompleteReservationOfReservedRoom() throws Exception {

        ReservationResult rr = rms.makeCompleteReservationBySpace(nif, room.getNumber(), building.getBuildingName(), dateTime);
        assertNull("Incorrect reservation result", rr.getReservation());
        assertNull("Incorrect reservation result", rr.getrUser());
        assertSuggestedSpacesRequirements(rr.getSuggestions());
    }

    @Test
    public void testMakeReservationByType() throws Exception {
        Reservation r = rms.makeReservationByType(nif, room.getClass().getName(), building.getBuildingName(),
                capacity, dateTime);
        assertReservation(r);
    }

    @Test
    public void testMakeReservationByTypeNoEmptyRooms() throws Exception {
        Reservation r = rms.makeReservationByType(nif, room.getClass().getName(), building.getBuildingName(),
                capacity, dateTime);
        assertNull(r);

    }

    @Test
    public void testMakeReservationByTypeNoReservationUser() throws Exception {
        User staffAdm = new StaffAdmin();

        Reservation r = rms.makeReservationByType(nif, room.getClass().getName(), building.getBuildingName(),
                capacity, dateTime);
        assertNull(r);
        assertFalse(staffAdm instanceof ReservationUser);

    }

    @Test
    public void testMakeReservationByTypeNoSameTypeRooms() throws Exception {

        Reservation r = rms.makeReservationByType(nif, room.getClass().getName(), building.getBuildingName(),
                capacity, dateTime);
        assertNull(r);
    }*/

    private void assertReservation(Reservation r) {
        assertEquals(reservation.getReservationDate(), r.getReservationDate());
        assertEquals(reservation.getRoom(), r.getRoom());
        assertEquals(reservation.getrUser(), r.getrUser());
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
