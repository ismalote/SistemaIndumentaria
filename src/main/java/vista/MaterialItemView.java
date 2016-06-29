package main.java.vista;

public class MaterialItemView {
	
	private MaterialView material;
	private int cantidad;
	
	public MaterialItemView(MaterialView material, int cantidad) {
		this.material = material;
		this.cantidad = cantidad;		
	}

	public MaterialView getMaterial() {
		return material;
	}

	public int getCantidad() {
		return cantidad;
	}
}
