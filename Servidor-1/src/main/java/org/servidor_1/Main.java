package org.servidor_1;

import org.opencv.imgcodecs.Imgcodecs;

public class Main {
    public static void main(String[] args) {
        ServidorUno uno = new ServidorUno();
        uno.aceptarConexiones();
        // Implementando llamada a procesamiento
        ClienteHandler procIm = new ClienteHandler();
        


    }
}