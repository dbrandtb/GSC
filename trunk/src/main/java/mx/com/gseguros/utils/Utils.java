package mx.com.gseguros.utils;

import java.util.Collection;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.ValidationDataException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Utiler&iacute;as para validar datos de la aplicacion
 * @author Ricardo
 *
 */
public class Utils {
	
	private static Logger logger = Logger.getLogger(Utils.class);
	
	
	/**
	 * Lanza una excepcion cuando la cadena es null o vacía
	 * 
	 * @param cadena Elemento a validar
	 * @param mensaje Mensaje agregado a la excepcion
	 * @throws ValidationDataException
	 */
	public static void isEmpty(String cadena, String mensaje) throws ValidationDataException {
		if(StringUtils.isBlank(cadena)) {
			throw new ValidationDataException(mensaje);
		}
	}
	
	
	/**
	 * Lanza una excepcion cuando el objeto es null
	 * 
	 * @param objeto Elemento a validar
	 * @param mensaje Mensaje agregado a la excepcion
	 * @throws ValidationDataException
	 */
	public static void isEmpty(Object objeto, String mensaje) throws ValidationDataException {
		if(objeto==null) {
			throw new ValidationDataException(mensaje);
		}
	}
	
	
	/**
	 * Lanza una excepcion cuando la coleccion es null o vacia
	 * 
	 * @param coll Coleccion a validar
	 * @param mensaje Mensaje agregado a la excepcion
	 * @throws ValidationDataException
	 */
	public static void isEmpty(Collection<?> coll, String mensaje) throws ValidationDataException {
		if(CollectionUtils.isEmpty(coll)) {
			throw new ValidationDataException(mensaje);
		}
	}
	
	
	/**
	 * Lanza una excepcion cuando el mapa es null o vacio
	 * 
	 * @param map Mapa a validar
	 * @param mensaje Mensaje agregado a la excepcion
	 * @throws ValidationDataException
	 */
	public static void isEmpty(Map<?,?> map, String mensaje) throws ValidationDataException {
		if(MapUtils.isEmpty(map)) {
			throw new ValidationDataException(mensaje);
		}
	}
	
	
	/**
	 * Lanza una excepcion cuando la condicion es null o false
	 * 
	 * @param condicion Elemento a validar
	 * @param mensaje Mensaje agregado a la excepcion
	 * @throws ValidationDataException
	 */
	public static void isFalse(Boolean condicion, String mensaje) throws ValidationDataException {
		if(BooleanUtils.isNotTrue(condicion)) {
			throw new ValidationDataException(mensaje);
		}
	}
	
	
	/**
	 * Maneja excepciones de la aplicacion y devuelve el mensaje de error correspondiente
	 * @param e
	 * @return
	 */
	public static String manejaExcepcion(Exception e) {
		
		String respuesta;
		if(e instanceof ApplicationException) {
			respuesta = new StringBuilder(e.getMessage()).append(" #").append(System.currentTimeMillis()).toString();
		} else {
			respuesta = "Error del sistema.";
		}
		logger.error(respuesta, e);
		return respuesta;
	}
	
	/**
	 * Genera una ApplicationException o la lanza si existe
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public static ApplicationException generaExcepcion(Exception e) throws Exception {
		
		if(e.getClass().equals(ApplicationException.class)) {
			throw e;
		} else {
			throw new ApplicationException("Error del sistema", e);
		}
	}
	
	
/*	
	public static void main(String[] args) {
		try {
			
			String msje = manejaException(new Exception("mensaje de excepcion"));
			
			logger.debug("Mensaje de repuesta de excepcion: " + msje);
			
//			List<String> lista = new ArrayList<String>();
//			lista.add(null);
//			Utils.isEmpty(lista, "lista invalida");
			
//			Map<String, String> map = new HashMap<String, String>();
//			Utils.isEmpty(map, "mapa invalido");
		
//			Object objeto="";
//			ValidationUtils.isEmpty(objeto, "Objeto invalido");
		
//			String cadena="d";
//			ValidationUtils.isEmpty(cadena, "Cadena invalida");
		
//			Boolean condicion = new Boolean(null);
//			ValidationUtils.isFalse(condicion, "condicion invalida");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
*/

}