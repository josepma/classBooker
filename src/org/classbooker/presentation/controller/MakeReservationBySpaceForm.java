/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.controller;

import com.toedter.calendar.JCalendar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.service.ReservationMgrService;
import org.classbooker.service.SpaceMgrService;
import org.joda.time.DateTime;


/**
 *
 * @author hellfish
 */
class MakeReservationBySpaceForm extends JPanel {
    
    private JFrame parent;
    private ReservationMgrService reservationServ;
    private SpaceMgrService spaceServ;
    
    private JList typeRooms, hoursList;
    private JScrollPane scrollPanelRooms, scrollPanelhours;
    public JTextField buildingName;
    JCalendar calendar;
    
    private final String[] type = { "MetingRoom","LaboratoryRoom", "ClassRoom" };
    private final Integer[] hours = { 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};

    public MakeReservationBySpaceForm(JFrame parent, ReservationMgrService reservationServ, SpaceMgrService spaceServ) {

        this.parent = parent;
        this.reservationServ = reservationServ;
        this.spaceServ = spaceServ;
        
        JLabel labelType = new JLabel();
        labelType.setText(" Type Room :");
        typeRooms = new JList(type);
        typeRooms.setSelectionMode(
        ListSelectionModel.SINGLE_SELECTION);
        typeRooms.setVisibleRowCount(type.length);
        scrollPanelRooms = new JScrollPane(typeRooms);
        
        JLabel labelHours = new JLabel();
        labelHours.setText(" Hour :");
        hoursList = new JList(hours);
        hoursList.setSelectionMode(
        ListSelectionModel.SINGLE_SELECTION);
        hoursList.setVisibleRowCount(5);
        scrollPanelhours = new JScrollPane(hoursList);

        calendar = new JCalendar(Calendar.getInstance());
        
        JLabel labelBuilding = new JLabel();
        labelBuilding.setText(" Building name :");
        buildingName = new JTextField();
        buildingName.setPreferredSize( new Dimension( 200, 24 ) );
        
        JButton submitRes = new JButton("Reservar");
        submitRes.setText("Reservar");
        submitRes.addActionListener(new ListenerMakeReservation(this));
        
        this.add(calendar);
        
        this.add(labelHours);
        this.add(scrollPanelhours);
        this.add(labelType);
        this.add(scrollPanelRooms);
        this.add(labelBuilding);
        this.add(buildingName);
        this.add(submitRes);
        

    }

    private class ListenerMakeReservation implements ActionListener{
        
        JPanel fr;
        
        public ListenerMakeReservation(JPanel fr){
            this.fr = fr;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            System.err.println(e.getActionCommand());

            try {
                String typeRoom = type[typeRooms.getAnchorSelectionIndex()];
                String building = buildingName.getText();
                DateTime date = new DateTime(calendar.getCalendar());
                date.withHourOfDay(hours[hoursList.getAnchorSelectionIndex()]);
                reservationServ.makeReservationByType("12345678",typeRoom,building,10,date);
                JOptionPane.showMessageDialog(null, "S'ha efectuat la reserva del espai"
                                             ,"Error", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
            }
        }

        
    }
    
}
