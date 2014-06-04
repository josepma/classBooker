/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.classbooker.service.*;
import org.classbooker.presentation.controller.*;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 * @author aba8
 */
public class UserInsertionForm extends JPanel {

    public JTextField nif;
    public JTextField userName;
    public JTextField email;
    public JButton but;
    public JFrame parent;
    private StaffMgrService services;

    public UserInsertionForm(JFrame frame, StaffMgrService services) {
        

        this.services = services;
        this.parent = frame;

        JLabel labelNif = new JLabel();
        JLabel labelName = new JLabel();
        JLabel labelEmail = new JLabel();
        labelNif.setText(" NIF :");
        labelName.setText(" User name :");
        labelEmail.setText(" Email :");
        nif = new JTextField();
        userName = new JTextField();
        email = new JTextField();


        nif.setPreferredSize( new Dimension( 200, 24 ) );
        userName.setPreferredSize( new Dimension( 200, 24 ) );
        email.setPreferredSize( new Dimension( 200, 24 ) );
        
        but = new JButton();
        but.setText("Submit");
        
        
        JPanel lines = new JPanel(); 
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));
        JPanel line1 = new JPanel(); 
        line1.add(labelNif);  
        line1.add(nif);
        lines.add(line1);
        JPanel line2 = new JPanel(); 
        line2.add(labelName);  
        line2.add(userName);
        lines.add(line2);
        JPanel line3 = new JPanel(); 
        line3.add(labelEmail);  
        line3.add(email);
        lines.add(line3);
        JPanel line4 = new JPanel();
        line4.add(but);
        lines.add(line4);
        
        this.add(lines);

        SubmitUserInsertionAction action = new SubmitUserInsertionAction(this);
        action.setServices(services);

        but.addActionListener(action);
    }


}
