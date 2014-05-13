/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.main;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.User;
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
 
          ProfessorPas pp = new ProfessorPas("989898", "josepma@popo.pp","popo");
          em.getTransaction().begin();
          em.persist(pp);
          User userDB = em.find(User.class, "989898");
            
          em.remove(userDB);
          System.out.println(userDB);
            
          em.getTransaction().commit();

          em.close();
        
         
     
     }
}
