/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import org.classbooker.entity.Reservation;
import org.classbooker.presentation.view.DisplayReservations;
import org.classbooker.presentation.view.FindUserReservationsForm;
import org.classbooker.service.ReservationMgrService;

/**
 *
 * @author Albert
 */
public class GetUserReservationsAction implements ActionListener {

    FindUserReservationsForm form;
    ReservationMgrService services;

    public GetUserReservationsAction(FindUserReservationsForm form) {
        this.form = form;
    }

    public void setServices(ReservationMgrService services) {
        this.services = services;
    }

    
    public void actionPerformed(ActionEvent e) {
        System.out.println("EEE");
        String nif = form.nif.getText();
        
        form.parent.getContentPane().removeAll();
        
        System.out.println("NIF::"+nif);
        if(services == null) System.out.println("NUUUULLL");
        else System.out.println("OKKK");
        
        
        
        List<Reservation> userReservations = services.getReservationsByNif(nif);
        DisplayReservations dr = new DisplayReservations(userReservations);
        form.parent.getContentPane().add(dr, BorderLayout.CENTER);
        form.parent.revalidate();
        form.parent.getContentPane().repaint();
    }
}
