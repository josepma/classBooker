/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classBooker.service.exception;

/**
 *
 * @author Antares
 */
public class UnexpectedFormatFileException extends Exception{
    
     /**
     * Creates a new instance of <code>UnexpectedFormatFileException</code> without
     * detail message.
     */
    public UnexpectedFormatFileException() {
    }

    /**
     * Constructs an instance of <code>UnexpectedFormatFileException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UnexpectedFormatFileException(String msg) {
        super(msg);
    }
    
}
