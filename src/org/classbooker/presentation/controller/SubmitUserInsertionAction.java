/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.classbooker.presentation.view.*;
import org.classbooker.service.*;
import org.classbooker.dao.exception.AlreadyExistingBuildingException;
import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.ReservationUser;
import org.classbooker.entity.User;

/**
 *
 * @author aba8
 */
public class SubmitUserInsertionAction implements ActionListener{
    UserInsertionForm userInsertionForm;
    StaffMgrService services;
    
   public SubmitUserInsertionAction(UserInsertionForm form){
       userInsertionForm = form;
   } 
    
   public void setServices(StaffMgrService services){
       
       this.services = services;
   }
   
   public void actionPerformed(ActionEvent e){
   
       String nif = userInsertionForm.nif.getText();
       String userName = userInsertionForm.userName.getText();
       String email = userInsertionForm.email.getText();
       User user = new ProfessorPas(nif,email,userName);
       userInsertionForm.parent.getContentPane().removeAll();
      
        try{
          services.addUser(new ProfessorPas("dhsjdd","ejkkfd@djk","Angel"));
         // ConfirmationForm confirm = new ConfirmationForm();
         // buildingInsertionForm.parent.getContentPane().add(confirm,BorderLayout.CENTER);
     
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
