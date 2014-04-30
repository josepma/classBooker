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
import org.classBooker.dao.exception.AlreadyExistingBuilding;
import org.classBooker.dao.exception.AlreadyExistingRoom;
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
    public void addRoom(Room room) throws PersistException, IncorrectRoomException, AlreadyExistingRoom,AlreadyExistingBuilding  {
     
            em.getTransaction().begin();
          
            if(checkExistingBuilding(room.getBuilding())){              
                    throw new AlreadyExistingBuilding();
                
            }
            if(checkExistingRoom(room)){              
                    throw new AlreadyExistingRoom();
                
            }

            em.persist(room);
            em.getTransaction().commit();
            
        
    }

   
    @Override
    public Room getRoomById(long id) {
        Room room = null;
       
        em.getTransaction().begin();
        room = (Room) em.find(Room.class, id);
        em.getTransaction().commit();
        
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
    public void addBuilding(Building building) throws PersistException, IncorrectBuildingException, AlreadyExistingBuilding {
            em.getTransaction().begin();          
            if(checkExistingBuilding(building)){
              
                    throw new AlreadyExistingBuilding();
                
            }

            em.persist(building);
            em.getTransaction().commit();
            System.out.print(".");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Room> getAllRoomsOfOneTypeAndOneBuilding(String Type, Building building) throws IncorrectBuildingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Room getRoomByNbAndBuilding(String roomNb, String buildingName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
