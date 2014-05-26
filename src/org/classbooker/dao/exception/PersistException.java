package org.classbooker.dao.exception;


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
}
