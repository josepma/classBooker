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
import org.classbooker.presentation.view.MassiveUserInsertionForm;
import org.classbooker.service.ReservationMgrService;
import org.classbooker.service.SpaceMgrService;

/**
 *
 * @author hellfish
 */
public class MakeReservationBySpaceAction implements ActionListener{
    
    private JFrame parent;
    private ReservationMgrService reservationServ;
    private SpaceMgrService spaceServ;

    public MakeReservationBySpaceAction(ClassBookerFrame cbf, ReservationMgrService rms, SpaceMgrService spaceService) {
        this.parent = cbf;
        this.reservationServ = rms;
        this.spaceServ = spaceService;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        parent.getContentPane().removeAll();
         
        MakeReservationBySpaceForm form = new MakeReservationBySpaceForm(parent,reservationServ, spaceServ);
        parent.getContentPane().add(form,BorderLayout.CENTER);
     
        parent.revalidate();
    }
    
}
