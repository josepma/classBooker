/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

import org.apache.log4j.Logger;

/**
 *
 * @author Marc Sole, Carles Mònico
 */
public class IncorrectBuildingException extends Exception {
    
    /**
     *
     */
    public IncorrectBuildingException(){
        
    }
    
    /**
     *
     * @param message
     */
    public IncorrectBuildingException(String message){
        super(message);
    }
    public IncorrectBuildingException(Logger log,String nameFunction) {
        log.error("in"+ nameFunction +"error:"+ this.getMessage());
    }
    
}
