/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.SpaceDAOImpl;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.NonBuildingException;
import org.classbooker.entity.Building;
import org.classbooker.entity.Room;

/**
 *
 * @author saida
 */
public class SpaceMgrServiceImpl implements SpaceMgrService{
    private  Logger log = Logger.getLogger("MiLogger");
    private SpaceDAO spd;

    public SpaceMgrServiceImpl() {
        spd = new SpaceDAOImpl();
    }

    public SpaceDAO getSpd() {
        return spd;
    }

    public void setSpd(SpaceDAO spd) {
        this.spd = spd;
    }
    
    
    
    
    
    @Override
    public void addRoom(String number, String buildingName, int capacity, String type) throws DAOException {
        Room newRoom = null; 
        Building building = new Building(buildingName);
        try {

            Constructor classType = Class.forName("org.classbooker.entity." + type)
                    .getConstructor(Building.class, String.class, int.class);

            newRoom = (Room) classType.newInstance(building,
                    number, capacity);

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            log.warning("Error");
        }
        spd.addRoom(newRoom);
    }

    @Override
    public void addBuilding(String name) throws DAOException {
        Building building = new Building(name);
        spd.addBuilding(building);
    }

    @Override
    public void deleteRoom(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteBuilding(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Room modifyRoom(long id, String buildingName, int capacity, String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Building modifyBuilding(long id, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
