/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

/**
 *
 * @author richeng Jin
 */
public class IncorrectTimeException extends Exception{
      public IncorrectTimeException(){
        
      }
      
      public IncorrectTimeException(String message){
        super(message);
      }
}
