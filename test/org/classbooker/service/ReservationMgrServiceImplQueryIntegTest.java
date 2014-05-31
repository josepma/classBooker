/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import org.classbooker.service.ReservationMgrServiceImplQuery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.classbooker.dao.ReservationDAO;
import org.classbooker.dao.ReservationDAOImpl;
import org.classbooker.dao.SpaceDAO;
import org.classbooker.dao.SpaceDAOImpl;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectBuildingException;
import org.classbooker.entity.Building;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;
import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author sht1
 */
public class ReservationMgrServiceImplQueryIntegTest {
   
   ReservationUser rUser;
   ReservationDAOImpl resDao;
   SpaceDAOImpl spaDao;
   DateTime startDate;
   DateTime endDate;
   Reservation res1,res2,res3,res4,res5;
   Room room;
   Building building;
   List<Reservation>lres,lreser;
   ReservationMgrServiceImpl rmsQ;
   EntityManager em;
    
    private String nif;
    private DateTime startD;
    private DateTime endD;
    private String buildingName;
    private String roomNb;
    private int capacity;
    private String roomType;
    
    @Before
    public void setup() throws Exception{
        lres = new ArrayList<>();
        lreser = new ArrayList<>();
        rmsQ = new ReservationMgrServiceImpl();
        createEm();
    }
    
    @Test 
    public void zeroReservationsByNif() throws Exception{    
     
      List <Reservation> tested = rmsQ.getReservationsByNif("123456");
      List <Reservation> resDB = resDao.getAllReservationByUserNif("123456");
      
      assertEquals("0 reservations",0,tested.size());
      assertTrue(resDB.isEmpty());
    }
 
    @Test 
    public void ReservationsByNif() throws Exception{
       
      searchReservationsByFields("12345678",null,null,null,null,0,null);
      List <Reservation> tested = rmsQ.getReservationsByNif(nif);
      List <Reservation> resDB = resDao.getAllReservationByUserNif(nif);
      
      assertEquals("Same size",13,tested.size());
      assertEquals("Same size in BD",13,resDB.size());
      assertTrue("The same Nif in all reservation",
                  resDB.get(0).getrUser().getNif().equals(nif));
    }
   
    @Test 
    public void ReservationWithoutFieldsForFiltering() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,0,null);     
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      
      List <Reservation> resDB = resDao.getAllReservationByUserNif(nif);
      assertEquals("Same size",13,tested.size());
      assertEquals("Same size in BD",13,resDB.size());
      assertTrue("The same Nif in all reservation",
                  resDB.get(0).getrUser().getNif().equals(nif));
     
    }
     
    @Test 
    public void ReservationFilteredByDates() throws Exception{
      
  
      searchReservationsByFields("12345678",new DateTime(2014,6,10,9,0),
              new DateTime(2014,6,10,10,0),null,null,0,null);       
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      
      List <Reservation> aux = filterDate(nif,startD);
  
      assertEquals("Same size",1,tested.size());
      assertEquals("Same size in BD",1,aux.size());
      assertTrue(aux.get(0).getReservationDate().isEqual(startD));
      
    }
    
    @Test 
    public void ReservationFilteredByBuildingName() throws Exception{
      
      searchReservationsByFields("12345678",null,null,"Rectorate Building",null,0,null); 
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      List <Reservation> aux = filterBuilding(nif,buildingName);
      
      assertEquals("Same size",7,tested.size());
      assertEquals("Same size",7,aux.size());
      assertTrue("The Same Building",aux.get(4).getRoom().getBuilding().getBuildingName().equals(buildingName));
    }
    
    @Test 
    public void ReservationFilteredByRoomNb() throws Exception{
      searchReservationsByFields("12345678",null,null,"Rectorate Building",
                                 "1.0",0,null);
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      
      List <Reservation> aux = filterRoomBuilding(nif,buildingName,roomNb);
         
      assertEquals("Same size",7,tested.size());
      assertEquals("Same size in DB",7,aux.size());
      assertTrue("The Same roomNb", aux.get(3).getRoom().getBuilding().getBuildingName().equals(buildingName));
      assertTrue("The Same roomNb", aux.get(3).getRoom().getNumber().equals(roomNb));
    }
    
    @Test 
    public void ReservationFilteredByCapacity() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,20,null);
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      List <Reservation> aux = filterCapacity(nif,capacity);
      
      assertEquals("Same size",13,tested.size());
      assertEquals("Same size in DB",13,aux.size());
      assertTrue("The Same Capacity or higher",aux.get(6).getRoom().getCapacity()>=capacity);
    }
    
    @Test 
    public void ReservationFilteredByRoomType() throws Exception{
      searchReservationsByFields("12345678",null,null,null,null,0,"MeetingRoom");
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      List <Reservation> aux = filterRoomType(nif,roomType);
      
      assertEquals("Same size",5,tested.size());
      assertEquals("Same size in DB",5,aux.size());
      
    }
    
    @Test 
    public void ReservationFilteredByAllFields() throws Exception{
      searchReservationsByFields("12345678",
                                  new DateTime(2014,6,10,9,0),
                                  new DateTime(2014,6,10,10,0),
                                  "Main Library","1.0",
                                  40,
                                  "MeetingRoom");
      
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
      
      List <Reservation> aux = allFilter(nif,buildingName,roomNb,capacity,startD,roomType);
      
      assertEquals("Same size",1,tested.size());
      assertEquals("Same size in DB",1,aux.size());
      assertTrue("Same nif",aux.get(0).getrUser().getNif().equals(nif));
      assertTrue("Same capacity or higher",aux.get(0).getRoom().getCapacity()>=capacity);
      assertTrue("Same roomNb",aux.get(0).getRoom().getNumber().equals(roomNb));
      assertTrue("Same building",
                  aux.get(0).getRoom().getBuilding().getBuildingName().equals(buildingName));
      assertTrue("Same Date",aux.get(0).getReservationDate().isEqual(startD));
    }
    
    @Test 
    public void findReservationBySpace() throws IncorrectBuildingException, DAOException{
      
      List <Reservation> tested = rmsQ.findReservationByBuildingAndRoomNb("Rectorate Building","1.0");
      
      List <Reservation> aux = filterRoomBuilding("Rectorate Building","1.0");
      
      assertEquals("Same Size",10,tested.size());
      assertEquals("Same Size in DB",10,aux.size());
      assertTrue("The Same building",
                  aux.get(3).getRoom().getBuilding().getBuildingName().equals("Rectorate Building"));
      assertTrue("The Same roomNb",aux.get(3).getRoom().getNumber().equals("1.0"));
    }
    
    @Test
    public void IncorrectFields() throws Exception{
       
       searchReservationsByFields("12345678",null,null,"2.04",null,0,null); 
       List<Reservation> result = new ArrayList<>();
       List<Reservation> tested = rmsQ.getFilteredReservation(nif, 
                                                              startDate, 
                                                              endDate, 
                                                              buildingName, 
                                                              roomNb, 
                                                              capacity, 
                                                              roomType);
       
        List <Reservation> resDB = resDao.getAllReservationByUserNif(nif);
        for(Reservation res : resDB){
            if(res.getRoom().getBuilding().getBuildingName().equals("2.04")){
                result.add(res);
            }
        }
       assertEquals("Non rerserves in DB, building is incorrect",result,tested);
    }
    
    @Test 
    public void validateDatesNoInit() throws Exception{
     
      searchReservationsByFields("12345678",null,new DateTime(2014,5,9,13,0)
                                 ,null,null,0,null);
      List<Reservation> result = new ArrayList<>();
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
        
      List <Reservation> resDB = resDao.getAllReservationByUserNif(nif);
        for(Reservation res : resDB){
            if(res.getReservationDate().isBefore(endD)){
                result.add(res);
            }
        }
        assertEquals("Non reserves in DB,StartDate incorrect",result,tested);
        
    }
    
    @Test 
    public void validateDatesNoEnd() throws Exception{
     
      searchReservationsByFields("12345678",new DateTime(2014,5,9,12,0),null
                                 ,null,null,0,null);
      List<Reservation> result = new ArrayList<>();
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
          
        assertEquals("Non reserves,EndDate incorrect",result,tested);
    }
    
    @Test 
    public void validateDateEndBeforeInit() throws Exception{
     
      searchReservationsByFields("12345678",new DateTime(2014,5,9,12,0),
                                  new DateTime(2014,5,9,11,0),
                                 null,null,0,null);
      List<Reservation> result = new ArrayList<>();
      List <Reservation> tested = rmsQ.getFilteredReservation(nif,
                                                              startD,
                                                              endD,
                                                              buildingName,
                                                              roomNb,
                                                              capacity,
                                                              roomType);
        List <Reservation> resDB = resDao.getAllReservationByUserNif(nif);
        for(Reservation res : resDB){
            if(res.getReservationDate().isBefore(endD)){
                result.add(res);
            }
        }
        assertEquals("Non reserves,EndDate is before than startDate",
                                                                result,tested);
    }
    
    private void createEm() throws Exception{
        resDao = new ReservationDAOImpl();
        spaDao = new SpaceDAOImpl();
        em = getEntityManager();
        resDao.setEm(em);
        spaDao.setEm(em);
        resDao.setsDao(spaDao);
        rmsQ.setReservationDao(resDao);
        rmsQ.setSpaceDao(spaDao);
    }
    
    private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBookerIntegration");
        return emf.createEntityManager();  
    }

     private void searchReservationsByFields(String nif, 
                                          DateTime startDate,
                                          DateTime endDate, 
                                          String buildingName,
                                          String roomNb,
                                          int capacity,
                                          String roomType){
        this.nif = nif;
        this.startD = startDate;
        this.endD = endDate;
        this.buildingName = buildingName;
        this.roomNb = roomNb;
        this.capacity = capacity;
        this.roomType = roomType;    
    }
     
     private List<Reservation> filterDate(String nif, DateTime startD) throws DAOException{
        List <Reservation> aux = new ArrayList<>();
        List <Reservation> resDB = resDao.getAllReservationByUserNif(nif);
        for (Reservation r: resDB){
            if(r.getReservationDate().isEqual(startD)){        
                aux.add(r);
            }      
        }
        return aux;
     }
     
     private List <Reservation> filterBuilding(String nif,String buildingName) throws DAOException{
        List <Reservation> aux = new ArrayList<>();
        List <Reservation> resDB = resDao.getAllReservationByUserNif(nif);
        for (Reservation r: resDB){
            if(r.getRoom().getBuilding().getBuildingName().equals(buildingName)){        
                aux.add(r);
            }
        } 
        return aux;
     }
     
     private List <Reservation> filterRoomBuilding(String nif,String buildingName,String roomNb) throws DAOException{
        List <Reservation> aux = new ArrayList<>();
        List <Reservation> resDB = resDao.getAllReservationByUserNif(nif);
        for (Reservation r: resDB){
                if(r.getRoom().getBuilding().getBuildingName().equals(buildingName) 
                    && r.getRoom().getNumber().equals(roomNb)){        
                    aux.add(r);
                }
        }
        return aux;
     }
     
     private List <Reservation> filterCapacity(String nif,int capacity) throws DAOException{
       List <Reservation> aux = new ArrayList<>();
       List <Reservation> resDB = resDao.getAllReservationByUserNif(nif);
       for (Reservation r: resDB){
            if(r.getRoom().getCapacity()>=capacity){        
                aux.add(r);
            }
        }
       return aux;
     }
     
     private List <Reservation> filterRoomType(String nif, String roomType) throws DAOException{
        List <Reservation> aux = new ArrayList<>();
        List <Room> roomDB = spaDao.getAllRoomsOfOneType(roomType);
        for (Room r: roomDB){
            List<Reservation> resDB = r.getReservations();        
                for(Reservation res : resDB){
                   if(res.getrUser().getNif().equals(nif)){
                       aux.add(res);
                   } 
                }
        }
        return aux;
     }
     
     private List <Reservation> allFilter(String nif,String buildingName,String roomNb,
             int capacity,DateTime startD,String roomType) throws DAOException{
         
        List <Reservation> aux = new ArrayList<>();
        List <Room> roomDB = spaDao.getAllRoomsOfOneType(roomType);
        for(Room r: roomDB){
            List<Reservation> resDB = r.getReservations(); 
            for (Reservation res: resDB){
                if(res.getrUser().getNif().equals(nif) &&
                    res.getRoom().getBuilding().getBuildingName().equals(buildingName) &&
                    res.getRoom().getNumber().equals(roomNb) && 
                    res.getRoom().getCapacity()>=capacity &&
                    res.getReservationDate().isEqual(startD)){
                        aux.add(res);
                } 
            }
        }  
        return aux;
     }
     
     private List <Reservation> filterRoomBuilding(String buildingName,String roomNb) throws DAOException{
        List <Reservation> aux = new ArrayList<>();
        List <Reservation> resDB = resDao.getAllReservationByBuilding(buildingName);
        for(Reservation res : resDB){
            if(res.getRoom().getNumber().equals(roomNb)){
                aux.add(res);
            }
        }
        return aux;
     } 
}
