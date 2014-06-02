/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.classbooker.dao.SpaceDAOImpl;
import org.classbooker.dao.exception.AlreadyExistingRoomException;
import org.classbooker.dao.exception.AlredyExistReservationException;
import org.classbooker.dao.exception.IncorrectTypeException;
import org.classbooker.dao.exception.NonBuildingException;
import org.classbooker.dao.exception.NoneExistingRoomException;

import org.classbooker.entity.Building;
import org.classbooker.entity.ClassRoom;
import org.classbooker.entity.LaboratoryRoom;
import org.classbooker.entity.MeetingRoom;
import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author saida, genis
 */
public class SpaceDAOImplIntegTest {

    EntityManager ema;
    SpaceDAOImpl sdi;
    Room roomfind, roomnew, labRoom, meetRoom, classRoom;
    Building buildingfind, buildingnew;
    DateTime dataRes1;
    ReservationUser user1;
    Reservation reserv;
    private Query query;

   
    public SpaceDAOImplIntegTest() {
        this.sdi = new SpaceDAOImpl();
    }

 
    @Before
    public void setUp() {
        
        user1 = new ProfessorPas("55555", "manganito@gmail.com", "Manganito");
        dataRes1 = new DateTime(2014, 05, 11, 12, 00);
        ema = getEntityManager();
        reserv = new Reservation(dataRes1, user1, roomnew);
        ema.getTransaction().begin();
        sdi.setEm(ema);
        ema.getTransaction().commit();
        
    }
    @After
    public void tearDown() throws Exception {
       
       
        if(roomnew!=null)
        {  
            sdi.removeRoom(roomnew);}
        if(buildingnew!=null)
            sdi.removeBuilding(buildingnew);
        
        ema.close();
    }


    @Test
    public void testAddRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS");        
        roomnew = new ClassRoom(buildingfind, "2.09", 30);      
        long rom = sdi.addRoom(roomnew);
        Room roomdb = sdi.getRoomByNbAndBuilding("2.09", buildingfind.getBuildingName());
        
        assertEquals(roomdb.getBuilding(), roomnew.getBuilding());
        assertEquals(rom, roomnew.getRoomId());
        assertEquals(roomnew.getBuilding(), sdi.getBuildingByName(buildingfind.getBuildingName()));
        assertTrue(sdi.getAllRooms().contains(roomnew));
        assertTrue(sdi.getBuildingByName(buildingfind.getBuildingName()).getRooms().contains(roomnew));

    }


    @Test(expected = AlreadyExistingRoomException.class)
    public void testAddExistingRoom() throws Exception {
        roomfind = ema.find(Room.class, (long)1);  
        sdi.addRoom(roomfind);
   
    }


    @Test(expected = NonBuildingException.class)
    public void testAddRoomNoneExistingBuilding() throws Exception {
        Building building = new Building("FDE");
        Room room = new ClassRoom(building, "2.01", 30);
        sdi.addRoom(room);
    }


    @Test
    public void testRemoveRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS"); 
        Room room = new ClassRoom(buildingfind, "2.10", 30);
        
        long rom = sdi.addRoom(room);
        sdi.removeRoom(room);
        

        assertFalse(sdi.getAllRooms().contains(room));
        assertFalse(sdi.getAllRoomsOfOneBuilding(buildingfind.getBuildingName()).contains(buildingfind));
    }


    @Test(expected = NoneExistingRoomException.class)
    public void testRemoveNoneExistingRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS");
        Room room3 = new ClassRoom(buildingfind, "2.15", 30);
        sdi.removeRoom(room3);
    }


    @Test
    public void testGetRoomById() {
        long id = 1;
        roomfind = ema.find(Room.class, id); 
        assertEquals(roomfind, sdi.getRoomById(id));
    }


    @Test
    public void testGetAllRooms() throws Exception {
           
        final Set<Room> expected = new HashSet<>();
        expected.add(ema.find(Room.class, (long)1));
        expected.add(ema.find(Room.class, (long)2));
        expected.add(ema.find(Room.class, (long)3));
        expected.add(ema.find(Room.class, (long)4));
        expected.add(ema.find(Room.class, (long)15));
        expected.add(ema.find(Room.class, (long)20));
        expected.add(ema.find(Room.class, (long)25));
        expected.add(ema.find(Room.class, (long)30));        
        List<Room> result = sdi.getAllRooms();
        Set<Room> resultSet = new HashSet(result);
        assertEquals(expected, resultSet);

    }


    @Test
    public void testAddBuilding() throws Exception {
        Building building = new Building("ETSEA");
        sdi.addBuilding(building);
        assertEquals(building,sdi.getBuildingByName("ETSEA"));
        sdi.removeBuilding(building);
       
    }


    @Test
    public void testModifyCapacityRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS"); 
        Room room = new ClassRoom(buildingfind, "2.29", 30);
        sdi.addRoom(room);
        roomnew=sdi.modifyRoom(room, null, 20);       
        assertEquals(20, roomnew.getCapacity());
        
    }

    @Test
    public void testModifyTypeRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS"); 
        Room room = new ClassRoom(buildingfind, "2.39", 30);
        sdi.addRoom(room);
        roomnew=sdi.modifyRoom(room, "MeetingRoom", 0);
        assertTrue(roomnew instanceof MeetingRoom);
        
    }

    @Test
    public void testModifyTypeAndCapacityRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS"); 
        Room room = new ClassRoom(buildingfind, "3.29", 30);
        sdi.addRoom(room);
        roomnew=sdi.modifyRoom(room, "MeetingRoom", 50);
        assertTrue(roomnew instanceof MeetingRoom);
        assertEquals(50, roomnew.getCapacity());
      }

    @Test(expected = AlredyExistReservationException.class)
    public void testModifyRoomWithExistingReservation() throws Exception {
        
        roomfind = ema.find(Room.class, (long)1);       
        sdi.modifyRoom(roomfind, "MeetingRoom", 50);
    }


    @Test
    public void testRemoveBuilding() throws Exception {
        Building building = new Building("ETSEA");
        sdi.addBuilding(building);
        sdi.removeBuilding(building);
        assertFalse(sdi.getAllBuildings().contains(building));
    }

  
    @Test
    public void testGetBuildingByName() throws Exception {
        buildingfind= ema.find(Building.class, "EPS"); 
        assertEquals(buildingfind, sdi.getBuildingByName("EPS"));
    }

    @Test
    public void testGetAllBuildings() throws Exception {
        
       
        final Set<Building> expected = new HashSet<>();
        expected.add(ema.find(Building.class, "Main Library"));
        expected.add(ema.find(Building.class, "Rectorate Building"));
        expected.add(ema.find(Building.class, "EPS"));
        List<Building> result = sdi.getAllBuildings();
        Set<Building> resultSet = new HashSet(result);
        assertEquals(expected, resultSet);
    }


    @Test
    public void testGetAllRoomsOfOneBuilding() throws Exception {
        List<Room> rooms = new ArrayList<Room>();
        buildingfind= ema.find(Building.class, "Main Library"); 
        roomfind = ema.find(Room.class, (long)1); 
        Room roomfind2 = ema.find(Room.class, (long)2); 
        
        buildingfind.setRooms(rooms);
        Set<Room> roomsSet = new HashSet<>(rooms);
        Set<Room> result = new HashSet<>(sdi.getAllRoomsOfOneBuilding(buildingfind.getBuildingName()));
        assertEquals(roomsSet, result);
    }


    @Test
    public void testGetAllRoomsOfOneType() throws Exception {
        meetRoom = ema.find(Room.class, (long)1);
        buildingfind= ema.find(Building.class, "Main Library");         
        List<Room> meetingRooms = new ArrayList<Room>();
        meetingRooms.add(meetRoom);
        buildingfind.setRooms(meetingRooms);
        Set<Room> roomsSet = new HashSet<>(meetingRooms);
        Set<Room> result = new HashSet<>(sdi.getAllRoomsOfOneType("MeetingRoom"));
        assertEquals(roomsSet, result);
    }

    @Test(expected = IncorrectTypeException.class)
    public void testGetAllRoomsOfOneIncorrectType() throws Exception {      
    
        Set<Room> result = new HashSet<>(sdi.getAllRoomsOfOneType("ComputerRoom"));
     
    }


    @Test
    public void testGetAllRoomsOfOneTypeAndOneBuilding() throws Exception {
        meetRoom = ema.find(Room.class, (long)1);
        buildingfind= ema.find(Building.class, "Main Library");         
        List<Room> meetingRooms = new ArrayList<Room>();
        meetingRooms.add(meetRoom);
        buildingfind.setRooms(meetingRooms);
        Set<Room> roomsSet = new HashSet<>(meetingRooms);
        Set<Room> result = new HashSet<>(sdi.getAllRoomsOfOneTypeAndOneBuilding("MeetingRoom", buildingfind));
        assertEquals(roomsSet, result);
    }

    @Test
    public void testGetAllRoomsOfOneTypeAndOneBuildingandcapacity() throws Exception {
        
        meetRoom = ema.find(Room.class, (long)1);
        buildingfind= ema.find(Building.class, "Main Library");         
        List<Room> meetingRooms = new ArrayList<Room>();
        meetingRooms.add(meetRoom);
        buildingfind.setRooms(meetingRooms);
        Set<Room> roomsSet = new HashSet<>(meetingRooms);
        Set<Room> result = new HashSet<>(sdi.getAllRoomsByTypeAndCapacity("MeetingRoom", 40, buildingfind.getBuildingName()));
        assertEquals(roomsSet, result);
    }


    @Test
    public void testgetRoomByNbAndBuilding() throws Exception {
        roomfind=ema.find(Room.class, (long)1);
        
        assertEquals(roomfind, sdi.getRoomByNbAndBuilding("1.0", "Main Library"));
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBookerIntegration");
        return emf.createEntityManager();
    }

    
    
}
