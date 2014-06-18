/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.SpaceDAOImpl;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectTypeException;
import org.classbooker.dao.exception.NonBuildingException;
import org.classbooker.dao.exception.NoneExistingRoomException;
import org.classbooker.entity.Building;
import org.classbooker.entity.Room;

/**
 *
 * @author saida, genis
 */
public class SpaceMgrServiceImpl implements SpaceMgrService{
    private  Logger log = Logger.getLogger("MiLogger");
    private SpaceDAO spd;
    private EntityManager em;

    
    public SpaceMgrServiceImpl() {
        spd = new SpaceDAOImpl();
    }

    /**
     * To get the SpaceDao
     * @return spd SpaceDAO
     */
    @Override
    public SpaceDAO getSpd() {
        return spd;
    }

    /**
     * To set the SpaceDAO
     * @param spd SpaceDao
     */
    @Override
    public void setSpd(SpaceDAO spd) {
        this.spd = spd;
    }
    
    /**
     * Get a room by identifier, return null if non exist room
     * @param id this is the id of the room.
     * @return the room of this id.
     * @throws NoneExistingRoomException
     */
    @Override
    public Room getRoomById(long id)throws DAOException{
        return spd.getRoomById(id);
        
    }
    
    /**
     * Get a room by Number and Building, return null if non exist room
     * @param number this is the number of the room
     * @param buildingName this is the name of the building's room.
     * @return room of the Number and Building
     * @throws NoneExistingRoomException
     */
    @Override
    public Room getRoomByNbAndBuilding(String number, String buildingName) throws DAOException{
        return spd.getRoomByNbAndBuilding(number, buildingName);
    }
    
    
    /**
     * Get a building by Name, return null if non exist Building.
     * @param name this is the name of the building
     * @return the building of this name.
     * @throws NonBuildingException
     */
    @Override
    public Building getBuildingbyName(String name) throws DAOException{
        return spd.getBuildingByName(name);
    
    }  
    
    
    /**
     * Add new Room in a existing Building with String parameters
     * @param number Represents the number room in the building
     * @param buildingName The building name room 
     * @param capacity People capacity in the room
     * @param type The type room (MetingRoom, LaboratoryRoom or ClassRoom)
     * @return id new room
     * @throws NonBuildingException
     */
    @Override
    public long addRoom(String number, String buildingName, int capacity, String type) throws DAOException {       
        return spd.addRoom(number, buildingName, capacity, type);
    }

    /**
     * Add a new building
     * @param name the name of the building
     * @throws AlreadyExistingBuildingException
     * @throws AlreadyExistingRoomException
     */
    @Override
    public void addBuilding(String name) throws DAOException {
        Building building = new Building(name);        
        spd.addBuilding(building);
    }
    
    /**
     * Remove a existing Room
     * @param id of room.
     * @throws NoneExistingRoomException
     */
    @Override
    public void deleteRoom(long id) throws DAOException {
        Room room= spd.getRoomById(id);
        if (room==null)
            throw new NoneExistingRoomException();
        spd.removeRoom(room);
    }

    /**
     * Remove an existing Building
     * @param name the name of the building.
     * @throws NonBuildingException
     */
    @Override
    public void deleteBuilding(String name)throws DAOException {
        Building building = spd.getBuildingByName(name);
        spd.removeBuilding(building);
    }

    /**
     * Not implemented yet.
     * @param id
     * @param buildingName
     * @param capacity
     * @param type
     * @return 
     */
    @Override
    public Room modifyRoom(long id, String buildingName, int capacity, String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Not implemented yet.
     * @param id
     * @param name
     * @return 
     */
    @Override
    public Building modifyBuilding(long id, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Change the entity Manager 
     * @param em Entity Manager
     */
    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }
    /**
     * To get the Entity Manager
     * @return em Entity manager
     */
    public EntityManager getEm() {
        return em;
    }

    @Override
    public List<Building> getAllBuilding() {
        return spd.getAllBuildings();
    }
    
    
}
