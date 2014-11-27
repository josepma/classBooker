/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.view;

import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.classbooker.entity.Reservation;
import org.classbooker.presentation.controller.SubmitAcceptReservationByTypeAction;
import org.classbooker.service.ReservationMgrService;

/**
 *
 * @author Hijazo
 */
public class AcceptReservationByTypeForm extends JPanel{
    
    private Reservation reservation;
    private ReservationMgrService services;
    public JTextField type;
    public JTextField buildingName;
    public JTextField roomId;
    public JTextField capacity;
    public JTextField dateIni;
    public JButton but1, but2;
    public JFrame parent;
    
    public AcceptReservationByTypeForm(JFrame frame, Reservation reservation, ReservationMgrService services) {
        
        this.reservation = reservation;
        this.parent = frame;
        this.services = services;
        
        JLabel label = new JLabel();
        label.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" THIS IS THE RESERVATION THAT SUGGEST THE SYSTEM:"));
        JLabel labelType = new JLabel();
        labelType.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("TYPE ROOM: ")+reservation.getRoom().getClass().getSimpleName().toString());
        type = new JTextField();
        JLabel labelName = new JLabel();
        labelName.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("BUILDING: ")+reservation.getRoom().getBuilding().getBuildingName());
        buildingName = new JTextField();
        JLabel labelRoom = new JLabel();
        labelRoom.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("ROOM NUMBER: ")+Long.toString(reservation.getRoom().getRoomId()));
        roomId= new JTextField();
        JLabel labelCap = new JLabel();
        labelCap.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("CAPACITY ROOM: ")+Integer.toString(reservation.getRoom().getCapacity()));
        capacity = new JTextField();
        JLabel labelDate = new JLabel();
        String dtf = reservation.getReservationDate().toString();
        labelDate.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("DATE: ")+dtf);
        dateIni = new JTextField();
        
        //type.setPreferredSize( new Dimension( 200, 24 ) );
        //buildingName.setPreferredSize( new Dimension( 200, 24 ) );
        //capacity.setPreferredSize( new Dimension( 200, 24 ) );
        //dateIni.setPreferredSize( new Dimension( 200, 24 ) );
        
        but1 = new JButton();
        but1.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("DO IT!"));
        //but2 = new JButton();
        //but2.setText("No");
        
        JPanel lines = new JPanel();
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));
        JPanel line1 = new JPanel();
        line1.add(label);
        lines.add(line1);
        JPanel line2 = new JPanel();
        line2.add(labelType);
        line2.add(type);
        lines.add(line2);
        JPanel line3 = new JPanel();
        line3.add(labelName);
        line3.add(buildingName);
        lines.add(line3);
        JPanel line4 = new JPanel();
        line4.add(labelRoom);
        line4.add(roomId);
        lines.add(line4);
        JPanel line5 = new JPanel();
        line5.add(labelCap);
        line5.add(capacity);
        lines.add(line5);
        JPanel line6 = new JPanel();
        line6.add(labelDate);
        line6.add(dateIni);
        lines.add(line6);
        JPanel line7 = new JPanel();
        line7.add(but1);
        lines.add(line7);
        //JPanel line7 = new JPanel();
        //line7.add(but2);
        //lines.add(line7);
        
        this.add(lines);
        
        SubmitAcceptReservationByTypeAction action = new SubmitAcceptReservationByTypeAction(parent,reservation);
        action.setServices(services);
        but1.addActionListener(action);
     }

}
