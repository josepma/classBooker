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
public class AlreadyExistingBuilding extends Exception {

    /**
     * Creates a new instance of <code>AlreadyExistingBuilding</code> without
     * detail message.
     */
    public AlreadyExistingBuilding() {
    }

    /**
     * Constructs an instance of <code>AlreadyExistingBuilding</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AlreadyExistingBuilding(String msg) {
        super(msg);
    }
}
