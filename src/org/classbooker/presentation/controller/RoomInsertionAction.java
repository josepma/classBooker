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
import org.classbooker.presentation.view.*;
import org.classbooker.service.*;

/**
 *
 * @author genis, saida
 */
public class RoomInsertionAction implements ActionListener {

    JFrame parent;
    SpaceMgrService services;
    
    public RoomInsertionAction(JFrame frame, SpaceMgrService spaceservice) {
        parent = frame;
        this.services = spaceservice;
    }

    @Override
    public void actionPerformed(ActionEvent e){
    
        parent.getContentPane().removeAll();
         
        RoomInsertionForm form = new RoomInsertionForm(parent,services);
        parent.getContentPane().add(form,BorderLayout.CENTER);
     
        parent.revalidate();                        
    }
    
}
