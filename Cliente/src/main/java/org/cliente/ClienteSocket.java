package org.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cliente.dtos.PaqueteCliente;
import org.opencv.core.MatOfByte;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClienteSocket {
    private Socket socketTransmisor;
    private ServerSocket socketReceptor;

    public ClienteSocket(String ipDestino, int puerto) {
        try {
            this.socketTransmisor = new Socket(ipDestino,puerto);
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor ALPHA");
            throw new RuntimeException(e);
        }
    }

    public Socket getSocketTransmisor() {
        return socketTransmisor;
    }
    //crear metodo para enviar el paquete al servidor UNO
    public void enviarImg(PaqueteCliente paquete){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(paquete);

            PrintWriter out = new PrintWriter(this.socketTransmisor.getOutputStream(),true);
            out.println(json);
            System.out.println("Imagen enviada al servidor ALPHA");
            this.socketTransmisor.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void guardarImg(byte[] imgProcesada, String rutaGuardar){
        MatOfByte mob = new MatOfByte(imgProcesada);
        Mat imagenProcesada = Imgcodecs.imdecode(mob,Imgcodecs.IMREAD_UNCHANGED);
        Imgcodecs.imwrite(rutaGuardar,imagenProcesada);
    }

    private String renombrar(String nombreImg){
        int i = nombreImg.lastIndexOf('.');
        String nombre = "";
        String ext = "";

        if(i > 0 && i < nombreImg.length()-1){
            nombre = nombreImg.substring(0,i);
            ext = nombreImg.substring(i);
        }
        return nombre+"_proc"+ext;
    }

    public void recibirImg(String ruta){
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.socketReceptor = new ServerSocket(3005);
            Socket socket = this.socketReceptor.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String jsonImg = in.readLine();
            PaqueteCliente paqueteCliente = mapper.readValue(jsonImg, PaqueteCliente.class);
            System.out.println("Imgen recibida desde el servidor BETA");
            guardarImg(paqueteCliente.getImagenEnBytes(),ruta+renombrar(paqueteCliente.getNombreImg()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
