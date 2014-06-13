/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.classbooker.entity.StaffAdmin;
import org.classbooker.entity.User;
import org.classbooker.presentation.view.ConfirmationForm;
import org.classbooker.presentation.view.ExceptionInfo;
import org.classbooker.presentation.view.MassiveUserInsertionForm;
import org.classbooker.service.AuthenticationMgr;
import static org.classbooker.service.AuthenticationMgr.getLoggedUser;
import org.classbooker.service.StaffMgrService;

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
      
       massiveUserInsertionForm.parent.getContentPane().removeAll();
       if(loggedUser == null){
          ExceptionInfo exception = new ExceptionInfo("You have to be logged as Staff Administrator to use this service");
          massiveUserInsertionForm.parent.getContentPane().add(exception,BorderLayout.CENTER);
          massiveUserInsertionForm.parent.revalidate();  
       }
       else if(loggedUser instanceof StaffAdmin){
           addMassiveUserProcess();
       }
       else{
          ExceptionInfo exception = new ExceptionInfo("Logged User is not StaffAdmin");
          massiveUserInsertionForm.parent.getContentPane().add(exception,BorderLayout.CENTER); 
          massiveUserInsertionForm.parent.revalidate();  
       }
   }

    private void addMassiveUserProcess() {
       String fileName = massiveUserInsertionForm.fileName.getText();
       
        try{
          services.addMassiveUser(fileName);
          System.out.println("ok,inserted");
          
          ConfirmationForm confirm = new ConfirmationForm("Massive Users successfully added ");
          massiveUserInsertionForm.parent.getContentPane().add(confirm,BorderLayout.CENTER);
     
        }
        catch(Exception exc){ //AlreadyExistingBuildingException exc){
           exc.printStackTrace(); 
           ExceptionInfo exception = new ExceptionInfo("Add Massive User Failed");
           massiveUserInsertionForm.parent.getContentPane().add(exception,BorderLayout.CENTER);
        }
        finally{
            massiveUserInsertionForm.parent.revalidate();                        
        }
    }
}
