package main.java.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class PersistenciaDao {
	public abstract void insert(Object o);
	public abstract void update(Object o);
	public abstract void delete(Object o);
	public abstract List<Object> readAll();
	
	public int getMaxValue(String tableName, String columnName) {
		Connection con = PoolConnection.getInstancia().obtenerConexion();
		int maxCodigo = 0;
		
		try {
			PreparedStatement s = con.prepareStatement(String.format("select MAX(%s) as maxId from %s", columnName, tableName));
			ResultSet rs = s.executeQuery();
			while(rs.next()) {
				maxCodigo = rs.getInt("maxId");
			}
			PoolConnection.getInstancia().liberarConexion(con);
			return maxCodigo;
		}catch(SQLException e) {
			System.out.println("Mensaje Error al insertar Prenda Conjunto: " + e.getMessage());
			System.out.println("Stack Trace al insertar Prenda Conjunto: " + e.getStackTrace());
			PoolConnection.getInstancia().liberarConexion(con);
		}
		return maxCodigo;
	}
}
