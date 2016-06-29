package main.java.model;

import java.util.List;

import main.java.persistencia.PrendaConTemporadaDao;
import main.java.vista.PrendaConTemporadaView;

public class PrendaConTemporada extends Prenda {
	private float porcentajeIncremento;
	private Temporada temporada;
	
	public PrendaConTemporada() {
		super();
	}
	
	public PrendaConTemporada(String nombrePrenda, int stock, float porcentajeIncremento, Temporada temporada) {
		super(nombrePrenda, stock);
		this.porcentajeIncremento = porcentajeIncremento;
		this.temporada = temporada;
		
		PrendaConTemporadaDao.getInstancia().insert(this);
	}
	
	public PrendaConTemporadaView getViewPCT() {
		return new PrendaConTemporadaView(nombrePrenda, stock, porcentajeIncremento);
	}

	public float getPorcentajeIncremento() {
		return porcentajeIncremento;
	}

	public void setPorcentajeIncremento(float porcentajeIncremento) {
		this.porcentajeIncremento = porcentajeIncremento;
	}

	public Temporada getTemporada() {
		return temporada;
	}

	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}
	
	public void agregarMaterial(Material material, int cantidad, boolean insertaBD) {
		MaterialItem item = new MaterialItem(material, cantidad);
		
		if (insertaBD){
			PrendaConTemporadaDao.getInstancia().insertItemMaterial(this, item);			
		}
		materiales.add(item);
	}
	public void agregarMaterial(Material material, int cantidad) {
		agregarMaterial(material, cantidad, true);
	}

	@Override
	public float calcularPrecio() {
		float precio = 0;
		
		for (MaterialItem item : materiales) {
			precio = precio + (item.getMaterial().getCosto() * item.getCantidad());
		}
		
		return precio * (1 + (this.porcentajeIncremento/100));
	}
	
	@Override
	public boolean verificarStock(int cantidad) {
		return (this.getStock() >= cantidad);
	}
	
	@Override
	public void actualizarStock(int stockActualizado) {
		this.setStock(stockActualizado);
		
		PrendaConTemporadaDao.getInstancia().updateStock(this);
	}
	
	// Dao methods

	public void modificarPrenda(String nombrePrendaModificado, int stock, float porcentajeIncremento) {
		this.setNombrePrenda(nombrePrendaModificado);
		this.setStock(stock);
		this.setPorcentajeIncremento(porcentajeIncremento);
		PrendaConTemporadaDao.getInstancia().update(this);
	}

	public void deletePrenda() {
		PrendaConTemporadaDao.getInstancia().delete(this);
	}
	
	public List<Object> buscarAllPrendasConTemporada() {
		return PrendaConTemporadaDao.getInstancia().readAll();
	}
	
	public PrendaConTemporada buscarPrendaConTemporada(String nombre) {
		return PrendaConTemporadaDao.getInstancia().read(nombre);
	}
	
	public PrendaConTemporada buscarPrendaConTemporada(int codigoPrenda) {
		return PrendaConTemporadaDao.getInstancia().read(codigoPrenda);
	}
	
}
