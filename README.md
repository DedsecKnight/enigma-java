# Enigma Simulation
- A simple implementation of Enigma, a crypto device used by the Germans in WWII, in Java

### Objective
- To familiarize with: 
    + Using JUnit for testing Java application
    + Project Maven folder structure
    + Building small-sized application with Java
- Explore how the Enigma machine encrypts and decrypts a message

### Components
- Rotors: 
    + There are currently 5 rotors to choose from: 
        + Rotor I (Enigma I)
        + Rotor II (Enigma I)
        + Rotor III (Enigma I)
        + Rotor IV (M3 Army Model)
        + Rotor V (M3 Army Model)
- Reflectors
    + There are currently 3 reflectors to choose from: 
        + Reflector A (UKW_A)
        + Reflector B (UKW_B)
        + Reflector C (UKW_C)
- Plugboard

### Features
- Allows choosing 3 out of the 5 available rotors 
- Allows reconfiguring of letter mapping using the plugboard
- Allows reconfiguring the ring position and initial position of each rotor
- Allows choosing 1 out of the 3 available reflectors 

### How to use
- Create a new Enigma object with the following syntax (the rotors will be ordered from left to right): 
    + <code> Enigma enigma = new Enigma(\
            "&lt;Rotor1> &lt;Rotor2> &lt;Rotor3>",\
            "&lt;Ring position for Rotor1>&lt;Ring position for Rotor2>&lt;Ring position for Rotor3>",\
            "&lt;Initial position for Rotor1>&lt;Initial position for Rotor2>&lt;Initial position for Rotor3>"\
        ); </code>
    + Following is an example: 
        <code>
            Enigma enigma = new Enigma("I II III", "ABC", "DEF");
        </code> <br>
    + The above example will result in an enigma with the following configuration: 
        - The leftmost rotor will be rotor I with ring position of A and initial position of D
        - The middle rotor will be rotor II with ring position of B and initial position of E
        - The rightmost rotor will be rotor III with ring position of C and initial position of F
    + To configure the plugboard, do the following: 
        + <code> enigma.configuration("&lt;Plugboard configuration goes here>") </code>
        + Here is an example configuration: DN GR IS
            + D will be mapped to N before and after conversion and vice versa
            + G will be mapped to R before and after conversion and vice versa
            + I will be mapped to S before and after conversion and vice versa
