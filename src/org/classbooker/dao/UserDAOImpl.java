/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.classbooker.dao.exception.AlreadyExistingUserException;
import org.classbooker.dao.exception.IncorrectUserException;
import org.classbooker.dao.exception.PersistException;
import org.classbooker.entity.User;

/**
 *
 * @author jpb3
 */
public class UserDAOImpl implements UserDAO{
    private EntityManager em;
    
    public UserDAOImpl(){
        em = Persistence.createEntityManagerFactory("classBooker").createEntityManager();
    }
    
    public UserDAOImpl(String persistanceUnit){
        em = Persistence.createEntityManagerFactory(persistanceUnit).createEntityManager();
    }
    
    public void setEntityManager(EntityManager em){
        this.em = em;
    }
    
    public EntityManager getEntityManager(){
        return em;
    }

    @Override
    public void addUser(User user) throws AlreadyExistingUserException {
        User u = getUserByNif(user.getNif());
        if(u!=null)
            throw new AlreadyExistingUserException();
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        finally{
            
        }
    }

    @Override
    public User getUserByNif(String nif) {
        
        User u = null;
        
        try{
            em.getTransaction().begin();
            u = em.find(User.class, nif);
            em.getTransaction().commit();
        }
        finally{
            
        }
        return u;
    }
    
    @Override
    public List<User> getUsersByName(String name) {
        List<User> users = null;
        
        try{
            em.getTransaction().begin();
            Query q = em.createQuery
                    ("SELECT u FROM User u WHERE u.name ='"+name+"'");
            users = q.getResultList();
            em.getTransaction().commit();
        }
        finally{
            
        }
        return users;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        
        try{
            em.getTransaction().begin();
            Query q = em.createQuery
                    ("SELECT u FROM User u ");
            users = q.getResultList();
            em.getTransaction().commit();
        }
        finally{
            
        }
        return users;
    }

    @Override
    public void modifyUser(User user) throws PersistException, IncorrectUserException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeUser(User user) throws IncorrectUserException {
        try{
            em.getTransaction().begin();
            Query q = em.createQuery
                    ("DELETE FROM User u WHERE u.nif ='"+user.getNif()+"'");
            q.executeUpdate();
            em.getTransaction().commit();
        }
        finally{
            
        }
    }
    
    public void tearDown(){
        em.getTransaction().begin();

        Query query = em.createQuery("DELETE FROM User");

        int deleteRecords = query.executeUpdate();

        em.getTransaction().commit();
        System.out.println("All records have been deleted.");
    }
    
}
