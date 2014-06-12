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

    /**
     * It tries to log the user with the nif nif and the password password into 
     * the system, if the user is not in the database throw an
     * InexistentUserException, if the password don't matches the username, it 
     * decrement the tries counter, and returns false, on the other hand if the
     * password and the user name matches, it logs in the user.
     * 
     * @param nif
     * @param password
     * @return
     * @throws InexistentUserException
     * @throws NoSuchAlgorithmException 
     */
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

    /**
     * If a user is logged in, this function logs out it, if there's no user
     * logged in it returns false.
     * 
     * @return 
     */
    public static boolean logout(){
        if(loggedUser!=null){
            loggedUser = null;
            return true;
        }
        return false;
    }

    private static String getSHA(String password) throws NoSuchAlgorithmException{
        return Encoder.codifySHA256(password);
    }

    /**
     * It returns the user that is logged into the system, if therses no
     * logged user in, returns null.
     * 
     * @return 
     */
    public static User getLoggedUser(){
        return loggedUser;
    }
    
    /**
     * Return the restant number of tries to log in a user into the system.
     * 
     * @return 
     */
    public static int getTries(){
        return tries;
    }
    
      
}
