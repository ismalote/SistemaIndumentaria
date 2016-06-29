package main.java.model;

import java.util.List;

import main.java.persistencia.PrendaConTemporadaDao;
import main.java.persistencia.PrendaSinTemporadaDao;
import main.java.vista.PrendaSinTemporadaView;

public class PrendaSinTemporada extends Prenda {

	public PrendaSinTemporada() {
		
	}
	
	public PrendaSinTemporada(String nombrePrenda, int stock) {
		super(nombrePrenda, stock);
		
		PrendaSinTemporadaDao.getInstancia().insert(this);
	}
	
	public PrendaSinTemporadaView getViewPST() {
		return new PrendaSinTemporadaView(nombrePrenda, stock);
	}
	
	public void agregarMaterial(Material material, int cantidad) {
		MaterialItem item = new MaterialItem(material, cantidad);
		
		PrendaSinTemporadaDao.getInstancia().insertItemMaterial(this, item);
		materiales.add(item);
	}

	@Override
	public float calcularPrecio() {
		float precio = 0;
		
		for (MaterialItem item : materiales) {
			precio = precio + (item.getMaterial().getCosto() * item.getCantidad());
		}
		
		return precio;
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


	public void modificarPrenda(String nombrePrendaModificado, int stock) {
		this.setNombrePrenda(nombrePrendaModificado);
		this.setStock(stock);
		PrendaSinTemporadaDao.getInstancia().update(this);
	}

	public void deletePrenda() {
		PrendaSinTemporadaDao.getInstancia().delete(this);
	}
	
	public List<Object> buscarAllPrendasSinTemporada() {
		return PrendaSinTemporadaDao.getInstancia().readAll();
	}
	
	public PrendaSinTemporada buscarPrendaSinTemporada(String nombre) {
		return PrendaSinTemporadaDao.getInstancia().read(nombre);
	}
	
	public PrendaSinTemporada buscarPrendaSinTemporada(int codigoPrenda) {
		return PrendaSinTemporadaDao.getInstancia().read(codigoPrenda);
	}
}
