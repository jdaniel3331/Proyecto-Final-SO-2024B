package org.servidorBeta;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorBeta {
    private final int PORT = 3004;
    private ServerSocket serverSocket;

    public ServidorBeta() {
        try {
            this.serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor Beta iniciado. Esperando conexiones...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void aceptarConexiones(){
        while (true){
            try {
                Socket clienteSocket = this.serverSocket.accept();
                System.out.println("Servidor ALPHA conectado desde " + clienteSocket.getInetAddress());
                ClienteHandler clienteHandler = new ClienteHandler(clienteSocket);
                clienteHandler.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
