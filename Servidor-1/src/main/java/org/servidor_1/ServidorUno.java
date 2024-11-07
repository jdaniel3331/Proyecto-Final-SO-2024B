package org.servidor_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorUno {
    private final int PORT = 3000;
    private ServerSocket serverSocket;

    public ServidorUno() {
        try {
            this.serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor UNO iniciado. Esperando conexiones...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void aceptarConexiones(){
        while (true){
            try {
                Socket clienteSocket = this.serverSocket.accept();
                System.out.println("Cliente conectado desde "+clienteSocket.getInetAddress());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
