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

        for (Reservation r : reservations) {
            System.out.println("DINS DE MOSTRAR");
            JLabel l = new JLabel(r.toString());
            l.setFont(new Font("Serif", Font.PLAIN, 10));
            Color c = new Color(0, 153, 0);
            l.setForeground(c);
            this.add(l);
            this.setVisible(true);

        }
        
        System.out.println("FORA BUCLE");
    }

}
