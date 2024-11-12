package org.servidor_1;

import org.servidor_1.dtos.PaqueteCliente;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteHandler extends Thread {
    private Socket servidorSocket;

    public ClienteHandler(Socket socket){
        this.servidorSocket = socket;
    }

    @Override
    public void run(){
        try(ObjectInputStream in = new ObjectInputStream(this.servidorSocket.getInputStream())){
            PaqueteCliente paqueteCliente = (PaqueteCliente) in.readObject();
            System.out.println("Objeto recibido");
            proc(paqueteCliente);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //crear metodo que realize la conversion color -> b/n
    public void proc(PaqueteCliente paqueteCliente){
        // Convert image to grayscale
        Mat originImage = paqueteCliente.getImagenCargada();;
        Mat grayImage = new Mat(originImage.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(originImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        System.out.println(grayImage);

        // Send the grayscale image to server 2
        sendImageToServer2(grayImage);
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
