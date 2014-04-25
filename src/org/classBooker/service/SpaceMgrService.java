/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.service;

import org.classBooker.entity.Building;
import org.classBooker.entity.Room;

/**
 *
 * @author josepma
 */
public interface SpaceMgrService {
    public void addRoom(long id, String buildingName, int capacity, String type);
    public void addBuilding(long id, String name);
    public void deleteRoom(long id);
    public void deleteBuilding(long id);
    public Room modifyRoom(long id, String buildingName, int capacity, String type);
    public Building modifyBuilding(long id, String name);
}
