package com.enigma;


public class App  {
    public static void main( String[] args ) {
        Enigma e = new Enigma("IV II V", "GMY", "RLP");
        e.configurePlugboard("DN GR IS KC QX TM PV HY FW BJ");
        System.out.println(
            e.convertMessage(
                "NQVLT YQFSE WWGJZ GQHVS EIXIM YKCNW IEBMB ATPPZ TDVCU PKAY"
            )
        );
    }
}
