/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import org.classbooker.dao.UserDAO;
import org.classbooker.dao.exception.AlreadyExistingUserException;
import org.classbooker.entity.User;
import org.classbooker.service.exception.ServiceException;

/**
 *
 * @author josepma
 */
public interface StaffMgrService {
    /**
     * Adds the user User into the database. 
     * If the user is already in the database it 
     * throws an AlreadyExistingUserException.
     * 
     * @param user
     * @throws AlreadyExistingUserException 
     */
    void addUser(User user) throws AlreadyExistingUserException;
    
    /**
     * Adds all the users in the file filename in to the Database, if the file 
     * is not found or is bad formatted it threws a serviceException.
     * 
     * @param filename
     * @throws ServiceException 
     */
    void addMassiveUser(String filename) 
            throws ServiceException;
    
    /**
     * Retrieve the user with the nif nif from the database.
     * 
     * @param nif
     * @return 
     */
    User getUser(String nif);
    
    /**
     * Deletes the user passe by parameter from the Database.
     * 
     * @param user 
     */
    void deleteUser(User user);
    
    /**
     * Modify the user passe by parameter from the Database.
     * 
     * @param user 
     */
    void modifyUserInformation(User user);
    
    /**
     * Set the DAO object.
     * 
     * @param userdao 
     */
    void setUserDao(UserDAO userdao);
}
