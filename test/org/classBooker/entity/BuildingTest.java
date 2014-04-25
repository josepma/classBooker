/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.entity;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mauro Fernando Churata
 */
public class BuildingTest {

    /**
     * First tests method, of class Building.
     */
    @Test
    public void testGetBuildingWithoutName() {
        System.out.println("get Building Without Name");
        Building instance = new Building();
        String expResult = "";
        String result = instance.getBuildingName();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBuildingWithName() {
        System.out.println("get Building With Name");
        Building instance = new Building("PolarBuild");
        String expResult = "PolarBuild";
        String result = instance.getBuildingName();
        assertEquals(expResult, result);
    }
    
}
