/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao;

import org.classbooker.dao.UserDAOImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.classbooker.dao.exception.AlreadyExistingUserException;
import org.classbooker.dao.exception.IncorrectUserException;
import org.classbooker.dao.exception.InexistentUserException;
import org.classbooker.entity.User;
import org.classbooker.entity.ProfessorPas;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author jpb3
 */
public class UserDAOImplIntegTest {
    UserDAOImpl udao;
    User u;
    User us;
    User u1;
    List<User> expected; 
    Set<User> expectedSet;
    
    public UserDAOImplIntegTest() {
    }
    
    @Before
    public void setUp() throws Exception{
        expected = new ArrayList<>();
        udao = new UserDAOImpl("classBookerIntegration");
        u = new ProfessorPas("12345","pepito@gmail.com","Pepito");
        us = new ProfessorPas("98765","jaunito@gmail.com","Juanito");
        addUser(u);
        addUser(us);
        expected.add(u);
        expected.add(us);
        expectedSet = new HashSet(expected);
    }
    
    @Test
    public void testAddUser() throws Exception{
        u1 = new ProfessorPas("66666","manganito@gmail.com","Manganito");
        udao.addUser(u1);
        User exp = findUserByNif("66666");
        assertEquals(u1,exp);
        removeUser(exp);
    }
    
    @Test
    public void testRemoveUser() throws Exception{
        User user = udao.getUserByNif("12345");
        assertEquals(u,user);
        udao.removeUser(user);
        user = udao.getUserByNif("12345");
        assertNull(user);
    }
    
    @Test(expected = InexistentUserException.class)
    public void removeInexistentUser() throws Exception{
        User user  = new ProfessorPas("9999", "notin@database.com", "Inexistent");
        udao.removeUser(user);
    }
    
    @Test(expected = AlreadyExistingUserException.class)
    public void testAddRepeatedUser() throws Exception{
        udao.addUser(u);
    }
    
    @Test
    public void testGetInexistingUser(){
        User user = udao.getUserByNif("NotANifInTheDatabase");
        assertNull(user);
    }
    
    @Test
    public void testGetUserByNif() throws Exception {
        User u2 = udao.getUserByNif("12345");
        assertEquals("These two users should be equals",u,u2);
        
    }
    
    @Test
    public void testGetUsersByName() throws Exception {
        List<User> users = udao.getUsersByName("Pepito");
        assertEquals("These two users should be equals",u,users.get(0));
        
    }
    
    @Test
    public void testGetUsersByNameThatNoAreInTheDatabase() throws Exception {
        clear();
        List<User> users = udao.getUsersByName("Pepito");
        assertEquals(new ArrayList(),users);
        
    }
 
    @After
    public void clear() throws IncorrectUserException{
        removeUser(u);
        removeUser(us);
    }
    
    private void addUser(User u){
        EntityManager em = udao.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        }
        finally{
            
        }
    }
    
    private User findUserByNif(String nif){
        User u = null;
        
        EntityManager em = udao.getEntityManager();
        
        try{
            em.getTransaction().begin();
            u = em.find(User.class, nif);
            em.getTransaction().commit();
        }
        finally{
            
        }
        return u;
    }
    
    private void removeUser(User u){
        EntityManager em = udao.getEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery
                ("DELETE FROM User u WHERE u.nif ='"+u.getNif()+"'");
        q.executeUpdate();
        em.getTransaction().commit();
    }
    
}
