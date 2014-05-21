/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao.exception;

import org.apache.log4j.Logger;

/**
 *
 * @author saida
 */
public class AlreadyExistingRoomException extends Exception {

    /**
     * Creates a new instance of <code>AlreadyExistingRoom</code> without detail
     * message.
     */
    public AlreadyExistingRoomException() {
    }

    /**
     * Constructs an instance of <code>AlreadyExistingRoom</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AlreadyExistingRoomException(String msg) {
        super(msg);
    }
    
    public AlreadyExistingRoomException(Logger log,String nameFunction) {
        log.error("in"+ nameFunction +"error:"+ this.getMessage());
    }
}
