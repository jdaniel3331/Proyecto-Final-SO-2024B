package org.cliente.dtos;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.Serializable;

public class PaqueteCliente implements Serializable {
    private String ipCliente;
    private byte[] imagenEnBytes;

    public PaqueteCliente(Mat imagenCargada, String ipCliente) {
        this.ipCliente = ipCliente;
        this.imagenEnBytes = convertMat(imagenCargada);
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

    private byte[] convertMat(Mat img){
        MatOfByte aux = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, aux);
        return aux.toArray();
    }
}
