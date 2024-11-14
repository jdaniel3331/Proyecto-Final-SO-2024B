package org.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cliente.dtos.PaqueteCliente;
import java.io.IOException;

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
