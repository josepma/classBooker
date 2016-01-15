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
    public JTextField nif, type, buildingName, capacity, dateIni, dateEnd, roomNb;

    public FindUserReservationsForm(JFrame frame, ReservationMgrService services) {
        
        this.services = services;
        this.parent = frame;

        JLabel labelNif = new JLabel();
        labelNif.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" USER NIF (MANDATORY) :"));
        nif = new JTextField();
        JLabel labelType = new JLabel();
        labelType.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" TYPE ROOM :"));
        type = new JTextField();
        JLabel labelName = new JLabel();
        labelName.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" BUILDING NAME :"));
        buildingName = new JTextField();
        JLabel labelRoomNb = new JLabel();
        labelRoomNb.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" ROOM NUMBER :"));
        roomNb = new JTextField();
        JLabel labelCap = new JLabel();
        labelCap.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" CAPACITY :"));
        capacity = new JTextField();
        JLabel labelDateIni = new JLabel();
        labelDateIni.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" INITIAL DATE (I.E. DD-MM-YYYY HH:MM) :"));
        dateIni = new JTextField();
        
        nif.setPreferredSize( new Dimension( 200, 24 ) );
        type.setPreferredSize( new Dimension( 200, 24 ) );
        buildingName.setPreferredSize( new Dimension( 200, 24 ) );
        roomNb.setPreferredSize( new Dimension( 200, 24 ) );
        capacity.setPreferredSize( new Dimension( 200, 24 ) );
        dateIni.setPreferredSize( new Dimension( 200, 24 ) );
        
        but = new JButton();
        but.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("FIND"));
        
        JPanel lines = new JPanel();
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));
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
        line5.add(labelDateIni);
        line5.add(dateIni);
        lines.add(line5);        
        JPanel line8 = new JPanel();
        line8.add(labelRoomNb);
        line8.add(roomNb);
        lines.add(line8);
        JPanel line6 = new JPanel();
        line6.add(but);
        lines.add(line6);
        
        this.add(lines);

        GetUserReservationsAction action = new GetUserReservationsAction(this);
        action.setServices(services);
        but.addActionListener(action);
    }
}
