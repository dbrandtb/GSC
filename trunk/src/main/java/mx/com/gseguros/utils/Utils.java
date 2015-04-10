package mx.com.gseguros.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	 * Lanza una excepcion cuando la cadena es null o vac�a
	 * 
	 * @param cadena Elemento a validar
	 * @param mensaje Mensaje agregado a la excepcion
	 * @throws ValidationDataException
	 */
	public static void validate(String cadena, String mensaje) throws ValidationDataException {
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
	public static void validate(Object objeto, String mensaje) throws ValidationDataException {
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
	public static void validate(Collection<?> coll, String mensaje) throws ValidationDataException {
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
	public static void validate(Map<?,?> map, String mensaje) throws ValidationDataException {
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
	public static void validate(Boolean condicion, String mensaje) throws ValidationDataException {
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
			respuesta = new StringBuilder("Error del sistema #").append(System.currentTimeMillis()).toString();
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
	public static ApplicationException generaExcepcion(Exception e,String paso) throws Exception {
		
		if(e.getClass().equals(ApplicationException.class)) {
			throw e;
		} else {
			throw new ApplicationException(Utilerias.join("Error en el proceso: ",paso), e);
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
	public static String convierteListaEnXml(List<Map<String,String>>lista)
	{
		StringBuilder sb = new StringBuilder("<lista>");
		for(Map<String,String>registro:lista)
		{
			sb.append("<registro>");
			for(Entry<String,String>en:registro.entrySet())
			{
				sb.append("<")
				  .append(en.getKey())
				  .append(">")
				  .append(en.getValue()!=null?en.getValue():"")
				  .append("</")
				  .append(en.getKey())
				  .append(">");
			}
			sb.append("</registro>");
		}
		return sb.append("</lista>").toString();
	}

}