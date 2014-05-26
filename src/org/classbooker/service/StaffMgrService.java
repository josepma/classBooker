/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import org.classbooker.dao.exception.AlreadyExistingUserException;
import org.classbooker.entity.User;
import org.classbooker.service.exception.InexistentFileException;
import org.classbooker.service.exception.UnexpectedFormatFileException;

/**
 *
 * @author josepma
 */
public interface StaffMgrService {
    public void addUser(User user) throws AlreadyExistingUserException;
    public void addMassiveUser(String filename) 
            throws UnexpectedFormatFileException, InexistentFileException;
    public User getUser(String nif);
    public void deleteUser(User user);
    public void modifyUserInformation(User user);
}
