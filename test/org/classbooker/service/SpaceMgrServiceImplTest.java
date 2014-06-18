/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.exception.AlreadyExistingBuildingException;
import org.classbooker.dao.exception.AlreadyExistingRoomException;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectRoomException;
import org.classbooker.dao.exception.IncorrectTypeException;
import org.classbooker.dao.exception.NonBuildingException;
import org.classbooker.entity.Building;
import org.classbooker.entity.ClassRoom;
import org.classbooker.entity.Room;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import static org.jmock.Expectations.throwException;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author saida
 */
public class SpaceMgrServiceImplTest {
     Mockery context = new JUnit4Mockery();
     SpaceDAO spdao;
     SpaceMgrServiceImpl space;
     Building building;
     long id;
     Room room ;
    
     
     
    public SpaceMgrServiceImplTest() {
    }
    
    @Before
    public void setUp() {
        space = new SpaceMgrServiceImpl();
        spdao = context.mock(SpaceDAO.class);
        space.setSpd(spdao);
        building = new Building("EPS");
        room= new ClassRoom(building, "2.65", 45);
    }

    @Test
    public void addroom() throws DAOException {
        setExpectationsAddRoom();
        space.addRoom("2.65", "EPS", 45, "ClassRoom");
        assertEquals(spdao.getRoomByNbAndBuilding("2.65", "EPS"),room);  
    }

    @Test(expected =IncorrectRoomException.class)
    public void testAddRoomnegativeCapacity()throws Exception{
        context.checking(new Expectations(){{ 
            oneOf(spdao).addRoom("2.08", "EPS", -40, "ClassRoom"); will(throwException(new IncorrectRoomException()));       
         }});
        long id = space.addRoom("2.08", "EPS", -40, "ClassRoom");
    }
    
    @Test(expected = NonBuildingException.class)
    public void testAddRoomNoneExistingBuilding() throws Exception {
        context.checking(new Expectations(){{ 
            oneOf(spdao).addRoom("2.01", "prova", 30, "ClassRoom"); will(throwException(new NonBuildingException()));
            oneOf(spdao).getRoomByNbAndBuilding("2.01", "prova");
         }});
        space.addRoom("2.01", "prova", 30, "ClassRoom");
    }
     @Test(expected = IncorrectTypeException.class)
     public void testAddRoomIncorrectTypeRoom()throws Exception{
       context.checking(new Expectations(){{ 
            oneOf(spdao).addRoom("2.01", "EPS", 45, "ComputerRoom");will(throwException(new IncorrectTypeException()));
           
         }});  
         space.addRoom("2.01", "EPS", 45, "ComputerRoom");
     }
    
    @Test(expected = AlreadyExistingRoomException.class)
    public void testAddExistingRoom() throws Exception {
        context.checking(new Expectations(){{ 
            oneOf(spdao).addRoom("0.15", "EPS", 15, "ClassRoom");will(throwException(new AlreadyExistingRoomException()));
            oneOf(spdao).getRoomByNbAndBuilding("0.15", "EPS"); 
         }}); 
        space.addRoom("0.15", "EPS", 15, "ClassRoom");
        
    }
    private void setExpectationsAddRoom() throws DAOException {
           context.checking(new Expectations(){{ 
            oneOf(spdao).addRoom("2.65", "EPS", 45, "ClassRoom");
            oneOf(spdao).getRoomByNbAndBuilding("2.65", "EPS"); will(returnValue(room));
         }});  
    }
    @Test
    public void addBuilding() throws DAOException{
        setExpectationsAddBuilding();
        space.addBuilding("EPS");
        assertEquals(spdao.getBuildingByName("EPS"), building);
        
    }

    @Test(expected = AlreadyExistingBuildingException.class)
    public void testAddExistingBuilding() throws Exception{
        context.checking(new Expectations(){{
        oneOf(spdao).addBuilding(building); will(throwException(new AlreadyExistingBuildingException()));
        oneOf(spdao).getBuildingByName("EPS"); 
        }});
        space.addBuilding("EPS");
    }
    
    
    private void setExpectationsAddBuilding() throws DAOException {
        context.checking(new Expectations(){{
        oneOf(spdao).addBuilding(building);
        oneOf(spdao).getBuildingByName("EPS");will(returnValue(building));
        }});
    }

    
}
