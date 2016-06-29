package main.java.vista;

public class ClienteView {
	
	private long dni;
	private String nombre;
	private String estado;

	public ClienteView(long dni, String nombre, String estado) {
		this.dni = dni;
		this.nombre = nombre;
		this.estado = estado;
	}

	public long getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getEstado() {
		return estado;
	}

}
