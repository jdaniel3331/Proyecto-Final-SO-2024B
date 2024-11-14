package org.servidor_1.dtos;
import java.io.Serializable;

public class PaqueteCliente implements Serializable {
    private String ipCliente;
    private byte[] imagenEnBytes;

    public PaqueteCliente() {
    }

    public PaqueteCliente(byte[] imagenEnBytes, String ipCliente) {
        this.ipCliente = ipCliente;
        this.imagenEnBytes = imagenEnBytes;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public void setIpCliente(String ipCliente) {
        this.ipCliente = ipCliente;
    }

    public byte[] getImagenEnBytes() {
        return imagenEnBytes;
    }

    public void setImagenEnBytes(byte[] imagenEnBytes) {
        this.imagenEnBytes = imagenEnBytes;
    }
}
