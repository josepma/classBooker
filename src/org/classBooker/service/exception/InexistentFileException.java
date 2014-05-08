/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classBooker.service.exception;

/**
 *
 * @author Antares
 */
public class InexistentFileException extends Exception{
    
    /**
     * Creates a new instance of <code>InexistentFileException</code> without
     * detail message.
     */
    public InexistentFileException() {
    }

    /**
     * Constructs an instance of <code>InexistentFileException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InexistentFileException(String msg) {
        super(msg);
    }
    
}
