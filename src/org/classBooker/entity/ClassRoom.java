/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author saida
 */
@Entity
@DiscriminatorValue(value="CLA_R")
public class ClassRoom extends Room{

    public ClassRoom() {
        super();
    }
    
    public ClassRoom( Building building, String number, int capacity) {
        super(building, number, capacity); 
    }
    
}
