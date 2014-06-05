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
import org.classbooker.service.ReservationMgrService;
import java.awt.Dimension;
import org.classbooker.presentation.controller.SubmitMakeReservationByTypeAction;

/**
 *
 * @author Mauro Churata
 */
public class ReservationByTypeInsertionForm extends JPanel {
    
    public JTextField nif; 
    public JTextField type;
    public JTextField buildingName;
    public JTextField capacity;
    public JTextField dateIni;
    public JButton but;
    public JFrame parent;
    private ReservationMgrService services;
    
    public ReservationByTypeInsertionForm(JFrame frame, ReservationMgrService services) {
        

        this.services = services;
        this.parent = frame;

        JLabel labelNif = new JLabel();
        labelNif.setText(" User nif :");
        nif = new JTextField();
        JLabel labelType = new JLabel();
        labelType.setText(" Type Room :");
        type = new JTextField();
        JLabel labelName = new JLabel();
        labelName.setText(" Building name :");
        buildingName = new JTextField();
        JLabel labelCap = new JLabel();
        labelCap.setText(" Capacity :");
        capacity = new JTextField();
        JLabel labelDate = new JLabel();
        labelDate.setText(" Date Reservation :");
        dateIni = new JTextField();
        
        nif.setPreferredSize( new Dimension( 200, 24 ) );
        type.setPreferredSize( new Dimension( 200, 24 ) );
        buildingName.setPreferredSize( new Dimension( 200, 24 ) );
        capacity.setPreferredSize( new Dimension( 200, 24 ) );
        dateIni.setPreferredSize( new Dimension( 200, 24 ) );
        
        but = new JButton();
        but.setText("Submit");
        
        JPanel lines = new JPanel();
        JPanel line1 = new JPanel(); 
        line1.add(labelNif);
        line1.add(nif);
        lines.add(line1);
        JPanel line2 = new JPanel();
        line2.add(labelName);
        line2.add(buildingName);
        lines.add(line2);
        JPanel line3 = new JPanel();
        line3.add(labelType);
        line3.add(type);
        lines.add(line3);
        JPanel line4 = new JPanel();  
        line4.add(labelCap);
        line4.add(capacity);
        lines.add(line4);
        JPanel line5 = new JPanel();
        line5.add(labelDate);
        line5.add(dateIni);
        lines.add(line5);
        JPanel line6 = new JPanel();
        line6.add(but);
        lines.add(line6);
        
        this.add(lines);
        
        SubmitMakeReservationByTypeAction action = new SubmitMakeReservationByTypeAction(this);
        action.setServices(services);

        but.addActionListener(action);
    }
}
