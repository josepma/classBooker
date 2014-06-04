/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.classbooker.dao.exception.AlreadyExistingUserException;
import org.classbooker.dao.exception.IncorrectUserException;
import org.classbooker.dao.exception.InexistentUserException;
import org.classbooker.dao.exception.PersistException;
import org.classbooker.entity.User;

/**
 *
 * @author jpb3
 */
public class UserDAOImpl implements UserDAO{
    private EntityManager em;
    
    public UserDAOImpl(){
        em = Persistence.createEntityManagerFactory("classBookerIntegration").createEntityManager();
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
        if(u!=null){
            throw new AlreadyExistingUserException();
        }
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

    }

    @Override
    public User getUserByNif(String nif) {
        
        User u = null;
        
        em.getTransaction().begin();
        u = em.find(User.class, nif);
        em.getTransaction().commit();
        
        return u;
    }
    
    @Override
    public List<User> getUsersByName(String name) {
        List<User> users = null;
        
        em.getTransaction().begin();
        Query q = em.createQuery
                ("SELECT u FROM User u WHERE u.name ='"+name+"'");
        users = q.getResultList();
        em.getTransaction().commit();
        
        return users;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        
        em.getTransaction().begin();
        Query q = em.createQuery
                ("SELECT u FROM User u ");
        users = q.getResultList();
        em.getTransaction().commit();
            
        return users;
    }

    @Override
    public void modifyUser(User user) throws PersistException {
        //To change body of generated methods, choose Tools | Templates.
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void removeUser(User user) throws InexistentUserException {
        
        User u = getUserByNif(user.getNif());
        if(u==null){
            throw new InexistentUserException();
        }
        
        em.getTransaction().begin();
        Query q = em.createQuery
                ("DELETE FROM User u WHERE u.nif ='"+user.getNif()+"'");
        q.executeUpdate();
        em.getTransaction().commit();
               
    }
    
    public void tearDown(){
        em.getTransaction().begin();

        Query query = em.createQuery("DELETE FROM User");

        query.executeUpdate();

        em.getTransaction().commit();
        
    }
    
}
