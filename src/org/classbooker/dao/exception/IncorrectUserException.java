/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao.exception;


/**
 *
 * @author Marc Sole, Carles Mònico
 */
public class IncorrectUserException extends DAOException {
    
    /**
     *
     */
    public IncorrectUserException(){
        
    }
    
    /**
     *
     * @param msg
     */
    public IncorrectUserException(String msg) {
        super(msg);
    }
            
}
