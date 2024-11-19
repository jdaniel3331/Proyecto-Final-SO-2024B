package org.servidor_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorAlpha {
    private final int PORT = 3003;
    private ServerSocket serverSocket;

    /*
    * Falta manejar excepciones*/

    public ServidorAlpha() {
        try {
            this.serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor ALPHA iniciado. Esperando conexiones...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void aceptarConexiones(){
        while (true){
            try {
                Socket clienteSocket = this.serverSocket.accept();
                System.out.println("Cliente conectado desde " + clienteSocket.getInetAddress());
                ClienteHandler clienteHandler = new ClienteHandler(clienteSocket);
                clienteHandler.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
