package org.cliente;

import org.cliente.dtos.PaqueteCliente;
import org.opencv.imgcodecs.Imgcodecs;
public class Main {
    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();
        String rutaImagen = "/home/jdaniel/Downloads/gato.jpg";

        ClienteSocket clienteSocket = new ClienteSocket("127.0.0.1",3000);
        PaqueteCliente paqueteCliente = new PaqueteCliente(Imgcodecs.imread(rutaImagen),clienteSocket.getSocket().getInetAddress().toString());
        System.out.println(paqueteCliente.getIpCliente());

    }
}