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
public class AlreadyExistingRoom extends Exception {

    /**
     * Creates a new instance of <code>AlreadyExistingRoom</code> without detail
     * message.
     */
    public AlreadyExistingRoom() {
    }

    /**
     * Constructs an instance of <code>AlreadyExistingRoom</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AlreadyExistingRoom(String msg) {
        super(msg);
    }
}
