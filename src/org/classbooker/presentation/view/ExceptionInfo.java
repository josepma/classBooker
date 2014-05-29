/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.presentation.view;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 *
 * @author josepma
 */
public class ExceptionInfo extends JPanel{
 
    public ExceptionInfo(String text){
     JLabel l=new JLabel();
      l.setText(text);
      l.setForeground(Color.red);
      this.add(l);
      this.setVisible(true);
                            

    }
    
}
