package main.java.model;

import java.util.List;

import main.java.enums.Estado;
import main.java.persistencia.ProveedorDao;
import main.java.vista.ProveedorView;

public class Proveedor {
	
	private String cuit;
	private String razonSocial;
	private Estado estado;

	public Proveedor() {
		
	}
	
	public Proveedor(String cuit, String razonSocial) {
		super();
		this.cuit = cuit;
		this.razonSocial = razonSocial;
		this.estado = Estado.HA;
		
		ProveedorDao.getInstancia().insert(this);
	}

	public ProveedorView getView() {
		return new ProveedorView(cuit, razonSocial, estado.toString());
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public boolean esProveedor(String cuit) {
		return this.cuit.toUpperCase().contentEquals(cuit.toUpperCase());
	}
	
	// Dao methods

	public void modificarProveedor(String razonSocial) {
		this.razonSocial = razonSocial;
		ProveedorDao.getInstancia().update(this);
	}

	public void deleteProveedor() {
		ProveedorDao.getInstancia().update(this);
	}
	
	public List<Object> buscarAllProveedores(String cuit) {
		return ProveedorDao.getInstancia().readAll();
	}

	public Proveedor buscarProveedor(String cuit) {
		return ProveedorDao.getInstancia().read(cuit);
	}
}
