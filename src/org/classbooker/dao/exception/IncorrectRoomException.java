/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao.exception;

import org.apache.log4j.Logger;

/**
 *
 * @author Marc Sole, Carles Mònico
 */
public class IncorrectRoomException extends DAOException{

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
    public IncorrectRoomException(Logger log,String nameFunction) {
        log.error("in"+ nameFunction +"error:"+ this.getMessage());
    }
}
