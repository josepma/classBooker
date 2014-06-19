/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.classbooker.entity.StaffAdmin;
import org.classbooker.entity.User;
import org.classbooker.presentation.view.ConfirmationForm;
import org.classbooker.presentation.view.ExceptionInfo;
import org.classbooker.presentation.view.MassiveUserInsertionForm;
import org.classbooker.service.AuthenticationMgr;
import static org.classbooker.service.AuthenticationMgr.getLoggedUser;
import org.classbooker.service.StaffMgrService;
import org.classbooker.service.exception.InexistentFileException;
import org.classbooker.service.exception.ServiceException;
import org.classbooker.service.exception.UnexpectedFormatFileException;
import org.classbooker.util.UtilAppender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.Logger;
import org.classbooker.service.ReservationMgrServiceImpl;
import static org.junit.Assert.assertEquals;
/**
 *
 * @author aba8
 */
public class SubmitMassiveUserInsertionAction implements ActionListener{
    MassiveUserInsertionForm massiveUserInsertionForm;
    StaffMgrService services;
    AuthenticationMgr authentication;
    
   public SubmitMassiveUserInsertionAction(MassiveUserInsertionForm form){
       massiveUserInsertionForm = form;
       
   } 
    
   public void setServices(StaffMgrService services){
       
       this.services = services;
   }
   
   public void actionPerformed(ActionEvent e){
       User loggedUser = getLoggedUser();
      
   
       if(loggedUser == null){
            JOptionPane.showMessageDialog(null, 
                        "Please, You have to be logged as Staff Administrator to use this service", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
          massiveUserInsertionForm.parent.revalidate();  
       }
       else if(loggedUser instanceof StaffAdmin){
           if(checkEmptyFileName()){
                massiveUserInsertionForm.parent.revalidate();  
           }
           else{
               try {
                   addMassiveUserProcess();
               } catch (ServiceException ex) {
                   Logger.getLogger(SubmitMassiveUserInsertionAction.class);
               }
           } 
       }
       else{
           JOptionPane.showMessageDialog(null, 
                        "Logged User is not Staff Administrator", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
          massiveUserInsertionForm.parent.revalidate();  
       }
   }

    private void addMassiveUserProcess() throws ServiceException {
       String fileName = massiveUserInsertionForm.fileName.getText();
       
        try{
          UtilAppender appender = new UtilAppender();
          Logger logger = Logger.getRootLogger();
          logger.addAppender(appender);
          
          services.addMassiveUser(fileName);
          
          String message = checkLoggerMessageProcess(logger,appender);
          System.out.println(message);
          if(message.equals("The file contains Repeated Users ")){
              repeatedUserWarning();
          }
              System.out.println("ok,inserted");
              confirmitionAddMassiveUser();
          
        }
        catch(UnexpectedFormatFileException ex){
            JOptionPane.showMessageDialog(null, 
                        "Please,The format of file should be XML or CSV", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
           
        }
        catch(InexistentFileException ex){
            JOptionPane.showMessageDialog(null, 
                        "Can not find the File with this  name,introduce again", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);  
        }
        finally{
            massiveUserInsertionForm.parent.revalidate();                        
        }
    }

    private boolean checkEmptyFileName() {
          if("".equals(massiveUserInsertionForm.fileName.getText())){
           JOptionPane.showMessageDialog(null, 
                        "Please, The field of filename can not be empty, introduce again!", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
           return true;
        }
          return false;
    }

    private String checkLoggerMessageProcess(Logger logger,UtilAppender appender) {
         try{
            Logger.getLogger(ReservationMgrServiceImpl.class);
        }
        finally{
            logger.removeAppender(appender);
        }
        List<LoggingEvent> log = appender.getLog();
        LoggingEvent firstLogEntrey = log.get(0);
        String message = firstLogEntrey.getMessage().toString();
        return message;
    }

    private void repeatedUserWarning() {
       JOptionPane.showMessageDialog(null, 
                        "This file contains repeated User,but the process will continue", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
          
    }

    private void confirmitionAddMassiveUser() {
          ConfirmationForm confirm = new ConfirmationForm("Massive Users successfully added ");
          massiveUserInsertionForm.parent.getContentPane().removeAll();
          massiveUserInsertionForm.parent.getContentPane().add(confirm,BorderLayout.CENTER);
          massiveUserInsertionForm.parent.revalidate();    
    }
}
