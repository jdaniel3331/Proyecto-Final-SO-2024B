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
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread {
    private Socket servidorSocket;
    private final String IP_SERVER_BETA = "127.0.0.1";
    private final int BETA_SERVER_PORT = 3004;

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
            //System.out.println(paqueteCliente.getNombreImg());
            proc(paqueteCliente);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void proc(PaqueteCliente paqueteCliente){
        MatOfByte mob = new MatOfByte(paqueteCliente.getImagenEnBytes());
        Mat originImage = Imgcodecs.imdecode(mob, Imgcodecs.IMREAD_UNCHANGED);
        Mat grayImage = new Mat(originImage.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(originImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        sendToBetaServer(grayImage,paqueteCliente);

    }

    private byte[] imageToBytes(Mat grayImg,PaqueteCliente paqueteCliente){
        MatOfByte aux = new MatOfByte();
        Imgcodecs.imencode(obtenerExtension(paqueteCliente.getNombreImg()),grayImg,aux);
        return aux.toArray();
    }
    private String obtenerExtension(String nombreImg){
        int i = nombreImg.lastIndexOf(".");
        String ext = "";
        if (i > 0 && i < nombreImg.length() - 1){
            ext = nombreImg.substring(i);
        }
        return ext;
    }

    //crear otro método para mandar la imagen al servidor 2
    private void sendToBetaServer(Mat grayImage, PaqueteCliente p) {
        PaqueteCliente paqueteEnviar = new PaqueteCliente(imageToBytes(grayImage,p),p.getIpCliente(),p.getNombreImg());
        try {
            Socket socket = new Socket(IP_SERVER_BETA, BETA_SERVER_PORT);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(paqueteEnviar);

            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            out.println(json);
            System.out.println("Imagen enviada al servidor BETA");
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
