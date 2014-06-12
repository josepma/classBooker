/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service;

import java.security.NoSuchAlgorithmException;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.UserDAOImpl;
import org.classbooker.entity.User;
import org.classbooker.dao.exception.InexistentUserException;
import org.classbooker.util.Encoder;

/**
 *
 * @author Alejandro
 */
public class AuthenticationMgr{
    
    private static UserDAO uDao = new UserDAOImpl("classBookerIntegration");   
    private static User loggedUser = null;
    private static int tries=3;

    public static boolean login (String nif, String password) throws InexistentUserException, NoSuchAlgorithmException{
        User u = uDao.getUserByNif(nif);
        if(u==null){
            throw new InexistentUserException();
        }
        if(u.getPassword().equals(getSHA(password))){
            loggedUser = u;
            tries=3;
            return true;
        }
        tries=tries-1;
        return false;
        
    }

    public static boolean logout(){
        if(loggedUser!=null){
            loggedUser = null;
            return true;
        }
        return false;
    }

    public static String getSHA(String password) throws NoSuchAlgorithmException{
        return Encoder.codifySHA256(password);
    }

    public static User getLoggedUser(){
        return loggedUser;
    }
    
    public static int getTries(){
        return tries;
    }
    
      
}
