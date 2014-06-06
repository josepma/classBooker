/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.presentation.view;

import com.toedter.calendar.JCalendar;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import org.classbooker.entity.Reservation;
import org.classbooker.service.ReservationMgrService;
import org.classbooker.service.SpaceMgrService;
import org.classbooker.util.ReservationResult;
import org.joda.time.DateTime;


/**
 *
 * @author hellfish
 */
public class MakeReservationBySpaceForm extends JPanel {
    
    private final JFrame parent;
    private final ReservationMgrService reservationServ;
    private final SpaceMgrService spaceServ;
    
    private final SpinnerNumberModel hoursList;
    public JTextField buildingName, roomNumber;
    private final JCalendar calendar;
    private final JLabel title, labelHours, labelBuilding, labelRoom;
    private final JButton submitRes;
    private final JSpinner hoursSpinner;

    public MakeReservationBySpaceForm(JFrame parent, ReservationMgrService reservationServ, SpaceMgrService spaceServ) {

        this.parent = parent;
        this.reservationServ = reservationServ;
        this.spaceServ = spaceServ;

        title = new JLabel();
        title.setText(" MakeReservationBySpace ");
        title.setFont(new Font("Ubuntu", 0, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        
        labelHours = new JLabel();
        labelHours.setText(" Hour :");
        hoursList = new SpinnerNumberModel(1, 1, 24, 1);
        hoursSpinner = new JSpinner(hoursList);

        calendar = new JCalendar(Calendar.getInstance());
        
        labelBuilding = new JLabel();
        labelBuilding.setText(" Building name:");
        buildingName = new JTextField();
        buildingName.setPreferredSize( new Dimension( 200, 24 ) );
        
        labelRoom = new JLabel();
        labelRoom.setText(" Room Number: ");
        roomNumber = new JTextField();
        roomNumber.setPreferredSize( new Dimension( 200, 24 ) );
        
        submitRes = new JButton("Reservar");
        submitRes.setText("Reservar");
        submitRes.addActionListener(new ListenerMakeReservation());
        
        /*this.add(calendar);
        
        this.add(labelHours);
        this.add(hoursSpinner);
        this.add(labelBuilding);
        this.add(buildingName);
        this.add(labelRoom);
        this.add(roomNumber);
        this.add(submitRes);*/

        addelements();
    }
    
    private void addelements(){
        
        GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(calendar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelHours)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hoursSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(submitRes)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelBuilding)
                                    .addComponent(labelRoom))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buildingName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(roomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calendar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelHours)
                            .addComponent(hoursSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelBuilding)
                            .addComponent(buildingName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelRoom)
                            .addComponent(roomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(submitRes)))
                .addContainerGap(236, Short.MAX_VALUE))
        );
    }

    private class ListenerMakeReservation implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e) {
            System.err.println(e.getActionCommand());

            try {
                String room = roomNumber.getText();
                String building = buildingName.getText();
                Calendar cal = calendar.getCalendar();
                cal.set(Calendar.HOUR_OF_DAY, hoursList.getNumber().intValue());
                DateTime date = new DateTime(cal);

                System.err.println(room);
                System.err.println(building);
                System.err.println(date);
                
                //aki peta
                ReservationResult res= reservationServ.makeCompleteReservationBySpace(room, room, building, date);         
                if(res.getReservation() == null) reservationServ.acceptReservation(res.getReservation());
                
                JOptionPane.showMessageDialog(null, "S'ha efectuat la reserva correctament: "+res
                                             ,"Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                
                JOptionPane.showMessageDialog(null, ex.toString(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
            }
        }

        
    }
    
}
