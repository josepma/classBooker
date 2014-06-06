/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.entity;

import org.classbooker.entity.User;
import org.classbooker.entity.StaffAdmin;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author agl5
 */
public class SpaceAdminTest {
    
    public SpaceAdminTest() {
    }
    @Test
    public void testCreateUser() {
        
        User user = new StaffAdmin("123","email@udl.cat","manolito","");
        assertEquals("123",user.getNif());
        assertEquals("email@udl.cat",user.getEmail());
        assertEquals("manolito",user.getName());
    }
}
