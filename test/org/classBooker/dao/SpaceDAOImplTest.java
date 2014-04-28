/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.classBooker.entity.Building;
import org.classBooker.entity.ClassRoom;
import org.classBooker.entity.Room;
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
     EntityTransaction transaction;
     SpaceDAOImpl sdi;
     Room room;
     Building building;
    public SpaceDAOImplTest() {
        this.sdi=new SpaceDAOImpl();
    }
    
    @Before
    public void setUp() {
        em  = context.mock(EntityManager.class);
        transaction = context.mock(EntityTransaction.class);
        sdi.setEm(em);
        
        
    }

    
 
     
    @Test
    public void testAddRoom() throws Exception {
        building=new Building("EPS");
        room=new ClassRoom(building, "2.01", 30);
        transactionExpectations();
        context.checking(new Expectations() {{  
                oneOf (em).find(Room.class, room.getRoomId());
                will(returnValue(null));  //clubExist -> false                
                ignoring (em).find(Room.class, null);
                
                oneOf (em).persist(room);  
                }});
        
        sdi.addRoom(room);
        
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
  //  @Test
    public void testGetRoomById() {
    }

    /**
     * Test of getAllRooms method, of class SpaceDAOImpl.
     */
  //  @Test
    public void testGetAllRooms() {
    }

    /**
     * Test of addBuilding method, of class SpaceDAOImpl.
     */
   // @Test
    public void testAddBuilding() throws Exception {
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
    //@Test
    public void testGetBuildingByName() throws Exception {
    }

    /**
     * Test of getAllBuildings method, of class SpaceDAOImpl.
     */
    //@Test
    public void testGetAllBuildings() {
    }

    /**
     * Test of getAllRoomsOfOneBuilding method, of class SpaceDAOImpl.
     */
    //@Test
    public void testGetAllRoomsOfOneBuilding() throws Exception {
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
}
