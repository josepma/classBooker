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
public class NonBuildingException extends Exception {

    /**
     * Creates a new instance of <code>NonBuildingException</code> without
     * detail message.
     */
    public NonBuildingException() {
    }

    /**
     * Constructs an instance of <code>NonBuildingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NonBuildingException(String msg) {
        super(msg);
    }
}
