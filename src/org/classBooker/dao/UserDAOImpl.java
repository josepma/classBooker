/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.dao.exception.PersistException;
import org.classBooker.entity.User;

/**
 *
 * @author jpb3
 */
public class UserDAOImpl implements UserDAO{
    
    private EntityManagerFactory emf;
    private String persistenceUnit;
    
    public UserDAOImpl(){
        emf = null;
        persistenceUnit = null;
    }
    
    public UserDAOImpl(String pu){
        persistenceUnit = pu;
        emf = Persistence.createEntityManagerFactory(pu);
    }
    
    public void setPersistenceUnit(String pu){
        persistenceUnit = pu;
        emf = Persistence.createEntityManagerFactory(pu);
    }
    
    public String getPersistenceUnit(){
        return persistenceUnit;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    @Override
    public void addUser(User user) throws PersistException, 
            IncorrectUserException {
        EntityManager em = this.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        finally{
            if(em.isOpen())
                em.close();
        }
    }

    @Override
    public User getUserByNif(String nif) {
        
        User u = null;
        
        EntityManager em = emf.createEntityManager();
        
        try{
            em.getTransaction().begin();
            u = em.find(User.class, nif);
            em.getTransaction().commit();
        }
        finally{
            if(em.isOpen())
                em.close();
        }
        return u;
    }
    
    @Override
    public User getUserByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getAllUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifyUser(User user) throws PersistException, IncorrectUserException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeUser(User user) throws IncorrectUserException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void close(){
        if(emf!=null && emf.isOpen())
            emf.close();
    }
    
    public void tearDown(){
        EntityManager em = this.getEntityManager();
        if (em.isOpen())
            em.close();
        em = getEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("DELETE FROM User");

        int deleteRecords = query.executeUpdate();

        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");
    }
    
}
