/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.classbooker.dao.exception.AlreadyExistingUserException;
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
       
    //   userInsertionForm.parent.getContentPane().removeAll();
       if(loggedUser == null){
           mustLoggedAsStaffAdminMessage();
       }
       else if(loggedUser instanceof StaffAdmin){
           if(checkEmptyFields()){
              userInsertionForm.parent.revalidate();  
            }
           else{
              addUserProcess();
           }
       }
       else{
           loggedUserIsNotStaffAdmin();
       }
   }   

   
   
    private void addUserProcess() {
          String nif = userInsertionForm.nif.getText();
          String userName = userInsertionForm.userName.getText();
          String email = userInsertionForm.email.getText();
          User user = new ProfessorPas(nif,email,userName,"");
          
        try{
          
          services.addUser(user);
          ConfirmationForm confirm = new ConfirmationForm("User successfully added");
          userInsertionForm.parent.getContentPane().removeAll();
          userInsertionForm.parent.getContentPane().add(confirm,BorderLayout.CENTER);
              
        }
        catch(AlreadyExistingUserException exc){ //AlreadyExistingBuildingException exc){
           JOptionPane.showMessageDialog(null, 
                        "Already Existing this user", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);  
         }
        finally{
            userInsertionForm.parent.revalidate();                        
        }
    }

    private boolean checkEmptyFields() {
        return checkNifField() || checkUserNameField()  || checkEmailField();
        
    }

    private void mustLoggedAsStaffAdminMessage() {
        JOptionPane.showMessageDialog(null, 
                        "Please, You have to be logged as Staff Administrator to use this service", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
          userInsertionForm.parent.revalidate();  
    }

    private void loggedUserIsNotStaffAdmin() {
        JOptionPane.showMessageDialog(null, 
                        "Logged User is not Staff Administrator", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
          userInsertionForm.parent.revalidate();   
    }

    private boolean checkNifField() {
        if("".equals(userInsertionForm.nif.getText())){
           JOptionPane.showMessageDialog(null, 
                        "Please, The field Nif can not be empty, introduce again!", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
           return true;
        }
        return false;
    }

    private boolean checkUserNameField() {
        if("".equals(userInsertionForm.userName.getText())){
           JOptionPane.showMessageDialog(null, 
                        "Please, The field User can not be empty, introduce again!", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
           return true;
        }
        return false;
    }

    private boolean checkEmailField() {
        if("".equals(userInsertionForm.email.getText())){
           JOptionPane.showMessageDialog(null, 
                        "Please, The field Email can not be empty, introduce again!", 
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
           return true;
        }
        return false;
    }
}
