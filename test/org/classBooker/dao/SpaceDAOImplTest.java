/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.classBooker.dao.exception.AlreadyExistingBuildingException;
import org.classBooker.dao.exception.AlreadyExistingRoomException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.PersistException;
import org.classBooker.entity.Building;
import org.classBooker.entity.ClassRoom;
import org.classBooker.entity.LaboratoryRoom;
import org.classBooker.entity.MeetingRoom;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.ReservationUser;
import org.classBooker.entity.Room;
import org.classBooker.entity.User;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author saida
 */
public class SpaceDAOImplTest {
     
     EntityManager ema;
     
     SpaceDAOImpl sdi;
     Room room;
     Building building;
     private Query query;
     
    public SpaceDAOImplTest() {
        this.sdi=new SpaceDAOImpl();
        
    }
    
    @Before
    public void setUp() {       
        building=new Building("EPS");
        room=new ClassRoom(building, "2.01", 30);        
      
        ema = getEntityManager();
        
        ema.getTransaction().begin();
        ema.persist(room);
        ema.persist(building);    
        ema.getTransaction().commit();
        
        sdi.setEm(ema);
        
    }

    
 
     
    @Test 
    public void testAddRoom() throws Exception {
        Building building2=new Building("FDE");
        Room room2 =new ClassRoom(building, "2.01", 30);
        sdi.addBuilding(building2);        
        sdi.addRoom(room2);
        
    }
    @Test(expected = AlreadyExistingRoomException.class)
    public void testAddExistingRoom() throws Exception{
        Building building2=new Building("FDE");
        Room room2 =new ClassRoom(building, "2.01", 30);
        sdi.addBuilding(building2);        
        sdi.addRoom(room2);
        sdi.addRoom(room2);
    }
    
    @Test(expected= AlreadyExistingBuildingException.class)
    public void testAddRoomNoneExistingBuilding()throws Exception{
        Building building2=new Building("FDE");        
        Room room2= new ClassRoom (building2, "2.01", 30);
        sdi.addRoom(room2);
        
    }
    /**
     * Test of removeRoom method, of class SpaceDAOImpl.
     */
//    @Test
    public void testRemoveRoom() throws Exception {
    }

    /**
     * Test of getRoomById method, of class SpaceDAOImpl.
     */
    @Test
    public void testGetRoomById() {
       
        assertEquals(room, sdi.getRoomById(room.getRoomId()));
    }

    /**
     * Test of getAllRooms method, of class SpaceDAOImpl.
     */
    @Test
    public void testGetAllRooms() {
        
        final List<Room> expected = new ArrayList<Room>();
        expected.add(room);       
      
        
        List<Room> result = sdi.getAllRooms();
        
        assertEquals(expected,result);
        
        
    }

    /**
     * Test of addBuilding method, of class SpaceDAOImpl.
     */
    @Test
    public void testAddBuilding() throws Exception {
         Building building2=new Building("FDE");              
        sdi.addBuilding(building2);
    }

    @Test(expected=AlreadyExistingBuildingException.class)
    public void testAddExixtingBuilding() throws Exception {
        Building building2=new Building("FDE"); 
        sdi.addBuilding(building);               
        sdi.addBuilding(building);
    }
    
    /**
     * Test of modifyBuilding method, of class SpaceDAOImpl.
     */
   // @Test
    public void testModifyBuilding() throws Exception {
    }

    /**
     * Test of removeBuilding method, of class SpaceDAOImpl.
     */
    //@Test
    public void testRemoveBuilding() throws Exception {
    }

    /**
     * Test of getBuildingByName method, of class SpaceDAOImpl.
     */
    @Test
    public void testGetBuildingByName() throws Exception {
         building=new Building("EPS");
               
        assertEquals(building, sdi.getBuildingByName("EPS"));
    }

    /**
     * Test of getAllBuildings method, of class SpaceDAOImpl.
     */
    @Test
    public void testGetAllBuildings() {
        
        
        final List<Building> expected = new ArrayList<Building>();
        expected.add(building);
        
       
        List<Building> result = sdi.getAllBuildings();
      
        assertEquals(expected,result);
        
        
    }

    /**
     * Test of getAllRoomsOfOneBuilding method, of class SpaceDAOImpl.
     */
    @Test
    public void testGetAllRoomsOfOneBuilding() throws Exception {
        
     
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(room);       
        building.setRooms(rooms);
        assertEquals(rooms, sdi.getAllRoomsOfOneBuilding(building));
        
    }

    /**
     * Test of getAllRoomsOfOneType method, of class SpaceDAOImpl.
     */
    @Test
    public void testGetAllRoomsOfOneType() throws Exception {
        Room room2 = new LaboratoryRoom(building, "2.1", 10);
        Room room3 = new MeetingRoom(building, "1.08", 50);
        Room room4 = new ClassRoom(building, "3.01", 100);
        sdi.addRoom(room2);
        sdi.addRoom(room3);
        sdi.addRoom(room4);
        List<Room> meetingRooms = new ArrayList<Room>();
        meetingRooms.add(room3);
        assertEquals(meetingRooms, sdi.getAllRoomsOfOneType("MeetingRoom"));
    }

    /**
     * Test of getAllRoomsOfOneTypeAndOneBuilding method, of class SpaceDAOImpl.
     */
    @Test
    public void testGetAllRoomsOfOneTypeAndOneBuilding() throws Exception {
    }
    
    
   
    private EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBooker");
        return emf.createEntityManager();
    }


     @After
    public void tearDown() throws Exception{
        
        ema = sdi.getEm();
        if (ema.isOpen()) ema.close();
        
        ema = getEntityManager();
        ema.getTransaction().begin();
   
        Query query2=ema.createQuery("DELETE FROM Room");
        Query query3=ema.createQuery("DELETE FROM Building");
  
        int deleteRecords=query2.executeUpdate();
        deleteRecords=query3.executeUpdate();
      
        ema.getTransaction().commit();
        ema.close();
        System.out.println("All records have been deleted.");
         
    }

}