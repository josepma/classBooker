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
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.classbooker.dao.exception.AlreadyExistingBuildingException;
import org.classbooker.dao.exception.AlreadyExistingRoomException;
import org.classbooker.dao.exception.AlredyExistReservationException;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectBuildingException;
import org.classbooker.dao.exception.IncorrectRoomException;
import org.classbooker.dao.exception.IncorrectTypeException;
import org.classbooker.dao.exception.NonBuildingException;
import org.classbooker.dao.exception.NoneExistingRoomException;
import org.classbooker.dao.exception.PersistException;
import org.classbooker.entity.Building;
import org.classbooker.entity.MeetingRoom;
import org.classbooker.entity.Room;

/**
 *
 * @author saida
 */
public class SpaceDAOImpl implements SpaceDAO {

    private EntityManager em;
    private  Logger log = Logger.getLogger("MiLogger");
    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * Add new room in database and add room in buiding
     *
     * @param room
     * @return RoomId
     * @throws PersistException
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

        em.persist(room);
        building.getRooms().add(room);
  

        return room.getRoomId();

    }

    /**
     * Find Room by id
     *
     * @param id
     * @return Room
     */
    @Override
    public Room getRoomById(long id) {
        Room room = null;

     
        room = (Room) em.find(Room.class, id);
     

        return room;
    }

    /**
     * Find all the rooms in the database
     *
     * @return List<Room>
     *
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
     * Add new building in database
     *
     * @param building
     * @throws AlreadyExistingBuildingException
     * @throws AlreadyExistingRoomException
     */
    @Override
    public void addBuilding(Building building) throws DAOException
             {

  
        checkExistingBuildingOrRoom(building);
        em.persist(building);
 

    }

    /**
     * Find building about one name ex("EPS")
     *
     * @param name
     * @return building If buiding non exist 
     *
     */
    @Override
    public Building getBuildingByName(String name) throws DAOException {
        Building building = null;       
        building = (Building) em.find(Building.class, name);
        if (building==null)
             throw new NonBuildingException();  
        return building;
    }

    /**
     * Find all buildings in database
     *
     * @return List<Building>
     *
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
     * Find List rooms in one building
     *
     * @param buildingName
     * @return List<Room>
     *
     */
    @Override
    public List<Room> getAllRoomsOfOneBuilding(String buildingName) throws DAOException {

        Building building = getBuildingByName(buildingName);
        return building.getRooms();

    }

    /**
     * Find all the rooms about one room type (MetingRoom, LaboratoryRoom or
     * ClassRoom)
     *
     * @param type
     * @return List<Room>
     * @throws org.classbooker.dao.exception.DAOException
     *
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
     * Find list of Rooms about one room Type(MetingRoom, LaboratoryRoom or
     * ClassRoom) and one Building.class
     *
     * @param type, building
     *
     * @return List<Room>
     *
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
     * Find room about one building name and one room name/number
     *
     * @param buildingName The building name Ex.("EPS")
     * @param roomNb The number about room Ex.(2.01)
     * @return Room If room non exist return null
     * @throws org.classBooker.dao.exception.IncorrectBuildingException
     * @throws org.classBooker.dao.exception.IncorrectRoomException
     */
    @Override
    public Room getRoomByNbAndBuilding(String roomNb, String buildingName) throws DAOException{
        Building building = getBuildingByName(buildingName);

        List<Room> rooms = building.getRooms();
        for (Room r : rooms) {
            if (r.getNumber().equals(roomNb)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void modifyRoom(Room room, String type, int capacity) throws DAOException {

        if (!room.getReservations().isEmpty()) {
            throw new AlredyExistReservationException();
        }
        if (capacity != 0) {
            
            modifyCapacity(room, capacity);
        }
        if (type != null) {
         
            modifyType(room, type);
        }
    }

    @Override
    public void removeRoom(Room room) throws DAOException {
        if (!roomExist(room)) {
            throw new NoneExistingRoomException();
        }
        room.getBuilding().getRooms().remove(room);
        em.remove(room);
    }

    @Override
    public void removeBuilding(Building building) throws DAOException {
        em.remove(building);
    }

    /**
     * Find all the rooms about one type, more than one capacity and one buiding
     *
     * @param buildingName
     * @param type
     * @param capacity
     * @throws org.classBooker.dao.exception.IncorrectBuildingException
     */
    @Override
    public List<Room> getAllRoomsByTypeAndCapacity(String type, int capacity, String buildingName)
            throws DAOException {

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

    private void checkExistingBuildingOrRoom(Building building) throws
            DAOException {
        if (buildingExist(building)) {

            throw new AlreadyExistingBuildingException();

        }
        if (roomsExist(building.getRooms())) {
            throw new AlreadyExistingRoomException();
        }
    }

    private boolean roomsExist(List<Room> rooms) {
        for (Room r : rooms) {
            Room room = (Room) em.find(Room.class, r.getRoomId());
            if (room != null) {
                return true;
            }
        }

        return false;
    }

    private boolean buildingExist(Building building) {

        return em.find(Building.class, building.getBuildingName()) != null;
    }

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

    private void modifyCapacity(Room room, int capacity) {

    
        room.setCapacity(capacity);


    }

    private void modifyType(Room room, String type) throws DAOException {
      
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

    }

}
