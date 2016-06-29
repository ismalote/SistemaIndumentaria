package main.java.vista;

public class MaterialView {

	private int codigoMaterial;
	private String nombreMaterial;
	private int stock;
	private int puntoPedido;
	private String nombreProveedor;
	private float costo;
	private String estado;
	
	public MaterialView(int codigoMaterial, String nombreMaterial, int stock, int puntoPedido, String nombreProveedor, float costo, String estado) {
		this.codigoMaterial = codigoMaterial;
		this.nombreMaterial = nombreMaterial;
		this.stock = stock;
		this.puntoPedido = puntoPedido;
		this.nombreProveedor = nombreProveedor;
		this.costo = costo;
		this.estado = estado;
	}

	public int getCodigoMaterial() {
		return codigoMaterial;
	}
	
	public String getEstado() {
		return estado;
	}

	public String getNombreMaterial() {
		return nombreMaterial;
	}

	public int getStock() {
		return stock;
	}

	public int getPuntoPedido() {
		return puntoPedido;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public float getCosto() {
		return costo;
	}

}
