/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

/**
 *
 * @author Marc Sole, Carles Mònico
 */
public class IncorrectRoomException extends Exception{

    /**
     *
     */
    public IncorrectRoomException(){
    }

    /**
     *
     * @param message
     */
    public IncorrectRoomException(String message){
        super(message); 
    }
    
}
