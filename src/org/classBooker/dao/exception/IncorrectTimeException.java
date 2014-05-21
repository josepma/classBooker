/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.dao.exception;

import org.apache.log4j.Logger;

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
    public IncorrectTimeException(Logger log,String nameFunction) {
        log.error("in"+ nameFunction +"error:"+ this.getMessage());
    }
}
