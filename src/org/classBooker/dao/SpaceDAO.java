/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.util.List;
import org.classBooker.dao.exception.AlreadyExistingBuildingException;
import org.classBooker.dao.exception.AlreadyExistingRoomException;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTypeException;
import org.classBooker.dao.exception.NonBuildingException;
import org.classBooker.dao.exception.PersistException;
import org.classBooker.entity.Building;
import org.classBooker.entity.Room;


/**
 *
 * @author josepma, Marc Solé, Carles Mònico
 */
public interface SpaceDAO {
    
    /**
     * Add a new Room in a existing Buiding 
     * @param room
     * @throws PersistException
     * @throws IncorrectRoomException
     * @throws org.classBooker.dao.exception.AlreadyExistingRoomException
     */
    void addRoom (Room room) throws PersistException, IncorrectRoomException,
                                AlreadyExistingRoomException, AlreadyExistingBuildingException,
                                NonBuildingException;
    /**
     * Remove a exisiting Room
     * @param room
     * @throws IncorrectRoomException
     */
    void removeRoom (Room room) throws IncorrectRoomException;

    /**
     * Get a room by identifier
     * @param id
     * @return
     */
    Room getRoomById(long id);

    /**
     * Get all rooms
     * @return
     */
    List<Room> getAllRooms();
    
    /**
     * Add a new building
     * @param building
     * @throws PersistException
     * @throws IncorrectBuildingException
     */
    void addBuilding(Building building)throws PersistException, 
                                                IncorrectBuildingException,
                                                AlreadyExistingBuildingException,
                                                AlreadyExistingRoomException;
    
    /**
     *
     * @param building
     * @throws PersistException
     * @throws IncorrectBuildingException
     */
    void modifyBuilding(Building building) throws PersistException, 
                                                IncorrectBuildingException;
    
    /**
     * Remove a existing building
     * @param building
     * @throws IncorrectBuildingException
     */
    void removeBuilding(Building building)throws IncorrectBuildingException;
    
    /**
     * Get building by name
     * @param name
     * @return
     * @throws IncorrectBuildingException
     */
    Building getBuildingByName(String name)throws IncorrectBuildingException;
    
    /**
     * Get a list of all buildings
     * @return
     */
    List<Building> getAllBuildings();
    
    
    /**
     * Get all room of a building 
     * @param building
     * @return
     * @throws IncorrectBuildingException
     */
    List<Room> getAllRoomsOfOneBuilding(Building building) 
                                        throws IncorrectBuildingException;
    
    /**
     *
     * @param Type
     * @return
     * @throws IncorrectTypeException
     */
    List<Room> getAllRoomsOfOneType(String Type)throws IncorrectTypeException;
    
    /**
     *
     * @param Type
     * @param building
     * @return
     * @throws IncorrectBuildingException
     */
    List<Room> getAllRoomsOfOneTypeAndOneBuilding(String Type, 
                                            Building building)
                                            throws IncorrectBuildingException;
   
    Room getRoomByNbAndBuilding(String roomNb, String buildingName)throws IncorrectBuildingException, IncorrectRoomException;
    List<Room> getAllRoomsByTypeAndCapacity(String type, int capacity, String buildingName)throws IncorrectBuildingException;
}
