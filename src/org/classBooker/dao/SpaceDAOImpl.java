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


    @Override
    public void addRoom(Room room) throws PersistException, IncorrectRoomException, AlreadyExistingRoomException,AlreadyExistingBuildingException  {
       try{
            em.getTransaction().begin();
            if (!buildingExist(room.getBuilding())){
                throw new AlreadyExistingBuildingException();
            }
            if (roomExist(room)){
                throw new AlreadyExistingRoomException();
            }
            room.getBuilding().setRoom(room);
            em.persist(room);
            em.getTransaction().commit();
            
           } catch (PersistenceException e) {
            throw e;
        } 
        
    }

   
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

  
    @Override
    public void addBuilding(Building building)  throws PersistException, 
                                                IncorrectBuildingException,
                                                AlreadyExistingBuildingException,
                                                AlreadyExistingRoomException {
            try{
            em.getTransaction().begin();       
            checkExistingBuildingOrRoom(building);
            em.persist(building);
            em.getTransaction().commit();
            }catch (PersistenceException e) {}
    } 

    @Override
    public Building getBuildingByName(String name) throws IncorrectBuildingException {
       Building building = null;
       
        em.getTransaction().begin();
        building = (Building) em.find(Building.class, name);
        em.getTransaction().commit();
        
        return building;
    }

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

    @Override
    public List<Room> getAllRoomsOfOneBuilding(Building building) throws IncorrectBuildingException {
          
               
        return building.getRooms();
        
    
    }

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

    @Override
    public List<Room> getAllRoomsOfOneTypeAndOneBuilding(String Type, Building building) throws IncorrectBuildingException {
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

    private boolean checkExistingRoom(Room room) {
        return em.find(Room.class, room.getRoomId())!=null;
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

    private boolean checkExistingBuilding(Building building) {
        return em.find(Building.class, building.getBuildingName())!=null;
    }

    @Override
    public List<Room> getAllRoomsByTypeAndCapacity(String type, int capacity, String buildingName) throws IncorrectBuildingException {
       
        List<Room> roomsOneType = null;
        List<Room> roomsOneTypeOneBuilding =new ArrayList();
        Building building = getBuildingByName(buildingName);
        Query query;
        try{
            em.getTransaction().begin();
            query = em.createQuery("SELECT r FROM "+ type+" r" + " WHERE r.capacity=" +capacity );
            roomsOneType = (List<Room>) query.getResultList();
            em.getTransaction().commit();            
        }catch(PersistenceException e){}
        
         for(Room r:roomsOneType){
             if(r.getBuilding()==building)roomsOneTypeOneBuilding.add(r);
         }
        return roomsOneTypeOneBuilding;
    
    }

    private void checkExistingBuildingOrRoom(Building building) throws AlreadyExistingRoomException, AlreadyExistingBuildingException {
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
