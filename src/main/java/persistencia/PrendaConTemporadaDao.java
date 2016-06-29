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
import main.java.model.MaterialItem;
import main.java.model.PrendaConTemporada;
import main.java.model.Temporada;
import main.java.util.UtilLog;

public class PrendaConTemporadaDao extends PersistenciaDao {
	
	private static PrendaConTemporadaDao instancia;
	
	private PrendaConTemporadaDao() {
		
	}
	
	public static PrendaConTemporadaDao getInstancia() {
		if (instancia == null) {
			instancia = new PrendaConTemporadaDao();
		}
		
		return instancia;
	}
	
	
	@Override
	public void insert(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			PrendaConTemporada a = (PrendaConTemporada)o;
			PreparedStatement s = con.prepareStatement("insert into Prenda(nombrePrenda, stock, estado) values (?,?,?)");
			//agregar campos
			s.setString(1,a.getNombrePrenda());			
			s.setInt(2, a.getStock());
			s.setString(3, a.getEstado().toString());
			s.execute();
			PreparedStatement ps = con.prepareStatement("select * from Prenda where nombrePrenda = (?)");
			ps.setString(1, a.getNombrePrenda());
			int codigo = 0;
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				codigo = result.getInt("codigoPrenda");
			}
			s = con.prepareStatement("insert into prenda_temporada values (?,?,?)");
			s.setInt(1, codigo);
			s.setFloat(2, a.getPorcentajeIncremento());
			s.setInt(3, a.getTemporada().getCodigoTemporada());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			UtilLog.exception(e, "Mensaje Error al insertar Prenda con Temporada: " + e.getMessage());			
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}
	

	public void insertItemMaterial(PrendaConTemporada p, MaterialItem item) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			PreparedStatement s = con.prepareStatement("insert into material_item values (?,?,?)");
			s.setInt(1, p.getCodigoPrenda());
			s.setFloat(2, item.getMaterial().getCodigoMaterial());
			s.setInt(3, item.getCantidad());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			UtilLog.exception(e, "Mensaje Error al insertar un item material para la Prenda con Temporada: " + e.getMessage());			
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void update(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			PrendaConTemporada a = (PrendaConTemporada)o;
			PreparedStatement s = con.prepareStatement("update Prenda " +
					"set nombrePrenda = ? , "
					+ "stock = ? " +
					"where codigoPrenda = ? ");
			s.setString(1, a.getNombrePrenda());
			s.setInt(2, a.getStock());
			s.setInt(3, a.getCodigoPrenda());
			s.execute();
			s = con.prepareStatement("update prenda_temporada " +
					"set porcentajeIncremento = ? " +
					"where codigoPrenda = ? ");
			s.setFloat(1, a.getPorcentajeIncremento());
			s.setInt(2,a.getCodigoPrenda());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			UtilLog.exception(e, "Mensaje Error al modificar Prenda con Temporada: " + e.getMessage());			
			PoolConnection.getInstancia().liberarConexion(con);
		}
		
	}
	
	public void updateStock(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			PrendaConTemporada p = (PrendaConTemporada)o;
			PreparedStatement s = con.prepareStatement("update Prenda " +
					"set stock = ? " +
					"where codigoPrenda = ? ");
			s.setInt(1, p.getStock());
			s.setInt(2, p.getCodigoPrenda());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			UtilLog.exception(e, "Mensaje Error al modificar stock de Prenda con Temporada: " + e.getMessage());			
			PoolConnection.getInstancia().liberarConexion(con);
		}
	}

	@Override
	public void delete(Object o) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			PrendaConTemporada a = (PrendaConTemporada)o;
			PreparedStatement s = con.prepareStatement("update Prenda " +
					"set estado = 'BA' " +
					"where codigoPrenda = ? ");
			s.setInt(1,a.getCodigoPrenda());
			s.execute();
			
			PoolConnection.getInstancia().liberarConexion(con);
		}catch(SQLException e) {
			UtilLog.exception(e, "Mensaje Error al dar de baja Prenda con Temporada: " + e.getMessage());			
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
					+ "inner join prenda_temporada pct on p.codigoPrenda = pct.PRENDA_codigoPrenda");
			while (rs.next()) {
				
				PrendaConTemporada p = new PrendaConTemporada();
				
				// Agrego los materiales a la prenda
				
				String senten = "SELECT * FROM material_item where PRENDA_codigoPrenda = ? ";
				PreparedStatement ps = con.prepareStatement(senten);
				ps.setInt(1,rs.getInt("codigoPrenda"));
				ResultSet resultIM = ps.executeQuery();
				while(resultIM.next()) {
					Material mat = MaterialDao.getInstancia().read(resultIM.getInt("MATERIAL_codigoMaterial"));
					p.agregarMaterial(mat, resultIM.getInt("cantidad"), false);
				}
				
				// Agrego resto de los datos
				p.setCodigoPrenda(rs.getInt("codigoPrenda"));
				p.setNombrePrenda(rs.getString("nombrePrenda"));
				p.setStock(rs.getInt("stock"));
				p.setEstado(Estado.valueOf(rs.getString("estado")));
				p.setPorcentajeIncremento(rs.getFloat("porcentajeIncremento"));
				
				Temporada temp = TemporadaDao.getInstancia().read(rs.getInt("TEMPORADA_codigoTemporada"));
				
				p.setTemporada(temp);
				
				resultado.add(p);
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return resultado;
		}catch(SQLException e) {
			UtilLog.exception(e, "Mensaje Error al leer todas las Prendas con Temporada: " + e.getMessage());			
			PoolConnection.getInstancia().liberarConexion(con);
		}
		
		return resultado;
	}

	public PrendaConTemporada read(String nombrePrenda) {
		PrendaConTemporada p = null; 
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try {
			String senten = "SELECT * from prenda p "
					+ "INNER JOIN prenda_temporada pct on pct.prenda_codigoPrenda = p.codigoPrenda "
					+ "where nombrePrenda = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setString(1,nombrePrenda);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				
				p = new PrendaConTemporada();
				
				// Agrego los materiales a la prenda
				
				senten = "SELECT * FROM material_item where PRENDA_codigoPrenda = ? ";
				ps = con.prepareStatement(senten);
				ps.setInt(1,result.getInt("codigoPrenda"));
				ResultSet resultIM = ps.executeQuery();
				while(resultIM.next()) {
					Material mat = MaterialDao.getInstancia().read(resultIM.getInt("MATERIAL_codigoMaterial"));
					p.agregarMaterial(mat, resultIM.getInt("cantidad"), false);
				}
				
				// Agrego resto de los datos
				p.setCodigoPrenda(result.getInt("codigoPrenda"));
				p.setNombrePrenda(result.getString("nombrePrenda"));
				p.setStock(result.getInt("stock"));
				p.setEstado(Estado.valueOf(result.getString("estado")));
				p.setPorcentajeIncremento(result.getFloat("porcentajeIncremento"));
				
				Temporada temp = TemporadaDao.getInstancia().read(result.getInt("TEMPORADA_codigoTemporada"));
				
				p.setTemporada(temp);
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return p;
		}catch(SQLException e) {
				UtilLog.exception(e, "Mensaje Error al leer Prenda con Temporada: " + e.getMessage());				
				PoolConnection.getInstancia().liberarConexion(con);
		}
	    return p;
	}
	
	public PrendaConTemporada read(int codigoPrenda) {
		PrendaConTemporada p = null; 
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		
		try
		{
			String senten = "SELECT * from prenda p "
					+ "INNER JOIN prenda_temporada pct on pct.prenda_codigoPrenda = p.codigoPrenda "
					+ "where codigoPrenda = ? " ;
			PreparedStatement ps = null;
			ps = con.prepareStatement(senten);
			ps.setInt(1,codigoPrenda);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				
				p = new PrendaConTemporada();
				
				// Agrego los materiales a la prenda
				
				senten = "SELECT * FROM material_item where PRENDA_codigoPrenda = ? ";
				ps = con.prepareStatement(senten);
				ps.setInt(1,result.getInt("codigoPrenda"));
				ResultSet resultIM = ps.executeQuery();
				while(resultIM.next()) {
					Material mat = MaterialDao.getInstancia().read(resultIM.getInt("MATERIAL_codigoMaterial"));
					p.agregarMaterial(mat, resultIM.getInt("cantidad"), false);
				}
				
				// Agrego resto de los datos
				p.setCodigoPrenda(result.getInt("codigoPrenda"));
				p.setNombrePrenda(result.getString("nombrePrenda"));
				p.setStock(result.getInt("stock"));
				p.setEstado(Estado.valueOf(result.getString("estado")));
				p.setPorcentajeIncremento(result.getFloat("porcentajeIncremento"));
				
				Temporada temp = TemporadaDao.getInstancia().read(result.getInt("TEMPORADA_codigoTemporada"));
				
				p.setTemporada(temp);
			}
			
			PoolConnection.getInstancia().liberarConexion(con);
			return p;
		}catch(SQLException e) {
				UtilLog.exception(e, "Mensaje Error al leer Prenda con Temporada: " + e.getMessage());				
				PoolConnection.getInstancia().liberarConexion(con);
		}
	    return p;
	}

}
