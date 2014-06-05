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
import java.util.SimpleTimeZone;
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
import org.classbooker.entity.Reservation;
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
    
    private JList hoursList;
    private JScrollPane scrollPanelhours;
    public JTextField buildingName, roomNumber;
    JCalendar calendar;
    
    private final Integer[] hours = { 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};

    public MakeReservationBySpaceForm(JFrame parent, ReservationMgrService reservationServ, SpaceMgrService spaceServ) {

        this.parent = parent;
        this.reservationServ = reservationServ;
        this.spaceServ = spaceServ;
        


        
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
        
        JLabel labelRoom = new JLabel();
        labelRoom.setText(" Room Number :");
        roomNumber = new JTextField();
        roomNumber.setPreferredSize( new Dimension( 200, 24 ) );
        
        JButton submitRes = new JButton("Reservar");
        submitRes.setText("Reservar");
        submitRes.addActionListener(new ListenerMakeReservation(this));
        
        this.add(calendar);
        
        this.add(labelHours);
        this.add(scrollPanelhours);
        this.add(labelBuilding);
        this.add(buildingName);
        this.add(labelRoom);
        this.add(roomNumber);
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
                String room = roomNumber.getText();
                String building = buildingName.getText();
                Calendar cal = calendar.getCalendar();
                cal.set(Calendar.HOUR_OF_DAY, hours[hoursList.getAnchorSelectionIndex()]);
                DateTime date = new DateTime(cal);

                System.err.println(room);
                System.err.println(building);
                System.err.println(date);
                
                //aki peta
                Reservation res= reservationServ.makeReservationBySpace(Long.getLong(room), building, date);
                
                reservationServ.acceptReservation(res);
                
                JOptionPane.showMessageDialog(null, "S'ha efectuat la reserva correctament"+res
                                             ,"Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
            }
        }

        
    }
    
}
