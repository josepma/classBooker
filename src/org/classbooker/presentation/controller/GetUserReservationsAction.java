/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.classbooker.presentation.view.FindUserReservationsForm;
import org.classbooker.service.ReservationMgrService;

/**
 *
 * @author Albert
 */
public class GetUserReservationsAction implements ActionListener{
    FindUserReservationsForm form;
    ReservationMgrService services;
    public GetUserReservationsAction(FindUserReservationsForm form){
        this.form = form;
    }
    
    public void setServices(ReservationMgrService services){       
       this.services = services;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}