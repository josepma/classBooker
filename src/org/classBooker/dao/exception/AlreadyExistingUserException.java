/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

import org.apache.log4j.Logger;

/**
 *
 * @author jpb3
 */
public class AlreadyExistingUserException extends DAOException {
    
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
    public AlreadyExistingUserException(Logger log,String nameFunction) {
        log.error("in"+ nameFunction +"error:"+ this.getMessage());
    }
    
}
