package main.java.model;

import java.util.ArrayList;
import java.util.List;

import main.java.enums.Estado;
import main.java.persistencia.PrendaConTemporadaDao;
import main.java.vista.MaterialView;
import main.java.vista.PrendaView;

public abstract class Prenda {
	private static int proxCodigoPrenda;
	
	protected int codigoPrenda;
	protected String nombrePrenda;
	protected int stock;
	protected List<MaterialItem> materiales;
	protected Estado estado;
	
	public Prenda() {
		this.materiales = new ArrayList<>();
		this.estado = Estado.HA;
	}
	
	public Prenda(String nombrePrenda, int stock) {
		super();
		this.codigoPrenda = getProxCodigoPrenda();
		this.nombrePrenda = nombrePrenda;
		this.stock = stock;
		this.materiales = new ArrayList<>();
		this.estado = Estado.HA;
	}

	private static int getProxCodigoPrenda() {
		proxCodigoPrenda = PrendaConTemporadaDao.getInstancia().getMaxValue("prenda", "codigoPrenda");
		
		return ++proxCodigoPrenda;
	}

	public PrendaView getView() {
		List<MaterialView> materialList = new ArrayList<MaterialView>();
		for(MaterialItem material : materiales) {
			materialList.add(material.getMaterial().getView());
		}
		return new PrendaView(this.codigoPrenda, this.nombrePrenda, stock, estado.toString(), materialList);
	}
	
	public int getCodigoPrenda() {
		return codigoPrenda;
	}

	public void setCodigoPrenda(int codigoPrenda) {
		this.codigoPrenda = codigoPrenda;
	}

	public String getNombrePrenda() {
		return nombrePrenda;
	}

	public void setNombrePrenda(String nombrePrenda) {
		this.nombrePrenda = nombrePrenda;
	}
	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public List<MaterialItem> getMateriales() {
		return materiales;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public boolean esPrenda(int codigoPrenda) {
		return this.codigoPrenda == codigoPrenda;
	}
	
	public abstract float calcularPrecio();

	public abstract boolean verificarStock(int cantidad);
	
	public abstract void actualizarStock(int stockActualizado);	
}