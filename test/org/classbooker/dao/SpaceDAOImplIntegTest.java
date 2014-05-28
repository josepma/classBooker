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
import org.classbooker.dao.exception.AlreadyExistingBuildingException;
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
    //ReseReservationrvationDAOImpl reservation;
    DateTime dataRes1;
    ReservationUser user1;
    Reservation reserv;
    private Query query;

    /**
     *
     */
    public SpaceDAOImplIntegTest() {
        this.sdi = new SpaceDAOImpl();
    }

    /**
     *
     */
    @Before
    public void setUp() {
        
        roomfind = new ClassRoom(buildingfind, "2.01", 30);
        user1 = new ProfessorPas("55555", "manganito@gmail.com", "Manganito");
        dataRes1 = new DateTime(2014, 05, 11, 12, 00);
        ema = getEntityManager();
        reserv = new Reservation(dataRes1, user1, roomnew);
        ema.getTransaction().begin();
        sdi.setEm(ema);
        
    }
    @After
    public void tearDown() throws Exception {
       
       
        if(roomnew!=null)
        { 
            ema.remove(roomnew);}
        if(buildingnew!=null)
            ema.remove(buildingnew);
        ema.getTransaction().commit();
        ema.close();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testAddRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS");        
        roomnew = new ClassRoom(buildingfind, "2.09", 30);      
      //  ema.getTransaction().begin();
        long rom = sdi.addRoom(roomnew);
     //   ema.getTransaction().commit();
        Room roomdb = sdi.getRoomByNbAndBuilding("2.09", buildingfind.getBuildingName());
        
        assertEquals(roomdb.getBuilding(), roomnew.getBuilding());
        assertEquals(rom, roomnew.getRoomId());
        assertEquals(roomnew.getBuilding(), sdi.getBuildingByName(buildingfind.getBuildingName()));
        assertTrue(sdi.getAllRooms().contains(roomnew));
        assertTrue(sdi.getBuildingByName(buildingfind.getBuildingName()).getRooms().contains(roomnew));

    }

    /**
     *
     * @throws Exception
     */
    @Test(expected = AlreadyExistingRoomException.class)
    public void testAddExistingRoom() throws Exception {
        roomfind = ema.find(Room.class, (long)1);  
     //   ema.getTransaction().begin();
        sdi.addRoom(roomfind);
    //    ema.getTransaction().commit();

    }

    /**
     *
     * @throws Exception
     */
    @Test(expected = NonBuildingException.class)
    public void testAddRoomNoneExistingBuilding() throws Exception {
        buildingnew = new Building("FDE");
        roomnew = new ClassRoom(buildingnew, "2.01", 30);
        sdi.addRoom(roomnew);
    }

    /**
     * Test of removeRoom method, of class SpaceDAOImpl.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testRemoveRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS"); 
        roomnew = new ClassRoom(buildingfind, "2.10", 30);
        
        long rom = sdi.addRoom(roomnew);
        sdi.removeRoom(roomnew);
        

        assertFalse(sdi.getAllRooms().contains(roomnew));
        assertFalse(sdi.getAllRoomsOfOneBuilding(buildingfind.getBuildingName()).contains(buildingfind));
    }

    /**
     * Test of removeRoom method, of class SpaceDAOImpl.
     *
     * @throws java.lang.Exception
     */
    @Test(expected = NoneExistingRoomException.class)
    public void testRemoveNoneExistingRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS");
        Room room3 = new ClassRoom(buildingfind, "2.15", 30);
        sdi.removeRoom(room3);
    }

    /**
     * Test of getRoomById method, of class SpaceDAOImpl.
     */
    @Test
    public void testGetRoomById() {
        long id = 1;
        roomfind = ema.find(Room.class, id); 
        assertEquals(roomfind, sdi.getRoomById(id));
    }

    /**
     * Test of getAllRooms method, of class SpaceDAOImpl.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllRooms() throws Exception {
           
        final Set<Room> expected = new HashSet<>();
        expected.add(ema.find(Room.class, (long)1));
        expected.add(ema.find(Room.class, (long)2));
        expected.add(ema.find(Room.class, (long)3));
        expected.add(ema.find(Room.class, (long)4));
        expected.add(ema.find(Room.class, (long)3053));
        List<Room> result = sdi.getAllRooms();
        Set<Room> resultSet = new HashSet(result);
        assertEquals(expected, resultSet);

    }

    /**
     * Test of addBuilding method, of class SpaceDAOImpl.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAddBuilding() throws Exception {
        buildingnew = new Building("FDE");
        sdi.addBuilding(buildingnew);
        assertEquals(buildingnew,sdi.getBuildingByName("FDE"));
       
    }

    /**
     *
     * @throws Exception
     */
    @Test(expected = AlreadyExistingBuildingException.class)
    public void testAddExixtingBuilding() throws Exception {
        buildingnew = new Building("FDE");
        sdi.addBuilding(buildingnew);
        sdi.addBuilding(buildingnew);
    }

    /**
     * Test of modifyRoom method, of class SpaceDAOImpl.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testModifyCapacityRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS"); 
        Room room = new ClassRoom(buildingfind, "2.29", 30);
        long id = sdi.addRoom(room);
        sdi.modifyRoom(room, null, 20);
        roomnew=sdi.getRoomById(id);        
        System.out.println("rooomnew "+roomnew.toString());
        assertEquals(20, roomnew.getCapacity());
        
    }

   // @Test
    public void testModifyTypeRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS"); 
        Room room = new ClassRoom(buildingfind, "2.19", 30);
        long id = sdi.addRoom(room);
        sdi.modifyRoom(room, "MeetingRoom", 0);
        ema.remove(room);
        
    }

   // @Test
    public void testModifyTypeAndCapacityRoom() throws Exception {
        buildingfind= ema.find(Building.class, "EPS"); 
        Room room = new ClassRoom(buildingfind, "2.19", 30);
        long id = sdi.addRoom(room);
        sdi.modifyRoom(room, "MeetingRoom", 50);
        ema.remove(room);
      }

    //@Test(expected = AlredyExistReservationException.class)
    public void testModifyRoomWithExistingReservation() throws Exception {
        buildingfind= ema.find(Building.class, "EPS"); 
        roomnew = new ClassRoom(buildingfind, "2.19", 30);
        long id = sdi.addRoom(roomnew);
        List<Reservation> reservations = new ArrayList<Reservation>();
        reservations.add(reserv);
        roomnew.setReservations(reservations);
        sdi.modifyRoom(roomnew, "MeetingRoom", 50);
    }

    /**
     * Test of removeBuilding method, of class SpaceDAOImpl.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testRemoveBuilding() throws Exception {
        buildingnew = new Building("FDE");
        sdi.addBuilding(buildingnew);
        sdi.removeBuilding(buildingnew);
        assertFalse(sdi.getAllBuildings().contains(buildingnew));
    }

    /**
     * Test of getBuildingByName method, of class SpaceDAOImpl.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetBuildingByName() throws Exception {
        buildingfind= ema.find(Building.class, "EPS"); 
        assertEquals(buildingfind, sdi.getBuildingByName("EPS"));
    }

    /**
     * Test of getAllBuildings method, of class SpaceDAOImpl.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllBuildings() throws Exception {
        
       
        final Set<Building> expected = new HashSet<>();
        expected.add(ema.find(Building.class, "Main Library"));
        expected.add(ema.find(Building.class, "Rectorate Building"));
        expected.add(ema.find(Building.class, "EPS"));
        expected.add(ema.find(Building.class, "Acceptance"));
        List<Building> result = sdi.getAllBuildings();
        Set<Building> resultSet = new HashSet(result);
        assertEquals(expected, resultSet);
    }

    /**
     * Test of getAllRoomsOfOneBuilding method, of class SpaceDAOImpl.
     *
     * @throws java.lang.Exception
     */
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

    /**
     * Test of getAllRoomsOfOneType method, of class SpaceDAOImpl.
     *
     * @throws java.lang.Exception
     */
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

    /**
     *
     * @throws Exception
     */
    @Test(expected = IncorrectTypeException.class)
    public void testGetAllRoomsOfOneIncorrectType() throws Exception {      
    
        Set<Room> result = new HashSet<>(sdi.getAllRoomsOfOneType("ComputerRoom"));
     
    }

    /**
     * Test of getAllRoomsOfOneTypeAndOneBuilding method, of class SpaceDAOImpl.
     *
     * @throws java.lang.Exception
     */
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

    /**
     *
     * @throws Exception
     */
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

    /**
     *
     * @throws Exception
     */
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
