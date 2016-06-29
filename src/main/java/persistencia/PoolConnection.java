package main.java.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.util.UtilConstante;
import main.java.util.UtilLog;

public class PoolConnection {
	
	private static final String jdbc = "jdbc:";
	private static PoolConnection pool;	
	
	private List<Connection> conexiones = new ArrayList<>();
	private String conexion_conector;
	private String conexion_class;
	private String servidor;
	private String servidor_base = "";
	private String base;
	private String usuario;
	private String pass;
	private int tipo_conn;
	private int cantidad_max_conn;	
	
	private PoolConnection(){
		getConfiguracion();
		
		for (int i = 0; i < cantidad_max_conn; i++) {
			conexiones.add(conectar());
		}	
	}
	
	private void getConfiguracion() {		
		UtilConstante utilConstante = UtilConstante.getInstancia();
		
		tipo_conn = Integer.parseInt(utilConstante.getTexto("parametros_bd_tipo"));		
		base = utilConstante.getTexto("parametros_bd_base");
		usuario = utilConstante.getTexto("parametros_bd_user");
		pass = utilConstante.getTexto("parametros_bd_pass");
		cantidad_max_conn = Integer.parseInt(utilConstante.getTexto("parametros_bd_conn"));
				
		servidor = new StringBuffer()
					.append(utilConstante.getTexto("parametros_bd_serv"))					
					.toString();
		
		if (!base.isEmpty()){
			servidor_base =  new StringBuffer()				
					.append(utilConstante.getTexto("separador_bd"))
					.append(base)
					.toString();
		}
		
		switch ( getTipoConexion() ){
			case 1: // SQLSERVER
				conexion_conector = utilConstante.getTexto("parametros_bd_jdbc_sqlserver_conector");
				conexion_class = utilConstante.getTexto("parametros_bd_jdbc_sqlserver_class");								
				
				break;
			case 2: // MYSQL
				conexion_conector = utilConstante.getTexto("parametros_bd_jdbc_mysql_conector");
				conexion_class = utilConstante.getTexto("parametros_bd_jdbc_mysql_class");
				break;
		}
	}

	public static PoolConnection getInstancia(){
		if (pool == null) {
			pool = new PoolConnection();
		}
		
		return pool;
	}
	
	private Connection conectar(){
		UtilConstante utilConstante = UtilConstante.getInstancia();
		UtilLog.log("Se ha solicitado una conexion nueva");
		try{			
			Class.forName(conexion_class);
			StringBuffer buffer = new StringBuffer();
            String dbConnectString = buffer
            						.append(jdbc)
            						.append(conexion_conector)
            						.append(utilConstante.getTexto("separador_bd_conector"))            						
                        			.append(servidor)
                        			.append(servidor_base)
            						.toString();                     
//			String dbConnectString  = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=bd_api;";
            UtilLog.log(dbConnectString);
            
            Connection con = DriverManager.getConnection(dbConnectString, usuario, pass);                       
            
            return con;
		}
		catch (SQLException e){
			UtilLog.exception(e, e.getSQLState());			
			return null;
		}
		catch (Exception ex){
			UtilLog.exception(ex);
			return null;
		}
	}
	
	public void cerrarConexiones(){
		UtilLog.log("Se ha cerrado todas las conexiones");
		for (int i = 0; i < conexiones.size(); i++){
			try{
				conexiones.get(i).close();
			}
			catch(Exception e){
				UtilLog.exception(e);
			}
		}
	}
	
	public Connection obtenerConexion(){
		Connection c = null;
		if (conexiones.size() > 0) {
			c = conexiones.remove(0);
		}
		else{
			c = conectar();			
			UtilLog.log("Se ha creado una nueva conexion fuera de los parametros de configuracion");
		}
		return c;
	}
	
	public void liberarConexion(Connection c){
		conexiones.add(c);
		UtilLog.log("Se ha liberado una conexion");
	}
	
	public boolean hayConexionesDisponible(){
		return !conexiones.isEmpty(); 
	}
	
	public int getTipoConexion() {
		return tipo_conn;
	}
}
