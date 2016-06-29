package main.java.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.model.Temporada;

public class TemporadaDao extends PersistenciaDao {
	
	private static TemporadaDao instancia;
	
	private TemporadaDao() {
		
	}
	
	public static TemporadaDao getInstancia() {
		if (instancia == null) {
			instancia = new TemporadaDao();
		}
		
		return instancia;
	}

	@Override
	public void insert(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Temporada t = (Temporada)o;
			PreparedStatement s = con.prepareStatement("insert into temporada values (?,?)");
			//agregar campos
			s.setString(1, t.getNombreTemporada());
			s.setDate(2, new java.sql.Date(t.getFechaFinalizacion().getTime()));
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al leer Temporada: " + e.getMessage());
			System.out.println("Stack Trace al leer Temporada: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void update(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Temporada t = (Temporada)o;
			PreparedStatement s = con.prepareStatement("update temporada " +
					"set fechaFinalizacion = ? "
					+ "where nombreTemporada = ?)");
			s.setDate(1, new java.sql.Date(t.getFechaFinalizacion().getTime()));
			s.setString(2,t.getNombreTemporada());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al leer Temporada: " + e.getMessage());
			System.out.println("Stack Trace al leer Temporada: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void delete(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Temporada t = (Temporada)o;
			PreparedStatement s = con.prepareStatement("delete temporada " +
					"where nombreTemporada = ? ");
			s.setString(1, t.getNombreTemporada());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al leer Temporada: " + e.getMessage());
			System.out.println("Stack Trace al leer Temporada: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public List<Object> readAll() {
		List<Object> resultado = new ArrayList<>();
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * From temporada");
			
			while (rs.next()) {
				Temporada temp = new Temporada();
				temp.setCodigoTemporada(rs.getInt("codigoTemporada"));
				temp.setNombreTemporada(rs.getString("nombreTemporada"));
				temp.setFechaFinalizacion(rs.getDate("fechaFinalizacion"));
				
				resultado.add(temp);
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al leer Temporada: " + e.getMessage());
			System.out.println("Stack Trace al leer Temporada: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
		
		return resultado;
	}
	
	public Temporada read(String nombreTemporada) {
		Temporada t = null; 
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			String senten = "SELECT * from temporada where nombreTemporada = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setString(1, nombreTemporada);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				t = new Temporada();
				t.setCodigoTemporada(result.getInt("codigoTemporada"));
				t.setNombreTemporada(nombreTemporada);
				t.setFechaFinalizacion(result.getDate("fechaFinalizacion"));
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return t;
		}catch(SQLException e) {
				System.out.println("Mensaje Error al leer Temporada: " + e.getMessage());
				System.out.println("Stack Trace al leer Temporada: " + e.getStackTrace());
				PoolConnection.getInstancia().liberarConexion(con);
		}
	    return t;
	}
	
	public Temporada read(int codigoTemporada) {
		Temporada t = null; 
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try
		{
			String senten = "SELECT * from temporada where codigoTemporada = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setInt(1, codigoTemporada);
			ResultSet result = ps.executeQuery();
			while (result.next())
			{
				t = new Temporada();
				t.setCodigoTemporada(codigoTemporada);
				t.setNombreTemporada(result.getString("nombreTemporada"));
				t.setFechaFinalizacion(result.getDate("fechaFinalizacion"));
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return t;
		}catch(SQLException e) {
				System.out.println("Mensaje Error al leer Temporada: " + e.getMessage());
				System.out.println("Stack Trace al leer Temporada: " + e.getStackTrace());
				PoolConnection.getInstancia().liberarConexion(con);
		}
	    return t;
	}

}
