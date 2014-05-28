package org.classbooker.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.classbooker.dao.*;
import static org.classbooker.main.Main.resDao;
import org.classbooker.service.*;
import org.classbooker.presentation.view.*;

public final class Main{

    static ReservationMgrServiceImpl resService;
    static SpaceMgrService spaceService;
    static StaffMgrServiceImpl staffService;
        
    static ReservationDAO resDao;
    static SpaceDAO spaceDao ;
    static UserDAO userDao ;
        
    static EntityManager em;

    
    
    private Main(){
        
    }
    public static void main(String[] args) throws Exception{
        //Empty method
                System.out.println("hello world");
 

        setUpEnvironment();        
                
         
        
        ClassBookerFrame classBooker = new ClassBookerFrame(resService,spaceService,staffService);
        
    }
    


   private static void setUpEnvironment() throws Exception{

        resDao = new ReservationDAOImpl();
        spaceDao = new SpaceDAOImpl();
        UserDAO userDao = new UserDAOImpl();
        
        em= getEntityManager();
      
        spaceDao.setEm(em);
        userDao.setEntityManager(em);
        
        
         resDao.setEm(em);
         resDao.setuDao(userDao);
         resDao.setsDao(spaceDao);
        
         resService = new ReservationMgrServiceImpl();
         //spaceService = new SpaceMgrServiceImpl();
         staffService = new StaffMgrServiceImpl();
         
         resService.setReservationDao(resDao);
         resService.setSpaceDao(spaceDao);
         resService.setUserDao(userDao);
         
         staffService.setUserDao(userDao);
         

    }

    private static EntityManager getEntityManager() throws Exception {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("classBooker");

        return emf.createEntityManager();
    }


}