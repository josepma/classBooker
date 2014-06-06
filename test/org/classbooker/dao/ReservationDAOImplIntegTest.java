/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.classbooker.dao.exception.AlredyExistReservationException;
import org.classbooker.dao.exception.IncorrectReservationException;
import org.classbooker.dao.exception.IncorrectUserException;
import org.classbooker.dao.exception.NoneExistingRoomException;
import org.classbooker.entity.Building;
import org.classbooker.entity.MeetingRoom;
import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.Room;
import org.joda.time.DateTime;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 *
 * @author josepma, Carles Mònico Bonell, Marc Solé Farré
 */

public class ReservationDAOImplIntegTest {
    
    ReservationDAOImpl rDao;
    UserDAO uDao;
    SpaceDAO sDao;

    
    List<Reservation> elist = new ArrayList<>();

    EntityManager em;
    Room room1, room2, roomaux;
    Building building1, building2, buildingaux;
    ReservationUser user1, user2, user3, useraux;
    Reservation reservation1, reservation2, reservation3, reservationaux, actualReservation, 
                expectsReservation;
    DateTime dataRes1, dataRes2, dataRes3;

    
    
    public ReservationDAOImplIntegTest() {
        
    }
    @Before
    public void setUp() throws Exception {

        rDao = new ReservationDAOImpl();
        uDao = new UserDAOImpl();
        sDao = new SpaceDAOImpl();
      
        em = getEntityManager();
        rDao.setEm(em);  
        
        uDao.setEntityManager(em);
        sDao.setEm(em);
        
        rDao.setsDao(sDao);
        rDao.setuDao(uDao);
        
        // entity objects
        building1 = sDao.getBuildingByName("EPS");
        building2 = sDao.getBuildingByName("Main Library");
        buildingaux = null;
        room1 = sDao.getRoomByNbAndBuilding("0.15", "EPS");
        room2 = sDao.getRoomByNbAndBuilding("1.0", "Main Library");
        roomaux = null;
        user1 = (ReservationUser) uDao.getUserByNif("12345678");
        user2 = (ReservationUser) uDao.getUserByNif("9876544");
        user3 = (ReservationUser) uDao.getUserByNif("55555");
        useraux = null;
        dataRes1 = new DateTime(2001, 05, 11, 12, 00);
        dataRes2 = new DateTime(2002, 06, 12, 10, 00);
        dataRes3 = new DateTime(2003, 07, 12, 10, 00);
        reservation1 = new Reservation(dataRes1, user1, room1);
        reservation2 = new Reservation(dataRes2, user2, room2);
        reservation3 = new Reservation(dataRes3, user3, room1);
        reservationaux = null; 
   
    }
    
    @After
    public void tearDown() throws Exception {
       
        deleteEList();
    }

    @Test
    public void testAddReservationByReservation() throws Exception {
        
        long id = rDao.addReservation(reservation1);
        elist.add(reservation1);
        actualReservation = getReservationFromDB(id);  
        
        checkExistReservation(reservation1, actualReservation);
    }
    
    @Test
    public void  testReservationIdGenerationIsNotTheSame() throws Exception{
    
        long id1, id2, id3;
        id1 = rDao.addReservation(reservation1);
        id2 = rDao.addReservation(reservation2);
        id3 = rDao.addReservation(reservation3);
        
        elist.add(reservation1);
        elist.add(reservation2);
        elist.add(reservation3);
        
        assertNotSame(id1, id2);
        assertNotSame(id1, id3);
        assertNotSame(id3, id2);
    }
    
    @Test(expected = IncorrectUserException.class)
    public void testAddReservationNotExistUser() throws Exception {
        
        useraux = new ProfessorPas("4765665M","random@professor.ly","Manolo","");
        reservation1.setrUser(useraux);
        rDao.addReservation(reservation1);
    }
    
    @Test(expected = NoneExistingRoomException.class)
    public void testAddReservationNotExistRoom() throws Exception {
        
        roomaux = new MeetingRoom(building1, "20", 10);
        reservationaux = new Reservation(dataRes1, user1, roomaux);
        rDao.addReservation(reservationaux);
    }
    
    @Test(expected = AlredyExistReservationException.class)
    public void testAddReservationSameTimeAndRoomButDifferentUser() throws Exception {  
        
        reservationaux = new Reservation(dataRes1, user2, room1);
        elist.add(reservation1);
        rDao.addReservation(reservation1);
        rDao.addReservation(reservationaux);
    }
        
    @Test
    public void testAddReservationSameTimeAndRoomAndUser() throws Exception { 
        
        reservationaux = new Reservation(dataRes1, user1, room1);
        elist.add(reservation1);
        rDao.addReservation(reservation1);
        rDao.addReservation(reservationaux);
    }
    
    @Test
    public void testAddReservationByAttribute() throws Exception {  
           
        long resId = rDao.addReservation(user1.getNif(), room1.getNumber(), 
                                         room1.getBuilding().getBuildingName(),
                                         dataRes1);
        expectsReservation = reservation1;
        actualReservation = rDao.getReservationById(resId);
        elist.add(actualReservation);
        checkExistReservation(expectsReservation , actualReservation);
        
    }
        
    @Test(expected = NoneExistingRoomException.class)
    public void testAddReservationByAttributeNotExistRoom() throws Exception {
        
        rDao.addReservation("12345678", "11", "EPS", dataRes1);
    }
    
    @Test(expected = NoneExistingRoomException.class)
    public void testAddReservationByAttributeNotExistBuilding() throws Exception {
        
        rDao.addReservation("12345678", "0.15","NotExistBuilding", dataRes1);
    }
    
    @Test(expected = IncorrectUserException.class)
    public void testAddReservationByAttributeNotExistUser() throws Exception {
        
        rDao.addReservation("47658205M", "0.15", "EPS", dataRes1);
    }

    @Test
    public void testGetReservationById()throws Exception{
        
        long resId = rDao.addReservation(reservation1);
        actualReservation = rDao.getReservationById(resId);
        elist.add(reservation1);
        assertEquals(reservation1,actualReservation);
    }

    @Test
    public void testGetAllReservation() throws Exception{
        
        rDao.addReservation(reservation1);
        elist.add(reservation1);
        List resultList = em.createQuery("SELECT r FROM Reservation r ")
                .getResultList();
        
        assertEquals(reservationsToSet(resultList), 
                                reservationsToSet(rDao.getAllReservation()));
     }
    
    @Test
    public void testGetAllReservationByUserNif() throws Exception {
        
        List resultList;
        resultList = em.createQuery("SELECT r FROM Reservation r WHERE r.rUser.nif = :nif ")
                .setParameter("nif", user1.getNif())
                .getResultList();
        
        assertEquals(reservationsToSet(resultList),
                     reservationsToSet(rDao.getAllReservationByUserNif(user1.getNif())));

    }
    
    @Test
    public void testGetAllReservationMultiReservations() throws Exception {
        
        addAllReservations();
        
        List resultList = em.createQuery("SELECT r FROM Reservation r ")
                .getResultList();
        
        assertEquals(reservationsToSet(resultList), reservationsToSet(rDao.getAllReservation()));
     }

    @Test
    public void testGetAllReservationByBuilding() throws Exception {
        
        List resultList;
        resultList = em.createQuery("SELECT r FROM Reservation r WHERE r.room.building.name = :building ")
                .setParameter("building", "Main Library")
                .getResultList();

        assertEquals(reservationsToSet(resultList), 
                     reservationsToSet(rDao.getAllReservationByBuilding("Main Library")));
        
    }

    @Test
    public void testGetAllReservationByRoom() throws Exception {
        
        List resultList = em.createQuery("SELECT r FROM Reservation r WHERE r.room.roomId = :idRoom ")
                .setParameter("idRoom", room1.getRoomId())
                .getResultList();
        
        assertEquals(reservationsToSet(resultList),reservationsToSet(rDao.getAllReservationByRoom(room1.getRoomId())));
    }

    @Test
    public void testRemoveReservation() throws Exception {
        
        long resId = rDao.addReservation(reservation1);
        actualReservation = rDao.getReservationById(resId);
        rDao.removeReservation(resId);
        
        checkDontExistReservation(reservation1);
    }
    
    @Test
    public void testRemoveReservationByAtributes() throws Exception {
        
        long resId = rDao.addReservation(reservation1);
        actualReservation = rDao.getReservationById(resId);
        rDao.removeReservation(dataRes1, 
                                room1.getNumber(), 
                                building1.getBuildingName());
        
        checkDontExistReservation(actualReservation);
    }
    
    @Test(expected = IncorrectReservationException.class)
    public void testRemoveReservationByAtributesNoneExistReservation() throws Exception {

        rDao.removeReservation(dataRes1, 
                                room1.getNumber(), 
                                building1.getBuildingName());

    }
    
    @Test(expected = IncorrectReservationException.class)
    public void testRemoveReservationByAtributesBadBuilding() throws Exception {

        rDao.removeReservation(dataRes1, 
                                room1.getNumber(), 
                                building1.getBuildingName());
    }

    private EntityManager getEntityManager() throws Exception{
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classBookerIntegration");
        return emf.createEntityManager();  
    }
    
    private Reservation getReservationFromDB(long id) throws Exception{        

        em = getEntityManager();
        em.getTransaction().begin();
        Reservation reservationDB = (Reservation) em.find(Reservation.class, id);
        em.getTransaction().commit();
        em.close();

        return reservationDB; 
    }
    
    private void checkExistReservation(Reservation expected,Reservation actual){
        
        assertEquals(expected, actual);
        assertTrue(actual.getRoom().getReservations().contains(expected));
        assertTrue(actual.getrUser().getReservations().contains(expected));
    }
    
    private void checkDontExistReservation(Reservation res){
        
        Room room = sDao.getRoomById(res.getRoom().getRoomId());
        
        assertEquals("exist reservation",null, rDao.getReservationById(res.getReservationId()));
        assertFalse("Exist reservation in room",room.getReservations().contains(res));
        assertFalse("Exist reservation in user",res.getrUser().getReservations().contains(res));
    }
        
    private Set<Reservation> reservationsToSet(List res){
        return new HashSet<>(res);
    }

    private void deleteEList() throws Exception{
        
      for (Reservation entity : elist){

          rDao.removeReservation(entity.getReservationId());
      }
      elist.clear();
    }

    private void addAllReservations() throws Exception{
        rDao.addReservation(reservation1);
        rDao.addReservation(reservation2);
        rDao.addReservation(reservation3);
        elist.add(reservation1);
        elist.add(reservation2);
        elist.add(reservation3);
        
    }
    
}
