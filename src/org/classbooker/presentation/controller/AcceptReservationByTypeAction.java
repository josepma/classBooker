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
import org.classbooker.entity.Reservation;
import org.classbooker.presentation.view.AcceptReservationByTypeForm;
import org.classbooker.presentation.view.ReservationByTypeInsertionForm;

/**
 *
 * @author Hijazo
 */
public class AcceptReservationByTypeAction implements ActionListener{
    JFrame parent;
    Reservation reservation;
    public AcceptReservationByTypeAction(JFrame frame, Reservation res) {
        parent = frame;
        this.reservation = res;
    }
    public void actionPerformed(ActionEvent e) {
        
        parent.getContentPane().removeAll();
         
//        ReservationByTypeInsertionForm form = new AcceptReservationByTypeForm(parent,reservation);
//        parent.getContentPane().add(form,BorderLayout.CENTER);
     
        parent.revalidate();                        
    }
}
