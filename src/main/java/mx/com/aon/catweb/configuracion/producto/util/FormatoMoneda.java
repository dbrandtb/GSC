/**
 * 
 */
package mx.com.aon.catweb.configuracion.producto.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.apache.commons.lang.StringUtils;

/**
 * Clase que formatea números
 * @author Cesar Hernandez
 *
 */
public class FormatoMoneda {
	
	/**
	 * Formatea un número a una máscara especifica
	 * @param valor El número que se quiere formatear
	 * @param mascara La máscara con la que se formateara el número
	 * @param separadores Son los separadores de miles y decimales
	 * @return El número con formato
	 */
	public static String formatoMoneda(double valor, String mascara, String separadores)
	{
		if (mascara == null){
			throw new NullPointerException();
		}
		if (separadores == null){
			throw new NullPointerException();
		}
		if (StringUtils.isBlank(mascara)){
			throw new IllegalArgumentException("El parametro mascara no debe ser blanco o nulo");
		}
		if (StringUtils.isBlank(separadores)){
			throw new IllegalArgumentException("El parametro separadores no debe ser blanco o nulo");
		}
		
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		
		dfs.setGroupingSeparator(separadores.charAt(0));
		
		if(separadores.length() > 2)
			dfs.setDecimalSeparator(separadores.charAt(1));
		
		DecimalFormat df = new DecimalFormat(mascara, dfs);
	    
	    return df.format(valor);
	}
	
	/**
	 * Formatea un número con respecto al código del lenguaje y país
	 * @param valor El número que se quiere formatear
	 * @param languageCode Código del lenguaje
	 * @param countryCode Código del país
	 * @param separadores Son los separadores de miles y decimales
	 * @return El número con formato
	 */
	public static String formatoMoneda(double valor, String languageCode, String countryCode, String separadores)
	{
		if(languageCode.toLowerCase().equals("es") && countryCode.toLowerCase().equals("mx")){
			return formatoMoneda(valor, "$#,###.##", separadores);
		}
		if(languageCode.toLowerCase().equals("en") && countryCode.toLowerCase().equals("us")){
			return formatoMoneda(valor, "$#,###.##", separadores);
		}
		if(languageCode.toLowerCase().equals("en") && countryCode.toLowerCase().equals("au")){
			return formatoMoneda(valor, "", separadores);
		}
		if(languageCode.toLowerCase().equals("en") && countryCode.toLowerCase().equals("ca")){
			return formatoMoneda(valor, "", separadores);
		}
		if(languageCode.toLowerCase().equals("fr") && countryCode.toLowerCase().equals("ca")){
			return formatoMoneda(valor, "", separadores);
		}
		if(languageCode.toLowerCase().equals("pt") && countryCode.toLowerCase().equals("br")){
			return formatoMoneda(valor, "", separadores);
		}
		
		return "";
	}
}
