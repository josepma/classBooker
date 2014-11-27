/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.presentation.view;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.classbooker.entity.Reservation;

/**
 *
 * @author abg7
 */
public class DisplayReservations extends JPanel {

    public DisplayReservations(List<Reservation> reservations) {

        if(reservations.isEmpty()){
            JLabel l = new JLabel(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("NO RESERVATIONS ASSERT THE SPECIFIED FEATURES."));
            l.setFont(new Font("Serif", Font.PLAIN, 20));
            Color c = new Color(0, 153, 0);
            l.setForeground(c);
            this.add(l);
            this.setVisible(true);
        }
        for (Reservation r : reservations) {
            JLabel l = new JLabel(r.toString());
            l.setFont(new Font("Serif", Font.PLAIN, 10));
            Color c = new Color(0, 153, 0);
            l.setForeground(c);
            this.add(l);
            this.setVisible(true);

        }
        
    }

}
