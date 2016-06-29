package main.java.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.enums.Estado;
import main.java.model.Proveedor;

public class ProveedorDao extends PersistenciaDao {
	private static ProveedorDao instancia;
	
	private ProveedorDao() {
		
	}
	
	public static ProveedorDao getInstancia() {
		if (instancia == null) {
			instancia = new ProveedorDao();
		}
		
		return instancia;
	}
	
	@Override
	public void insert(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Proveedor p = (Proveedor)o;
			PreparedStatement s = con.prepareStatement("insert into proveedor values (?,?,?)");
			//agregar campos
			s.setString(1, p.getCuit());
			s.setString(2, p.getRazonSocial());
			s.setString(3,p.getEstado().toString());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al insertar Proveedor: " + e.getMessage());
			System.out.println("Stack Trace al insertar Proveedor: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void update(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Proveedor p = (Proveedor)o;
			PreparedStatement s = con.prepareStatement("update proveedor " +
					"set razonSocial = ? "
					+ "where cuit = ?)");
			s.setString(1, p.getRazonSocial());
			s.setString(2,p.getCuit());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al modificar Proveedor: " + e.getMessage());
			System.out.println("Stack Trace al modificar Proveedor: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void delete(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Proveedor p = (Proveedor)o;
			
			PreparedStatement s = con.prepareStatement("update proveedor " +
					"set estado = 'BA'" +
					"where cuit = ?)");
			s.setString(1, p.getCuit());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al dar de baja Proveedor: " + e.getMessage());
			System.out.println("Stack Trace al dar de baja Proveedor: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}
	
	@Override
	public List<Object> readAll() {
		List<Object> resultado = new ArrayList<>();
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * From proveedor");
			
			while (rs.next()) {
				Proveedor pro = new Proveedor(rs.getString("cuit"), rs.getString("razonSocial"));
				pro.setEstado(Estado.valueOf(rs.getString("estado")));
				
				resultado.add(pro);
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al leer todos los Proveedores: " + e.getMessage());
			System.out.println("Stack Trace al leer todos los Proveedores: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
		
		return resultado;
	}

	public Proveedor read(String cuit) {
		Proveedor p = null; 
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try
		{
			String senten = "SELECT * from proveedor where cuit = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setString(1, cuit);
			ResultSet result = ps.executeQuery();
			while (result.next())
			{
				p = new Proveedor();
				p.setCuit(result.getString("cuit"));
				p.setRazonSocial(result.getString("razonSocial"));
				p.setEstado(Estado.valueOf(result.getString("estado")));
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return p;
		}catch(SQLException e) {
				System.out.println("Mensaje Error al leer Proveedor: " + e.getMessage());
				System.out.println("Stack Trace al leer Proveedor: " + e.getStackTrace());
				PoolConnection.getInstancia().liberarConexion(con);
		}
	    return p;
	}
	
}
