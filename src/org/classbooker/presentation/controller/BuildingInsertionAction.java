package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.classbooker.service.*;
import org.classbooker.presentation.view.*;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class BuildingInsertionAction implements ActionListener {

    JFrame parent;
    SpaceMgrService services;

    public BuildingInsertionAction(JFrame frame, SpaceMgrService spaceservice) {
        parent = frame;
        this.services = spaceservice;
    }

    @Override
    public void actionPerformed(ActionEvent e){
    
        parent.getContentPane().removeAll();
         
        BuildingInsertionForm form = new BuildingInsertionForm(parent,services);
        parent.getContentPane().add(form,BorderLayout.CENTER);
     
        parent.revalidate();                        
    }
}
