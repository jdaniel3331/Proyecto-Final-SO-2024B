package org.cliente.dtos;

import org.opencv.core.Mat;

import java.io.Serializable;

public class PaqueteCliente implements Serializable {
    private Mat imagenCargada;
    private String ipCliente;

    public PaqueteCliente(Mat imagenCargada, String ipCliente) {
        this.imagenCargada = imagenCargada;
        this.ipCliente = ipCliente.substring(1);
    }

    public Mat getImagenCargada() {
        return imagenCargada;
    }

    public void setImagenCargada(Mat imagenCargada) {
        this.imagenCargada = imagenCargada;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public void setIpCliente(String ipCliente) {
        this.ipCliente = ipCliente;
    }
}
