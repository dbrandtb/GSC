package mx.com.gseguros.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	
	/**
	 * Convierte una fecha String a Calendar
	 * @param fecha String
	 * @return devuelve objeto Calendar
	 */
	public static Calendar getCalendar(String fecha, String dateFormat) {
		
		Calendar  cal = null;
		
		try {
			//Asumimos que tiene formato dd/MM/YYYY
			//TODO:preparar este metodo  para otros formatos
			if(Utilerias.esFechaValida(fecha, dateFormat)){
				String [] fechaArr = fecha.split("/");   
				int dia  =  Integer.parseInt(fechaArr[0]);
				int mes  =  Integer.parseInt(fechaArr[1])-1;
				int anio =  Integer.parseInt(fechaArr[2]);
				cal = Calendar.getInstance();
				cal.set(anio, mes, dia);
			}
		} catch (Exception e) {
			logger.error("Error al formatear la fecha", e);
		}
		
		return cal;
	}
	
	public static boolean esFechaValida(String dateToValidate, String dateFromat){
		 
		if(dateToValidate == null){
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
 
		try {
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		
		
		System.out.println(Utilerias.getCalendar("11/25/2014", "dd/MM/yyyy"));
		System.out.println(Utilerias.esFechaValida("11/25/2014", "dd/MM/yyyy"));
		
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
	
	
	/**
	 * Indica si el User-Agent recibido corresponde a un dispositivo m&oacute;vil o no
	 * @param userAgent User-Agent del cliente que realiz&oacute; la petici&oacute;n
	 * @return true si el origen de la petici&oacute;n es de un dispositivo m&oacute;vil, false si no 
	 */
	public static boolean esSesionMovil(String userAgent) {
		boolean esMovil=false;
		// Revisamos si el user agent del browser corresponde a un dispositivo movil:
		String regExMobile1 = "(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|android|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od|ad)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino).*";
		String regExMobile2 = "(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-";
		esMovil=userAgent.matches(regExMobile1) || userAgent.substring(0, 4).matches(regExMobile2);
		return esMovil;
	}

}
