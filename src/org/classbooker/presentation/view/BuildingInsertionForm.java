package org.classbooker.presentation.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.classbooker.service.*;
import org.classbooker.presentation.controller.*;
import java.awt.Dimension;

/**
 * Contains the form to add a team in a competition.
 * @author David Hernández
 * @author Anton Urrea
 */
public class BuildingInsertionForm extends JPanel {

    public JTextField buildingName;
    public JButton but;
    public JFrame parent;
    private SpaceMgrService services;

    public BuildingInsertionForm(JFrame frame, SpaceMgrService services) {
        

        this.services = services;
        this.parent = frame;

        JLabel labelName = new JLabel();
        labelName.setText(" Building name :");
        buildingName = new JTextField();


        buildingName.setPreferredSize( new Dimension( 200, 24 ) );
        
        but = new JButton();
        but.setText("Submit");

        this.add(labelName);
        this.add(buildingName);
        this.add(but);

        SubmitBuildingInsertionAction action = new SubmitBuildingInsertionAction(this);
        action.setServices(services);

        but.addActionListener(action);
    }


}
