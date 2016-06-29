package main.java.model;

import java.util.Date;
import java.util.List;

import main.java.persistencia.TemporadaDao;
import main.java.vista.TemporadaView;

public class Temporada {

	private static int proxCodigoTemporada;
	
	private int codigoTemporada;
	private String nombreTemporada;
	private Date fechaFinalizacion;
	
	public Temporada() {
		
	}
	
	public Temporada(String nombreTemporada, Date fechaFinalizacion) {
		this.codigoTemporada = getProxCodigoTemporada();
		this.nombreTemporada = nombreTemporada;
		this.fechaFinalizacion = fechaFinalizacion;
		
		TemporadaDao.getInstancia().insert(this);
	}
	
	public static int getProxCodigoTemporada() {
		proxCodigoTemporada = TemporadaDao.getInstancia().getMaxValue("temporada", "codigoTemporada");
		
		return ++proxCodigoTemporada;
	}
	
	public TemporadaView getView() {
		return new TemporadaView(codigoTemporada, nombreTemporada, fechaFinalizacion);
	}
	
	public int getCodigoTemporada() {
		return codigoTemporada;
	}

	public void setCodigoTemporada(int codigoTemporada) {
		this.codigoTemporada = codigoTemporada;
	}

	public String getNombreTemporada() {
		return nombreTemporada;
	}

	public void setNombreTemporada(String nombreTemporada) {
		this.nombreTemporada = nombreTemporada;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}
	
	// Dao methods

	public void modificarTemporada(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
		TemporadaDao.getInstancia().update(this);
	}

	public void deleteTemporada() {
		TemporadaDao.getInstancia().delete(this);
	}
	
	public List<Object> buscarAllTemporadas() {
		return TemporadaDao.getInstancia().readAll();
	}

	public Temporada buscarTemporada(String nombreTemporada) {
		return TemporadaDao.getInstancia().read(nombreTemporada);
	}
	
	public Temporada buscarTemporada(int codigoTemporada) {
		return TemporadaDao.getInstancia().read(codigoTemporada);
	}

}
