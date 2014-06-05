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
import org.classbooker.presentation.controller.GetUserReservationsAction;
import org.classbooker.presentation.controller.SubmitRoomInsertionAction;
import org.classbooker.service.ReservationMgrService;
import org.classbooker.service.SpaceMgrService;

/**
 *
 * @author Albert
 */
public class FindUserReservationsForm extends JPanel {
      
    
    public JButton but;
    public JFrame parent;
    private ReservationMgrService services;
    public JTextField nif;

    public FindUserReservationsForm(JFrame frame, ReservationMgrService services) {
        this.services = services;
        this.parent = frame;

        JLabel labelNIF = new JLabel();
        labelNIF.setText(" User NIF :");
        nif = new JTextField();
        nif.setPreferredSize( new Dimension( 200, 24 ) );
        
        but = new JButton();
        but.setText("Submit");

        JPanel lines = new JPanel(); 
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));
        JPanel line1 = new JPanel(); 
        line1.add(labelNIF);  
        line1.add(nif);
        lines.add(line1);
        JPanel line5 = new JPanel();
        line5.add(but);
        lines.add(line5);
        
        this.add(lines);

        GetUserReservationsAction action = new GetUserReservationsAction(this);
        action.setServices(services);

        but.addActionListener(action);
    }
}
