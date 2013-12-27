package mx.com.gseguros.utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class Utilerias {
	
	private static Logger logger          = Logger.getLogger(Utilerias.class);
	private static SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	
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
	
	/**
	 * RECIBE UN MAPA DE TIPO STRING Y PONE TODAS LAS LLAVES QUE ENPIECEN CON "FE" Y "PV_FE" COMO TIPO DATE
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> ponFechas(Map<String,String>params) throws Exception
	{
		Map<String,Object> omap=new HashMap<String,Object>(0);
		if(params!=null)
		{
			for(Entry<String,String> en:params.entrySet())
			{
				String llave=(String)en.getKey();
				if((llave.length()>=2&&llave.substring(0,2).equalsIgnoreCase("fe"))||(llave.length()>=5&&llave.substring(0,5).equalsIgnoreCase("pv_fe")))
				{
					String fecha=en.getValue();
					if(fecha!=null&&fecha.length()>0)
					{
						omap.put(llave,renderFechas.parse(fecha));
					}
					else
					{
						omap.put(llave,null);
					}
				}
				else
				{
					omap.put(llave,en.getValue());
				}
			}
		}
		return omap;
	}

}
