package org.cliente;

import org.cliente.dtos.PaqueteCliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
            ObjectOutputStream salida = new ObjectOutputStream(this.socket.getOutputStream());
            salida.writeObject(paquete);
            salida.flush();
            System.out.println("Imagen enviada al servidor ALPHA");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
