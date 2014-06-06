/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.classbooker.dao.SpaceDAOImpl;
import org.classbooker.entity.Building;
import org.classbooker.entity.Room;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author saida
 */
public class SpaceMrcServiceImplIntegTest {
    SpaceMgrServiceImpl sms;
    SpaceDAOImpl space;
    EntityManager ema;
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
    //User story: AddSpace (Room)
    
    @Test
    public void testAddRoom()throws Exception{
        long id = sms.addRoom("2.08", "EPS", 40, "ClassRoom");
        Room room =space.getRoomById(id);
        assertEquals(room.getBuilding().getBuildingName(),"EPS");
        assertEquals(room.getCapacity(),40);
        assertEquals(room.getNumber(),"2.08");
        sms.deleteRoom(id);
    }
    
    @Test
    public void testAddBuilding() throws Exception { 
        Building FDE = new Building("FDE");
        sms.addBuilding("FDE"); 
        assertEquals(sms.getBuildingbyName("FDE"), FDE);
        sms.deleteBuilding("FDE");
    }
   private EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBookerIntegration");
        return emf.createEntityManager();
    }
}
