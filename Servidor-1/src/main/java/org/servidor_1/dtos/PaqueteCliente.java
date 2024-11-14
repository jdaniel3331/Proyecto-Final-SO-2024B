package org.servidor_1.dtos;
import java.io.Serializable;

public class PaqueteCliente implements Serializable {
    private String ipCliente;
    private byte[] imagenEnBytes;
    private String nombreImg;

    public PaqueteCliente() {
    }

    public PaqueteCliente(byte[] imagenEnBytes, String ipCliente, String nombreImg) {
        this.ipCliente = ipCliente;
        this.imagenEnBytes = imagenEnBytes;
        this.nombreImg = nombreImg;
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

    public String getNombreImg() {
        return nombreImg;
    }

    public void setNombreImg(String nombreImg) {
        this.nombreImg = nombreImg;
    }
}
