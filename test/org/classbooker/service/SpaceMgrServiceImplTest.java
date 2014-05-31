/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.entity.Building;
import org.classbooker.entity.ClassRoom;
import org.classbooker.entity.Room;
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

    private void setExpectationsAddRoom() throws DAOException {
           context.checking(new Expectations(){{ 
            oneOf(spdao).addRoom("2.65", "EPS", 45, "ClassRoom");
            oneOf(spdao).getRoomByNbAndBuilding("2.65", "EPS"); will(returnValue(room));
         }});  
    }

}
