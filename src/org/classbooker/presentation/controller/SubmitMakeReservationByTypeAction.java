/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.classbooker.presentation.view.ExceptionInfo;
import org.classbooker.presentation.view.ReservationByTypeInsertionForm;
import org.classbooker.service.ReservationMgrService;

/**
 *
 * @author Mauro Churata
 */
public class SubmitMakeReservationByTypeAction implements ActionListener{
    ReservationByTypeInsertionForm reservationByTypeInsertionForm;
    ReservationMgrService services;
    public SubmitMakeReservationByTypeAction(ReservationByTypeInsertionForm form){
       reservationByTypeInsertionForm = form;
   }
    public void setServices(ReservationMgrService services){
       this.services = services;
   }
    public void actionPerformed(ActionEvent e){
   
       
       String nif = reservationByTypeInsertionForm.nif.getText();
       String type = reservationByTypeInsertionForm.nif.getText(); 
       String buildingName = reservationByTypeInsertionForm.nif.getText();
       int capacity = Integer.parseInt(reservationByTypeInsertionForm.nif.getText());
       DateTime dateIni = reservationByTypeInsertionForm.nif.getText();
 
       reservationByTypeInsertionForm.parent.getContentPane().removeAll();
      
        try{
//          Reservation makeReservationByType(String nif, String type, String buildingName, int capacity, DateTime date)
          services.makeReservationByType(nif, type, buildingName, capacity, dateIni); 
         // ConfirmationForm confirm = new ConfirmationForm();
         // buildingInsertionForm.parent.getContentPane().add(confirm,BorderLayout.CENTER);
     
        }
        catch(Exception exc){ //AlreadyExistingBuildingException exc){
           exc.printStackTrace(); 
           ExceptionInfo exception = new ExceptionInfo("Existing Reservation");
           reservationByTypeInsertionForm.parent.getContentPane().add(exception,BorderLayout.CENTER);
        }
        finally{
           reservationByTypeInsertionForm.parent.revalidate();                        
        }
   }
}
