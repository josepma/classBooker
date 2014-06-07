/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.entity.Reservation;
import org.classbooker.presentation.view.AcceptReservationByTypeForm;
import org.classbooker.presentation.view.BuildingInsertionForm;
import org.classbooker.presentation.view.ConfirmationForm;
import org.classbooker.presentation.view.ExceptionInfo;
import org.classbooker.service.ReservationMgrService;
import org.classbooker.service.SpaceMgrService;

/**
 *
 * @author xurata
 */
public class SubmitAcceptReservationByTypeAction implements ActionListener {
    Reservation reservation;
    JFrame parent;
    ReservationMgrService services;
    
   public SubmitAcceptReservationByTypeAction(JFrame frame, Reservation res){
       this.reservation = res;
       this.parent= frame;
   } 

    public void setServices(ReservationMgrService services){
       
       this.services = services;
   }
    
   
   public void actionPerformed(ActionEvent e){
 
       parent.getContentPane().removeAll();
      
        try{
          services.acceptReservation(reservation); 
          ConfirmationForm confirm = new ConfirmationForm("Reservation added");
          parent.getContentPane().add(confirm,BorderLayout.CENTER);
     
        }
        catch(DAOException exc){
           exc.printStackTrace(); 
           ExceptionInfo exception = new ExceptionInfo("Existing reservation");
           parent.getContentPane().add(exception,BorderLayout.CENTER);
        }
        finally{
          
          parent.revalidate();                        
          parent.getContentPane().repaint();

        }
   } 
}
