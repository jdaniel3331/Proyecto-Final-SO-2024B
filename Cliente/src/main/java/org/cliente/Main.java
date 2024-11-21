package org.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cliente.dtos.PaqueteCliente;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    private static final String IP_SERVER_ALPHA = "127.0.0.1";
    private static final int ALPHA_SERVER_PORT = 3003;

    private static String getExtensionImg(String nombreImg){
        int i = nombreImg.lastIndexOf(".");
        String ext = "";
        if (i > 0 && i < nombreImg.length() - 1){
            ext = nombreImg.substring(i);
        }
        return ext;
    }
    private static String getRutaGuardado(){
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "C:\\Users\\Lenovo\\Downloads\\";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            String nombreUsuario = System.getenv("USER");
            return "/home/"+nombreUsuario+"/Downloads/";
        } else {
            return "desconocido";
        }
    }
    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();
        String rutaImagen;
        Scanner in = new Scanner(System.in);
        System.out.println("Ingresa la ruta completa de tu imagen a procesar: ");
        rutaImagen = in.nextLine();

        if(!rutaImagen.isEmpty()) {
            File archivo = new File(rutaImagen);
            String nombreImagen = archivo.getName();

            MatOfByte aux = new MatOfByte();
            Imgcodecs.imencode(getExtensionImg(nombreImagen), Imgcodecs.imread(rutaImagen), aux);
            byte[] imgBytes = aux.toArray();

            String localIpAddress = "";
            try {
                localIpAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            ClienteSocket clienteSocket = new ClienteSocket(IP_SERVER_ALPHA, ALPHA_SERVER_PORT);
            PaqueteCliente paqueteCliente = new PaqueteCliente(imgBytes, localIpAddress, nombreImagen);

            clienteSocket.enviarImg(paqueteCliente);
            clienteSocket.recibirImg(getRutaGuardado());
            //guardarla en la carpeta de descargas dependiendo del SO
            // color -> b/n -> cambiar el contraste subir
        }
    }
}