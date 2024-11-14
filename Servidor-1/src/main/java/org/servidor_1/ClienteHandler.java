package org.servidor_1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.servidor_1.dtos.PaqueteCliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
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
            System.out.println(paqueteCliente.getNombreImg());
            proc(paqueteCliente);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //crear metodo que realize la conversion color -> b/n

    public void proc(PaqueteCliente paqueteCliente){
        // Convert image to grayscale
        MatOfByte mob = new MatOfByte(paqueteCliente.getImagenEnBytes());
        Mat originImage = Imgcodecs.imdecode(mob, Imgcodecs.IMREAD_UNCHANGED);
        Mat grayImage = new Mat(originImage.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(originImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        //System.out.println(grayImage);

        // Send the grayscale image to server 2
        //sendImageToServer2(grayImage);
    }

    //crear otro método para mandar la imagen al servidor 2
    private void sendImageToServer2(Mat grayImage) {
        try (Socket socket = new Socket("server2_address", 12345);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            out.writeObject(grayImage);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
