package mx.com.gseguros.utils;

import org.apache.log4j.Logger;

public class Utilerias {
	
	private static Logger logger = Logger.getLogger(Utilerias.class);
	
	/**
	 * Formatea un string con el contenido de una fecha
	 * @param fecha Fecha formateada
	 * @return
	 */
	public static String formateaFecha(String fecha) {
		logger.debug("#debugfecha="+fecha);
		if(fecha!=null && fecha.length()>10) {
			// 1 9 9 0 - 1 2 - 0 1 _ 0 0 : 0 0 : 0 0 . 0 <<CADENA
			//0 1 2 3 4 5 6 7 8 9 k <<INDICE
			String aux="";
			aux+=fecha.substring(8,10);
			aux+="/";
			aux+=fecha.substring(5,7);
			aux+="/";
			aux+=fecha.substring(0,4);
			fecha=aux;
		}
		return fecha;
	}

}
