/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classBooker.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.classBooker.dao.UserDAO;
import org.classBooker.dao.exception.AlreadyExistingUserException;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.User;
import org.classBooker.service.exception.InexistentFileException;
import org.classBooker.service.exception.UnexpectedFormatFileException;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import static org.jmock.Expectations.throwException;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Antares
 */
public class StaffMgrServiceImplTest {
    
    Mockery context = new JUnit4Mockery();
    UserDAO uDao;
    StaffMgrServiceImpl staff;
    
    final User u = new ProfessorPas("nif", "email", "name");
    
    public StaffMgrServiceImplTest() {
    }
    
    @Before
    public void setUp(){
        staff = new StaffMgrServiceImpl();
        uDao = context.mock(UserDAO.class);
        staff.setUserDao(uDao);
    }
    
    @Test
    public void addUser() throws AlreadyExistingUserException{
        setExpectationsAddUser();
        staff.addUser(u);
        assertEquals(staff.getUser(u.getNif()),u);
    }
    
    @Test(expected = AlreadyExistingUserException.class)
    public void addRepeatedUser() throws AlreadyExistingUserException{
        setExpectationsAddRepeatedUser();
        staff.addUser(u);
        staff.addUser(u);
    }
    
    @Test
    public void addMassiveUserCsv() throws Exception {
        final TestAppender appender = new TestAppender();
        final Logger logger = Logger.getRootLogger();
        logger.addAppender(appender);
        
        setExpectationsAddUser();
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
        
        
        setExpectationsAddRepeatedUserCsv();
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
        assertEquals(l,"The file contains Repeated Users");
        assertEquals(staff.getUser(u.getNif()),u);
    }
    
    @Test
    public void addMassiveUserXml() throws Exception {
        setExpectationsAddUser();
        staff.addMassiveUser("users.xml");
        assertEquals(staff.getUser(u.getNif()),u);
    }
    
    @Test(expected = InexistentFileException.class)
    public void addMassiveUserWithInexistentFile() throws Exception {
        staff = new StaffMgrServiceImpl();
        staff.addMassiveUser("inexistent.csv");
    }
    
    @Test(expected = UnexpectedFormatFileException.class)
    public void addMassiveUserWithUnexpectedFormatFile() throws Exception {
        staff = new StaffMgrServiceImpl();
        staff.addMassiveUser("format.unexpected");
    }

    private void setExpectationsAddUser() throws AlreadyExistingUserException{
        context.checking(new Expectations(){{ 
            allowing(uDao).addUser(u);
            oneOf(uDao).getUserByNif(u.getNif());will(returnValue(u));
         }});  
    }

    private void setExpectationsAddRepeatedUser() throws AlreadyExistingUserException{
        context.checking(new Expectations(){{ 
            oneOf(uDao).addUser(u);
            oneOf(uDao).addUser(u);will(throwException(new AlreadyExistingUserException()));
         }}); 
    }
    
    private void setExpectationsAddRepeatedUserCsv() throws AlreadyExistingUserException{
        context.checking(new Expectations(){{ 
            oneOf(uDao).addUser(u);
            oneOf(uDao).addUser(u);will(throwException(new AlreadyExistingUserException()));
            oneOf(uDao).getUserByNif(u.getNif());will(returnValue(u));
         }}); 
    }
    
    
}

class TestAppender extends AppenderSkeleton {
    private final List<LoggingEvent> log = new ArrayList<LoggingEvent>();

    @Override
    protected void append(final LoggingEvent le) {
        log.add(le);
    }

    @Override
    public void close() {
        
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
    
    public List<LoggingEvent> getLog(){
        return new ArrayList<LoggingEvent>(log);
    }
}