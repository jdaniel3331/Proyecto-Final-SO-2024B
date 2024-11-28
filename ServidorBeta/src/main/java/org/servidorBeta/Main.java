package org.servidorBeta;

public class Main {
    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();
        ServidorBeta servidorBeta = new ServidorBeta();
        servidorBeta.aceptarConexiones();
    }
}