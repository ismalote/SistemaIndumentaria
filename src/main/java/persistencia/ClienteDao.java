package main.java.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.enums.Estado;
import main.java.model.Cliente;

public class ClienteDao extends PersistenciaDao{
	private static ClienteDao instancia;
	
	private ClienteDao() {
		
	}
	
	public static ClienteDao getInstancia() {
		if (instancia == null) {
			instancia = new ClienteDao();
		}
		
		return instancia;
	}
	
	@Override
	public void insert(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		try {
			Cliente c = (Cliente)o;
			PreparedStatement s = con.prepareStatement("insert into cliente values (?,?,?)");
			//agregar campos
			s.setLong(1, c.getDni());
			s.setString(2, c.getNombre());
			s.setString(3, c.getEstado().toString());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch (SQLException e) {
			System.out.println("Mensaje Error al insertar Cliente: " + e.getMessage());
			System.out.println("Stack Trace al insertar Cliente: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void update(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		try {
			Cliente c = (Cliente)o;
			PreparedStatement s = con.prepareStatement("update cliente " +
					"set nombre = ? "
					+ "where dni = ?)");
			s.setString(1, c.getNombre());
			s.setLong(2, c.getDni());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch (SQLException e) {
			System.out.println("Mensaje Error al modificar Cliente: " + e.getMessage());
			System.out.println("Stack Trace al modificar Cliente: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void delete(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		try {
			Cliente c = (Cliente)o;
			PreparedStatement s = con.prepareStatement("update cliente " +
					"set estado = 'BA'" +
					"where dni = ?)");
			s.setLong(1, c.getDni());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch (SQLException e) {
			System.out.println("Mensaje Error al dar de baja Cliente: " + e.getMessage());
			System.out.println("Stack Trace al dar de baja Cliente: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	public List<Object> readAll() {
		List<Object> resultado = new ArrayList<Object>();
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * From cliente");
			
			while (rs.next()) {			
				Cliente cli = new Cliente();
				cli.setCodigoCliente(rs.getInt("codigoCliente"));
				cli.setDni(rs.getLong("dni"));
				cli.setNombre(rs.getString("nombre"));
				cli.setEstado(Estado.valueOf(rs.getString("estado")));
				
				resultado.add(cli);
			}
			
			PoolConnection.getInstancia().liberarConexion(con);;
		} catch (SQLException e) {
			System.out.println("Mensaje Error al leer todos los Clientes: " + e.getMessage());
			System.out.println("Stack Trace al leer todos los Clientes: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
		
		return resultado;
	}
	
	public Cliente read(long dni) {
		Cliente c = null; 
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try
		{
			String senten = "SELECT * from Cliente where dni = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setLong(1, dni);
			ResultSet result = ps.executeQuery();
			while (result.next())
			{
				c = new Cliente();
				c.setCodigoCliente(result.getInt("codigoCliente"));
				c.setDni(dni);
				c.setNombre(result.getString("nombre"));
				c.setEstado(Estado.valueOf(result.getString("estado")));
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return c;
		}catch(SQLException e) {
				System.out.println("Mensaje Error al leer Cliente: " + e.getMessage());
				System.out.println("Stack Trace al leer Cliente: " + e.getStackTrace());
				PoolConnection.getInstancia().liberarConexion(con);
		}
	    return c;
	}
	
	public Cliente read(int codigoCliente) {
		Cliente c = null; 
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try
		{
			String senten = "SELECT * from Cliente where codigoCliente = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setInt(1, codigoCliente);
			ResultSet result = ps.executeQuery();
			while (result.next())
			{
				c = new Cliente();
				c.setCodigoCliente(codigoCliente);
				c.setDni(result.getLong("dni"));
				c.setNombre(result.getString("nombre"));
				c.setEstado(Estado.valueOf(result.getString("estado")));
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return c;
		}catch(SQLException e) {
				System.out.println("Mensaje Error al leer Cliente: " + e.getMessage());
				System.out.println("Stack Trace al leer Cliente: " + e.getStackTrace());
				PoolConnection.getInstancia().liberarConexion(con);
		}
	    return c;
	}
}
