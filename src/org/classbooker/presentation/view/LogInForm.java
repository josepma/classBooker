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
import org.classbooker.presentation.controller.MakeLogInAction;
import org.classbooker.presentation.controller.SubmitUserLogin;
import org.classbooker.service.AuthenticationMgr;

/**
 *
 * @author Alejandro
 */
public class LogInForm extends JPanel{
    
    public JButton but;
    public JFrame parent;
    private AuthenticationMgr authMgr;
    public JTextField nif;
    public JTextField password;

    public LogInForm(JFrame parent, AuthenticationMgr authMgr) {
        
        
        this.authMgr = authMgr;
        this.parent = parent;

        JLabel labelName = new JLabel();
        JLabel labelName2 = new JLabel();
        labelName.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("NIF:"));
        labelName2.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("PASSWORD:"));
        
        nif = new JTextField();
        password = new JTextField();
        
        nif.setPreferredSize(new Dimension(200,24));
        password.setPreferredSize(new Dimension(200,24));
        
        but = new JButton();
        but.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("SUBMIT"));        
        

        JPanel lines = new JPanel(); 
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));
        JPanel line1 = new JPanel(); 
        line1.add(labelName);  
        line1.add(nif);
        lines.add(line1);
        JPanel line2 = new JPanel(); 
        line2.add(labelName2);  
        line2.add(password);
        lines.add(line2);
        JPanel line3 = new JPanel();
        line3.add(but);
        lines.add(line3);
        
        this.add(lines);

        SubmitUserLogin action = new SubmitUserLogin(this);  
        action.setServices(authMgr);
        
        but.addActionListener(action);
        
    }
    
    
    
}
