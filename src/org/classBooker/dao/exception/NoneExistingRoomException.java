/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

/**
 *
 * @author villartexr
 */
public class NoneExistingRoomException extends Exception {

    /**
     * Creates a new instance of <code>NoneExistingRoomException</code> without
     * detail message.
     */
    public NoneExistingRoomException() {
    }

    /**
     * Constructs an instance of <code>NoneExistingRoomException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoneExistingRoomException(String msg) {
        super(msg);
    }
}