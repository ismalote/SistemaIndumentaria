package main.java.vista;

public class ProveedorView {

	private String cuit;
	private String razonSocial;
	private String estado;

	public ProveedorView(String cuit, String razonSocial, String estado) {
		this.cuit = cuit;
		this.razonSocial = razonSocial;
		this.estado = estado;
	}

	public String getCuit() {
		return cuit;
	}

	public String getRazonSocial() {
		return razonSocial;
	}
	
	public String getEstado() {
		return estado;
	}

}
