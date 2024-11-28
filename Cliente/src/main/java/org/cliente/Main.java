package org.cliente;

import org.cliente.dtos.PaqueteCliente;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
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
            return "C:\\Users\\"+System.getProperty("user.name")+"\\Downloads\\";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            String nombreUsuario = System.getenv("USER");
            return "/home/"+nombreUsuario+"/Downloads/";
        } else {
            return "desconocido";
        }
    }
    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();
        String localIpAddress = "";
        Scanner in = new Scanner(System.in);

        System.out.println("Ingresa la direccion del servidor ALPHA");
        String ipAlpha = in.nextLine();

        System.out.println("Ingresa la ruta completa de tu imagen a procesar: ");
        String rutaImagen = in.nextLine();

        if(!rutaImagen.isEmpty()) {
            File archivo = new File(rutaImagen);
            if(!archivo.exists() || !archivo.isFile()){
                System.out.println("El archivo especificado no existe");
                return;
            }
            String nombreImagen = archivo.getName();

            MatOfByte aux = new MatOfByte();
            Imgcodecs.imencode(getExtensionImg(nombreImagen), Imgcodecs.imread(rutaImagen), aux);
            byte[] imgBytes = aux.toArray();


            try {
                localIpAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                System.err.println("No se pudo obtener la direccion IP local");
                return;
            }

            ClienteSocket clienteSocket = new ClienteSocket(ipAlpha, ALPHA_SERVER_PORT);
            PaqueteCliente paqueteCliente = new PaqueteCliente(imgBytes, localIpAddress, nombreImagen);

            clienteSocket.enviarImg(paqueteCliente);
            clienteSocket.recibirImg(getRutaGuardado());
        }else {
            System.out.println("Ruta vacía");
        }
    }
}