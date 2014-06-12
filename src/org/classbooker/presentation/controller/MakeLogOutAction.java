/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.classbooker.presentation.view.ConfirmationForm;
import org.classbooker.presentation.view.ExceptionInfo;
import org.classbooker.service.AuthenticationMgr;

/**
 *
 * @author Alejandro
 */
public class MakeLogOutAction implements ActionListener{

    JFrame frame;
    AuthenticationMgr authMgr;
    
    public MakeLogOutAction(JFrame frame, AuthenticationMgr authMgr) {
        this.frame = frame;
        this.authMgr = authMgr;
        
        
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.getContentPane().removeAll();
         
        if(AuthenticationMgr.logout()){
            ConfirmationForm confirm = new ConfirmationForm("User successfully logged out.");
            frame.getContentPane().add(confirm,BorderLayout.CENTER);
        }
        else{
            ExceptionInfo error = new ExceptionInfo("You were not logged in.");
            frame.getContentPane().add(error,BorderLayout.CENTER);
        }
     
        frame.revalidate();      
    }
    
}
