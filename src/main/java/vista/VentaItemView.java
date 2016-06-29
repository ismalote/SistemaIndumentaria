package main.java.vista;

public class VentaItemView {
	
	private PrendaView prenda;
	private int cantidad;
	
	public VentaItemView(PrendaView prenda, int cantidad) {
		this.prenda = prenda;
		this.cantidad = cantidad;		
	}

	public PrendaView getPrenda() {
		return prenda;
	}

	public int getCantidad() {
		return cantidad;
	}
}
