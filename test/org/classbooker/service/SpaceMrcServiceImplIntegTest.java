/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.classbooker.dao.SpaceDAOImpl;
import org.classbooker.dao.exception.AlreadyExistingBuildingException;
import org.classbooker.dao.exception.AlreadyExistingRoomException;
import org.classbooker.dao.exception.AlredyExistReservationException;
import org.classbooker.dao.exception.IncorrectRoomException;
import org.classbooker.dao.exception.IncorrectTypeException;
import org.classbooker.dao.exception.NonBuildingException;
import org.classbooker.dao.exception.NoneExistingRoomException;
import org.classbooker.entity.Building;
import org.classbooker.entity.ClassRoom;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.Room;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author saida
 */
public class SpaceMrcServiceImplIntegTest {
    SpaceMgrServiceImpl sms;
    SpaceDAOImpl space;
    EntityManager ema;
    Building buildingFind;
    public SpaceMrcServiceImplIntegTest() {
    }
    
    @Before
    public void setUp() {
        this.space = new SpaceDAOImpl();
        sms= new SpaceMgrServiceImpl();
        ema = getEntityManager();
        sms.setSpd(space);
        space.setEm(ema);
       
    }
    
    @After
    public void tearDown() throws Exception {
   
    }

    @Test
    public void testAddRoom()throws Exception{
        long id = sms.addRoom("2.08", "EPS", 40, "ClassRoom");
        Room room =space.getRoomById(id);
        assertEquals(room.getBuilding().getBuildingName(),"EPS");
        assertEquals(room.getCapacity(),40);
        assertEquals(room.getNumber(),"2.08");
        sms.deleteRoom(id);
    }
   
    @Test(expected =IncorrectRoomException.class)
    public void testAddRoomnegativeCapacity()throws Exception{
        long id = sms.addRoom("2.08", "EPS", -40, "ClassRoom");
    }
    
    @Test(expected = NonBuildingException.class)
    public void testAddRoomNoneExistingBuilding() throws Exception {
        sms.addRoom("2.01", "prova", 30, "ClassRoom");
    }
     @Test(expected = IncorrectTypeException.class)
     public void testAddRoomIncorrectTypeRoom()throws Exception{
         sms.addRoom("2.01", "EPS", 30, "ComputerRoom");
     }
    
    @Test(expected = AlreadyExistingRoomException.class)
    public void testAddExistingRoom() throws Exception {
        sms.addRoom("0.15", "EPS", 15, "ClassRoom");
        
    }

    @Test
    public void testAddBuilding() throws Exception { 
        Building FDE = new Building("FDE");
        sms.addBuilding("FDE"); 
        assertEquals(sms.getBuildingbyName("FDE"), FDE);
        sms.deleteBuilding("FDE");
    }  
    
    @Test(expected = AlreadyExistingBuildingException.class)
    public void testAddExistingBuilding() throws Exception{
        sms.addBuilding("EPS");
    }
    
    

    @Test
    public void testRemoveRoom() throws Exception {
        
        long rom = sms.addRoom("2.10", "EPS", 30, "ClassRoom");
        sms.deleteRoom(rom);  
        assertNull(ema.find(Room.class, rom));
    }
    
    @Test(expected = NoneExistingRoomException.class)
    public void testRemoveNoneExistingRoom() throws Exception {
        buildingFind= ema.find(Building.class, "EPS");
        Room room3 = new ClassRoom(buildingFind, "2.15", 30);
        sms.deleteRoom(room3.getRoomId());
    }
   
   @Test(expected = AlredyExistReservationException.class)
    public void testRemoveRoomWithExistingReservation() throws Exception {
        sms.deleteRoom(1);
    }
    

    @Test
    public void testRemoveBuilding() throws Exception{
        sms.addBuilding("UDL");
        sms.deleteBuilding("UDL");
        assertNull(ema.find(Building.class, "UDL"));
        assertNull(space.getAllRoomsOfOneBuilding("UDL"));
    }
    
     
    
   private EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBookerIntegration");
        return emf.createEntityManager();
    }
}
