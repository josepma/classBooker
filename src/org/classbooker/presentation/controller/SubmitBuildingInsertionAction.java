package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.classbooker.presentation.view.*;
import org.classbooker.service.*;
import org.classbooker.dao.exception.*;


public class SubmitBuildingInsertionAction implements ActionListener{
    BuildingInsertionForm buildingInsertionForm;
    SpaceMgrService services;
    
   public SubmitBuildingInsertionAction(BuildingInsertionForm form){
       buildingInsertionForm = form;
   } 
    
   public void setServices(SpaceMgrService services){
       
       this.services = services;
   }
   
   public void actionPerformed(ActionEvent e){

       String buildingName = buildingInsertionForm.buildingName.getText();
 
       buildingInsertionForm.parent.getContentPane().removeAll();
      
        try{
          services.addBuilding(buildingName); 
          ConfirmationForm confirm = new ConfirmationForm("Building added");
          buildingInsertionForm.parent.getContentPane().add(confirm,BorderLayout.CENTER);
     
        }
        catch(DAOException exc){
           exc.printStackTrace(); 
           ExceptionInfo exception = new ExceptionInfo("Existing building");
           buildingInsertionForm.parent.getContentPane().add(exception,BorderLayout.CENTER);
        }
        finally{
          
          buildingInsertionForm.parent.revalidate();                        
          buildingInsertionForm.parent.getContentPane().repaint();

        }
   }   
}
