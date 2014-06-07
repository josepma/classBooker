/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.classbooker.dao.exception.AlreadyExistingBuildingException;
import org.classbooker.dao.exception.AlreadyExistingRoomException;
import org.classbooker.dao.exception.AlredyExistReservationException;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectTypeException;
import org.classbooker.dao.exception.NonBuildingException;
import org.classbooker.dao.exception.NoneExistingRoomException;
import org.classbooker.entity.Building;
import org.classbooker.entity.Room;

/**
 *
 * @author saida
 */
public class SpaceDAOImpl implements SpaceDAO {

    private EntityManager em;
    private  Logger log = Logger.getLogger("MiLogger");

    /**
     * To get the Entity Manager
     * @return em Entity manager
     */
    public EntityManager getEm() {
        return em;
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
     * Add a new Room in a existing Buiding
     *
     * @param room new room
     * @return Room Id
     * @throws AlreadyExistingRoomException
     * @throws NonBuildingException
     */
    @Override
    public long addRoom(Room room) throws  DAOException{
        
        Building building = getBuildingByName(room.getBuilding().getBuildingName());
        if (building == null) {
            throw new NonBuildingException();
        }
        if (roomExist(room)) {
            throw new AlreadyExistingRoomException();
        }
        em.getTransaction().begin();
        em.persist(room);
        building.getRooms().add(room);
        em.getTransaction().commit();
  

        return room.getRoomId();

    }
    
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
    @Override
    public long addRoom(String number, String buildingName, int capacity, String type) throws DAOException{
     Room newRoom = null; 
        Building building = new Building(buildingName);
        try {

            Constructor classType = Class.forName("org.classbooker.entity." + type)
                    .getConstructor(Building.class, String.class, int.class);

            newRoom = (Room) classType.newInstance(building,
                    number, capacity);

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            log.warning("Error to create new room");
        }
        addRoom(newRoom);
        return newRoom.getRoomId(); 
    }
    
    /**
     * Get a room by identifier, return null if non exist room
     *
     * @param id identifier room
     * @return Room 
     */
    @Override
    public Room getRoomById(long id) {
        Room room = null;

     
        room = (Room) em.find(Room.class, id);
     

        return room;
    }

    /**
     * Get all rooms, return list empty if don't have rooms in DataBase
     * @return List rooms 
     */
    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = null;
        Query query;

        query = em.createQuery("SELECT r FROM Room r");
        rooms = (List<Room>) query.getResultList();

        return rooms;

    }

    /**
     * Add a new building
     *
     * @param building new building
     * @throws AlreadyExistingBuildingException
     * @throws AlreadyExistingRoomException
     */
    @Override
    public void addBuilding(Building building) throws DAOException
             {

        em.getTransaction().begin();
        checkExistingBuildingOrRoom(building);
        em.persist(building);
        em.getTransaction().commit();

    }

    /**
     * Get building by name, if don't find it, return null     *
     * @param name building name
     * @return building 
     */
    @Override
    public Building getBuildingByName(String name) {
        Building building = null;   
      
        building = (Building) em.find(Building.class, name);   
   
        return building;
    }


    /**
     * Get a list of all buildings, And return list empty if don't have buildings in DataBase
     *
     * @return List Buildings. 
     */
    @Override
    public List<Building> getAllBuildings() {
        List<Building> buildings = null;
        Query query;

      
        query = em.createQuery("SELECT b FROM Building b");
        buildings = (List<Building>) query.getResultList();
     

        return buildings;
    }

    /**
     * Get all rooms of a building, If non exist rooms in building return empty list,
     * If non exist building return null
     * @param building buiding to list its rooms
     * @return List Rooms. 
     */
    @Override
    public List<Room> getAllRoomsOfOneBuilding(String buildingName) {

        Building building = getBuildingByName(buildingName);
        if(building==null)
            return null;
        return building.getRooms();

    }

    /**
     * Find all the rooms about one room type (MetingRoom, LaboratoryRoom or ClassRoom)
     * If incorrect type, return exception
     * Return empty list if non exist room with one type 
     * @param type the type room to list (MetingRoom, LaboratoryRoom or ClassRoom)
     * @return List of Rooms. And return empty list if non exist rooms
     * @throws IncorrectTypeException
     */
    @Override
    public List<Room> getAllRoomsOfOneType(String type) throws DAOException {
        List<Room> rooms = null;
        Query query;

        if (!"MeetingRoom".equals(type) && !"LaboratoryRoom".equals(type) && !"ClassRoom".equals(type)) {
            throw new IncorrectTypeException();
        }

     
        query = em.createQuery("SELECT r FROM " + type + " r");
        rooms = (List<Room>) query.getResultList();
   

        return rooms;
    }

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
    @Override
    public List<Room> getAllRoomsOfOneTypeAndOneBuilding(String type, Building building) {
        List<Room> roomsOneType = null;
        List<Room> roomsOneTypeOneBuilding = new ArrayList();

        Query query;

   
        query = em.createQuery("SELECT r FROM " + type + " r");
        roomsOneType = (List<Room>) query.getResultList();
   

        for (Room r : roomsOneType) {
            if (r.getBuilding() == building) {
                roomsOneTypeOneBuilding.add(r);
            }
        }
        return roomsOneTypeOneBuilding;
    }

    /**
     * Find room about one building name and one room number
     * Return null if non exist building
     * Return null if non exist room in the building
     * @param roomNb The number about room
     * @param buildingName The building name
     * @return Room  
      */
    @Override
    public Room getRoomByNbAndBuilding(String roomNb, String buildingName) {
        Building building = getBuildingByName(buildingName);
        if(building==null){
            return null;
        }
        List<Room> rooms = building.getRooms();
        for (Room r : rooms) {
            if (r.getNumber().equals(roomNb)) {
                return r;
            }
        }
        return null;
    }

    
    /**
     * Modify a exisiting Room by capacity and type, If have reservations, don't have modify room
     *
     * @param room The room that was modified.
     * @param newType If the newType is null, then this is not changed.
     * @param capacity If the capacity is 0, then this is not changed.
     * @return Room modified
     * @throws AlredyExistReservationException
     */
    @Override
    public Room modifyRoom(Room room, String type, int capacity) throws DAOException {
        Room roomnew=null;
        if (!room.getReservations().isEmpty()) {
            throw new AlredyExistReservationException();
        }
        if (capacity != 0) {
            
            roomnew=modifyCapacity(room, capacity);
        }
        if (type != null) {
         
            roomnew=modifyType(room, type);
        }
        return roomnew;
    }

    
    /**
     * Remove a exisiting Room
     *
     * @param room old room
     * @throws NoneExistingRoomException If non existing Room in DataBase
     * @throws AlredyExistReservationException If exist reservations in the room
     */
    @Override
    public void removeRoom(Room room) throws DAOException {
        if (!room.getReservations().isEmpty()) {
            throw new AlredyExistReservationException();
        }
        
        if (!roomExist(room)) {
            throw new NoneExistingRoomException();
        }
        em.getTransaction().begin();
        room.getBuilding().getRooms().remove(room);
        em.remove(room);
        em.getTransaction().commit();
    }

    
    /**
     * Remove an existing building
     *
     * @param building old building
     * @throws NonBuildingException
     */
    @Override
    public void removeBuilding(Building building) throws DAOException {
        if(!buildingExist(building)){
            throw new NonBuildingException();
        }
        em.getTransaction().begin();
        em.remove(building);
        em.getTransaction().commit();
    }

    /**
     * Find all the rooms about one type, more than one capacity and one buiding
     * And return empty list if non exist rooms with this capacity, type or/and building
     * @param buildingName The building name
     * @param type the type room to list (MetingRoom, LaboratoryRoom or ClassRoom)
     * @param capacity list the rooms about this capacity or more
     * @return List of Rooms.
     */
    @Override
    public List<Room> getAllRoomsByTypeAndCapacity(String type, int capacity, String buildingName)
       {

        List<Room> roomsOneType = null;
        List<Room> roomsOneTypeOneBuilding = new ArrayList();
        Building building = getBuildingByName(buildingName);
        Query query;

  
        query = em.createQuery("SELECT r FROM " + type + " r" + " WHERE r.capacity>=" + capacity);
        roomsOneType = (List<Room>) query.getResultList();


        for (Room r : roomsOneType) {
            if (r.getBuilding() == building) {
                roomsOneTypeOneBuilding.add(r);
            }
        }
        return roomsOneTypeOneBuilding;

    }
    /**
     * This function checks that there is not existing Building or any Rooms. 
     * @param building building that checks if there.
     * @throws AlreadyExistingBuildingException
     * @throws AlreadyExistingRoomException
     */
    private void checkExistingBuildingOrRoom(Building building) throws
            DAOException {
        if (buildingExist(building)) {

            throw new AlreadyExistingBuildingException();

        }
        if (roomsExist(building.getRooms())) {
            throw new AlreadyExistingRoomException();
        }
    }
    /**
     * This function uses the list of rooms in search of something that exists.
     * @param rooms list where you are looking.
     * @return true if there rooms, false if not.
     */
    private boolean roomsExist(List<Room> rooms) {
      
        
        for (Room r : rooms) {
           
            Room room = (Room) em.find(Room.class, r.getRoomId());
       
            if (room != null) {
                return true;
            }
        }

        return false;
    }

    /**
     * This function search if the building exist.
     * @param building building that comprove if exist.
     * @return true if the building exist, or false if not.
     */
    private boolean buildingExist(Building building) {

        return em.find(Building.class, building.getBuildingName()) != null;
    }
    
    /**
     * Function to see if the room exist in the Data Base
     * @param room room if you are looking in the Data Base
     * @return true if exist in the database, false if not.
     */
    private boolean roomExist(Room room) {
      
       try {
           Room room2;
           
           room2 = (Room)  em.createQuery("SELECT r "
                    + "FROM Room r "
                    + "WHERE r.building.name = :buildingName AND "
                    + "r.number = :roomNb ")
                    .setParameter("buildingName", room.getBuilding().getBuildingName())
                    .setParameter("roomNb", room.getNumber())
                    .getSingleResult();
                    log.log(Level.INFO, "Exist{0}", room2.toString());
                 
        } catch (NoResultException e) {
            log.log(Level.WARNING, "Non exist{0}", room.toString());
            return false;
        }
        return true;
    }

    /**
     * Function that modifies the capacity of a room
     * @param room amending the room capacity
     * @param capacity this is the new capacity of the room.
     * @return the room with modified capacity.
     */
    private Room modifyCapacity(Room room, int capacity) {
        room.setCapacity(capacity);
        return room;

    }

    /**
     * Function that modifies the type of a room.
     * @param room amending the room type.
     * @param type this is the new type of the room.
     * @return the room with modified type.
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private Room modifyType(Room room, String type) throws DAOException {
      
        Room newRoom = null;
        try {

            Constructor classType = Class.forName("org.classbooker.entity." + type)
                    .getConstructor(Building.class, String.class, int.class);

            newRoom = (Room) classType.newInstance(room.getBuilding(),
                    room.getNumber(), room.getCapacity());
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            log.warning("Error");
        }
        removeRoom(room);
        addRoom(newRoom);
        return newRoom;
    }

}
