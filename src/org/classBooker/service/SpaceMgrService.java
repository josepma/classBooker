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
    public void addRoom(Room room);
    public void addBuilding(Building building);
    public void deleteRoom(Room room);
    public void deleteBuilding(Building building);
    public Room modifyRoom();
    public Building modifyBuilding();
}
