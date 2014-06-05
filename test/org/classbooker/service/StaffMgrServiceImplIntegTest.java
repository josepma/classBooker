/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.service;

import org.classbooker.service.StaffMgrServiceImpl;
import org.classbooker.service.ReservationMgrServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.UserDAOImpl;
import org.classbooker.dao.exception.AlreadyExistingUserException;
import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.User;
import org.classbooker.service.exception.InexistentFileException;
import org.classbooker.service.exception.UnexpectedFormatFileException;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import static org.jmock.Expectations.throwException;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Antares
 */
public class StaffMgrServiceImplIntegTest {
    
    UserDAO uDao;
    StaffMgrServiceImpl staff;
    
    final User u = new ProfessorPas("nif", "email", "name");
    
    public StaffMgrServiceImplIntegTest() {
    }
    
    @Before
    public void setUp(){
        staff = new StaffMgrServiceImpl();
        uDao = new UserDAOImpl("classBookerIntegration");
        
        staff.setUserDao(uDao);
    }
    
    @Test
    public void addUser() throws AlreadyExistingUserException{
        staff.addUser(u);
        assertEquals(staff.getUser(u.getNif()),u);
    }
    
    @Test(expected = AlreadyExistingUserException.class)
    public void addRepeatedUser() throws AlreadyExistingUserException{
        staff.addUser(u);
        staff.addUser(u);
    }
    
    @Test
    public void addMassiveUserCsv() throws Exception {
        final TestAppender appender = new TestAppender();
        final Logger logger = Logger.getRootLogger();
        logger.addAppender(appender);
        
        staff.addMassiveUser("users.csv");
        
        try{
            Logger.getLogger(ReservationMgrServiceImpl.class);
        }
        finally{
            logger.removeAppender(appender);
        }
        final List<LoggingEvent> log = appender.getLog();
        final LoggingEvent firstLogEntrey = log.get(0);
        final LoggingEvent secondLogEntrey = log.get(1);
        String l = firstLogEntrey.getMessage().toString();
        assertEquals(l,"Empty data user.");
        l = secondLogEntrey.getMessage().toString();
        assertEquals(l,"Bad data user.");
        assertEquals(staff.getUser(u.getNif()),u);
    }
    
    @Test
    public void addMassiveUserWithRepeatedUsersCsv() throws Exception {
        final TestAppender appender = new TestAppender();
        final Logger logger = Logger.getRootLogger();
        logger.addAppender(appender);
        
        
        staff.addMassiveUser("repeatedUsers.csv");
        
        try{
            Logger.getLogger(ReservationMgrServiceImpl.class);
        }
        finally{
            logger.removeAppender(appender);
        }
        final List<LoggingEvent> log = appender.getLog();
        final LoggingEvent firstLogEntrey = log.get(0);
        String l = firstLogEntrey.getMessage().toString();
        assertEquals(l,"The file contains Repeated Users ");
        assertEquals(staff.getUser(u.getNif()),u);
    }
    
    @Test
    public void addMassiveUserXml() throws Exception {
        final TestAppender appender = new TestAppender();
        final Logger logger = Logger.getRootLogger();
        logger.addAppender(appender);
        
        staff.addMassiveUser("users.xml");
        
        try{
            Logger.getLogger(ReservationMgrServiceImpl.class);
        }
        finally{
            logger.removeAppender(appender);
        }
        final List<LoggingEvent> log = appender.getLog();
        final LoggingEvent firstLogEntrey = log.get(0);
        final LoggingEvent secondLogEntrey = log.get(1);
        String l = firstLogEntrey.getMessage().toString();
        assertEquals(l,"Empty data user.");
        l = secondLogEntrey.getMessage().toString();
        assertEquals(l,"Bad data user in the xml.");
        assertEquals(staff.getUser(u.getNif()),u);
    }
    
    @Test(expected = InexistentFileException.class)
    public void addMassiveUserWithInexistentFile() throws Exception {
        staff = new StaffMgrServiceImpl();
        staff.setUserDao(uDao);
        staff.addMassiveUser("inexistent.csv");
    }
    
    @Test(expected = UnexpectedFormatFileException.class)
    public void addMassiveUserWithUnexpectedFormatFile() throws Exception {
        staff = new StaffMgrServiceImpl();
        staff.setUserDao(uDao);
        staff.addMassiveUser("format.unexpected");
    }
    
    @After
    public void clear(){
        staff.deleteUser(u);
    }
    
}

