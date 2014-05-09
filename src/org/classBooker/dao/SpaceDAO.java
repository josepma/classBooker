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
     * @throws org.classBooker.dao.exception.AlreadyExistingBuildingException
     * @throws org.classBooker.dao.exception.NonBuildingException
     */
    long addRoom (Room room) throws PersistException, IncorrectRoomException,
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
     * @throws org.classBooker.dao.exception.AlreadyExistingBuildingException
     * @throws org.classBooker.dao.exception.AlreadyExistingRoomException
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
    void modifyBuilding(Building building, String newName) throws PersistException, 
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
     * Find all the rooms about one room type (MetingRoom, LaboratoryRoom or ClassRoom)
     * @param Type
     * @return List<Room>     
     * @throws org.classBooker.dao.exception.IncorrectTypeException
     * 
     */
    List<Room> getAllRoomsOfOneType(String Type)throws IncorrectTypeException;
    
    /**
     * Find list of Rooms about one room Type(MetingRoom, LaboratoryRoom or ClassRoom)
     * and one Building.class
     * @param Type, Building
     * @param building
     * @return List<Room>
     * @throws org.classBooker.dao.exception.IncorrectBuildingException
     *  
     */
    List<Room> getAllRoomsOfOneTypeAndOneBuilding(String Type, 
                                            Building building)
                                            throws IncorrectBuildingException;
   
   /**
     *Find room about one building name and one room name/number
     *@param buildingName  The building name Ex.("EPS")
     *@param roomNb The number about room Ex.(2.01)
     *@return Room 
     * @throws org.classBooker.dao.exception.IncorrectBuildingException 
     * @throws org.classBooker.dao.exception.IncorrectRoomException 
     */
    Room getRoomByNbAndBuilding(String roomNb, String buildingName)throws IncorrectBuildingException, IncorrectRoomException;

    /**
     * Find all the rooms about one type, more than one capacity and one buiding
     * @param buildingName 
     * @param type 
     * @param capacity  
     * @return List rooms  
     * @throws org.classBooker.dao.exception.IncorrectBuildingException  
     */
    List<Room> getAllRoomsByTypeAndCapacity(String type, int capacity, String buildingName)throws IncorrectBuildingException;
}
