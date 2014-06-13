/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.StaffAdmin;
import org.classbooker.entity.User;
import org.classbooker.presentation.view.*;
import org.classbooker.service.*;
import static org.classbooker.service.AuthenticationMgr.getLoggedUser;

/**
 *
 * @author aba8
 */
public class SubmitUserInsertionAction implements ActionListener{
    UserInsertionForm userInsertionForm;
    StaffMgrService services;
    AuthenticationMgr authentication;
   public SubmitUserInsertionAction(UserInsertionForm form){
       userInsertionForm = form;
   } 
    
   public void setServices(StaffMgrService services){
       
       this.services = services;
   }
   
   public void actionPerformed(ActionEvent e){
       User loggedUser = getLoggedUser();
       
       userInsertionForm.parent.getContentPane().removeAll();
       if(loggedUser == null){
          ExceptionInfo exception = new ExceptionInfo("You have to be logged as Staff Administrator to use this service");
          userInsertionForm.parent.getContentPane().add(exception,BorderLayout.CENTER);
          userInsertionForm.parent.revalidate();  
       }
       else if(loggedUser instanceof StaffAdmin){
           addUserProcess();
       }
       else{
          ExceptionInfo exception = new ExceptionInfo("Logged User is not StaffAdmin");
          userInsertionForm.parent.getContentPane().add(exception,BorderLayout.CENTER); 
          userInsertionForm.parent.revalidate();  
       }
   }   

    private void addUserProcess() {
          String nif = userInsertionForm.nif.getText();
          String userName = userInsertionForm.userName.getText();
          String email = userInsertionForm.email.getText();
          User user = new ProfessorPas(nif,email,userName,"");
          
         try{
          services.addUser(user);
          System.out.println("ok,inserted");
        //  services.deleteUser(user);
          ConfirmationForm confirm = new ConfirmationForm("User successfully added");
          userInsertionForm.parent.getContentPane().add(confirm,BorderLayout.CENTER);
     
        }
        catch(Exception exc){ //AlreadyExistingBuildingException exc){
           exc.printStackTrace(); 
           ExceptionInfo exception = new ExceptionInfo("Existing User");
           userInsertionForm.parent.getContentPane().add(exception,BorderLayout.CENTER);
         }
        finally{
            userInsertionForm.parent.revalidate();                        
        }
    }
}
