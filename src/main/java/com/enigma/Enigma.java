package com.enigma;

public class Enigma {
    Rotor[] rotors;
    String reflector;
    char[] plugBoard;

    static final String UKW_A = "EJMZALYXVBWFCRQUONTSPIKHGD";
    static final String UKW_B = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
    static final String UKW_C = "FVPJIAOYEDRZXWGCTKUQSBNMHL";

    public Enigma(String rotorConfiguration, String ringPosition, String initialPosition) {
        rotors = new Rotor[3];
        
        try {
            initializeRotorConfiguration(rotorConfiguration, ringPosition, initialPosition);  
        } catch (InvalidConfigurationException e) {
            throw e;
        }

        setReflector("UKW-B");
        plugBoard = new char[26];

        for (int i = 0; i<26; i++) {
            plugBoard[i] = (char) (i+65);
        }
    }

    private boolean completeCycle(int rotorIndex) {
        return rotors[rotorIndex].offset == rotors[rotorIndex].notch;
    }

    private void rotateRotor() {
        if (completeCycle(1)) {
            for (int i = 0; i<3; i++) rotors[i].increment();
        }
        else if (completeCycle(2)) {
            for (int i = 1; i<3; i++) rotors[i].increment();
        }
        else rotors[2].increment();
    }

    private char convertCharacter(char inputCharacter) {
        rotateRotor();
        char currentCharacter = plugBoard[(int) inputCharacter - 65];

        for (int i = 2; i>=0; i--) {
            int currentIndex = ((int) currentCharacter - 65 + rotors[i].offset + 26) % 26;
            int nextIndex = (int) rotors[i].characterConfiguration.charAt(currentIndex) - 65 - rotors[i].offset;
            nextIndex = (nextIndex + 26) % 26;
            currentCharacter = (char) (nextIndex + 65);
        }

        currentCharacter = reflector.charAt((int) currentCharacter - 65);

        for (int i = 0; i<3; i++) {
            char targetChar = (char) ((currentCharacter - 65 + rotors[i].offset + 26) % 26 + 65);
            int nextIndex = (rotors[i].characterConfiguration.indexOf(targetChar) - rotors[i].offset + 26) % 26;
            currentCharacter = (char) (nextIndex + 65);
        }

        return plugBoard[(int) currentCharacter-65];
    }

    public void initializeRotorConfiguration(String rotorConfiguration, String ringPosition, String initialPosition) throws InvalidConfigurationException {
        String[] rotorNames = rotorConfiguration.split(" ", 0);
            
        if (rotorNames.length != 3) 
            throw new InvalidConfigurationException(
                String.format(
                    "Invalid number of rotors (expected 3, but %d found)",
                    rotorNames.length
                )
            );

        if (ringPosition.length() != 3) 
            throw new InvalidConfigurationException(
                String.format(
                    "Invalid length for ring position configuration (expected 3, but %d found)",
                    ringPosition.length()
                )
            );
    
        if (initialPosition.length() != 3) 
            throw new InvalidConfigurationException(
                String.format(
                    "Invalid length for initial position configuration (expected 3, but %d found)",
                    initialPosition.length()
                )
            );
        
        for (int i = 0; i<3; i++) {
            if (!Character.isLetter(ringPosition.charAt(i))) 
                throw new InvalidConfigurationException(
                    String.format(
                        "Letter is expected in ring position configuration (\'%c\' found)",
                        ringPosition.charAt(i)
                    )
                );
            
            if (!Character.isLetter(initialPosition.charAt(i))) 
                throw new InvalidConfigurationException(
                    String.format(
                        "Letter is expected in initial position configuration (\'%c\' found)",
                        initialPosition.charAt(i)
                    )
                );
            
            rotors[i] = new Rotor(
                rotorNames[i], 
                ringPosition.charAt(i) - 65, 
                initialPosition.charAt(i) - 65
            );
        }
    }

    public void setReflector(String newReflector) throws InvalidConfigurationException {
        if (newReflector.equals("UKW-A")) reflector = UKW_A;
        else if (newReflector.equals("UKW-B")) reflector = UKW_B;
        else if (newReflector.equals("UKW-C")) reflector = UKW_C;
        else throw new InvalidConfigurationException("Invalid reflector found");
    }

    public String convertMessage(String input) {
        input = input.toUpperCase();
        StringBuilder str = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) str.append(c);
            else str.append(convertCharacter(c));
        }

        return str.toString();
    }

    public void configurePlugboard(String configuration) {
        String[] plugBoardPair = configuration.split(" ", 0);
        try {
            for (String pair : plugBoardPair) {
                char firstCharacter = pair.charAt(0);
                char secondCharacter = pair.charAt(1);
                
                if (plugBoard[(int) firstCharacter-65] != firstCharacter) 
                    throw new InvalidConfigurationException(
                        "Plugboard for first character is already used"
                    );

                if (plugBoard[(int) secondCharacter-65] != secondCharacter)
                    throw new InvalidConfigurationException(
                        "Plugboard for second character is already used"
                    );
                
                plugBoard[(int) firstCharacter-65] = secondCharacter;
                plugBoard[(int) secondCharacter-65] = firstCharacter;
                
            }    
        } 
        catch (InvalidConfigurationException e) {
            throw e;
        }
    }
}