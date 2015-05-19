package mx.com.gseguros.portal.general.service.impl;

import java.io.File;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.general.service.ServiciosManager;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.Utilerias;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiciosManagerImpl implements ServiciosManager
{
	private static Logger logger = Logger.getLogger(ServiciosManagerImpl.class);
	
	@Value("${ruta.documentos.poliza}")
	private String rutaDocumentosPoliza;
	
	@Value("${ruta.servidor.reports}")
	private String rutaServidorReports;
	
	@Value("${pass.servidor.reports}")
	private String passServidorReports;
	
	@Autowired
	private CotizacionDAO cotizacionDAO;
	
	@Override
	public String reemplazarDocumentoCotizacion(StringBuilder sb, String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception
	{
		sb.append(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ reemplazarDocumentoCotizacion @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				));
		
		String paso = null;
		
		try
		{
			paso = "Validando operacion y recuperando datos para el reporte";
			sb.append(Utilerias.join("\n",paso));
			
			Map<String,String> datos = cotizacionDAO.validarReemplazoDocumentoCotizacion(cdunieco,cdramo,estado,nmpoliza);
			sb.append(Utilerias.join("\ndatos recuperados=",datos));
			
			String cdtipsit = datos.get("cdtipsit");
			String cdplan   = datos.get("cdplan");
			String cdperpag = datos.get("cdperpag");
			String tipoflot = datos.get("tipoflot");
			String ntramite = datos.get("ntramite");
			String cdusuari = datos.get("cdusuari");
			String reporte  = datos.get("reporte");
			
			Utils.validate(sb
					,cdperpag , "No se recupero la forma de pago"
					,ntramite , "No se recupero el numero de tramite"
					,cdusuari , "No se recupero el usuario"
					,reporte  , "No se recupero el reporte");
			
			paso = "Verificando carpeta del tramite";
			sb.append(Utilerias.join("\n",paso));
			
			String rutaCarpeta = Utilerias.join(rutaDocumentosPoliza,"/",ntramite);
			File   carpeta     = new File(rutaCarpeta);
            if(!carpeta.exists())
            {
            	sb.append("\nSe va a crear la carpeta ").append(carpeta);
            	if(!carpeta.mkdir())
            	{
            		throw new ApplicationException("No se pudo crear la carpeta para los documentos",sb.toString());
            	}
            }
			
            paso = "Construyendo url de reporte";
			sb.append(Utilerias.join("\n",paso));
            String url = null;
			
			if(StringUtils.isBlank(tipoflot))
			{
				url = new StringBuilder(rutaServidorReports)
							.append("?destype=cache")
							.append("&desformat=PDF")
							.append("&userid=")       .append(passServidorReports)
							.append("&report=")       .append(reporte)
							.append("&paramform=no")
							.append("&ACCESSIBLE=YES")
							.append("&p_unieco=")     .append(cdunieco)
							.append("&p_ramo=")       .append(cdramo)
							.append("&p_subramo=")    .append(cdtipsit)
							.append("&p_estado=")     .append(estado)
							.append("&p_poliza=")     .append(nmpoliza)
							.append("&p_suplem=0")
							.append("&p_cdplan=")     .append(cdplan)
							.append("&p_plan=")       .append(cdplan)
							.append("&p_perpag=")     .append(cdperpag)
							.append("&p_ntramite=")   .append(ntramite)
							.append("&p_cdusuari=")   .append(cdusuari)
							.toString();
			}
			else
			{
				url = new StringBuilder(rutaServidorReports)
							.append("?destype=cache")
							.append("&desformat=PDF")
							.append("&userid=")       .append(passServidorReports)
							.append("&report=")       .append(reporte)
							.append("&paramform=no")
							.append("&ACCESSIBLE=YES")
							.append("&p_unieco=")     .append(cdunieco)
							.append("&p_ramo=")       .append(cdramo)
							.append("&p_estado=")     .append(estado)
							.append("&p_poliza=")     .append(nmpoliza)
							.append("&p_suplem=0")
							.append("&p_perpag=")     .append(cdperpag)
							.append("&p_ntramite=")   .append(ntramite)
							.append("&p_cdusuari=")   .append(cdusuari)
							.toString();
			}
			
			paso = "Generando cotizacion";
			sb.append(Utilerias.join("\n",paso,"\nurl=",url));
			
			if(!HttpUtil.generaArchivo(url,rutaCarpeta+"/cotizacion.pdf"))
			{
				throw new ApplicationException("No se pudo generar el archivo de cotizacion",sb.toString());
			}
			
			paso = "ok";
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		return paso;
	}
	
	public void setCotizacionDAO(CotizacionDAO cotizacionDAO)
	{
		this.cotizacionDAO=cotizacionDAO;
	}
}