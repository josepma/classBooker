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
    
    public Room getRoomById(long id)throws DAOException{
        return spd.getRoomById(id);
        
    }
    
    public Room getRoomByNbAndBuilding(String number, String buildingName) throws DAOException{
        return spd.getRoomByNbAndBuilding(number, buildingName);
    }
    
    
    public Building getBuildingbyName(String name) throws DAOException{
        return spd.getBuildingByName(name);
    
    }  
    
    @Override
    public long addRoom(String number, String buildingName, int capacity, String type) throws DAOException {
       return spd.addRoom(number, buildingName, capacity, type);
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
