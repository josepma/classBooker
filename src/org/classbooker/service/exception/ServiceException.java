/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.service.exception;

/**
 *
 * @author Alejandro
 */
public class ServiceException extends Exception{
    public ServiceException(){
        
    }
    
    /**
     *
     * @param msg
     */
    public ServiceException(String msg) {
        super(msg);
    }
}
