package main.java.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import org.apache.ibatis.jdbc.ScriptRunner;

import main.java.persistencia.PoolConnection;

public class UtilDB {

	public static boolean comprobarConexion() {
		return PoolConnection.getInstancia().hayConexionesDisponible();		
	}

	public static int getTipoConexionBD() {
		return PoolConnection.getInstancia().getTipoConexion();	
	}
	
	public static void ejecutarScriptSQL(String direccion_archivo) {
		try {
			ScriptRunner sr = new ScriptRunner(
										PoolConnection.getInstancia().obtenerConexion());

			Reader reader = new BufferedReader(
                               new FileReader(direccion_archivo));

			sr.runScript(reader);

		} catch (Exception e) {
			UtilLog.exception(e);			
		}
	}

}
