package main.java.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.controller.SistemaIndumentaria;
import main.java.enums.Estado;
import main.java.model.Prenda;
import main.java.model.PrendaConjunto;

public class PrendaConjuntoDao extends PersistenciaDao {
	
	private static PrendaConjuntoDao instancia;
	
	private PrendaConjuntoDao() {
		
	}
	
	public static PrendaConjuntoDao getInstancia() {
		if (instancia == null) {
			instancia = new PrendaConjuntoDao();
		}
		
		return instancia;
	}
	
	@Override
	public void insert(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			PrendaConjunto p = (PrendaConjunto)o;
			PreparedStatement s = con.prepareStatement("insert into prenda values (?,?,?)");
			//agregar campos
			s.setString(1,p.getNombrePrenda());
			s.setInt(2, p.getStock());
			s.setString(3, p.getEstado().toString());
			s.execute();
			PreparedStatement ps = con.prepareStatement("select * from Prenda where nombrePrenda = (?)");
			ps.setString(1, p.getNombrePrenda());
			int codigo = 0;
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				codigo = result.getInt("codigoPrenda");
			}
			s = con.prepareStatement("insert into prenda_conjunto values (?,?)");
			s.setInt(1, codigo);
			s.setFloat(2, p.getDescuento());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al insertar Prenda Conjunto: " + e.getMessage());
			System.out.println("Stack Trace al insertar Prenda Conjunto: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}
	
	public void insertItemPrendaConjunto(Object o, int codigo) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			PrendaConjunto p = (PrendaConjunto)o;
			PreparedStatement s = con.prepareStatement("insert into prenda_conjunto_item values (?,?)");
			s.setInt(1, p.getCodigoPrenda());
			s.setInt(2, codigo);
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al insertar un item prenda conjunto: " + e.getMessage());
			System.out.println("Stack Trace al insertar un item prenda conjunto: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void update(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			PrendaConjunto p = (PrendaConjunto)o;
			PreparedStatement s = con.prepareStatement("update Prenda " +
					"set nombrePrenda = ? " +
					"where codigoPrenda = ?)");
			s.setString(1, p.getNombrePrenda());
			s.setInt(2,p.getCodigoPrenda());
			s.execute();
			s = con.prepareStatement("update prenda_conjunto " +
					"set descuento = ? " +
					"where codigoPrenda = ?)");
			s.setFloat(1, p.getDescuento());
			s.setInt(2,p.getCodigoPrenda());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al modificar Prenda Conjunto: " + e.getMessage());
			System.out.println("Stack Trace al modificar Prenda Conjunto: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void delete(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			PrendaConjunto p = (PrendaConjunto)o;
			PreparedStatement s = con.prepareStatement("update Prenda " +
					"set estado = 'BA' " +
					"where codigoPrenda = ?)");
			s.setInt(1,p.getCodigoPrenda());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			System.out.println("Mensaje Error al dar de baja Prenda Conjunto: " + e.getMessage());
			System.out.println("Stack Trace al dar de baja Prenda Conjunto: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public List<Object> readAll() {
		List<Object> resultado = new ArrayList<Object>();
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * From Prenda p "
					+ "inner join prenda_conjunto pconj on p.codigoPrenda = pconj.PRENDA_codigoPrenda");
			while (rs.next()) {
				
				PrendaConjunto p = new PrendaConjunto();
				
				// Agrego las prendas al conjunto de prendas
				
				String senten = "SELECT * FROM prenda_conjunto_item where PRENDA_CONJUNTO_PRENDA_codigoPrenda = ? ";
				PreparedStatement ps = con.prepareStatement(senten);
				ps.setInt(1,rs.getInt("codigoPrenda"));
				ResultSet resultIM = ps.executeQuery();
				while(resultIM.next()) {
					Prenda pre = SistemaIndumentaria.getInstancia().buscarPrenda(resultIM.getInt("PRENDA_codigoPrenda"));
					p.agregarPrenda(pre);
				}
				
				// Agrego resto de los datos
				p.setCodigoPrenda(rs.getInt("codigoPrenda"));
				p.setNombrePrenda(rs.getString("nombrePrenda"));
				p.setStock(rs.getInt("stock"));
				p.setEstado(Estado.valueOf(rs.getString("estado")));
				p.setDescuento(rs.getFloat("descuento"));
				
				resultado.add(p);
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return resultado;
		}catch(SQLException e) {
			System.out.println("Mensaje Error al leer Prenda con Temporada: " + e.getMessage());
			System.out.println("Stack Trace al leer Prenda con Temporada: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
		return resultado;
	}
	
	public PrendaConjunto read(String nombrePrenda) {
		PrendaConjunto p = null;
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			String senten = "SELECT * from prenda p "
					+ "INNER JOIN prenda_conjunto pconj on pconj.prenda_codigoPrenda = p.codigoPrenda "
					+ "where nombrePrenda = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setString(1,nombrePrenda);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				
				p = new PrendaConjunto();
				
				// Agrego las prendas al conjunto de prendas
				
				senten = "SELECT * FROM prenda_conjunto_item where PRENDA_CONJUNTO_PRENDA_codigoPrenda = ? ";
				ps = con.prepareStatement(senten);
				ps.setInt(1,result.getInt("codigoPrenda"));
				ResultSet resultIM = ps.executeQuery();
				while(resultIM.next()) {
					Prenda pre = SistemaIndumentaria.getInstancia().buscarPrenda(resultIM.getInt("PRENDA_codigoPrenda"));
					p.agregarPrenda(pre);
				}
				
				// Agrego resto de los datos
				p.setCodigoPrenda(result.getInt("codigoPrenda"));
				p.setNombrePrenda(result.getString("nombrePrenda"));
				p.setStock(result.getInt("stock"));
				p.setEstado(Estado.valueOf(result.getString("estado")));
				p.setDescuento(result.getFloat("descuento"));
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return p;
		}catch(SQLException e) {
			System.out.println("Mensaje Error al leer Prenda con Temporada: " + e.getMessage());
			System.out.println("Stack Trace al leer Prenda con Temporada: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
		return p;
	}
	
	public PrendaConjunto read(int codigoPrenda) {
		PrendaConjunto p = null;
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			String senten = "SELECT * from prenda p "
					+ "INNER JOIN prenda_conjunto pconj on pconj.prenda_codigoPrenda = p.codigoPrenda "
					+ "where codigoPrenda = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setInt(1, codigoPrenda);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				
				p = new PrendaConjunto();
				
				// Agrego las prendas al conjunto de prendas
				
				senten = "SELECT * FROM prenda_conjunto_item where PRENDA_CONJUNTO_PRENDA_codigoPrenda = ? ";
				ps = con.prepareStatement(senten);
				ps.setInt(1,result.getInt("codigoPrenda"));
				ResultSet resultIM = ps.executeQuery();
				while(resultIM.next()) {
					Prenda pre = SistemaIndumentaria.getInstancia().buscarPrenda(resultIM.getInt("PRENDA_codigoPrenda"));
					p.agregarPrenda(pre);
				}
				
				// Agrego resto de los datos
				p.setCodigoPrenda(result.getInt("codigoPrenda"));
				p.setNombrePrenda(result.getString("nombrePrenda"));
				p.setStock(result.getInt("stock"));
				p.setEstado(Estado.valueOf(result.getString("estado")));
				p.setDescuento(result.getFloat("descuento"));
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return p;
		}catch(SQLException e) {
			System.out.println("Mensaje Error al leer Prenda con Temporada: " + e.getMessage());
			System.out.println("Stack Trace al leer Prenda con Temporada: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
		return p;
	}
	
}
