/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.main;

import org.classbooker.entity.Building;
import org.classbooker.entity.Room;
import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.User;
import org.classbooker.entity.Reservation;
import org.classbooker.entity.ClassRoom;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author josepma
 */
public class MainITest {
    
    public MainITest() {
    }

     @Test
     public void firstIT() {
     
         System.out.println("Integration test");
         
          EntityManager em = Persistence.createEntityManagerFactory("classBookerIntegration").createEntityManager();
 
          em.getTransaction().begin();
          ProfessorPas pp2 = (ProfessorPas) em.find(User.class,"12345678");
          Building building = (Building) em.find(Building.class,"Main Library");
          
          Room room2 = new ClassRoom(building, "222333", 30);
          
          em.persist(room2);
          
          Reservation reservation = new Reservation(
                  new DateTime(2014,10,10,1,0),pp2, room2); 
          
          em.persist(reservation);
          
          em.getTransaction().commit();

          em.close();
        
         
     
     }
}
