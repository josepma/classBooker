/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.util;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antares
 */
public class EncoderTest {
    
    public EncoderTest() {
    }

    @Test
    public void testSomeMethod() throws NoSuchAlgorithmException {
        Encoder e = new Encoder();
        String s = e.codifySHA512("Jack Bauer");
        assertEquals(s, "79272dc6885be55111b4b95980a20dbbadc87f93c50f34e4e91295ae4a477f59");
    }
}