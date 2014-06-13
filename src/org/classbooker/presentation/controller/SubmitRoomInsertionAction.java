/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.classbooker.dao.exception.AlreadyExistingBuildingException;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.presentation.view.ConfirmationForm;
import org.classbooker.presentation.view.ExceptionInfo;
import org.classbooker.presentation.view.RoomInsertionForm;
import org.classbooker.service.SpaceMgrService;

/**
 *
 * @author usuari
 */
public class SubmitRoomInsertionAction implements ActionListener {
    RoomInsertionForm roomInsertionForm;
    SpaceMgrService services;
    
    public SubmitRoomInsertionAction(RoomInsertionForm form){
       roomInsertionForm = form;
    } 
    
    public void setServices(SpaceMgrService services){
       
       this.services = services;
   }
   
   public void actionPerformed(ActionEvent e){
   
       String buildingName = roomInsertionForm.buildingName.getText();
       String roomNumber = roomInsertionForm.roomNumber.getText();
       int roomCapacity = Integer.parseInt(roomInsertionForm.roomCapacity.getText());
       String roomType = roomInsertionForm.roomType.getText();
 
       roomInsertionForm.parent.getContentPane().removeAll();
      
        try{
          services.addRoom(roomNumber, buildingName, roomCapacity, roomType);
         
          ConfirmationForm confirm = new ConfirmationForm
                ("Room: Number" + roomNumber +" Building Name "+buildingName+" added correctly");
          roomInsertionForm.parent.getContentPane().add(confirm,BorderLayout.CENTER);
     
        }
        catch(DAOException exc){ 
           exc.printStackTrace(); 
           ExceptionInfo exception = new ExceptionInfo(exc.getMessage());
           roomInsertionForm.parent.getContentPane().add(exception,BorderLayout.CENTER);
        }
        finally{
          roomInsertionForm.parent.revalidate();                        
        }
   }   
}
