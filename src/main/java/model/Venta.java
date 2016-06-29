package main.java.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.persistencia.VentaDao;
import main.java.vista.VentaItemView;
import main.java.vista.VentaView;

public class Venta {
	private static int proxNroVenta;
	
	private int nroVenta;
	private Date fecha;
	private Cliente cliente;
	private List<VentaItem> items;	
	/**
	 * PARA 0 : NUEVO / CREADO
	 * PARA 1 : FINALIZADO - SE CONFIRMO LA VENTA
	 * PARA 2 : RECHAZADO - NO SE CONFIRMO LA VENTA
	 */
	private Integer estado;
	
	public Venta() {
		
	}
	
	public Venta(Cliente cliente) {
		super();
		this.nroVenta = getProxNroVenta();
		this.fecha = new Date();
		this.cliente = cliente;
		this.items = new ArrayList<>();
		this.estado = VentaView.ESTADO_NUEVO;
		
		VentaDao.getInstancia().insert(this);
	}
	
	private static int getProxNroVenta() {
		proxNroVenta = VentaDao.getInstancia().getMaxValue("venta", "nroVenta");
		
		return ++proxNroVenta;
	}

	public VentaView getView() {
		List<VentaItemView> ventaItems = new ArrayList<>();
		for(VentaItem vi : items) {
			ventaItems.add(vi.getView());
		}
		return new VentaView(this.fecha.toString(), this.cliente.getNombre(), ventaItems, this.estado);
	}

	public int getNroVenta() {
		return nroVenta;
	}

	public void setNroVenta(int nroVenta) {
		this.nroVenta = nroVenta;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public List<VentaItem> getItems() {
		return items;
	}

	public void agregarItem(Prenda prenda, int cantidad) {
		VentaItem item = new VentaItem(prenda, cantidad);
		
		VentaDao.getInstancia().insertItemVenta(this, item);
		
		items.add(item);
	}
	
	public boolean esVenta(int nroVenta) {
		return this.nroVenta == nroVenta;
	}
	
	public float calcularTotal() {
		float total = 0;
		
		for (VentaItem item : items) {
			total = total + item.calcularSubTotal();
		}
		
		return total;
	}
	
	public void rechazarVenta(){
		setEstado(VentaView.ESTADO_RECHAZADO);
		
		VentaDao.getInstancia().update(this);
	}
	
	public void cerrarVenta() {
		
		for (VentaItem itemVenta : items) {
			Prenda p = itemVenta.getPrenda();
			int stockActualizado = (p.getStock()-itemVenta.getCantidad());
			p.actualizarStock(stockActualizado);
		}
		setEstado(VentaView.ESTADO_FINALIZADO);
		VentaDao.getInstancia().update(this);
	}
	
	// Dao Methods

	public void deleteVenta() {
		VentaDao.getInstancia().delete(this);
	}
	
	public List<Object> buscarAllVentas() {
		return VentaDao.getInstancia().readAll();
	}
	
	public Venta buscarVenta(int nroVenta) {
		return VentaDao.getInstancia().read(nroVenta);
	}
}