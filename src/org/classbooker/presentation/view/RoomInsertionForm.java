/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.classbooker.presentation.controller.SubmitRoomInsertionAction;
import org.classbooker.service.SpaceMgrService;

/**
 *
 * @author genis,saida
 */
public class RoomInsertionForm extends JPanel {
    
    
    public JButton but;
    public JFrame parent;
    private SpaceMgrService services;
    public JTextField buildingName;
    public JTextField roomNumber;
    public JTextField roomCapacity;
    public JTextField roomType;

    public RoomInsertionForm(JFrame frame, SpaceMgrService services) {
        this.services = services;
        this.parent = frame;

        JLabel labelName = new JLabel();
        JLabel labelName2 = new JLabel();
        JLabel labelName3 = new JLabel();
        JLabel labelName4 = new JLabel();
        labelName.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" ROOM NUMBER :"));
        labelName2.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" BUILDING NAME :"));
        labelName3.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" ROOM CAPACITY :"));
         labelName4.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" ROOM TYPE :"));
        roomNumber = new JTextField();
        buildingName = new JTextField();
        roomCapacity = new JTextField();
        roomType = new JTextField();
        roomNumber.setPreferredSize( new Dimension( 200, 24 ) );
        buildingName.setPreferredSize( new Dimension( 200, 24 ) );
        roomCapacity.setPreferredSize( new Dimension( 200, 24 ) );
        roomType.setPreferredSize( new Dimension( 200, 24 ) );
        
        but = new JButton();
        but.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("SUBMIT"));

        JPanel lines = new JPanel(); 
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));
        JPanel line1 = new JPanel(); 
        line1.add(labelName);  
        line1.add(roomNumber);
        lines.add(line1);
        JPanel line2 = new JPanel(); 
        line2.add(labelName2);  
        line2.add(buildingName);
        lines.add(line2);
        JPanel line3 = new JPanel(); 
        line3.add(labelName3);  
        line3.add(roomCapacity);
        lines.add(line3);
        JPanel line4 = new JPanel(); 
        line4.add(labelName4);
        line4.add(roomType);
        lines.add(line4);
        JPanel line5 = new JPanel();
        line5.add(but);
        lines.add(line5);
        
        this.add(lines);

        SubmitRoomInsertionAction action = new SubmitRoomInsertionAction(this);
        action.setServices(services);

        but.addActionListener(action);
    
    }
    
}
