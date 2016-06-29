package main.java.model;

import main.java.vista.MaterialItemView;

public class MaterialItem {
	private Material material;
	private int cantidad;
	
	public MaterialItem(Material material, int cantidad) {
		super();
		this.material = material;
		this.cantidad = cantidad;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public MaterialItemView getView() {
		return new MaterialItemView(this.material.getView(), this.cantidad);
	}
}
