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
import org.classbooker.presentation.view.MassiveUserInsertionForm;
import org.classbooker.service.StaffMgrService;

/**
 *
 * @author aba8
 */
public class MassiveUserInsertionAction implements ActionListener {
    
    JFrame parent;
    StaffMgrService services;

    public MassiveUserInsertionAction(JFrame frame, StaffMgrService service) {
        parent = frame;
        this.services = service;
    }

    public void actionPerformed(ActionEvent e){
    
        parent.getContentPane().removeAll();
         
        MassiveUserInsertionForm form = new MassiveUserInsertionForm(parent,services);
        parent.getContentPane().add(form,BorderLayout.CENTER);
     
        parent.revalidate();                        
    }
}
