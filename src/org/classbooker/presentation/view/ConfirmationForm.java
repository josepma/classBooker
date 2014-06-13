

package org.classbooker.presentation.view;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class ConfirmationForm extends JPanel{
 
    public ConfirmationForm(String text){
     JLabel l=new JLabel(text);
      l.setFont(new Font("Serif",Font.PLAIN,32));
      Color c = new Color(0,153,0);
      l.setForeground(c);
      this.add(l);
      this.setVisible(true);
                            

    }
    
}
