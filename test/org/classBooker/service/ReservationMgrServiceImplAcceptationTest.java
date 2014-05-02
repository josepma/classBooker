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
        
        rms.setSpaceDao(sDao);
        rms.setReservationDao(rDao);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void suggestedSpacesAssertRequirements() throws IncorrectTypeException, IncorrectBuildingException, IncorrectRoomException {

        lRooms = new ArrayList<>();
        lRooms.add(room);

        context.checking(new Expectations() {
            {
                oneOf(sDao).getRoomByNbAndBuilding(room.getNumber(), building.getBuildingName());
                will(returnValue(room));
                oneOf(sDao).getAllRoomsByTypeAndCapacity(room.getClass().toString(), room.getCapacity(), building.getBuildingName());
                will(returnValue(lRooms));
            }
        });
        List<Room> suggestedRooms = rms.suggestionSpace(room.getNumber(), building.getBuildingName());
        System.out.println("ee" + suggestedRooms.size());
        for (Room r : suggestedRooms) {
            assertEquals("Different room types.", r.getClass(), room.getClass());
            assertTrue("Less capacity than room intended to reserve.",
                    r.getCapacity() >= room.getCapacity());
        }
    }

    //@Test(expected = IncorrectTypeException.class)
    public void IncorrectTypeOfRoomThrowsException() throws IncorrectTypeException, IncorrectBuildingException, IncorrectRoomException {
        context.checking(new Expectations() {
            {
                oneOf(sDao).getRoomByNbAndBuilding(room.getNumber(), building.getBuildingName());
                will(returnValue(room));
                oneOf(sDao).getAllRoomsByTypeAndCapacity(room.getClass().toString(), room.getCapacity(), building.getBuildingName());
                will(throwException(new IncorrectTypeException()));

            }
        });
        List<Room> suggestedRooms = rms.suggestionSpace(room.getNumber(), building.getBuildingName());
    }
    @Test
    public void testAcceptReservation() throws IncorrectBuildingException, IncorrectRoomException, IncorrectTypeException, IncorrectReservationException, IncorrectUserException{
        context.checking(new Expectations() {
            {
                oneOf(rDao).addReservation(with(any(Reservation.class)));
            }
        });        
        rms.acceptReservation(reservation);
    }
    @Test
    public void testGetCurrentUserOfDemandedRoom() throws IncorrectRoomException{
        context.checking(new Expectations() {
            {
                oneOf(rDao).getReservationByDateRoomBulding(dateTime, room.getNumber(), building.getBuildingName());
                will(returnValue(reservation));
            }
        });
        ReservationUser ru = rms.getCurrentUserOfDemandedRoom(room.getNumber(), building.getBuildingName(), dateTime);
        assertEquals(reservation.getrUser(), ru);
    }
    
    //@Test (expected=IncorrectRoomException.class)
    public void testGetCurrentUserOfIncorrectRoom() throws IncorrectRoomException{
        context.checking(new Expectations() {
            {
                oneOf(rDao).getReservationByDateRoomBulding(dateTime, room.getNumber(), building.getBuildingName());
                will(throwException(new IncorrectRoomException()));
            }
        });
        ReservationUser ru = rms.getCurrentUserOfDemandedRoom(room.getNumber(), building.getBuildingName(), dateTime);
        
    }
    //@Test
    public void testCompleteReservation() throws Exception{
        context.checking(new Expectations() {
            {
                
            }
        });
        rms.makeCompleteReservationBySpace("", room.getNumber(), building.getBuildingName(), dateTime);
    }
}
