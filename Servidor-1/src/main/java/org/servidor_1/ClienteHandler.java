package org.servidor_1;

import org.servidor_1.dtos.PaqueteCliente;

import java.io.ObjectInputStream;
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //crear metodo que realize la conversion color -> b/n
    //crear otro método para mandar la imagen al servidor 2
}
