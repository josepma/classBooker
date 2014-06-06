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
import org.classbooker.presentation.view.UserInsertionForm;
import org.classbooker.service.ReservationMgrService;

/**
 *
 * @author Albert
 */
public class ReservationFindAction implements ActionListener {
    JFrame parent;
    ReservationMgrService services;

    public ReservationFindAction(JFrame frame, ReservationMgrService services) {
        parent = frame;
        this.services = services;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        parent.getContentPane().removeAll();
         
        FindUserReservationsForm form = new FindUserReservationsForm(parent,services);
        parent.getContentPane().add(form,BorderLayout.CENTER);
     
        parent.revalidate();                        
    }
}
