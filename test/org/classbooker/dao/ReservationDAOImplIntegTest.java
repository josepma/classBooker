/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.classbooker.dao.exception.AlredyExistReservationException;
import org.classbooker.dao.exception.IncorrectBuildingException;
import org.classbooker.dao.exception.IncorrectReservationException;
import org.classbooker.dao.exception.IncorrectRoomException;
import org.classbooker.dao.exception.IncorrectUserException;
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

/**
 *
 * @author msf7
 */

public class ReservationDAOImplIntegTest {
    
    ReservationDAOImpl rDao;
    UserDAO uDao;
    SpaceDAO sDao;
    

    EntityManager em;
    Room room1, room2, roomaux;
    Building building1, building2, buildingaux;
    ReservationUser user1, user2, user3, useraux;
    Reservation reservation1, reservation2, reservation3, reservationaux, actualReservation, 
                expectsReservation;
    Set <Reservation> allReservation;
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
        
        // data base
        
        
 
        
        // entity objects
        building1 = new Building("testBuilding1");
        building2 = new Building("testBuilding2");
        buildingaux = null;
        room1 = new MeetingRoom(building1, "10", 10);
        room2 = new MeetingRoom(building2, "11", 11);
        roomaux = null;
        user1 = new ProfessorPas("47658245M", "random@professor.ly", "Manolo");
        user2 = new ProfessorPas("47658246M", "random@professor.ly", "Lily");
        user3 = new ProfessorPas("47656546M", "random@professor.ly", "Ramon");
        useraux = null;
        dataRes1 = new DateTime(2014, 05, 11, 12, 00);
        dataRes2 = new DateTime(2014, 06, 12, 10, 00);
        dataRes3 = new DateTime(2014, 07, 12, 10, 00);
        reservation1 = new Reservation(dataRes1, user1, room1);
        reservation2 = new Reservation(dataRes2, user2, room2);
        reservation3 = new Reservation(dataRes3, user3, room1);
        reservationaux = null; 
        
        allReservation = new HashSet();
   
        
        addDB(user1, user2, user3);
        addDB(building1, building2);
        addDB(room1, room2);
        
    }
    
    @After
    public void tearDown() throws Exception {
        
        em = rDao.getEm();
        if (em.isOpen()) em.close();
        em = getEntityManager();
        deleteAll(reservation1,reservation2,reservation3);
        deleteAll(user1, user2, user3, useraux);
        deleteAll(room1, room2, roomaux);
        deleteAll(building1, building2, buildingaux);
        em.close();
        
    }
    
    @Test
    public void testAddReservationByReservation() throws Exception {
        
        rDao.addReservation(reservation1);
        actualReservation = getReservationFromDB(reservation1.getReservationId());      
        checkExistReservation(reservation1, actualReservation);
        
    }
    
    @Test
    public void  testReservationIdGenerationIsNotTheSame() throws Exception{
    

        long id1, id2, id3;
        id1 = rDao.addReservation(reservation1);
        id2 = rDao.addReservation(reservation2);
        id3 = rDao.addReservation(reservation3);
        
        assertNotSame(id1, id2);
        assertNotSame(id1, id3);
        assertNotSame(id3, id2);
    }
    
    
    
    @Test(expected = IncorrectUserException.class)
    public void testAddReservationNotExistUser() throws Exception {
        useraux = new ProfessorPas("4765665M", 
                                                     "random@professor.ly", 
                                                     "Manolo");
        reservation1.setrUser(useraux);
        rDao.addReservation(reservation1);
    }
    
    @Test(expected = IncorrectRoomException.class)
    public void testAddReservationNotExistRoom() throws Exception {
        roomaux = new MeetingRoom(building1, "20", 10);
        reservationaux = new Reservation(dataRes1, user1, roomaux);
        rDao.addReservation(reservationaux);
    }
    
    @Test(expected = AlredyExistReservationException.class)
    public void testAddReservationSameTimeAndRoomButDifferentUser() throws Exception {  
        reservationaux = new Reservation(dataRes1, user2, room1);
        rDao.addReservation(reservation1);
        rDao.addReservation(reservationaux);
    }
        
    @Test
    public void testAddReservationSameTimeAndRoomAndUser() throws Exception {  
        reservationaux = new Reservation(dataRes1, user1, room1);
        rDao.addReservation(reservation1);
        rDao.addReservation(reservationaux);
    }
    
    //@Test
    public void testAddReservationByAttribute() throws Exception {  
           
        long resId = rDao.addReservation(user1.getNif(), room1.getNumber(), 
                                         room1.getBuilding().getBuildingName(),
                                         dataRes1);
        expectsReservation = reservation1;
        actualReservation = rDao.getReservationById(resId);
        checkExistReservation(expectsReservation , actualReservation);
        
    }
        
    //@Test(expected = IncorrectRoomException.class)
    public void testAddReservationByAttributeNotExistRoom() throws Exception {
        
        rDao.addReservation("47658245M", "11", "testBuilding", dataRes1);
    }
    
    //@Test(expected = IncorrectBuildingException.class)
    public void testAddReservationByAttributeNotExistBuilding() throws Exception {
        
        rDao.addReservation("47658245M", "10","NotExistBuilding", dataRes1);
    }
    
    @Test(expected = IncorrectUserException.class)
    public void testAddReservationByAttributeNotExistUser() throws Exception {
        
        rDao.addReservation("47658205M", "10", "testBuilding", dataRes1);
    }

    @Test
    public void testGetReservationById()throws Exception{
        long resId = rDao.addReservation(reservation1);
        actualReservation = rDao.getReservationById(resId);
        assertEquals(reservation1,actualReservation);
    }


    @Test
    public void testGetAllReservation() throws Exception{
        rDao.addReservation(reservation1);
        assertEquals(reservationsToSet(reservation1), 
                                reservationsToSet(rDao.getAllReservation()));
     }
    
     @Test
     public void testGetAllReservationMultiReservations() throws Exception {
        allReservation.add(reservation1);
        allReservation.add(reservation2);
        allReservation.add(reservation3);
        
        addAllReservations();
        
        assertEquals(allReservation, reservationsToSet(rDao.getAllReservation()));
     }

    @Test
    public void testGetAllReservationByBuilding() throws Exception {
        allReservation.add(reservation1);
        allReservation.add(reservation3);
       
        addAllReservations();
        
        assertEquals(allReservation, 
                     reservationsToSet(rDao.getAllReservationByBuilding("testBuilding1")));
        
    }

    @Test
    public void testGetAllReservationByRoom() throws Exception {
        allReservation.add(reservation1);
        allReservation.add(reservation3);
        long idRoom = room1.getRoomId();
        addAllReservations();
        
        assertEquals(allReservation,reservationsToSet(rDao.getAllReservationByRoom(idRoom)));
    }

    @Test
    public void testRemoveReservation() throws Exception {
        long resId = rDao.addReservation(reservation1);
        actualReservation = rDao.getReservationById(resId);
        rDao.removeReservation(resId);
        
        checkDontExistReservation(reservation1, actualReservation);
        
    }
    
    //@Test
    public void testRemoveReservationByAtributes() throws Exception {
        
        long resId = rDao.addReservation(reservation1);
        actualReservation = rDao.getReservationById(resId);
        rDao.removeReservation(dataRes1, 
                                room1.getNumber(), 
                                building1.getBuildingName());
        
        checkDontExistReservation(reservation1, actualReservation);
        
    }
    
    //@Test(expected = IncorrectReservationException.class)
    public void testRemoveReservationByAtributesNoneExistReservation() throws Exception {

        rDao.removeReservation(dataRes1, 
                                room1.getNumber(), 
                                building1.getBuildingName());
        
        checkDontExistReservation(reservation1, actualReservation);
        
    }
    
    //@Test(expected = IncorrectBuildingException.class)
    public void testRemoveReservationByAtributesBadBuilding() throws Exception {

        rDao.removeReservation(dataRes1, 
                                room1.getNumber(), 
                                building1.getBuildingName());
        
        checkDontExistReservation(reservation1, actualReservation);
        
    }

    
    @Test
    public void testGetAllReservationByUserNif() throws Exception {
        reservation2.setrUser(user1);
        addAllReservations();
        
        allReservation.add(reservation1);
        allReservation.add(reservation2);
        
        assertEquals(allReservation,
                     reservationsToSet(rDao.getAllReservationByUserNif(user1.getNif())));

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
    
    private void addDB(Object... argv){
        em.getTransaction().begin();
        for (Object entity : argv) {
            em.persist(entity);
        }
        em.getTransaction().commit();
        
    }
    
    private void checkExistReservation(Reservation expected,Reservation actual){
        assertEquals(expected, actual);
        assertTrue(actual.getRoom().getReservations().contains(expected));
        assertTrue(actual.getrUser().getReservations().contains(expected));
    }
    
    private void checkDontExistReservation(Reservation check,Reservation on){
        assertEquals(null, rDao.getReservationById(on.getReservationId()));
        assertFalse(on.getRoom().getReservations().contains(check));
        assertFalse(on.getrUser().getReservations().contains(check));
    }
    
    private Set<Reservation> reservationsToSet(Reservation... res){
        return new HashSet<>(Arrays.asList(res));
    }
        
    private Set<Reservation> reservationsToSet(List res){
        return new HashSet<>(res);
    }

    private void deleteAll(Object... argv) {
        
        em.getTransaction().begin();
        for (Object entity : argv) {
            if(entity!=null){
                em.remove(em.merge(entity));
            }
        }
        /*
        
        Query query  = em.createQuery("DELETE FROM Reservation");
        Query query2 = em.createQuery("DELETE FROM User");
        Query query3 = em.createQuery("DELETE FROM Room");
        Query query4 = em.createQuery("DELETE FROM Building");
        int deleteRecords = query.executeUpdate();
        deleteRecords = query2.executeUpdate();
        deleteRecords = query3.executeUpdate();
        deleteRecords = query4.executeUpdate();
        */
        
        em.getTransaction().commit();
        
        

    }

    private void addAllReservations() throws Exception{
        rDao.addReservation(reservation1);
        rDao.addReservation(reservation2);
        rDao.addReservation(reservation3);
    }
    
}
