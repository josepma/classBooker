/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.UserDAOImpl;
import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.User;
import org.classbooker.presentation.view.ConfirmationForm;
import org.classbooker.presentation.view.ExceptionInfo;
import org.classbooker.presentation.view.MassiveUserInsertionForm;
import org.classbooker.service.StaffMgrService;

/**
 *
 * @author aba8
 */
public class SubmitMassiveUserInsertionAction implements ActionListener{
    MassiveUserInsertionForm massiveUserInsertionForm;
    StaffMgrService services;
    
   public SubmitMassiveUserInsertionAction(MassiveUserInsertionForm form){
       massiveUserInsertionForm = form;
   } 
    
   public void setServices(StaffMgrService services){
       
       this.services = services;
   }
   
   public void actionPerformed(ActionEvent e){
   
       String fileName = massiveUserInsertionForm.fileName.getText();
       
       massiveUserInsertionForm.parent.getContentPane().removeAll();
      //userDao not set entitymanager(classbookintegration)
       
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
