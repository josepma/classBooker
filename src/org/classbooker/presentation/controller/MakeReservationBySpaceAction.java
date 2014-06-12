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
import org.classbooker.presentation.view.ClassBookerFrame;
import org.classbooker.presentation.view.MakeReservationBySpaceForm;
import org.classbooker.service.ReservationMgrService;

/**
 *
 * @author  Carles Mònico Bonell, Marc Solé Farré
 */
public class MakeReservationBySpaceAction implements ActionListener{
    
    private final JFrame parent;
    private final ReservationMgrService reservationServ;

    public MakeReservationBySpaceAction(ClassBookerFrame cbf, ReservationMgrService rms) {
        this.parent = cbf;
        this.reservationServ = rms;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        parent.getContentPane().removeAll();
         
        MakeReservationBySpaceForm form = new MakeReservationBySpaceForm(reservationServ);
        parent.getContentPane().add(form,BorderLayout.CENTER);
     
        parent.revalidate();
    }
    
}
