/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antares
 */
public class SendMailTLSTest {
    
    public SendMailTLSTest() {
    }

    @Test
    public void testSendMail() {
        SendMailTLS mailer = new SendMailTLS();
        mailer.sendMail("joseppascualbadia@gmail.com","JACK BAUER 4 EVER");
    }
}