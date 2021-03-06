/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.classbooker.dao.exception.AlreadyExistingUserException;
import org.classbooker.dao.exception.IncorrectUserException;
import org.classbooker.dao.exception.InexistentUserException;
import org.classbooker.dao.exception.PersistException;
import org.classbooker.entity.User;

/**
 *
 * @author josepma, Marc Solé, Carles Mònico
 */
public interface UserDAO {
    
    /**
     * This function adds a User into the Database, if this user is already in, 
     * the function throws an AlreadyExistingUserException
     * 
     * @param user, is the user that would be inserted in the database
     * @throws AlreadyExistingUserException
     */
    void addUser(User user) throws AlreadyExistingUserException;

    /**
     * This function Search into the database a user with a specific nif
     *
     * @param nif, this is the nif that we would use to search into the DB
     * @return the user if it found it, if there's no user with thah nif
     * returns null
     */
    User getUserByNif(String nif);
    
    /**
     * This function Search into the database all the user's with a specific 
     * name, it would return more than one user.
     * 
     * @param name this is the name that we would use to search into the DB
     * @return a List filled with the users that match the name, if there's no
     * users with that name, returns an empty list
     */
    
    List<User> getUsersByName(String name);

    /**
     * This function retrieves all the users from the database
     * 
     * @return a List filled with all the users of the database, if the Database
     * is empty it would return an empty list
     */
    List<User> getAllUsers();
    
     /**
     * 
     * @param user
     * @throws PersistException
     * @throws IncorrectUserException
     */
    void modifyUser(User user) throws PersistException;
    
    /**
     *This function remove a User from the Database
     * 
     * @param user the user we want to remove
     * @throws InexistentUserException if the user is not in the Database
     */
    void removeUser(User user) throws InexistentUserException;
    void setEntityManager(EntityManager em);
    
}
