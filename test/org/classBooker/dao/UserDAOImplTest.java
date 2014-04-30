/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.dao.exception.PersistException;
import org.classBooker.entity.User;
import org.classBooker.entity.ProfessorPas;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author jpb3
 */
public class UserDAOImplTest {
    EntityManager em;
    UserDAOImpl udao;
    User u;
    
    public UserDAOImplTest() {
    }
    
    @Before
    public void setUp2() throws Exception{
        udao = new UserDAOImpl("classBooker");
        u = new ProfessorPas("nif","email","name");
        udao.addUser(u);
    }
    
    
    @Test
    public void testGetUserByNif() throws Exception {
        User u2 = udao.getUserByNif("nif");
        assertEquals("These two users should be equals",u,u2);
        
    }
    
    @Test
    public void testGetUserByName() throws Exception {
        User u2 = udao.getUserByName("name");
        assertEquals("These two users should be equals",u,u2);
        
    }
    
    @After
    public void clear(){
        udao.tearDown();
    }
    
}
