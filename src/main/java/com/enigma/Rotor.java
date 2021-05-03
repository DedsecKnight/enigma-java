package com.enigma;

import java.lang.StringBuilder;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Rotor {
    int offset;
    int notch;

    String characterConfiguration;

    public Rotor(String rotorName, int ringPosition, int initialPosition) {
        configure(rotorName, ringPosition, initialPosition);
    }

    public void configure(String rotorName, int ringPosition, int initialPosition) {
        try {
            Scanner sc = new Scanner(
                new File(
                    String.format(
                        "src/main/resources/rotors/%s.txt", 
                        rotorName
                    )
                )
            );
        
            characterConfiguration = configureRingPosition(
                sc.next(), 
                ringPosition
            );
            notch = (int) sc.next().charAt(0) - 65;
            offset = initialPosition;
            
            sc.close();
            
        } catch (FileNotFoundException e) {
            throw new InvalidConfigurationException(
                String.format(
                    "Invalid rotor found: %s",
                    rotorName
                )
            );
        }
    }

    private String configureRingPosition(String configuration, int ringPosition) {
        StringBuilder str = new StringBuilder();

        for (char c : configuration.toCharArray()) {
            int nextIdx = ((int) c - 65 + ringPosition) % 26;
            str.append((char) (nextIdx + 65));
        }

        return str.toString().substring(26 - ringPosition) + str.toString().substring(0, 26 - ringPosition);
    }

    public void increment() {
        offset = (offset + 1) % 26;
    }

}