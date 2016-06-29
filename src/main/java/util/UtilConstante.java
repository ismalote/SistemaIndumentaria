package main.java.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

public class UtilConstante {

	private String ARCHIVO_PROPERTIES = "main/resources/desarrollo.properties";
	private static UtilConstante instancia;
	private static Properties p;

	public static UtilConstante getInstancia() {
		if(instancia == null) {
			try {
				instancia = new UtilConstante();
			} catch (IOException e) {
				UtilLog.exception(e);
			}
		}
		return instancia;
	}	

	private UtilConstante() throws IOException{		
		p = new Properties();
		InputStream inputStream = null;
		inputStream = UtilConstante.class.getClassLoader().getResourceAsStream(ARCHIVO_PROPERTIES);
		p.load( inputStream );
		inputStream.close();
	}

	public String getTexto(String clave) {
		return getTexto(clave, true);
	}
	
	public String getTexto(String clave, Boolean trim) {
		String s = p.getProperty(clave);
		return !trim ? s : s.trim(); 		
	}
	
	public String getMensaje(String clave, Object... parametros){					
		return getMensaje(clave, true, parametros);
	}
	
	public String getMensaje(String clave, Boolean trim, Object... parametros){
		String s = MessageFormat.format(p.getProperty(clave), parametros);
		return !trim ? s : s.trim();		
	}
}
