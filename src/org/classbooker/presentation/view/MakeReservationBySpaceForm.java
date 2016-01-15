/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.presentation.view;

import com.toedter.calendar.JCalendar;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.classbooker.dao.exception.DAOException;
import org.classbooker.dao.exception.IncorrectBuildingException;
import org.classbooker.dao.exception.IncorrectRoomException;
import org.classbooker.dao.exception.IncorrectTimeException;
import org.classbooker.dao.exception.IncorrectUserException;
import org.classbooker.entity.Room;
import org.classbooker.entity.User;
import org.classbooker.service.AuthenticationMgr;
import org.classbooker.service.ReservationMgrService;
import org.classbooker.util.ReservationResult;
import org.joda.time.DateTime;

/**
 *
 * @author  Carles Mònico Bonell, Marc Solé Farré
 */
public class MakeReservationBySpaceForm extends JPanel {


    private final ReservationMgrService reservationServ;

    private final SpinnerNumberModel hoursList;
    public JTextField buildingName, roomNumber;
    private final JCalendar calendar;
    private final JLabel title, labelHours, labelBuilding, labelRoom;
    private final JButton submitRes;
    private final JSpinner hoursSpinner;
    private final JScrollPane suggestionsScroll;
    private final JList suggestionsRoomList;
    private List<Room> suggestedListRooms;

    public MakeReservationBySpaceForm(ReservationMgrService reservationServ) {

        this.reservationServ = reservationServ;

        title = new JLabel();
        title.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" ADD RESERVATION BY SPACE "));
        title.setFont(new Font("Ubuntu", 0, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        labelHours = new JLabel();
        labelHours.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" HOUR :"));
        hoursList = new SpinnerNumberModel(1, 1, 24, 1);
        hoursSpinner = new JSpinner(hoursList);

        calendar = new JCalendar(Calendar.getInstance());

        labelBuilding = new JLabel();
        labelBuilding.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" BUILDING NAME:"));
        buildingName = new JTextField();
        buildingName.setPreferredSize(new Dimension(200, 24));

        labelRoom = new JLabel();
        labelRoom.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString(" ROOM NUMBER: "));
        roomNumber = new JTextField();
        roomNumber.setPreferredSize(new Dimension(200, 24));

        submitRes = new JButton(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("RESERVAR"));
        submitRes.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("RESERVAR"));
        submitRes.addActionListener(new ListenerMakeReservation());

        suggestionsRoomList = new JList<Room>();
        suggestionsScroll = new JScrollPane(suggestionsRoomList);
        suggestionsRoomList.addListSelectionListener(new ListenerSuggestList());

        buildingName.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("EPS"));
        roomNumber.setText(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("0.20"));
        hoursList.setValue(9);

        //
        addelements();
    }

    private class ListenerMakeReservation implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                String room = roomNumber.getText();
                String building = buildingName.getText();
                DateTime date = getInputDate();

                User user = AuthenticationMgr.getLoggedUser();

                if (user != null) {
                    ReservationResult res = reservationServ.makeCompleteReservationBySpace(user.getNif(), room, building, date);
                    if (res.getReservation() != null) {
                        reservationServ.acceptReservation(res.getReservation());
                        JOptionPane.showMessageDialog(null, 
                                                      java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("THE RESERVATION HAS BEEN SUCCESSFUL: \n") 
                                                      + res.getReservation(), 
                                                      java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("INFO"), 
                                                      JOptionPane.INFORMATION_MESSAGE);
                    } else {

                        suggestedListRooms = res.getSuggestions();
                        if (!suggestedListRooms.isEmpty()) {
                            JOptionPane.showMessageDialog(null, 
                                java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("SPACE IS NOT AVAILABLE, SELECT ONE OF THE SUGGESTION LIST"), 
                                java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("INFO"), 
                                JOptionPane.INFORMATION_MESSAGE);
                            suggestionsRoomList.setListData(refineArrayRooms(suggestedListRooms));
                            suggestionsScroll.setVisible(true);
                            suggestionsScroll.getParent().validate();
                            suggestionsScroll.getParent().repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, 
                                    java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("SPACE IS NOT AVAILABLE"), 
                                    java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("INFO"), 
                                    JOptionPane.INFORMATION_MESSAGE);
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(null, 
                            java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("USER IS NOT AUTHENTICATED"), 
                            java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("INFO"), 
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (IncorrectUserException ex) {
                JOptionPane.showMessageDialog(null, 
                        java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("YOU DO NOT HAVE PERMISSION FOR THIS ACTION"), 
                        java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("WARNING"),
                        JOptionPane.WARNING_MESSAGE);

            } catch (IncorrectTimeException ex) {
                JOptionPane.showMessageDialog(null, 
                        java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("THE DATE IS INCORRECT.\n THE DATA HAS TO BE AFTER THE CURRENT TIME"), 
                        java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("ERROR"),
                        JOptionPane.ERROR_MESSAGE);

            } catch (IncorrectBuildingException ex) {
                JOptionPane.showMessageDialog(null, 
                        java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("THE BUILDING IS INCORRECT."), 
                        java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("ERROR"),
                        JOptionPane.ERROR_MESSAGE);

            } catch (IncorrectRoomException ex) {
                JOptionPane.showMessageDialog(null, 
                        java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("THE ROOM IS INCORRECT."), 
                        java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("ERROR"),
                        JOptionPane.ERROR_MESSAGE);

            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(null, 
                        java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("THE ENVIRONMENT THAT DOES NOT SUPPORT A KEYBOARD, DISPLAY, OR MOUSE"), 
                        java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            } catch (DAOException ex) {
                Logger.getLogger(MakeReservationBySpaceForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private String[] refineArrayRooms(List<Room> rooms) {

            String[] refineRooms = new String[rooms.size()];
            int i = 0;
            for (Room room : rooms) {
                refineRooms[i] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("NUMBER:{0} BUILDING:{1}"), new Object[] {room.getNumber(), room.getBuilding().getBuildingName()});
                i++;
            }

            return refineRooms;
        }

        private DateTime getInputDate() {
            Calendar cal = calendar.getCalendar();
            cal.set(Calendar.HOUR_OF_DAY, hoursList.getNumber().intValue());
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return new DateTime(cal);
        }
    }

    private class ListenerSuggestList implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            Room room = suggestedListRooms.get(e.getFirstIndex());
            roomNumber.setText(room.getNumber());
            buildingName.setText(room.getBuilding().getBuildingName());
        }

    }

    private void addelements() {

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
                                                .addComponent(suggestionsScroll)
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
                                        .addComponent(submitRes)
                                        .addGap(18, 18, 18)
                                        .addComponent(suggestionsScroll)))
                        .addContainerGap(236, Short.MAX_VALUE))
        );
        suggestionsScroll.setVisible(false);

    }

}
