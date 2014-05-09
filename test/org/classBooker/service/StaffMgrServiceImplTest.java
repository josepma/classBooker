/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classBooker.service;

import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.User;
import org.classBooker.service.exception.InexistentFileException;
import org.classBooker.service.exception.UnexpectedFormatFileException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antares
 */
public class StaffMgrServiceImplTest {
    
    public StaffMgrServiceImplTest() {
    }
    
    @Test
    public void addUser(){
        StaffMgrService staff = new StaffMgrServiceImpl();
        User u = new ProfessorPas("nif", "email", "name");
        staff.addUser(u);
    }
    
    @Test
    public void addMassiveUser() throws Exception {
        StaffMgrService staff = new StaffMgrServiceImpl();
        staff.addMassiveUser("users.xml");
    }
    
    @Test(expected = InexistentFileException.class)
    public void addMassiveUserWithInexistentFile() throws Exception {
        StaffMgrService staff = new StaffMgrServiceImpl();
        staff.addMassiveUser("inexistent.csv");
    }
    
    
}