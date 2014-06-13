package org.classbooker.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.classbooker.dao.*;
import static org.classbooker.main.Main.resDao;
import org.classbooker.service.*;
import org.classbooker.presentation.view.*;

public final class Main {

    static ReservationMgrServiceImpl resService;
    static SpaceMgrServiceImpl spaceService;
    static StaffMgrServiceImpl staffService;
    static AuthenticationMgr authMgr;

    static ReservationDAO resDao;
    static SpaceDAO spaceDao;
    static UserDAO userDao;

    static EntityManager em;

    private Main() {

    }

    public static void main(String[] args) throws Exception {
        //Empty method
        System.out.println("hello world");

        setUpEnvironment();

        ClassBookerFrame classBooker = new ClassBookerFrame(resService, spaceService, staffService, authMgr);

    }

    private static void setUpEnvironment() throws Exception {

        resDao = new ReservationDAOImpl();
        spaceDao = new SpaceDAOImpl();
        userDao = new UserDAOImpl();

        em = getEntityManager();

        spaceDao.setEm(em);
        userDao.setEntityManager(em);
        resDao.setEm(em);
        resDao.setuDao(userDao);
        resDao.setsDao(spaceDao);

        resService = new ReservationMgrServiceImpl();
        spaceService = new SpaceMgrServiceImpl();
        staffService = new StaffMgrServiceImpl();

        spaceService.setSpd(spaceDao);

        resService.setReservationDao(resDao);
        resService.setSpaceDao(spaceDao);
        resService.setUserDao(userDao);

        staffService.setUserDao(userDao);

        spaceService.setSpd(spaceDao);

    }

    private static EntityManager getEntityManager() throws Exception {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("classBookerIntegration");
                    //Three different persistence units can be used:
                    //"classBooker": local DB with embedded DERBY DBMS kept in memory.
                    //"classBookerHosted": local DB with hosted DERBY DBMS. You can see
                    //                     the contents of the DB in Netbeans
                    //"classBookerIntegration": Remote integration DB. MySQL DBMS.
                    //                          This DB should be restored at the end.
                    //Have a look at README and src/META-INF/persistence.xml.
        return emf.createEntityManager();
    }

}
