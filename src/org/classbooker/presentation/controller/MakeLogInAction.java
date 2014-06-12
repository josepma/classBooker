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
import org.classbooker.presentation.view.ExceptionInfo;
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
        if(AuthenticationMgr.getLoggedUser()!=null){
            ExceptionInfo error = new ExceptionInfo("You are already logged in.");
            parent.getContentPane().add(error,BorderLayout.CENTER);
        }
        else if(AuthenticationMgr.getTries()>0){
            LogInForm form = new LogInForm(parent,authMgr);
            parent.getContentPane().add(form,BorderLayout.CENTER);
        }
        else{
            ExceptionInfo error = new ExceptionInfo("You tried to log in wrongly soo many times, this incident will be reported.");
            parent.getContentPane().add(error,BorderLayout.CENTER);
        }
     
        parent.revalidate();      
    }
    
}
