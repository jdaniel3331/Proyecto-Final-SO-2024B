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
import java.net.Socket;

public class ClienteSocket {
    private Socket socket;

    public ClienteSocket(String ipDestino, int puerto) {
        try {
            this.socket = new Socket(ipDestino,puerto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }
    //crear metodo para enviar el paquete al servidor UNO
    public void enviarImg(PaqueteCliente paquete){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(paquete);

            PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true);
            out.println(json);
            System.out.println("Imagen enviada al servidor ALPHA");
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void guardarImg(byte[] imgProcesada, String rutaGuardar){
        MatOfByte mob = new MatOfByte(imgProcesada);
        Mat imagenProcesada = Imgcodecs.imdecode(mob,Imgcodecs.IMREAD_UNCHANGED);
        Imgcodecs.imwrite(rutaGuardar,imagenProcesada);
    }

    public void recibirImg(String ruta){
        ObjectMapper mapper = new ObjectMapper();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String jsonImg = in.readLine();
            PaqueteCliente paqueteCliente = mapper.readValue(jsonImg, PaqueteCliente.class);
            System.out.println("Imgen recibida desde el servidor BETA");
            guardarImg(paqueteCliente.getImagenEnBytes(),ruta+paqueteCliente.getNombreImg());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
