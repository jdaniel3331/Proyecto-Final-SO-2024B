package org.servidor_1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();
        Scanner in = new Scanner(System.in);

        System.out.println("Ingresa la direccion IP del servidor BETA");
        String ipBeta = in.nextLine();
        ServidorAlpha alpha = new ServidorAlpha(ipBeta);
        alpha.aceptarConexiones();
    }
}