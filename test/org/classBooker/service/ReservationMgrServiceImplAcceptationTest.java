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
import org.classBooker.dao.exception.IncorrectTypeException;
import org.classBooker.entity.Building;
import org.classBooker.entity.ClassRoom;
import org.classBooker.entity.Reservation;
import org.classBooker.entity.Room;
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
public class ReservationMgrServiceImplAcceptationTest {
    //Mockery context = new JUnit4Mockery();
    @Rule JUnitRuleMockery context = new JUnitRuleMockery();
    ReservationMgrServiceImplAcceptation rms;
    SpaceDAO sDao;
    UserDAO uDao;
    ReservationDAO rDao;
    Room room;
    Building building;
    List<Room> lRooms;
    
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
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void suggestedSpacesAssertRequirements() throws IncorrectTypeException{
        
        lRooms = new ArrayList<>();
        lRooms.add(room);
        
        context.checking(new Expectations(){{
                oneOf (sDao).getRoomByNbAndBuilding(room.getNumber(), building.getBuildingName());
                will(returnValue(room));
                oneOf (sDao).getAllRoomsByTypeAndCapacity(room.getClass().toString(),room.getCapacity(), building.getBuildingName());
                will(returnValue(lRooms));
                }});
        List<Room> suggestedRooms = rms.suggestionSpace(room.getNumber(), building.getBuildingName());
        for(Room r: suggestedRooms){
            assertEquals("Different room types.", r.getClass(), room.getClass());
            assertTrue("Less capacity than room intended to reserve.", 
                    r.getCapacity() >= room.getCapacity());
        }
    }
    
    @Test(expected = IncorrectTypeException.class)
    public void IncorrectTypeOfRoomThrowsException() throws IncorrectTypeException{
        context.checking(new Expectations(
        ));
        List<Room> suggestedRooms = rms.suggestionSpace(room.getNumber(), building.getBuildingName());
    }
}
