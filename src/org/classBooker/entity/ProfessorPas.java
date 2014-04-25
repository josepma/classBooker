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
 * @author josepma
 */
@Entity
@DiscriminatorValue(value="PPS_U")
public class ProfessorPas extends ReservationUser{

    public ProfessorPas() {
        super();
    }

    public ProfessorPas(String nif, String email, String name) {
        super(nif, email, name);
    }
    
}
