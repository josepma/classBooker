/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import org.classBooker.entity.User;

/**
 *
 * @author josepma
 */
public interface StaffMgrService {
    public void addUser(User user);
    public void addMassiveUser(String filename);
    public void deleteUser(User user);
    public void modifyUserInformation(User user);
}
