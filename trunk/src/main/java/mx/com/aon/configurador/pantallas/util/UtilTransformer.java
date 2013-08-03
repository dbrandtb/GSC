package mx.com.aon.configurador.pantallas.util;

import java.util.Map;


/**
 * 
 * @author Leopoldo Ramirez Montes
 *
 */
public interface UtilTransformer {
	
	
	//TODO: METODO QUE DEBE LANZAR EXCEPTION...
	/**
	 * 
	 * @param file
	 * @param parameters
	 * @return
	 */
	String transform(String file, Map<String,?> parameters, Boolean vistaPrevia);
	

}
