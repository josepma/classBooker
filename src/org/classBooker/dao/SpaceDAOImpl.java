/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
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
 * @author saida
 */
public class SpaceDAOImpl implements SpaceDAO{
    
    EntityManager em;
    
   
    public EntityManager getEm() {
        return em;
    }

   
    public void setEm(EntityManager em) {
        this.em = em;
    }
    
   /**
     * Add new room in database and add room in buiding 
     * @param room
     * @throws PersistException
     * @throws AlreadyExistingRoomException
     * @throws NonBuildingException
     */
   @Override
    public void addRoom(Room room) throws PersistException, 
                            AlreadyExistingRoomException, NonBuildingException  {
       try{
            Building building = getBuildingByName(room.getBuilding().getBuildingName());
            em.getTransaction().begin();
           
            if (building==null){
                throw new NonBuildingException();
            }
            if (roomExist(room)){
                throw new AlreadyExistingRoomException();
            }
            
            em.persist(room);
            building.getRooms().add(room);
            em.getTransaction().commit();
            
           } catch (PersistenceException e) {
            throw e;
        } 
        
    }

   /**
     * Find Room by id 
     * @param id      
     * @return Room
     */
    @Override
    public Room getRoomById(long id) {
        Room room = null;
        try{
        em.getTransaction().begin();
        room = (Room) em.find(Room.class, id);        
        em.getTransaction().commit();
        }catch (PersistenceException e) {
            throw e;}
        return room;
    }
    /**
     *Find all the rooms in the database
     * @return List<Room> 
     * 
     */
    @Override
    public List<Room> getAllRooms() {
       List<Room> rooms = null;
        Query query;
        try{
            em.getTransaction().begin();
            query = em.createQuery("SELECT r FROM Room r");
            rooms = (List<Room>) query.getResultList();
            em.getTransaction().commit();            
        }catch(PersistenceException e){}
        
        return rooms; 
    
    }

  /**
     * Add new building in database
     * @param building
     * @throws AlreadyExistingBuildingException
     * @throws AlreadyExistingRoomException
     */
    @Override
    public void addBuilding(Building building)  throws 
                                                AlreadyExistingBuildingException,
                                                AlreadyExistingRoomException {
            try{
            em.getTransaction().begin();       
            checkExistingBuildingOrRoom(building);
            em.persist(building);
            em.getTransaction().commit();
            }catch (PersistenceException e) {}
    } 
     /**
     *Find building about one name ex("EPS")
     * @param name     
     * @return building
     * 
     */
    @Override
    public Building getBuildingByName(String name)  {
       Building building = null;
      
        em.getTransaction().begin();
        building = (Building) em.find(Building.class, name);
        em.getTransaction().commit();
        
        return building;
    }
/**
     *Find all buildings in database
     * @return List<Building>
     * 
     */
    @Override
    public List<Building> getAllBuildings() {
        List<Building> buildings = null;
        Query query;
        try{
            em.getTransaction().begin();
            query = em.createQuery("SELECT b FROM Building b");
            buildings = (List<Building>) query.getResultList();
            em.getTransaction().commit();            
        }catch(PersistenceException e){}
        
        return buildings; 
    }
/**
     * Find List rooms in one building
     * @param building
     * @return List<Room>
     * 
     */
    @Override
    public List<Room> getAllRoomsOfOneBuilding(Building building){
          
               
        return building.getRooms();
        
    
    }
/**
     * Find all the rooms about one room type (MetingRoom, LaboratoryRoom or ClassRoom)
     * @param Type
     * @return List<Room>     * 
     * @throws org.classBooker.dao.exception.IncorrectTypeException
     * 
     */
    @Override
    public List<Room> getAllRoomsOfOneType(String Type) throws IncorrectTypeException {
        List<Room> rooms = null;
        Query query;
      
        if(!"MeetingRoom".equals(Type)&&!"LaboratoryRoom".equals(Type)&&!"ClassRoom".equals(Type))
            throw new IncorrectTypeException();
        try{
            em.getTransaction().begin();
            query = em.createQuery("SELECT r FROM "+ Type+" r");
            rooms = (List<Room>) query.getResultList();
            em.getTransaction().commit();            
        }catch(PersistenceException e){}
        return rooms;
    }
    /**
     * Find list of Rooms about one room Type(MetingRoom, LaboratoryRoom or ClassRoom)
     * and one Building.class
     * @param Type, Building
     * @return List<Room>
     *  
     */
    @Override
    public List<Room> getAllRoomsOfOneTypeAndOneBuilding(String Type, Building building) {
        List<Room> roomsOneType = null;
        List<Room> roomsOneTypeOneBuilding =new ArrayList();
        
        Query query;
        try{
            em.getTransaction().begin();
            query = em.createQuery("SELECT r FROM "+ Type+" r");
            roomsOneType = (List<Room>) query.getResultList();
            em.getTransaction().commit();            
        }catch(PersistenceException e){}
         for(Room r:roomsOneType){
             if(r.getBuilding()==building)roomsOneTypeOneBuilding.add(r);
         }
        return roomsOneTypeOneBuilding;
    }
    /**
     *Find room about one building name and one room name/number
     *@param buildingName  The building name Ex.("EPS")
     *@param roomNb The number about room Ex.(2.01)
     *@return Room 
     * @throws org.classBooker.dao.exception.IncorrectBuildingException 
     * @throws org.classBooker.dao.exception.IncorrectRoomException 
     */
    @Override
    public Room getRoomByNbAndBuilding(String roomNb, String buildingName) throws IncorrectBuildingException, IncorrectRoomException {
      Building building=  getBuildingByName(buildingName);
      List<Room> rooms = building.getRooms();      
      for(Room r:rooms){          
          if(r.getNumber().equals(roomNb)) {              
              return r;}
      }
      throw new IncorrectRoomException();
    }
    
       
    @Override
    public void removeRoom(Room room) throws IncorrectRoomException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void modifyBuilding(Building building) throws PersistException, IncorrectBuildingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeBuilding(Building building) throws IncorrectBuildingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Find all the rooms about one type, more than one capacity and one buiding
     * @param buildingName 
     * @param type 
     * @param capacity  
     * @throws org.classBooker.dao.exception.IncorrectBuildingException  
     */
    @Override
    public List<Room> getAllRoomsByTypeAndCapacity(String type, int capacity, String buildingName) 
                                                    throws IncorrectBuildingException {
       
        List<Room> roomsOneType = null;
        List<Room> roomsOneTypeOneBuilding =new ArrayList();
        Building building = getBuildingByName(buildingName);
        Query query;
        try{
            em.getTransaction().begin();
            query = em.createQuery("SELECT r FROM "+ type+" r" + " WHERE r.capacity<=" +capacity );
            roomsOneType = (List<Room>) query.getResultList();
            em.getTransaction().commit();            
        }catch(PersistenceException e){}
        
         for(Room r:roomsOneType){
             if(r.getBuilding()==building)roomsOneTypeOneBuilding.add(r);
         }
        return roomsOneTypeOneBuilding;
    
    }

    private void checkExistingBuildingOrRoom(Building building) throws
                                            AlreadyExistingRoomException, 
                                            AlreadyExistingBuildingException {
        if(buildingExist(building))
        {
            
            throw new AlreadyExistingBuildingException();
            
        }
        if(roomsExist(building.getRooms()))
            throw new AlreadyExistingRoomException();
    }

    private boolean roomsExist(List<Room> rooms) {
       for(Room r : rooms){
            Room room = (Room) em.find(Room.class, r.getRoomId());
            if(room != null)
                return true;
        }
        
        return false;
    }

    private boolean buildingExist(Building building) {
        
        return em.find(Building.class, building.getBuildingName())!=null;}
    

   private boolean roomExist(Room room) {
        
        return em.find(Room.class, room.getRoomId())!=null;}
   
  
}
