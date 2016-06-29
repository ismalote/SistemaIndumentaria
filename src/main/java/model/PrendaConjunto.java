package main.java.model;

import java.util.ArrayList;
import java.util.List;

import main.java.persistencia.PrendaConjuntoDao;
import main.java.vista.PrendaConjuntoView;
import main.java.vista.PrendaView;

public class PrendaConjunto extends Prenda {
	private float descuento;
	private List<Prenda> prendas;
	
	public PrendaConjunto() {
		
	}
	
	public PrendaConjunto(String nombrePrenda, float descuento) {
		super(nombrePrenda, 0);
		this.descuento = descuento;
		prendas = new ArrayList<>();
		
		PrendaConjuntoDao.getInstancia().insert(this);
	}
	
	public PrendaConjuntoView getViewPC() {
		List<PrendaView> prendasView = new ArrayList<PrendaView>();
		for(Prenda p : prendas) {
			prendasView.add(p.getView());
		}
		
		return new PrendaConjuntoView(nombrePrenda, descuento, prendasView);
	}

	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}
	
	public List<Prenda> getPrendas() {
		return prendas;
	}

	public void setPrendas(List<Prenda> prendas) {
		this.prendas = prendas;
	}

	public void agregarPrenda(Prenda prenda) {
		prendas.add(prenda);
		
		PrendaConjuntoDao.getInstancia().insertItemPrendaConjunto(this, prenda.getCodigoPrenda());
	}

	@Override
	public float calcularPrecio() {
		float precio = 0;
		
		for (Prenda prenda : prendas) {
			precio = precio + prenda.calcularPrecio();
		}
		
		return precio * (1 - (this.descuento/100));
	}
	
	@Override
	public boolean verificarStock(int cantidad) {
		boolean noStock = false;
		for(int i=0; i<prendas.size() && !noStock; i++) {
			noStock = prendas.get(i).verificarStock(cantidad);
		}
		return noStock;
	}
	
	@Override
	public void actualizarStock(int stockActualizado) {
		for(Prenda p : prendas) {
			p.actualizarStock(stockActualizado);
		}
	}
	
	// Dao methods

	public void modificarPrenda(String nombrePrendaModificado, float descuento) {
		this.nombrePrenda = nombrePrendaModificado;
		this.descuento = descuento;
		PrendaConjuntoDao.getInstancia().update(this);
	}
	
	public void deletePrenda() {
		PrendaConjuntoDao.getInstancia().delete(this);
	}
	
	public List<Object> buscarAllPrendasConjunto() {
		return PrendaConjuntoDao.getInstancia().readAll();
	}
	
	public PrendaConjunto buscarPrendaConjunto(String nombrePrenda) {
		return PrendaConjuntoDao.getInstancia().read(nombrePrenda);
	}
	
	public PrendaConjunto buscarPrendaConjunto(int codigoPrenda) {
		return PrendaConjuntoDao.getInstancia().read(codigoPrenda);
	}

}
