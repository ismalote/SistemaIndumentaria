package main.java.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.model.Cliente;
import main.java.model.Material;
import main.java.model.Prenda;
import main.java.model.Venta;
import main.java.model.VentaItem;

public class VentaDao extends PersistenciaDao{

	private static VentaDao instancia;
	
	private VentaDao() {
		
	}
	
	public static VentaDao getInstancia() {
		if (instancia == null) {
			instancia = new VentaDao();
		}
		
		return instancia;
	}
	
	@Override
	public void insert(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Venta v = (Venta)o;
			PreparedStatement s = con.prepareStatement("insert into Venta values (?,?,?)");
			//agregar campos
			s.setTimestamp(1, new Timestamp(new Date().getTime()) );
			s.setInt(2, v.getCliente().getCodigoCliente());
			s.setInt(3, v.getEstado());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch (SQLException e) {
			System.out.println("Mensaje Error al insertar una Venta: " + e.getMessage());
			System.out.println("Stack Trace al insertar una Venta: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}
	
	public void insertItemVenta(Venta v, VentaItem it) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		try {
			PreparedStatement s = con.prepareStatement("insert into venta_item values (?,?,?)");
			//agregar campos
			s.setInt(1, it.getPrenda().getCodigoPrenda());
			s.setInt(2, v.getNroVenta());
			s.setInt(3, it.getCantidad());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al insertar un Item Venta: " + e.getMessage());
			System.out.println("Stack Trace al insertar un Item Venta: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void update(Object o) {
		// LA UNICA COSA QUE SE MODIFICA DE LA VENTA ES EL ESTADO
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		try {
			Venta v = (Venta) o;
			PreparedStatement s = con.prepareStatement("update venta " +
					"set estado = ? " +					
					"where nroVenta = ? ");
			s.setInt(1, v.getEstado());
			s.setFloat(2, v.getNroVenta());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al modificar Venta: " + e.getMessage());
			System.out.println("Stack Trace al modificar Venta: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void delete(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Venta v = (Venta)o;
			PreparedStatement s = con.prepareStatement("delete venta_item " +
					"where venta_nroVenta = ? ");
			s.setInt(1, v.getNroVenta());
			s.execute();
			s = con.prepareStatement("delete venta " +
					"where nroVenta = ? ");
			s.setInt(1, v.getNroVenta());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al eliminar la Venta: " + e.getMessage());
			System.out.println("Stack Trace al eliminar la Venta: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public List<Object> readAll() {
		List<Object> resultado = new ArrayList<>();
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * From venta");
			
			while (rs.next()) {
				Venta venta = new Venta();
				
				// Agrego las prendas a la venta
				
				PreparedStatement ps = con.prepareStatement("SELECT * FROM venta_item where VENTA_nroVenta = ? ");
				ps.setInt(1,rs.getInt("nroVenta"));
				ResultSet resultVI = ps.executeQuery();
				while(resultVI.next()) {
					Prenda pre = PrendaConTemporadaDao.getInstancia().read(resultVI.getInt("PRENDA_codigoPrenda"));
					
					if(pre == null) {
						pre = PrendaSinTemporadaDao.getInstancia().read(resultVI.getInt("PRENDA_codigoPrenda"));
					}
					
					venta.agregarItem(pre, resultVI.getInt("cantidad"));
				}
				
				// Agrego resto de los datos
				venta.setNroVenta(rs.getInt("nroVenta"));
				venta.setFecha(rs.getDate("fecha"));
				venta.setEstado(rs.getInt("estado"));
				
				Cliente cli = ClienteDao.getInstancia().read(rs.getInt("CLIENTE_codigoCliente"));
				
				venta.setCliente(cli);
				
				resultado.add(venta);
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al leer todas las Ventas: " + e.getMessage());
			System.out.println("Stack Trace al leer todas las Ventas: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
		
		return resultado;
	}

	public Venta read(int nroVenta) {
		Venta v = null; 
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try
		{
			String senten = "SELECT * from venta where nroVenta = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setInt(1,nroVenta);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				
				v = new Venta();
				
				// Agrego las prendas a la venta
				
				senten = "SELECT * FROM venta_item where VENTA_nroVenta = ? ";
				ps = con.prepareStatement(senten);
				ps.setInt(1,result.getInt("nroVenta"));
				ResultSet resultVI = ps.executeQuery();
				while(resultVI.next()) {
					Prenda pre = PrendaConTemporadaDao.getInstancia().read(resultVI.getInt("PRENDA_codigoPrenda"));
					
					if(pre == null) {
						pre = PrendaSinTemporadaDao.getInstancia().read(resultVI.getInt("PRENDA_codigoPrenda"));
					}
					
					v.agregarItem(pre, resultVI.getInt("cantidad"));
				}
				
				// Agrego resto de los datos
				v.setNroVenta(result.getInt(nroVenta));
				v.setFecha(result.getDate("fecha"));
				v.setEstado(result.getInt("estado"));
				
				Cliente cli = ClienteDao.getInstancia().read(result.getInt("CLIENTE_codigoCliente"));
				
				v.setCliente(cli);
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return v;
		}catch(SQLException e) {
				System.out.println("Mensaje Error al leer la Venta: " + e.getMessage());
				System.out.println("Stack Trace al leer la Venta: " + e.getStackTrace());
				PoolConnection.getInstancia().liberarConexion(con);
		}
	    return v;
	}

}
