

package org.classbooker.presentation.view;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class ConfirmationForm extends JPanel{
 
    public ConfirmationForm(String text){
     JLabel l=new JLabel();
      l.setText(text);
      l.setForeground(Color.green);
      this.add(l);
      this.setVisible(true);
                            

    }
    
}
