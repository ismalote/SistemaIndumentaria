package main.java.vista;

public class PrendaConTemporadaView {
	
	private String nombrePrenda;
	private int stock;
	private float porcentajeIncremento;
	
	public PrendaConTemporadaView(String nombrePrenda, int stock, float porcentajeIncremento) {
		this.nombrePrenda = nombrePrenda;
		this.stock = stock;
		this.porcentajeIncremento = porcentajeIncremento;
	}

	public String getNombrePrenda() {
		return nombrePrenda;
	}

	public int getStock() {
		return stock;
	}

	public float getPorcentajeIncremento() {
		return porcentajeIncremento;
	}
	
}
