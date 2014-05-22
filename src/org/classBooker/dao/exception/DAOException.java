/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

/**
 *
 * @author cmb5
 */
public class DAOException extends Exception{
        
    public DAOException(){
        
    }
    
    /**
     *
     * @param msg
     */
    public DAOException(String msg) {
        super(msg);
    }
    
}
