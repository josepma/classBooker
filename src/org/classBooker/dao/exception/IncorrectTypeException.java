/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

/**
 *
 * @author msf7
 */
public class IncorrectTypeException extends Exception{
    
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
    
}
