package org.servidor_1;

public class Main {
    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();
        ServidorAlpha alpha = new ServidorAlpha();
        alpha.aceptarConexiones();
    }
}