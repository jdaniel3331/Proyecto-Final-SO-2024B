package org.cliente;

import org.cliente.dtos.PaqueteCliente;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
public class Main {
    private static final String IP_SERVER_ALPHA = "127.0.0.1";
    private static final int ALPHA_SERVER_PORT = 3003;
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

        MatOfByte aux = new MatOfByte();
        Imgcodecs.imencode(".jpg", Imgcodecs.imread(rutaImagen), aux);
        byte[] imgBytes = aux.toArray();

        ClienteSocket clienteSocket = new ClienteSocket(IP_SERVER_ALPHA,ALPHA_SERVER_PORT);
        PaqueteCliente paqueteCliente = new PaqueteCliente(imgBytes,clienteSocket.getSocket().getInetAddress().toString());
        clienteSocket.enviarImg(paqueteCliente);
        //guardarla en la carpeta de descargas dependiendo del SO
        // color -> b/n -> cambiar el contraste subir
    }
}