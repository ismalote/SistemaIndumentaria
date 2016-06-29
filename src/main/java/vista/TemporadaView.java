package main.java.vista;

import java.util.Date;

public class TemporadaView {

	private int codigoTemporada;
	private String nombreTemporada;
	private Date fechaFinalizacion; 

	public TemporadaView(int codigoTemporada, String nombreTemporada, Date fechaFinalizacion) {
		this.codigoTemporada = codigoTemporada;
		this.nombreTemporada = nombreTemporada;
		this.fechaFinalizacion = fechaFinalizacion;
	}
	
	public int getCodigoTemporada() {
		return codigoTemporada;
	}
	
	public String getNombreTemporada() {
		return nombreTemporada;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

}
