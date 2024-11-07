package org.cliente;

import java.io.IOException;
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
}
