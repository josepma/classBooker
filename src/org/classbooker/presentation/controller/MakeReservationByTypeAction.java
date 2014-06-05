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
import org.classbooker.presentation.view.FindUserReservationsForm;
import org.classbooker.presentation.view.ReservationByTypeInsertionForm;
import org.classbooker.service.ReservationMgrService;
import org.classbooker.service.SpaceMgrService;

/**
 *
 * @author Mauro Churata
 */
public class MakeReservationByTypeAction implements ActionListener{
    JFrame parent;
    ReservationMgrService services;
    public MakeReservationByTypeAction(JFrame frame, ReservationMgrService service) {
        parent = frame;
        this.services = service;
    }
    public void actionPerformed(ActionEvent e) {
        
        parent.getContentPane().removeAll();
         
        ReservationByTypeInsertionForm form = new ReservationByTypeInsertionForm(parent,services);
        parent.getContentPane().add(form,BorderLayout.CENTER);
     
        parent.revalidate();                        
    }
    
}
