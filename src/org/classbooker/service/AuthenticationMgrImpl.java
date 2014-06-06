/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import java.security.NoSuchAlgorithmException;
import org.classbooker.dao.UserDAO;
import org.classbooker.entity.User;
import org.classbooker.dao.exception.InexistentUserException;
import org.classbooker.util.Encoder;

/**
 *
 * @author Alejandro
 */
public class AuthenticationMgrImpl{
    
    private static UserDAO uDao;   
    static User loggedUser = null;

    static boolean login (String nif, String password) throws InexistentUserException, NoSuchAlgorithmException{
        User u = uDao.getUserByNif(nif);
        if(u==null){
            throw new InexistentUserException();
        }
        return u.getPassword().equals(getSHA(password));
    }

    static boolean logout(){
        loggedUser = null;
        return true;
    }

    static String getSHA(String password) throws NoSuchAlgorithmException{
        return Encoder.codifySHA256(password);
    }

    static User getLoggedUser(){
        return loggedUser;
    }
    
      
}
