package main.java.controller;

import main.java.controller.SistemaIndumentaria;

public class Main {
	private SistemaIndumentaria sistema;
	private int nroVenta;
	
	public Main() {
		sistema = SistemaIndumentaria.getInstancia();
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		
		main.generarPrendaConTemporada("Campera", 15, 20, "Verano");
		
		main.generarVenta(35864518);
		main.agregarItemVenta("Buzo", 2);
		main.agregarItemVenta("Remera", 1);
		main.cerrarVenta();
	}

	private void generarPrendaConTemporada(String nombrePrenda, int stock, int porcentajeIncremento, String nombreTemporada) {
		sistema.altaPrendaConTemporada(nombrePrenda, stock, porcentajeIncremento, sistema.buscarTemporada(nombreTemporada));
		
		System.out.println("Prenda generada correctamente");
	}

	private void generarVenta(int dni) {
		
		nroVenta = sistema.generarVenta(dni);
		
		if (nroVenta != 0) {
			System.out.println("Venta numero " + nroVenta + " generada.");
		}
	}
	
	private void agregarItemVenta(String nombrePrenda, int cantidad) {
		
//		if (sistema.verificarStock(codigoPrenda, cantidad)) {
			sistema.agregarItemVenta(nroVenta, nombrePrenda, cantidad);
			
			System.out.println("Item de venta agregado correctamente.");
//		}
//		else {
//			System.out.println("Item de venta sin stock disponible.");
//		}
	}
	
	private void cerrarVenta() {
		System.out.println("El monto de la venta es de : $ " + sistema.cerrarVenta(nroVenta));
		
		nroVenta = 0;
	}
}
