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
import org.classbooker.presentation.view.LogInForm;
import org.classbooker.service.AuthenticationMgr;

/**
 *
 * @author Alejandro
 */
public class MakeLogInAction implements ActionListener{

    JFrame parent;
    AuthenticationMgr authMgr;
    
    public MakeLogInAction(JFrame frame, AuthenticationMgr authMgr) {
        this.parent = frame;
        this.authMgr = authMgr;
        
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        parent.getContentPane().removeAll();
         
        LogInForm form = new LogInForm(parent,authMgr);
        parent.getContentPane().add(form,BorderLayout.CENTER);
     
        parent.revalidate();      
    }
    
}
