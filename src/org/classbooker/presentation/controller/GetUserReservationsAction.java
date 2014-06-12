/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.entity.Reservation;
import org.classbooker.presentation.view.DisplayReservations;
import org.classbooker.presentation.view.FindUserReservationsForm;
import org.classbooker.service.ReservationMgrService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Albert
 */
public class GetUserReservationsAction implements ActionListener {

    FindUserReservationsForm form;
    ReservationMgrService services;
    String nif, buildingName, roomNb, type;
    DateTime dateIni, dateEnd;
    int capacity;

    public GetUserReservationsAction(FindUserReservationsForm form) {
        this.form = form;
    }

    public void setServices(ReservationMgrService services) {
        this.services = services;
    }

    public void actionPerformed(ActionEvent e) {

        form.parent.getContentPane().removeAll();

        initializeParametersFromForm();

        List<Reservation> userReservations;
        try {
            userReservations = services.getFilteredReservation(nif, dateIni, dateEnd, buildingName, roomNb, capacity, type);
        } catch (DAOException ex) {
            userReservations = new ArrayList();
            Logger.getLogger(GetUserReservationsAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(userReservations.size());
        DisplayReservations dr = new DisplayReservations(userReservations);
        form.parent.getContentPane().add(dr, BorderLayout.CENTER);
        form.parent.revalidate();
        form.parent.getContentPane().repaint();
    }

    private void initializeParametersFromForm() {
        nif = form.nif.getText();
        capacity = 0;
        if (!form.capacity.getText().isEmpty()) {
            capacity = Integer.parseInt(form.capacity.getText());
        }
        buildingName = form.buildingName.getText();
        if (form.buildingName.getText().isEmpty()) {
            buildingName = null;
        }
        roomNb = form.roomNb.getText();
        if (form.roomNb.getText().isEmpty()) {
            roomNb = null;
        }
        dateIni = null;
        if (!form.dateIni.getText().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
            dateIni = formatter.parseDateTime(form.dateIni.getText());
        }
        dateEnd = null;
        if (!form.dateEnd.getText().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
            dateEnd = formatter.parseDateTime(form.dateEnd.getText());
        }
        type = form.type.getText();
        if (form.type.getText().isEmpty()) {
            type = null;
        }

    }
}
