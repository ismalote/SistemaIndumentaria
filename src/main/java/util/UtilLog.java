package main.java.util;

public class UtilLog {

	public static void exception(Exception e) {
		StringBuffer buffer = new StringBuffer();
		System.out.println(buffer.append("EXCEPTION - ").append(e.getMessage()));
		e.printStackTrace();
	}

	public static void exception(Exception e, String mensaje) {
		StringBuffer buffer = new StringBuffer();
		System.out.println(buffer.append("EXCEPTION - ").append(mensaje));
		e.printStackTrace();
	}
	
	public static void log(String texto) {
		StringBuffer buffer = new StringBuffer();
		System.out.println(buffer.append("LOG - ").append(texto));
	}

}
