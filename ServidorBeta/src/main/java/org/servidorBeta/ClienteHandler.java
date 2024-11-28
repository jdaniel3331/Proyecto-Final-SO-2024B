package org.servidorBeta;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.servidorBeta.dtos.PaqueteCliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread {
    private Socket servidorSocket;

    public ClienteHandler(Socket socket){
        this.servidorSocket = socket;
    }

    @Override
    public void run(){
        try{
            ObjectMapper mapper = new ObjectMapper();
            BufferedReader in = new BufferedReader(new InputStreamReader(this.servidorSocket.getInputStream()));
            String jsonRecibido = in.readLine();
            PaqueteCliente paqueteCliente = mapper.readValue(jsonRecibido,PaqueteCliente.class);
            System.out.println("Imagen recibida desde "+paqueteCliente.getIpCliente());
            espejoImagen(paqueteCliente);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void espejoImagen(PaqueteCliente paqueteCliente){
        MatOfByte mob = new MatOfByte(paqueteCliente.getImagenEnBytes());
        Mat grayImg = Imgcodecs.imdecode(mob, Imgcodecs.IMREAD_UNCHANGED);
        Mat imagenAjustada = new Mat();
        Core.flip(grayImg,imagenAjustada,1);

        //boolean wasSaved = Imgcodecs.imwrite("/home/jdaniel/Downloads/"+paqueteCliente.getNombreImg(),imagenAjustada);
        sendToCliente(imagenAjustada,paqueteCliente);

    }
    private String obtenerExtension(String nombreImg){
        int i = nombreImg.lastIndexOf(".");
        String ext = "";
        if (i > 0 && i < nombreImg.length() - 1){
            ext = nombreImg.substring(i);
        }
        return ext;
    }
    private byte[] imageToBytes(Mat grayImg,PaqueteCliente paqueteCliente){
        MatOfByte aux = new MatOfByte();
        Imgcodecs.imencode(obtenerExtension(paqueteCliente.getNombreImg()),grayImg,aux);
        return aux.toArray();
    }
    private void sendToCliente(Mat imgProc, PaqueteCliente paquete){
        PaqueteCliente paraCliente = new PaqueteCliente(imageToBytes(imgProc,paquete), paquete.getIpCliente(), paquete.getNombreImg());
        try{
            Socket socket = new Socket(paraCliente.getIpCliente(),3005);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(paraCliente);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(json);
            System.out.println("Imagen procesada enviada de vuelta al cliente");
            out.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
