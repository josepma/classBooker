/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classBooker.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.classBooker.dao.exception.AlreadyExistingBuildingException;
import org.classBooker.dao.exception.AlreadyExistingRoomException;
import org.classBooker.dao.exception.AlredyExistReservationException;
import org.classBooker.dao.exception.DAOException;
import org.classBooker.dao.exception.IncorrectBuildingException;
import org.classBooker.dao.exception.IncorrectRoomException;
import org.classBooker.dao.exception.IncorrectTypeException;
import org.classBooker.dao.exception.NonBuildingException;
import org.classBooker.dao.exception.NoneExistingRoomException;
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
     *
     * @param room
     * @return Room Id
     * @throws PersistException
     * @throws IncorrectRoomException
     * @throws org.classBooker.dao.exception.AlreadyExistingRoomException
     * @throws org.classBooker.dao.exception.AlreadyExistingBuildingException
     * @throws org.classBooker.dao.exception.NonBuildingException
     */
    long addRoom(Room room) throws DAOException;

    /**
     * Modify a exisiting Room by capacity and type.
     *
     * @param room The room that was modified.
     * @param newType If the newType is null, then this is not changed.
     * @param capacity If the capacity is 0, then this is not changed.
     * @throws org.classBooker.dao.exception.PersistException
     * @throws org.classBooker.dao.exception.AlreadyExistingRoomException
     * @throws org.classBooker.dao.exception.NonBuildingException
     * @throws org.classBooker.dao.exception.AlredyExistReservationException
     * @throws org.classBooker.dao.exception.NoneExistingRoomException
     */
     void modifyRoom(Room room, String newType, int capacity) throws DAOException;

    /**
     * Remove a exisiting Room
     *
     * @param room 
     * @throws NoneExistingRoomException If non exixting Room in DataBase
     */
    void removeRoom(Room room) throws  DAOException;

    /**
     * Get a room by identifier
     *
     * @param id
     * @return Room return null if non exist room
     */
    Room getRoomById(long id);

    /**
     * Get all rooms
     *
     * @return List rooms return list empty if don't have rooms in DataBase
     */
    List<Room> getAllRooms();

    /**
     * Add a new building
     *
     * @param building
     * @throws PersistException
     * @throws IncorrectBuildingException
     * @throws org.classBooker.dao.exception.AlreadyExistingBuildingException
     * @throws org.classBooker.dao.exception.AlreadyExistingRoomException
     */
    void addBuilding(Building building) throws DAOException;

    /**
     * Remove a existing building
     *
     * @param building
     * @throws IncorrectBuildingException
     */
    void removeBuilding(Building building) throws DAOException;

    /**
     * Get building by name
     *
     * @param name
     * @return building if don't find it, return null
     * @throws IncorrectBuildingException
     */
    Building getBuildingByName(String name) throws DAOException;

    /**
     * Get a list of all buildings
     *
     * @return
     */
    List<Building> getAllBuildings();

    /**
     * Get all room of a building
     *
     * @param building
     * @return List Rooms if non exist rooms in building return empty list
     * @throws IncorrectBuildingException 
     */
    List<Room> getAllRoomsOfOneBuilding(String building)
            throws DAOException;

    /**
     * Find all the rooms about one room type (MetingRoom, LaboratoryRoom or
     * ClassRoom)
     *
     * @param type
     * @return List<Room> return empty list if non exist rooms
     * @throws org.classBooker.dao.exception.IncorrectTypeException
     *
     */
    List<Room> getAllRoomsOfOneType(String type) throws DAOException;

    /**
     * Find list of Rooms about one room Type(MetingRoom, LaboratoryRoom or
     * ClassRoom) and one Building.class
     *
     * @param type, Building
     * @param building
     * @return List<Room> return empty list if non exist rooms
     * @throws org.classBooker.dao.exception.IncorrectBuildingException
     *
     */
    List<Room> getAllRoomsOfOneTypeAndOneBuilding(String type,
            Building building)
            throws DAOException;

    /**
     * Find room about one building name and one room name/number
     *
     * @param buildingName The building name Ex.("EPS")
     * @param roomNb The number about room Ex.(2.01)
     * @return Room return null if non exist room
     * @throws org.classBooker.dao.exception.IncorrectBuildingException
     * @throws org.classBooker.dao.exception.IncorrectRoomException
     */
    Room getRoomByNbAndBuilding(String roomNb, String buildingName) throws DAOException;

    /**
     * Find all the rooms about one type, more than one capacity and one buiding
     *
     * @param buildingName
     * @param type
     * @param capacity
     * @return List<Room> return empty list if non exist rooms
     * @throws org.classBooker.dao.exception.IncorrectBuildingException
     */
    List<Room> getAllRoomsByTypeAndCapacity(String type, int capacity, String buildingName) throws DAOException;
    void setEm(EntityManager em);
}
