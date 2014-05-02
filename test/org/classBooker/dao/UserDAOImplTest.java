/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.util.ArrayList;
import java.util.List;
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
    List<User> expected; 
    
    public UserDAOImplTest() {
    }
    
    @Before
    public void setUp() throws Exception{
        expected = new ArrayList<>();
        udao = new UserDAOImpl("classBooker");
        u = new ProfessorPas("12345","pepito@gmail.com","Pepito");
        User us = new ProfessorPas("98765","jaunito@gmail.com","Juanito");
        udao.addUser(u);
        udao.addUser(us);
        expected.add(u);
        expected.add(us);
    }
    
    //TODO: test addUser
    //      add repeated user
    
    @Test
    public void testGetUserByNif() throws Exception {
        User u2 = udao.getUserByNif("12345");
        assertEquals("These two users should be equals",u,u2);
        
    }
    
    @Test
    public void testGetUserByName() throws Exception {
        User u2 = udao.getUserByName("Pepito");
        assertEquals("These two users should be equals",u,u2);
        
    }
    
    //TODO: add a test returning more than one user by name
    
    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = udao.getAllUsers();
        assertEquals("These two users should be equals",users,expected);
        
    }
    
    @After
    public void clear(){
        udao.tearDown();
    }
    
}
