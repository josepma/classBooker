/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import org.classbooker.dao.exception.DAOException;
import org.classbooker.entity.Building;
import org.classbooker.entity.Room;

/**
 *
 * @author josepma
 */
public interface SpaceMgrService {
    public void addRoom(String number, String buildingName, int capacity, String type) throws DAOException;
    public void addBuilding(String name) throws DAOException;
    public void deleteRoom(long id);
    public void deleteBuilding(long id);
    public Room modifyRoom(long id, String buildingName, int capacity, String type);
    public Building modifyBuilding(long id, String name);
}
