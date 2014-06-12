/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.classbooker.dao.exception.AlreadyExistingRoomException;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.NonBuildingException;
import org.classbooker.dao.exception.NoneExistingRoomException;
import org.classbooker.dao.exception.AlreadyExistingBuildingException;
import org.classbooker.entity.Building;
import org.classbooker.entity.Room;
import org.classbooker.dao.exception.AlredyExistReservationException;
import org.classbooker.dao.exception.IncorrectTypeException;

/**
 *
 * @author josepma, Saida, Genis
 */
public interface SpaceDAO {

    /**
     * Add a new Room in a existing Buiding
     *
     * @param room new room
     * @return Room Id
     * @throws AlreadyExistingRoomException
     * @throws NonBuildingException
     */
    long addRoom(Room room) throws DAOException;

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
     * Modify a exisiting Room by capacity and type, If have reservations, don't have modify room
     *
     * @param room The room that was modified.
     * @param newType If the newType is null, then this is not changed.
     * @param capacity If the capacity is 0, then this is not changed.
     * @return Room modified
     * @throws AlredyExistReservationException
     */
     Room modifyRoom(Room room, String newType, int capacity) throws DAOException;

    /**
     * Remove a exisiting Room
     *
     * @param room old room
     * @throws NoneExistingRoomException If non exixting Room in DataBase
     * @throws AlredyExistReservationException If exist reservations in the room
     */
    void removeRoom(Room room) throws  DAOException;

    /**
     * Get a room by identifier, return null if non exist room
     *
     * @param id identifier room
     * @return Room 
     */
    Room getRoomById(long id);

    /**
     * Get all rooms, return list empty if don't have rooms in DataBase
     * @return List rooms 
     */
    List<Room> getAllRooms();

    /**
     * Add a new building
     *
     * @param building new building
     * @throws AlreadyExistingBuildingException
     * @throws AlreadyExistingRoomException
     */
    void addBuilding(Building building) throws DAOException;

    /**
     * Remove an existing building
     *
     * @param building old building
     * @throws NonBuildingException
     */
    void removeBuilding(Building building)throws DAOException;

    /**
     * Get building by name, if don't find it, return null     *
     * @param name building name
     * @return building 
     */
    Building getBuildingByName(String name);

    /**
     * Get a list of all buildings, And return list empty if don't have buildings in DataBase
     *
     * @return List Buildings. 
     */
    List<Building> getAllBuildings();

    /**
     * Get all rooms of a building, If non exist rooms in building return empty list,
     * If non exist building return null
     * @param building buiding to list its rooms
     * @return List Rooms. 
     */
    List<Room> getAllRoomsOfOneBuilding(String building);

    /**
     * Find all the rooms about one room type (MetingRoom, LaboratoryRoom or ClassRoom)
     * If incorrect type, return exception
     * Return empty list if non exist room with one type 
     * @param type the type room to list (MetingRoom, LaboratoryRoom or ClassRoom)
     * @return List of Rooms. And return empty list if non exist rooms
     * @throws IncorrectTypeException
     */
    List<Room> getAllRoomsOfOneType(String type) throws DAOException;

    /**
     * Find list of Rooms about one room Type(MetingRoom, LaboratoryRoom or ClassRoom) and one Building.class
     * If incorrect type, return exception
     * And return empty list if non exist rooms or/and one building
     * If non exist buiding return empty list too.
     * @param type  the type room to list (MetingRoom, LaboratoryRoom or ClassRoom)
     * @param building the building to list 
     * @return List of Rooms.
     * @throws IncorrectTypeException
     */
    List<Room> getAllRoomsOfOneTypeAndOneBuilding(String type,
            Building building)
            throws DAOException;

    /**
     * Find room about one building name and one room number
     * Return null if non exist building
     * Return null if non exist room in the building
     * @param roomNb The number about room
     * @param buildingName The building name
     * @return Room  
      */
    Room getRoomByNbAndBuilding(String roomNb, String buildingName);

    /**
     * Find all the rooms about one type, more than one capacity and one buiding
     * And return empty list if non exist rooms with this capacity, type or/and building
     * @param buildingName The building name
     * @param type the type room to list (MetingRoom, LaboratoryRoom or ClassRoom)
     * @param capacity list the rooms about this capacity or more
     * @return List of Rooms.
     */
    List<Room> getAllRoomsByTypeAndCapacity(String type, int capacity, String buildingName);

    /**
     * Change the entity Manager 
     * @param em Entity Manager
     */
    void setEm(EntityManager em);
}
