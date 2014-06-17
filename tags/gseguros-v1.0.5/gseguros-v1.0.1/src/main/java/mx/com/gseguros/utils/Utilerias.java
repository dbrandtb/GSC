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
		StringBuilder strBuilder = new StringBuilder();
		try {
			if(fecha!=null && fecha.length() > 10) {
				// 1990-12-01_00:00:00.0 <<CADENA
				// 0123456789k           <<INDICE
				strBuilder.append(fecha.substring(8, 10)).append("/")
						.append(fecha.substring(5, 7)).append("/")
						.append(fecha.substring(0, 4));
			} else {
				strBuilder.append(fecha);
			}
		} catch (Exception e) {
			logger.error("Error al formatear la fecha", e);
		}
		//logger.debug("#debug fecha="+strBuilder);
		return strBuilder.toString();
	}
	
	
	/*
	public static String formateaNumero(String numero)
	{
		String num="";
		if(numero!=null)
		{
			int l=numero.length();
			for(int p=l-1,i=0;p>=0;p--,i++)
			{
				//p es posicion
				//i es iteracion
				String digito=numero.substring(p, p+1);
				String regex="\\d";
				if(digito.matches(regex))//copiar digito
				{
					num+=digito;
				}
				else if(i<=2)//0 ó 1 ó 2
				{
					num+=".";
				}
			}
			String tmp="";
			for(int i=num.length()-1;i>=0;i--)
			{
				tmp+=num.charAt(i);
			}
			num=tmp;
		}
		logger.debug("formateaNumero entrada: "+numero+", salida:"+num);
		return num;
	}
	*/
	
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
