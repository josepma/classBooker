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
    void addUser(User user) throws AlreadyExistingUserException;
    void addMassiveUser(String filename) 
            throws ServiceException;
    User getUser(String nif);
    void deleteUser(User user);
    void modifyUserInformation(User user);
    void setUserDao(UserDAO userdao);
}
