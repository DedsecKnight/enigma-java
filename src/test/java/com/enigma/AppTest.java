package com.enigma;

import static org.junit.Assert.*;

import org.junit.Test;


import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void noConfiguration() {
        Enigma e = new Enigma("I II III", "AAA", "AAA");
        assertEquals("BDZGO", e.convertMessage("AAAAA"));
    }

    @Test
    public void configureRingPosition() {
        Enigma e = new Enigma("I II III", "BBB", "AAA");
        assertEquals("EWTYX", e.convertMessage("AAAAA"));
    }

    @Test
    public void randomConfiguration() {
        Enigma e = new Enigma("I II III", "SKG", "VEQ");
        assertEquals(
            "LWROFKKQCKMYTHMAORFSDUX", 
            e.convertMessage("YOUTUBEISAGREATPLATFORM")
        );
    }

    @Test
    public void decryptMessage() {
        Enigma e = new Enigma("I II III", "SKG", "VEQ");
        assertEquals(
            "YOUTUBEISAGREATPLATFORM", 
            e.convertMessage("LWROFKKQCKMYTHMAORFSDUX")
        );
    }

    @Test
    public void testPlugBoard() {
        Enigma e = new Enigma("I II III", "SKG", "WGN");
        e.configurePlugboard("ED GP QZ");
        assertEquals(
            "OFHOKSHVHWJMHEN", 
            e.convertMessage("TESTPLUGINBOARD")
        );
    }

    @Test
    public void testLowercase() {
        Enigma e = new Enigma("I II III", "BBB", "AAA");
        assertEquals("EWTYX", e.convertMessage("aaaaa"));
    }

    @Test
    public void nonAlphabeticCharacter() {
        Enigma e = new Enigma("IV I II", "VCD", "SLG");
        assertEquals(
            "SMBML JE 1 NQUOSPD NPYCUZ BDXQKNN", 
            e.convertMessage("THERE IS 1 CHANNEL CALLED YOUTUBE")
        );
    }

    @Test
    public void largeText() throws FileNotFoundException {
        Enigma e = new Enigma("III V IV", "UTD", "QSM");
        
        
        Scanner f = new Scanner(new File("src/test/resources/input1.txt"));
        String message = f.next();
        f.close();
        
        Scanner f2 = new Scanner(new File("src/test/resources/output1.txt"));
        String expected = f2.next();
        f2.close();

        assertEquals(expected, e.convertMessage(message));

    }

    @Test(expected = InvalidConfigurationException.class)
    public void invalidRotor() {
        try {
            Enigma e = new Enigma("VII VIII IX", "UTD", "QSM");
            e.convertMessage("Just put something here to turn off linting");
        } catch (InvalidConfigurationException e) {
            assertEquals("Invalid rotor found: VII", e.getMessage());
            throw e;
        }
        fail("No error is thrown");
    }

    @Test(expected = InvalidConfigurationException.class)
    public void invalidPlugboardConfiguration1() {
        try {
            Enigma e = new Enigma("I II III", "AAA", "AAA");
            e.configurePlugboard("AB AC");
        } catch (InvalidConfigurationException e) {
            assertEquals(
                "Plugboard for first character is already used", 
                e.getMessage()
            );
            throw e;
        }
        fail("No error is thrown");
    }

    @Test(expected = InvalidConfigurationException.class)
    public void invalidPlugboardConfiguration2() {
        try {
            Enigma e = new Enigma("I II III", "AAA", "AAA");
            e.configurePlugboard("AB CB");
        } catch (InvalidConfigurationException e) {
            assertEquals(
                "Plugboard for second character is already used", 
                e.getMessage()
            );
            throw e;
        }
        fail("No error is thrown");
    }

    @Test(expected = InvalidConfigurationException.class) 
    public void lessThan3Rotors() {
        try {
            Enigma e = new Enigma("I II", "AAA", "AAA");
            e.configurePlugboard("AB CD");
        } catch (InvalidConfigurationException e) {
            assertEquals(
                "Invalid number of rotors (expected 3, but 2 found)", 
                e.getMessage()
            );
            throw e;
        }
        fail("No error is thrown");
    }

    @Test(expected = InvalidConfigurationException.class) 
    public void moreThan3Rotors() {
        try {
            Enigma e = new Enigma("I II III IV V", "AAA", "AAA");
            e.configurePlugboard("AB CD");
        } catch (InvalidConfigurationException e) {
            assertEquals(
                "Invalid number of rotors (expected 3, but 5 found)", 
                e.getMessage()
            );
            throw e;
        } 
        fail("No error is thrown");
    }

    @Test(expected = InvalidConfigurationException.class) 
    public void invalidLengthRingPosition() {
        try {
            Enigma e = new Enigma("I II III", "AAAA", "AAA");
            e.configurePlugboard("AB CD");
        } catch (InvalidConfigurationException e) {
            assertEquals(
                "Invalid length for ring position configuration (expected 3, but 4 found)", 
                e.getMessage()
            );
            throw e;
        }
        fail("No error is thrown");
    }

    @Test(expected = InvalidConfigurationException.class) 
    public void invalidCharacterRingPosition() {
        try {
            Enigma e = new Enigma("I II III", "123", "AAA");
            e.configurePlugboard("AB CD");
        } catch (InvalidConfigurationException e) {
            assertEquals(
                "Letter is expected in ring position configuration (\'1\' found)", 
                e.getMessage()
            );
            throw e;
        }
        fail("No error is thrown");
    }

    @Test(expected = InvalidConfigurationException.class) 
    public void invalidLengthInitialPosition() {
        try {
            Enigma e = new Enigma("I II III", "AAA", "AAAA");
            e.configurePlugboard("AB CD");
        } catch (InvalidConfigurationException e) {
            assertEquals(
                "Invalid length for initial position configuration (expected 3, but 4 found)", 
                e.getMessage()
            );
            throw e;
        }
        fail("No error is thrown");
    }

    @Test(expected = InvalidConfigurationException.class) 
    public void invalidCharacterInitialPosition() {
        try {
            Enigma e = new Enigma("I II III", "AAA", "123");
            e.configurePlugboard("AB CD");
        } catch (InvalidConfigurationException e) {
            assertEquals(
                "Letter is expected in initial position configuration (\'1\' found)", 
                e.getMessage()
            );
            throw e;
        }
        fail("No error is thrown");
    }

    @Test
    public void changeToUKWC() {
        Enigma e = new Enigma("I II III", "AAA", "AAA");
        e.setReflector("UKW-C");
        assertEquals("DKCPIHIG", e.convertMessage("TESTUKWC"));
    }

    @Test(expected = InvalidConfigurationException.class)
    public void invalidUKW() {
        try {
            Enigma e = new Enigma("I II III", "AAA", "AAA");
            e.setReflector("UKW-D");
        } catch (InvalidConfigurationException e) {
            assertEquals("Invalid reflector found", e.getMessage());
            throw e;
        }
        fail("No error is thrown");
    }

    @Test
    public void changeRotorConfiguration() {
        Enigma e = new Enigma("I II III", "AAA", "AAA");
        assertEquals("HQGFDWURWOM", e.convertMessage("INITIALTEST"));
        e.configureRotor(0, "IV", 'A', 'C');
        assertEquals("WQRGCNNCX", e.convertMessage("FINALTEST"));
    }
    
}
