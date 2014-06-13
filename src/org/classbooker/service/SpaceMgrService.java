/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import java.util.List;
import javax.persistence.EntityManager;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.entity.Building;
import org.classbooker.entity.Room;

/**
 *
 * @author josepma
 */
public interface SpaceMgrService {
    /**
     * To get the SpaceDao
     * @return spd SpaceDAO
     */
    public SpaceDAO getSpd();
    
    /**
     * To set the SpaceDAO
     * @param spd SpaceDao
     */
    public void setSpd(SpaceDAO spd);
    
    /**
     * Add new Room in a existing Building with String parameters
     * @param number Represents the number room in the building
     * @param buildingName The building name room 
     * @param capacity People capacity in the room
     * @param type The type room (MetingRoom, LaboratoryRoom or ClassRoom)
     * @return id new room
     * @throws AlreadyExistingRoomException
     * @throws NonBuildingException
     */
    public long addRoom(String number, String buildingName, int capacity, String type) throws DAOException;
    
    /**
     * Add a new building
     *@param name the name of the building
     * @throws AlreadyExistingBuildingException
     * @throws AlreadyExistingRoomException
     */
    public void addBuilding(String name) throws DAOException;
    
    /**
     * Remove a existing Room
     * @param id of room.
     * @throws NoneExistingRoomException
     */
    public void deleteRoom(long id)throws DAOException;
    
    /**
    * Remove an existing Building
    * @param name the name of the building.
    * @throws NonBuildingException
    */
    public void deleteBuilding(String name)throws DAOException;
    
    /**
     * Not implemented yet.
     * @param id
     * @param buildingName
     * @param capacity
     * @param type
     * @return 
     */
    public Room modifyRoom(long id, String buildingName, int capacity, String type);
    
    /**
     * Not implemented yet.
     * @param id
     * @param name
     * @return 
     */
    public Building modifyBuilding(long id, String name);
    
    /**
     * Get a room by identifier, return null if non exist room
     * @param id this is the id of the room.
     * @return the room of this id.
     * @throws NoneExistingRoomException
     */
    public Room getRoomById(long id) throws DAOException;
    
    /**
     * Get a room by Number and Building, return null if non exist room
     * @param number this is the number of the room
     * @param buildingName this is the name of the building's room.
     * @return room of the Number and Building
     * @throws NoneExistingRoomException
     */
    public Room getRoomByNbAndBuilding(String number, String buildingName) throws DAOException;
    
    
    /**
     * Get a building by Name, return null if non exist Building.
     * @param name this is the name of the building
     * @return the building of this name.
     * @throws NonBuildingException
     */
    public Building getBuildingbyName(String name) throws DAOException;
    
    /**
     * Change the entity Manager 
     * @param em Entity Manager
     */
    public void setEm(EntityManager em);

    public List<Building> getAllBuilding();
    
}
