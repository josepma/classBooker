/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

/**
 *
 * @author jpb3
 */
public class AlreadyExistingUserException extends Exception {
    
    /**
     *
     */
    public AlreadyExistingUserException(){
        
    }
    
    /**
     *
     * @param msg
     */
    public AlreadyExistingUserException(String msg) {
        super(msg);
    }
    
}
