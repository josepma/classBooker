/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao;

import java.util.List;
import org.classBooker.dao.exception.IncorrectUserException;
import org.classBooker.dao.exception.PersistException;
import org.classBooker.entity.User;

/**
 *
 * @author josepma, Marc Solé, Carles Mònico
 */
public interface UserDAO {
    
    /**
     *
     * @param user
     * @throws PersistException
     * @throws IncorrectUserException
     */
    void addUser(User user) throws PersistException, IncorrectUserException;

    /**
     *
     * @param nif
     * @return
     */
    User getUserByNif(String nif);
    
    /**
     * @param name
     * @return
     */
    
    User getUserByName(String name);

    /**
     *
     * @return
     */
    List<User> getAllUsers();
    
     /**
     * 
     * @param user
     * @throws PersistException
     * @throws IncorrectUserException
     */
    void modifyUser(User user) throws PersistException, IncorrectUserException;
    
    /**
     *
     * @param user
     * @throws IncorrectUserException
     */
    void removeUser(User user) throws IncorrectUserException;
    
}
