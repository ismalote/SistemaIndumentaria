package main.java.vista;

import java.util.ArrayList;
import java.util.List;

public class PedidoView {
	
	private long numeroPedido;
	private ProveedorView proveedor;
	private List<PedidoItemView> listaItem;
	private float total;

	public PedidoView(long numeroPedido, ProveedorView proveedor) {
		this.numeroPedido = numeroPedido;
		this.proveedor = proveedor;
		
		this.listaItem = new ArrayList<PedidoItemView>();
		this.total = 0f;
	}

	public void agregarPedidoItem(PedidoItemView item){
		listaItem.add(item);		
		total = total + item.getSubTotal();
	}
	
	public List<PedidoItemView> getListaItem() {
		return listaItem;
	}
	public long getNumeroPedido() {
		return numeroPedido;
	}
	public ProveedorView getProveedor() {
		return proveedor;
	}
	public float getTotal() {
		return total;
	}
}
