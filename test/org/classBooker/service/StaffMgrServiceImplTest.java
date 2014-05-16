/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classBooker.service;

import org.classBooker.dao.UserDAO;
import org.classBooker.dao.exception.AlreadyExistingUserException;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.User;
import org.classBooker.service.exception.InexistentFileException;
import org.classBooker.service.exception.UnexpectedFormatFileException;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
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
        setExpectationsAddUser();
        staff.addMassiveUser("users.csv");
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
    
    
}