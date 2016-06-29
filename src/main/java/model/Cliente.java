package main.java.model;

import java.util.List;

import main.java.enums.Estado;
import main.java.persistencia.ClienteDao;
import main.java.vista.ClienteView;

public class Cliente {
	public static int proxCodigoCliente;
	
	private int codigoCliente;
	private long dni;
	private String nombre;
	private Estado estado;

	public Cliente() {
		
	}
	
	public Cliente(long dni, String nombre) {
		super();
		this.codigoCliente = getProxCodigoCliente();
		this.dni = dni;
		this.nombre = nombre;
		this.estado = Estado.HA;
		
		ClienteDao.getInstancia().insert(this);
	}
	
	private static int getProxCodigoCliente() {
		proxCodigoCliente = ClienteDao.getInstancia().getMaxValue("cliente", "codigoCliente");
		
		return ++proxCodigoCliente;
	}

	public ClienteView getView() {
		return new ClienteView(this.dni, this.nombre, this.estado.toString());
	}

	public int getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	
	public long getDni() {
		return dni;
	}

	public void setDni(long dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public boolean esCliente(int codigoCliente) {
		return this.codigoCliente == codigoCliente;
	}
	
	// Dao methods

	public void modificarCliente(String nombre) {
		this.nombre = nombre;
		ClienteDao.getInstancia().update(this);
	}
	
	public void deleteCliente() {
		ClienteDao.getInstancia().delete(this);
	}
	
	public List<Object> buscarAllClientes() {
		return ClienteDao.getInstancia().readAll();
	}

	public Cliente buscarCliente(long dni) {
		return ClienteDao.getInstancia().read(dni);
	}
	
	public Cliente buscarCliente(int codigoCliente) {
		return ClienteDao.getInstancia().read(codigoCliente);
	}
	
}
