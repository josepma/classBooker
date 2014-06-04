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
import org.classbooker.service.*;
import org.classbooker.presentation.view.*;

/**
 *
 * @author aba8
 */
public class UserInsertionAction implements ActionListener {

    JFrame parent;
    StaffMgrService services;

    public UserInsertionAction(JFrame frame, StaffMgrService service) {
        parent = frame;
        this.services = service;
    }

    public void actionPerformed(ActionEvent e){
    
        parent.getContentPane().removeAll();
         
        UserInsertionForm form = new UserInsertionForm(parent,services);
        parent.getContentPane().add(form,BorderLayout.CENTER);
     
        parent.revalidate();                        
    }

}
