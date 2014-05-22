package org.classbooker.dao.exception;

import org.apache.log4j.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Marc Sole, Carles Mònico
 */
public class PersistException extends DAOException {

    /**
     * Creates a new instance of
     * <code>PersistException</code> without detail message.
     */
    public PersistException() {
    }

    /**
     * Constructs an instance of
     * <code>PersistException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PersistException(String msg) {
        super(msg);
    }
    public PersistException(Logger log,String nameFunction) {
        log.error("in"+ nameFunction +"error:"+ this.getMessage());
    }
}
