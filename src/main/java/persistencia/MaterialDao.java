package main.java.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.enums.Estado;
import main.java.model.Material;
import main.java.model.Proveedor;

public class MaterialDao extends PersistenciaDao{
	private static MaterialDao instancia;
	
	private MaterialDao() {
		
	}
	
	public static MaterialDao getInstancia() {
		if (instancia == null) {
			instancia = new MaterialDao();
		}
		
		return instancia;
	}
	
	@Override
	public void insert(Object o) {
		
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		try {
			Material a = (Material)o;
			PreparedStatement s = con.prepareStatement("insert into Material values (?,?,?,?,?,?)");
			//agregar campos
			s.setString(1, a.getNombreMaterial());
			s.setInt(2,a.getStock());
			s.setInt(3, a.getPuntoPedido());
			s.setString(4, a.getProveedor().getCuit());
			s.setFloat(5, a.getCosto());
			s.setString(6, a.getEstado().toString());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al insertar Material: " + e.getMessage());
			System.out.println("Stack Trace al insertar Material: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void update(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		try {
			Material a = (Material)o;
			PreparedStatement s = con.prepareStatement("update material " +
					"set nombreMaterial = ?,"
					+ " stock = ?,"
					+ " puntoPedido = ?,"
					+ " costo = ? " +
					"where codigoMaterial = ? ");
			s.setString(1, a.getNombreMaterial());
			s.setInt(2,a.getStock());
			s.setInt(3, a.getPuntoPedido());
			s.setFloat(4, a.getCosto());
			s.setFloat(5, a.getCodigoMaterial());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al modificar Material: " + e.getMessage());
			System.out.println("Stack Trace al modificar Material: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void delete(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		try {
			Material a = (Material)o;	
			PreparedStatement s = con.prepareStatement("update Material " +
					"set estado = 'BA' " +
					"where codigoMaterial = ?)");
			s.setFloat(1, a.getCodigoMaterial());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al eliminar Material: " + e.getMessage());
			System.out.println("Stack Trace al eliminar Material: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	public List<Object> readAll() {
		List<Object> resultado = new ArrayList<Object>();
		
		try {
			Connection cn = PoolConnection.getInstancia().obtenerConexion();
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("Select * From Material");
			
			while (rs.next()) {
				Proveedor pro = ProveedorDao.getInstancia().read(rs.getString("PROVEEDOR_cuit"));
				
				Material mat = new Material();
				mat.setCodigoMaterial(rs.getInt("codigoMaterial"));
				mat.setNombreMaterial(rs.getString("nombreMaterial"));
				mat.setEstado(Estado.valueOf(rs.getString("estado")));
				mat.setStock(rs.getInt("stock"));
				mat.setPuntoPedido(rs.getInt("puntoPedido"));
				mat.setCosto(rs.getFloat("costo"));
				mat.setProveedor(pro);
				
				resultado.add(mat);
			}
			
			PoolConnection.getInstancia().liberarConexion(cn);;
		} catch (Exception ex) {
			System.out.println("Mensaje Error: " + ex.getMessage());
			System.out.println("Stack Trace: " + ex.getStackTrace());
		}
		
		return resultado;
	}

	public Material read(String nombreMaterial) {
		Material m = null; 
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try
		{
			String senten = "SELECT * from Material where nombreMaterial = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setString(1, nombreMaterial);
			ResultSet result = ps.executeQuery();
			while (result.next())
			{
				m = new Material();
				m.setCodigoMaterial(result.getInt("codigoMaterial"));
				m.setNombreMaterial(result.getString("nombreMaterial"));
				m.setEstado(Estado.valueOf(result.getString("estado")));
				m.setStock(result.getInt("stock"));
				m.setPuntoPedido(result.getInt("puntoPedido"));
				
				Proveedor prov = ProveedorDao.getInstancia().read(result.getString("PROVEEDOR_cuit"));
				
				m.setCosto(result.getFloat("costo"));
				m.setProveedor(prov);
				
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return m;
		}catch(SQLException e) {
				System.out.println("Mensaje Error al leer Material: " + e.getMessage());
				System.out.println("Stack Trace al leer Material: " + e.getStackTrace());
				PoolConnection.getInstancia().liberarConexion(con);
		}
	    return m;
	}
	
	public Material read(int codigoMaterial) {
		Material m = null; 
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try
		{
			String senten = "SELECT * from Material where codigoMaterial = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setInt(1,codigoMaterial);
			ResultSet result = ps.executeQuery();
			while (result.next())
			{
				m = new Material();
				m.setCodigoMaterial(result.getInt("codigoMaterial"));
				m.setNombreMaterial(result.getString("nombreMaterial"));
				m.setEstado(Estado.valueOf(result.getString("estado")));
				m.setStock(result.getInt("stock"));
				m.setPuntoPedido(result.getInt("puntoPedido"));
				
				Proveedor prov = ProveedorDao.getInstancia().read(result.getString("PROVEEDOR_cuit"));
				
				m.setCosto(result.getFloat("costo"));
				m.setProveedor(prov);
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return m;
		}catch(SQLException e) {
				System.out.println("Mensaje Error al leer Material: " + e.getMessage());
				System.out.println("Stack Trace al leer Material: " + e.getStackTrace());
				PoolConnection.getInstancia().liberarConexion(con);
		}
	    return m;
	}
}
