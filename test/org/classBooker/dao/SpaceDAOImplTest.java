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
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.classBooker.dao.exception.AlreadyExistingBuildingException;
import org.classBooker.dao.exception.AlreadyExistingRoomException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.PersistException;
import org.classBooker.entity.Building;
import org.classBooker.entity.ClassRoom;
import org.classBooker.entity.Room;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author saida
 */
public class SpaceDAOImplTest {
     Mockery context = new JUnit4Mockery();
     EntityManager em;
     EntityManager ema;
     EntityTransaction transaction;
     SpaceDAOImpl sdi;
     Room room;
     Building building;
     private Query query;
     
    public SpaceDAOImplTest() {
        this.sdi=new SpaceDAOImpl();
        
    }
    
    @Before
    public void setUp() {
        em  = context.mock(EntityManager.class);
        transaction = context.mock(EntityTransaction.class);
        //sdi.setEm(em);
        building=new Building("EPS");
        room=new ClassRoom(building, "2.01", 30);
        query = context.mock(Query.class);
        ema = getEntityManager();
        ema.getTransaction().begin();
        ema.persist(room);
        ema.persist(building);
        ema.getTransaction().commit();
        sdi.setEm(ema);
    }

    
 
     
    //@Test
    public void testAddRoom() throws Exception {
        building=new Building("EPS");
        room=new ClassRoom(building, "2.01", 30);
        transactionExpectations();
        context.checking(new Expectations() {{  
                oneOf (em).find(Room.class, room.getRoomId());
                will(returnValue(null));               
                ignoring (em).find(Room.class, null);                
                oneOf (em).persist(room);  
                }});
        // capacitat 0 o negativa
        // ja existeix la room
        //comprobar que existeixi el building
        
        sdi.addRoom(room);
        
    }
    @Test(expected = AlreadyExistingRoomException.class)
    public void testAddExistingRoom() throws Exception{
        sdi.addBuilding(building);
        sdi.addRoom(room);
        sdi.addRoom(room);
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
   // @Test
    public void testGetRoomById() {
        building=new Building("EPS");
        room=new ClassRoom(building, "2.01", 30);
        transactionExpectations();
         context.checking(new Expectations() {{                 
                oneOf (em).find(Room.class, room.getRoomId());
                will(returnValue(room));                  
                           
        }});  
         
        assertEquals(room, sdi.getRoomById(room.getRoomId()));
    }

    /**
     * Test of getAllRooms method, of class SpaceDAOImpl.
     */
  //  @Test
    public void testGetAllRooms() {
        building=new Building("EPS");
        room=new ClassRoom(building, "2.01", 30);
        
        
        final List<Room> expected = new ArrayList<Room>();
        expected.add(room);
        
        transactionExpectations();

        context.checking(new Expectations() {{                 
                oneOf (em).createQuery("SELECT r FROM Room r");
                will(returnValue(query));
                
                oneOf (query).getResultList();
                will(returnValue(expected));
                
        }});

        List<Room> result = sdi.getAllRooms();
        
        assertEquals(expected,result);
        
        
    }

    /**
     * Test of addBuilding method, of class SpaceDAOImpl.
     */
 //   @Test
    public void testAddBuilding() throws Exception {
        building=new Building("FDE");
        
        transactionExpectations();
        context.checking(new Expectations() {{  
                oneOf (em).find(Building.class, building.getBuildingName());
                will(returnValue(null));                  
                ignoring (em).find(Building.class, null);                
                oneOf (em).persist(building);  
                }});
                 
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
   // @Test
    public void testGetBuildingByName() throws Exception {
         building=new Building("EPS");
       
        transactionExpectations();
         context.checking(new Expectations() {{                 
                oneOf (em).find(Building.class, building.getBuildingName());
                will(returnValue(building));                  
                           
        }});  
         
        assertEquals(building, sdi.getBuildingByName("EPS"));
    }

    /**
     * Test of getAllBuildings method, of class SpaceDAOImpl.
     */
   // @Test
    public void testGetAllBuildings() {
         building=new Building("EPS");
        
        final List<Building> expected = new ArrayList<Building>();
        expected.add(building);
        
        transactionExpectations();

        context.checking(new Expectations() {{                 
                oneOf (em).createQuery("SELECT b FROM Building b");
                will(returnValue(query));
                
                oneOf (query).getResultList();
                will(returnValue(expected));
                
        }});

        List<Building> result = sdi.getAllBuildings();
        
        assertEquals(expected,result);
        
        
    }

    /**
     * Test of getAllRoomsOfOneBuilding method, of class SpaceDAOImpl.
     */
    @Test
    public void testGetAllRoomsOfOneBuilding() throws Exception {
        
      //  building=new Building("EPS");
       // room=new ClassRoom(building, "2.01", 30);
        
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(room);
        sdi.addRoom(room);
        building.setRooms(rooms);
        assertEquals(rooms, sdi.getAllRoomsOfOneBuilding(building));
        
    }

    /**
     * Test of getAllRoomsOfOneType method, of class SpaceDAOImpl.
     */
    //@Test
    public void testGetAllRoomsOfOneType() throws Exception {
    }

    /**
     * Test of getAllRoomsOfOneTypeAndOneBuilding method, of class SpaceDAOImpl.
     */
    //@Test
    public void testGetAllRoomsOfOneTypeAndOneBuilding() throws Exception {
    }
    
    
    private void transactionExpectations(){
        context.checking(new Expectations() {{
                allowing (em).getTransaction(); will(returnValue(transaction));        
                allowing (transaction).begin();
                allowing (transaction).commit();

        }});
    }
    private EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBooker");
        return emf.createEntityManager();
    }
}
//delete reservation, room, building, user 
