/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.view;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.classbooker.presentation.controller.SubmitMassiveUserInsertionAction;
import org.classbooker.service.StaffMgrService;

/**
 *
 * @author aba8
 */
public class MassiveUserInsertionForm extends JPanel {
    public JTextField fileName;
    public JButton but;
    public JFrame parent;
    private StaffMgrService services;

    public MassiveUserInsertionForm(JFrame frame, StaffMgrService services) {
        

        this.services = services;
        this.parent = frame;

        JLabel labelFileName = new JLabel();
        labelFileName.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" FILE NAME :"));
        fileName = new JTextField();


        fileName.setPreferredSize( new Dimension( 200, 24 ) );
        
        but = new JButton();
        but.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("SUBMIT"));
 
        this.add(labelFileName);  
        this.add(fileName);
        this.add(but);

        SubmitMassiveUserInsertionAction action = new SubmitMassiveUserInsertionAction(this);
        action.setServices(services);

        but.addActionListener(action);
    }
}
