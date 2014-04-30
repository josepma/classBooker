/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

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
}
