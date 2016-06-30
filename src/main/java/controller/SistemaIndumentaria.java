package main.java.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.enums.Estado;
import main.java.enums.ExitCodes;
import main.java.model.Cliente;
import main.java.model.Material;
import main.java.model.Prenda;
import main.java.model.PrendaConTemporada;
import main.java.model.PrendaConjunto;
import main.java.model.PrendaSinTemporada;
import main.java.model.Proveedor;
import main.java.model.Temporada;
import main.java.model.Venta;
import main.java.observer.Observable;
import main.java.persistencia.PoolConnection;
import main.java.util.UtilDB;
import main.java.util.UtilLog;
import main.java.vista.MaterialView;
import main.java.vista.PedidoView;
import main.java.vista.PrendaConTemporadaView;
import main.java.vista.PrendaView;
import main.java.vista.TemporadaView;

public class SistemaIndumentaria extends Observable {
	
	private static SistemaIndumentaria instancia;	
	
	private List<Material> materiales;
	private List<Proveedor> proveedores;
	private List<Prenda> prendas;
	private List<PrendaConTemporada> prendasConTemporada;
	private List<PrendaSinTemporada> prendasSinTemporada;
	private List<PrendaConjunto> prendasConjunto;
	private List<Cliente> clientes;
	private List<Venta> ventas;
	private List<Temporada> temporadas;
	
	public static SistemaIndumentaria getInstancia() {
		if(instancia == null) {
			instancia = new SistemaIndumentaria();
		}
		return instancia;
	}
	
	private SistemaIndumentaria() {	
		inicializarListas();		
	}	

	private void inicializarListas() {
		materiales = new ArrayList<>();
		proveedores = new ArrayList<>();
		prendas = new ArrayList<>();
		prendasConTemporada = new ArrayList<>();
		prendasSinTemporada = new ArrayList<>();
		prendasConjunto = new ArrayList<>();
		clientes = new ArrayList<>();
		ventas = new ArrayList<>();
		temporadas = new ArrayList<>();			
	}

	public void cargarDatosIniciales() {		
		if (UtilDB.comprobarConexion()){
			ejecutarScriptBD();					
		}
	}
	
	private void ejecutarScriptBD() {		
		UtilLog.log("EJECUTA EL SCRIPT INICIAL BD");
		String direccion_archivo = "";
		
		switch (UtilDB.getTipoConexionBD()) {
		case 1: // SQLSERVER
			direccion_archivo = "src/main/resources/create_table_sqlserver.sql";
			break;
		case 2: // MYSQL
			direccion_archivo = "src/main/resources/create_table_mysql.sql";
			break;
		}
		UtilDB.ejecutarScriptSQL(direccion_archivo);		
		UtilLog.log("EJECUTO EL SCRIPT INICIAL BD CON EXITO");
		
		
		UtilLog.log("EJECUTA EL SCRIPT DATOS BD");
		direccion_archivo = "";
		
		switch (UtilDB.getTipoConexionBD()) {
		case 1: // SQLSERVER
			direccion_archivo = "src/main/resources/insert_table_sqlserver.sql";
			break;
		case 2: // MYSQL
			direccion_archivo = "src/main/resources/insert_table_mysql.sql";
			break;
		}
		UtilDB.ejecutarScriptSQL(direccion_archivo);		
		UtilLog.log("EJECUTO EL SCRIPT DATOS BD CON EXITO");
		
	}

	// ABM Clientes
	
	public int altaCliente(long dni, String nombre) {
		Cliente cli = buscarCliente(dni);
		
		if(cli == null) {
			cli = new Cliente(dni, nombre);
			clientes.add(cli);
			return ExitCodes.OK;
		}
		return ExitCodes.YA_EXISTE_ENTIDAD;
	}

	public int modificarCliente(long dni, String nombre) {
		Cliente cli = buscarCliente(dni);
		
		if(cli != null) {
			cli.modificarCliente(nombre);
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public int bajaCliente(long dni) {
		Cliente cli = buscarCliente(dni);
		
		if(cli != null) {
			cli.deleteCliente();
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public Cliente buscarCliente(long dni) {
		Cliente cli = new Cliente();
		boolean existe = false;
		int i=0;
		while(!existe && i<clientes.size()) {
			cli = clientes.get(i);
			if(cli.getDni() == dni) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return cli;
		}else {
			cli = cli.buscarCliente(dni);
			if(cli != null) {
				clientes.add(cli);
			}
		}
		
		return cli;
	}
	
	public Cliente buscarCliente(int codigoCliente) {
		Cliente cli = new Cliente();
		boolean existe = false;
		int i=0;
		while(!existe && i<clientes.size()) {
			cli = clientes.get(i);
			if(cli.getCodigoCliente() == codigoCliente) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return cli;
		}else {
			cli = cli.buscarCliente(codigoCliente);
			if(cli != null) {
				clientes.add(cli);
			}
		}
		
		return cli;
	}
	
	// ABM Materiales
	
	public int altaMaterial(String nombreMaterial, int stock, int puntoPedido, String cuitProveedor, float costo) {
		Material mat = buscarMaterial(nombreMaterial);
		
		if(mat == null) {
			Proveedor prov = buscarProveedor(cuitProveedor);
			if(prov != null) {
				mat = new Material(nombreMaterial, stock, puntoPedido, prov, costo);
			}else {
				return ExitCodes.NO_EXISTE_PROVEEDOR;
			}
			materiales.add(mat);
			return ExitCodes.OK;
		}
		return ExitCodes.YA_EXISTE_ENTIDAD;
	}

	public int modificarMaterial(String nombreMaterialACambiar, String nombreMaterialModficado, int stock, int puntoPedido, float costo) {
		Material mat = buscarMaterial(nombreMaterialACambiar);
		
		if(mat != null) {
			mat.modificarMaterial(nombreMaterialModficado, stock, puntoPedido, costo);
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public int bajaMaterial(String nombreMaterial) {
		Material mat = buscarMaterial(nombreMaterial);
		
		if(mat != null) {
			mat.deleteMaterial();
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public Material buscarMaterial(String nombreMaterial) {
		Material mat = new Material();
		boolean existe = false;
		int i=0;
		while(!existe && i<materiales.size()) {
			mat = materiales.get(i);
			if(mat.getNombreMaterial().toUpperCase().contentEquals(nombreMaterial.toUpperCase())) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return mat;
		}else {
			mat = mat.buscarMaterial(nombreMaterial);
			if(mat != null) {
				materiales.add(mat);
			}
		}
		
		return mat;
	}
	
	public Material buscarMaterial(int codigoMaterial) {
		Material mat = new Material();
		boolean existe = false;
		int i=0;
		while(!existe && i<materiales.size()) {
			mat = materiales.get(i);
			if(mat.getCodigoMaterial() == codigoMaterial) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return mat;
		}else {
			mat = mat.buscarMaterial(codigoMaterial);
			if(mat != null) {
				materiales.add(mat);
			}
		}
		
		return mat;
	}
	
	// ABM prenda conjunto
	
	public int altaPrendaConjunto(String nombre, float descuento) {
		PrendaConjunto prenda = buscarPrendaConjunto(nombre);
		
		if(prenda == null) {
			prenda = new PrendaConjunto(nombre, descuento);
			prendas.add(prenda);
			prendasConjunto.add(prenda);
			return ExitCodes.OK;
		}
		return ExitCodes.YA_EXISTE_ENTIDAD;
	}
	
	public int agregarItemPrendaConjunto(String nombrePrendaConjunto, String nombrePrendaAdd) {
		PrendaConjunto prendaConjunto = buscarPrendaConjunto(nombrePrendaConjunto);
		
		if(prendaConjunto != null) {
			Prenda prenda = buscarPrenda(nombrePrendaAdd);
			if(prenda != null) {
				prendaConjunto.agregarPrenda(prenda);
			}
			return ExitCodes.OK;
		}
		return ExitCodes.YA_EXISTE_ENTIDAD;
	}
	
	public void modificarPrendaConjunto(String nombrePrendaACambiar, String nombrePrendaModificado, float descuento) {
		PrendaConjunto prenda = buscarPrendaConjunto(nombrePrendaACambiar);
		
		if(prenda != null) {
			prenda.modificarPrenda(nombrePrendaModificado, descuento);
		}
	}
	
	public void bajaPrendaConjunto(String nombrePrenda) {
		PrendaConjunto prenda = buscarPrendaConjunto(nombrePrenda);
		
		if(prenda != null) {
			prenda.deletePrenda();
		}
	}
	
	public PrendaConjunto buscarPrendaConjunto(String nombrePrenda) {
		PrendaConjunto pre = new PrendaConjunto();
		boolean existe = false;
		int i=0;
		while(!existe && i<prendasConjunto.size()) {
			pre = prendasConjunto.get(i);
			if(pre.getNombrePrenda().toUpperCase().contentEquals(nombrePrenda.toUpperCase())) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return pre;
		}else {
			pre = pre.buscarPrendaConjunto(nombrePrenda);
			if(pre != null) {
				prendasConjunto.add(pre);
			}
		}
		
		return pre;
	}
	
	public PrendaConjunto buscarPrendaConjunto(int codigoPrenda) {
		PrendaConjunto pre = new PrendaConjunto();
		boolean existe = false;
		int i=0;
		while(!existe && i<prendasConjunto.size()) {
			pre = prendasConjunto.get(i);
			if(pre.getCodigoPrenda() == codigoPrenda) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return pre;
		}else {
			pre = pre.buscarPrendaConjunto(codigoPrenda);
			if(pre != null) {
				prendasConjunto.add(pre);
			}
		}
		
		return pre;
	}
	
	// ABM prenda con temporada
	
	public int altaPrendaConTemporada(String nombre, int stock, float porcentajeIncremento, int idtemporada) {
		Temporada t = new Temporada();
		t = t.buscarTemporada(idtemporada);
		
		return altaPrendaConTemporada(nombre, stock, porcentajeIncremento, t);
	}
	
	public int altaPrendaConTemporada(String nombre, int stock, float porcentajeIncremento, Temporada temporada) {
		PrendaConTemporada prenda = buscarPrendaConTemporada(nombre);
		
		if(prenda == null) {
			prenda = new PrendaConTemporada(nombre, stock, porcentajeIncremento, temporada);
			prendas.add(prenda);
			prendasConTemporada.add(prenda);
			return ExitCodes.OK;
		}
		return ExitCodes.YA_EXISTE_ENTIDAD;
	}
	
	public int agregarItemPrendaConTemporada(String nombrePrenda, String nombreMaterial, int cantidad) {
		PrendaConTemporada prenda = buscarPrendaConTemporada(nombrePrenda);
		
		if(prenda != null) {
			Material mat = buscarMaterial(nombreMaterial);
			
			prenda.agregarMaterial(mat, cantidad);
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public int modificarPrendaConTemporada(int codigoPrenda, String nombrePrendaModificado, int stock, float porcentajeIncremento) {
		PrendaConTemporada prenda = buscarPrendaConTemporada(codigoPrenda);
		
		if(prenda != null) {
			prenda.modificarPrenda(nombrePrendaModificado, stock, porcentajeIncremento);
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public int bajaPrendaConTemporada(int codigoPrenda) {
		PrendaConTemporada prenda = buscarPrendaConTemporada(codigoPrenda);
		
		if(prenda != null) {
			prenda.deletePrenda();
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public PrendaConTemporada buscarPrendaConTemporada(String nombrePrenda) {
		PrendaConTemporada pre = new PrendaConTemporada();
		boolean existe = false;
		int i=0;
		while(!existe && i<prendasConTemporada.size()) {
			pre = prendasConTemporada.get(i);
			if(pre.getNombrePrenda().toUpperCase().contentEquals(nombrePrenda.toUpperCase())) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return pre;
		}else {
			pre = pre.buscarPrendaConTemporada(nombrePrenda);
			if(pre != null) {
				prendasConTemporada.add(pre);
			}
		}
		
		return pre;
	}
	
	public PrendaConTemporada buscarPrendaConTemporada(int codigoPrenda) {
		PrendaConTemporada pre = new PrendaConTemporada();
		boolean existe = false;
		int i=0;
		while(!existe && i<prendasConTemporada.size()) {
			pre = prendasConTemporada.get(i);
			if(pre.getCodigoPrenda() == codigoPrenda) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return pre;
		}else {
			pre = pre.buscarPrendaConTemporada(codigoPrenda);
			if(pre != null) {
				prendasConTemporada.add(pre);
			}
		}
		
		return pre;
	}
	
	// ABM prenda sin temporada
	
	public int altaPrendaSinTemporada(String nombrePrenda, int stock) {
		PrendaSinTemporada prenda = buscarPrendaSinTemporada(nombrePrenda);
		
		if(prenda == null) {
			prenda = new PrendaSinTemporada(nombrePrenda, stock);
			prendas.add(prenda);
			prendasSinTemporada.add(prenda);
			return ExitCodes.OK;
		}
		
		return ExitCodes.YA_EXISTE_ENTIDAD;
		
	}
	
	public int agregarItemPrendaSinTemporada(String nombrePrenda, String nombreMaterial, int cantidad) {
		PrendaSinTemporada prenda = buscarPrendaSinTemporada(nombrePrenda);
		
		if(prenda != null) {
			Material mat = buscarMaterial(nombreMaterial);
			
			prenda.agregarMaterial(mat, cantidad);
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public int modificarPrendaSinTemporada(String nombrePrendaACambiar, String nombrePrendaModificado, int stock) {
		PrendaSinTemporada prenda = buscarPrendaSinTemporada(nombrePrendaACambiar);
		
		if(prenda != null) {
			prenda.modificarPrenda(nombrePrendaModificado, stock);
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public int bajaPrendaSinTemporada(String nombrePrenda) {
		PrendaSinTemporada prenda = buscarPrendaSinTemporada(nombrePrenda);
		
		if(prenda != null) {
			prenda.deletePrenda();
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public PrendaSinTemporada buscarPrendaSinTemporada(String nombrePrenda) {
		PrendaSinTemporada pre = new PrendaSinTemporada();
		boolean existe = false;
		int i=0;
		while(!existe && i<prendasSinTemporada.size()) {
			pre = prendasSinTemporada.get(i);
			if(pre.getNombrePrenda().toUpperCase().contentEquals(nombrePrenda.toUpperCase())) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return pre;
		}else {
			pre = pre.buscarPrendaSinTemporada(nombrePrenda);
			if(pre != null) {
				prendasSinTemporada.add(pre);
			}
		}
		
		return pre;
	}
	
	public PrendaSinTemporada buscarPrendaSinTemporada(int codigoPrenda) {
		PrendaSinTemporada pre = new PrendaSinTemporada();
		boolean existe = false;
		int i=0;
		while(!existe && i<prendasSinTemporada.size()) {
			pre = prendasSinTemporada.get(i);
			if(pre.getCodigoPrenda() == codigoPrenda) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return pre;
		}else {
			pre = pre.buscarPrendaSinTemporada(codigoPrenda);
			if(pre != null) {
				prendasSinTemporada.add(pre);
			}
		}
		
		return pre;
	}
	
	// Busqueda de prenda entre todos los tipos
	
	public Prenda buscarPrenda(String nombrePrenda) {
		Prenda pre = null;
		pre = buscarPrendaConTemporada(nombrePrenda);
		if(pre == null) {
			pre = buscarPrendaSinTemporada(nombrePrenda);
		}
		if(pre == null) {
			pre = buscarPrendaConjunto(nombrePrenda);
		}
		return pre;
	}
	
	public Prenda buscarPrenda(int codigoPrenda) {
		Prenda pre = null;
		pre = buscarPrendaConTemporada(codigoPrenda);
		if(pre == null) {
			pre = buscarPrendaSinTemporada(codigoPrenda);
		}
		if(pre == null) {
			pre = buscarPrendaConjunto(codigoPrenda);
		}
		return pre;
	}

	// ABM Proveedor
	
	public int altaProveedor(String cuit, String razonSocial) {
		Proveedor pro = buscarProveedor(cuit);
		
		if(pro == null) {
			pro = new Proveedor(cuit, razonSocial);
			proveedores.add(pro);
			return ExitCodes.OK;
		}
		return ExitCodes.YA_EXISTE_ENTIDAD;
	}

	public int modificarProveedor(String cuit, String razonSocial) {
		Proveedor pro = buscarProveedor(cuit);
		
		if(pro != null) {
			pro.modificarProveedor(razonSocial);
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public int bajaProveedor(String cuit) {
		Proveedor pro = buscarProveedor(cuit);
		
		if(pro != null) {
			pro.deleteProveedor();
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public Proveedor buscarProveedor(String cuit) {
		Proveedor pro = new Proveedor();
		boolean existe = false;
		int i=0;
		while(!existe && i<proveedores.size()) {
			pro = proveedores.get(i);
			if(pro.getCuit().toUpperCase().contentEquals(cuit.toUpperCase())) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return pro;
		}else {
			pro = pro.buscarProveedor(cuit);
			if(pro != null) {
				proveedores.add(pro);
			}
		}
		
		return pro;
	}
	
	// ABM Temporada
	
	public int altaTemporada(String nombreTemporada, Date fechaFinalizacion) {
		Temporada temp = buscarTemporada(nombreTemporada);
		
		if(temp == null) {
			temp = new Temporada(nombreTemporada, fechaFinalizacion);
			temporadas.add(temp);
			return ExitCodes.OK;
		}
		return ExitCodes.YA_EXISTE_ENTIDAD;
	}

	public int modificarTemporada(String nombreTemporada, Date fechaFinalizacion) {
		Temporada temp = buscarTemporada(nombreTemporada);
		
		if(temp != null) {
			temp.modificarTemporada(fechaFinalizacion);
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public int bajaTemporada(String nombreTemporada) {
		Temporada temp = buscarTemporada(nombreTemporada);
		
		if(temp != null) {
			temp.deleteTemporada();
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public Temporada buscarTemporada(String nombreTemporada) {
		Temporada temp = new Temporada();
		boolean existe = false;
		int i=0;
		while(!existe && i<temporadas.size()) {
			temp = temporadas.get(i);
			if(temp.getNombreTemporada().toUpperCase().contentEquals(nombreTemporada.toUpperCase())) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return temp;
		}else {
			temp = temp.buscarTemporada(nombreTemporada);
			if(temp != null) {
				temporadas.add(temp);
			}
		}
		
		return temp;
	}
	
	public Temporada buscarTemporada(int codigoTemporada) {
		Temporada temp = new Temporada();
		boolean existe = false;
		int i=0;
		while(!existe && i<temporadas.size()) {
			temp = temporadas.get(i);
			if(temp.getCodigoTemporada() == codigoTemporada) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return temp;
		}else {
			temp = temp.buscarTemporada(codigoTemporada);
			if(temp != null) {
				temporadas.add(temp);
			}
		}
		
		return temp;
	}
		
	// Venta
	
	public int generarVenta(int codigoCliente) {
		Cliente cli = buscarCliente(codigoCliente);
		
		if (cli != null) {
			Venta vta = new Venta(cli);
			ventas.add(vta);
			
			return vta.getNroVenta();
		}
		
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public int agregarItemVenta(int nroVenta, String nombrePrenda, int cantidad) {
		Venta vta = buscarVenta(nroVenta);
		
		if (vta != null) {
			Prenda pre = buscarPrenda(nombrePrenda);
			
			if(pre != null) {
				if(pre.verificarStock(cantidad)) {
					vta.agregarItem(pre, cantidad);
					return ExitCodes.OK;
				}
				return ExitCodes.NO_HAY_STOCK_SUFICIENTE;
			}else {
				return ExitCodes.NO_EXISTE_PRENDA;
			}
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public float cerrarVenta(int nroVenta) {
		Venta vta = buscarVenta(nroVenta);
		
		if (vta != null) {
			vta.cerrarVenta();
			
			return vta.calcularTotal();
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public void rechazarVenta(int nroVenta){
		Venta vta = buscarVenta(nroVenta);
		
		if (vta != null) {
			vta.rechazarVenta();					
		}
	}
	
	public int eliminarVenta(int nroVenta) {
		Venta vta = buscarVenta(nroVenta);
		
		if (vta != null) {
			vta.deleteVenta();
			return ExitCodes.OK;
		}
		return ExitCodes.NO_EXISTE_ENTIDAD;
	}
	
	public Venta buscarVenta(int nroVenta) {
		Venta venta = new Venta();
		boolean existe = false;
		int i=0;
		while(!existe && i<ventas.size()) {
			venta = ventas.get(i);
			if(venta.getNroVenta() == nroVenta) {
				existe = true;
			}
			i++;
		}
		if(existe) {
			return venta;
		}else {
			venta = venta.buscarVenta(nroVenta);
			if(venta != null) {
				ventas.add(venta);
			}
		}
		
		return venta;
	}
	
	
	public PrendaConTemporadaView getPrendaConTemporada(String nombre)
	{
		PrendaConTemporada c = buscarPrendaConTemporada(nombre);
		if (c!=null)
			return c.getViewPCT();
		else
			return null;
		
	}

	public void salirAplicativo() {
		
		PoolConnection.getInstancia().cerrarConexiones();
		
		System.exit(0);
	}

	public List<TemporadaView> obtenerTemporadas() {
		List<TemporadaView> lista = new ArrayList<TemporadaView>();
		Temporada t = new Temporada();
		
		for (Object o : t.buscarAllTemporadas()){			
			lista.add(((Temporada) o).getView());
		}
		return lista;
	}

	public List<MaterialView> obtenerMaterialesActivos() {
		List<MaterialView> lista = new ArrayList<>();
		Material m = new Material();
		
		for (Object o : m.buscarAllMateriales()){
			Material material = (Material) o;
			if (material.getEstado() == Estado.HA){
				lista.add(material.getView());	
			}			
		}
		return lista;
	}

	public PrendaConTemporadaView buscarPrendaConTemporadaView(int codigoPrenda) {
		PrendaConTemporadaView pV = null;
		PrendaConTemporada prenda = buscarPrendaConTemporada(codigoPrenda);
		if (prenda != null){
			pV = prenda.getViewPCT();
		}
		return pV;
	}

	public List<PrendaView> obtenerPrendasActivos() {
		List<PrendaView> lista = new ArrayList<>();
		PrendaSinTemporada p1 = new PrendaSinTemporada();
		PrendaConTemporada p2 = new PrendaConTemporada();
		PrendaConjunto p3 = new PrendaConjunto();
		
		for (Object o : p1.buscarAllPrendasSinTemporada()){
			PrendaSinTemporada p = (PrendaSinTemporada) o;
			if (p.getEstado() == Estado.HA){
				lista.add(p.getView());	
			}			
		}
		
		for (Object o : p2.buscarAllPrendasConTemporada()){
			PrendaConTemporada p = (PrendaConTemporada) o;
			if (p.getEstado() == Estado.HA){
				lista.add(p.getView());	
			}			
		}
		
		for (Object o : p3.buscarAllPrendasConjunto()){
			PrendaConjunto p = (PrendaConjunto) o;
			if (p.getEstado() == Estado.HA){
				lista.add(p.getView());	
			}			
		}
		return lista;
	}

	public List<PedidoView> generarPedidoCompra() {
		List<PedidoView> pedidos = new ArrayList<PedidoView>();
		
		return pedidos;
	}
}
