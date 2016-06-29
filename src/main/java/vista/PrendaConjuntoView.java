package main.java.vista;

import java.util.List;

public class PrendaConjuntoView {
	
	private String nombrePrenda;
	private float descuento;
	private List<PrendaView> prendasView;
	
	public PrendaConjuntoView(String nombrePrenda, float descuento, List<PrendaView> prendasView) {
		this.nombrePrenda = nombrePrenda;
		this.descuento = descuento;
		this.prendasView = prendasView;
	}

	public String getNombrePrenda() {
		return nombrePrenda;
	}

	public float getDescuento() {
		return descuento;
	}

	public List<PrendaView> getPrendasView() {
		return prendasView;
	}
	
}
