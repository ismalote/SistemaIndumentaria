package main.java.model;

import main.java.vista.VentaItemView;

public class VentaItem {
	private Prenda prenda;
	private int cantidad;
	
	public VentaItem(Prenda prenda, int cantidad) {
		super();
		this.prenda = prenda;
		this.cantidad = cantidad;
	}

	public Prenda getPrenda() {
		return prenda;
	}

	public void setPrenda(Prenda prenda) {
		this.prenda = prenda;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public float calcularSubTotal() {
		return prenda.calcularPrecio() * cantidad;
	}

	public VentaItemView getView() {		
		return new VentaItemView(prenda.getView(), cantidad);
	}		
}
