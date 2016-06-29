package main.java.vista;

import java.util.List;

public class PrendaView {

	private int codigoPrenda;
	private String nombrePrenda;
	private int stock;
	private String estado;
	private List<MaterialView> materiales;
	
	public PrendaView(int codigoPrenda, String nombrePrenda, int stock, String estado, List<MaterialView> materiales) {
		this.codigoPrenda = codigoPrenda;
		this.nombrePrenda = nombrePrenda;
		this.stock = stock;
		this.estado = estado;
		this.materiales = materiales;
	}

	public int getCodigoPrenda() {
		return codigoPrenda;
	}
	
	public String getNombrePrenda() {
		return nombrePrenda;
	}
	
	public int getStock() {
		return stock;
	}

	public String getEstado() {
		return estado;
	}

	public List<MaterialView> getMateriales() {
		return materiales;
	}

}
