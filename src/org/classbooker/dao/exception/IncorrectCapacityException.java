/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.dao.exception;

/**
 *
 * @author Albert
 */
public class IncorrectCapacityException extends DAOException {

    /**
     * Creates a new instance of <code>IncorrectCapacityException</code> without
     * detail message.
     */
    public IncorrectCapacityException() {
    }

    /**
     * Constructs an instance of <code>IncorrectCapacityException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public IncorrectCapacityException(String msg) {
        super(msg);
    }
}
