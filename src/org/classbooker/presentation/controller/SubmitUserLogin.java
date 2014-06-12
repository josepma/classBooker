/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.classbooker.dao.exception.InexistentUserException;
import org.classbooker.presentation.view.ConfirmationForm;
import org.classbooker.presentation.view.ExceptionInfo;
import org.classbooker.presentation.view.LogInForm;
import org.classbooker.service.AuthenticationMgr;

import org.classbooker.util.Encoder;

/**
 *
 * @author Antares
 */
public class SubmitUserLogin implements ActionListener{

    LogInForm form;
    AuthenticationMgr mgr;
    
    public SubmitUserLogin(LogInForm f){
        this.form = f;
    }
    
    public void setServices(AuthenticationMgr m){
        this.mgr = m;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String nif = form.nif.getText();
        String password = form.password.getText();
        form.parent.getContentPane().removeAll();
        try {
            if(AuthenticationMgr.login(nif, password)){
                ConfirmationForm confirm = new ConfirmationForm("User successfully logged in.");
                form.parent.getContentPane().add(confirm,BorderLayout.CENTER);
            }else{
                ExceptionInfo error = new ExceptionInfo("The password is not correct.");
                form.parent.getContentPane().add(error,BorderLayout.CENTER);
            }
        } catch (InexistentUserException ex) {
            ExceptionInfo error = new ExceptionInfo("The username is not correct.");
            form.parent.getContentPane().add(error,BorderLayout.CENTER);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SubmitUserLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            form.parent.revalidate();                        
        }
    }
    
}
