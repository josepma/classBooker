/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.util;

import org.classbooker.entity.User;

/**
 *
 * @author Alejandro
 */

public interface AuthenticationMgr {
 
    static User loggedUser = null;

    boolean login (String nif, String password);

    boolean logout();

    String getSHA(String password);

    User getLoggedUser();
    
    
}
