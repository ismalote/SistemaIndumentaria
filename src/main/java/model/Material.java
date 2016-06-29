package main.java.model;

import java.util.List;

import main.java.enums.Estado;
import main.java.persistencia.MaterialDao;
import main.java.vista.MaterialView;

public class Material {
	private static int proxCodigoMaterial;
	
	private int codigoMaterial;
	private String nombreMaterial;
	private int stock;
	private int puntoPedido;
	private Proveedor proveedor;
	private float costo;
	private Estado estado;
	
	public Material() {
		
	}
	
	public Material(String nombreMaterial, int stock, int puntoPedido, Proveedor proveedor,
			float costo) {
		super();
		this.codigoMaterial = getProxCodigoMaterial();
		this.nombreMaterial = nombreMaterial;
		this.stock = stock;
		this.puntoPedido = puntoPedido;
		this.proveedor = proveedor;
		this.costo = costo;
		this.estado = Estado.HA;
		
		MaterialDao.getInstancia().insert(this);
	}
	
	public static int getProxCodigoMaterial() {
		proxCodigoMaterial = MaterialDao.getInstancia().getMaxValue("Material", "codigoMaterial");
		
		return ++proxCodigoMaterial;
	}

	public MaterialView getView() {
		return new MaterialView(this.codigoMaterial, this.nombreMaterial, this.stock, this.puntoPedido, 
				this.proveedor.getRazonSocial(), this.costo, this.estado.toString());
	}

	public int getCodigoMaterial() {
		return codigoMaterial;
	}
	
	public void setCodigoMaterial(int codigoMaterial) {
		this.codigoMaterial = codigoMaterial;
	}

	public String getNombreMaterial() {
		return nombreMaterial;
	}

	public void setNombreMaterial(String nombreMaterial) {
		this.nombreMaterial = nombreMaterial;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getPuntoPedido() {
		return puntoPedido;
	}

	public void setPuntoPedido(int puntoPedido) {
		this.puntoPedido = puntoPedido;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public boolean existeStockDisponible(int cantidad) {
		int stockActual = this.getStock();
		
		if (cantidad <= stockActual) {
			return true;
		}
		
		return false;
	}
	
	public void actualizarStock(int cantidad) {
		this.setStock(this.getStock() - cantidad);
		
		MaterialDao.getInstancia().update(this);
	}
	
	// Dao methods

	public void modificarMaterial(String nombreMaterialModficado, int stock, int puntoPedido, float costo) {
		this.setNombreMaterial(nombreMaterialModficado);
		this.setStock(stock);
		this.setPuntoPedido(puntoPedido);
		this.setCosto(costo);
		
		MaterialDao.getInstancia().update(this);
		
	}

	public void deleteMaterial() {
		MaterialDao.getInstancia().delete(this);
	}
	
	public List<Object> buscarAllMateriales() {
		return MaterialDao.getInstancia().readAll();
	}
	
	public Material buscarMaterial(String nombreMaterial) {
		return MaterialDao.getInstancia().read(nombreMaterial);
	}

	public Material buscarMaterial(int codigoMaterial) {
		return MaterialDao.getInstancia().read(codigoMaterial);
	}

}
