package org.cliente;

import org.cliente.dtos.PaqueteCliente;
import org.opencv.imgcodecs.Imgcodecs;
public class Main {
    public static void main(String[] args) {

        nu.pattern.OpenCV.loadLocally();
        String rutaImagen;
        
        // Detectar el sistema operativo
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            System.out.println("El sistema operativo es Windows");
            rutaImagen = "C:\\Users\\Lenovo\\Downloads\\gato-m.jpg";
            // Ruta para Windows
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            System.out.println("El sistema operativo es Unix/Linux/Mac");
            rutaImagen = "/home/jdaniel/Downloads/gato.jpg";
            // Ruta para Unix/Linux/Mac
        } else {
            System.out.println("Sistema operativo no soportado");
            rutaImagen = "";
        }

        ClienteSocket clienteSocket = new ClienteSocket("172.17.120.226",3003);
        PaqueteCliente paqueteCliente = new PaqueteCliente(Imgcodecs.imread(rutaImagen),clienteSocket.getSocket().getInetAddress().toString());
        //guardarla en la carpeta de descargas dependiendo del SO
        // color -> b/n -> cambiar el contraste subir
    }
}