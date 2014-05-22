/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

import org.apache.log4j.Logger;

/**
 *
 * @author msf7
 */
public class IncorrectTypeException extends DAOException{
    
    /**
     *
     */
    public IncorrectTypeException(){
        
    }
    
    /**
     *
     * @param message
     */
    public IncorrectTypeException(String message){
        super(message);
    }
    public IncorrectTypeException(Logger log,String nameFunction) {
        log.error("in"+ nameFunction +"error:"+ this.getMessage());
    }
    
}
