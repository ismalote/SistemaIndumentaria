package main.java.vista;

public class PedidoItemView {
		
	private MaterialView material;
	private int cantidad;

	public PedidoItemView(MaterialView material, int cantidad) {
		this.material = material;
		this.cantidad = cantidad;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	public MaterialView getMaterial() {
		return material;
	}
	public float getSubTotal(){
		return material.getCosto() * cantidad;
	}
}
