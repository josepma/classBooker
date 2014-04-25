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
@DiscriminatorValue(value="LAB_R")
public class LaboratoryRoom extends Room {

    public LaboratoryRoom() {
        super();
    }

    public LaboratoryRoom( Building building, String number, int capacity) {
        super(building, number, capacity);
    }
   
   
}
