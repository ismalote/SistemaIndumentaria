package main.java.vista;

import java.util.List;

public class VentaView {
	public static final Integer ESTADO_NUEVO = 0;
	public static final Integer ESTADO_FINALIZADO = 1;
	public static final Integer ESTADO_RECHAZADO = 2;
	
	private String fecha;
	private String nombreCliente;
	private List<VentaItemView> items;
	private Integer estado;
	
	public VentaView(String fecha, String nombreCliente, List<VentaItemView> items, Integer estado) {
		this.fecha = fecha;
		this.nombreCliente = nombreCliente;
		this.items = items;
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public List<VentaItemView> getItems() {
		return items;
	}

	public Integer getEstado() {
		return estado;
	}
}
