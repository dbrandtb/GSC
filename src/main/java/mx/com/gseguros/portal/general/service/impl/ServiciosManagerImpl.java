package mx.com.gseguros.portal.general.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.general.service.MailService;
import mx.com.gseguros.portal.general.service.ServiciosManager;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiciosManagerImpl implements ServiciosManager
{
	private static Logger logger = LoggerFactory.getLogger(ServiciosManagerImpl.class);
	
	@Value("${ruta.documentos.poliza}")
	private String rutaDocumentosPoliza;
	
	@Value("${ruta.servidor.reports}")
	private String rutaServidorReports;
	
	@Value("${pass.servidor.reports}")
	private String passServidorReports;
	
	@Autowired
	private CotizacionDAO cotizacionDAO;
	
	@Autowired
	private EndososDAO endososDAO;
	
	@Autowired
	private MailService mailService;
	
	@Override
	public String reemplazarDocumentoCotizacion(StringBuilder sb, String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception
	{
		sb.append(Utils.join(
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
			sb.append(Utils.join("\n",paso));
			
			Map<String,String> datos = cotizacionDAO.validarReemplazoDocumentoCotizacion(cdunieco,cdramo,estado,nmpoliza);
			sb.append(Utils.join("\ndatos recuperados=",datos));
			
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
			sb.append(Utils.join("\n",paso));
			
			String rutaCarpeta = Utils.join(rutaDocumentosPoliza,"/",ntramite);
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
			sb.append(Utils.join("\n",paso));
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
			sb.append(Utils.join("\n",paso,"\nurl=",url));
			
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
	
	@Override
	public void grabarEvento(
			StringBuilder sb
			,String cdmodulo
			,String cdevento
			,Date fecha
			,String cdusuari
			,String cdsisrol
			,String ntramite
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsolici
			,String cdagente
			,String cdusuariDes
			,String cdsisrolDes
			)throws Exception
	{
		sb.append(Utils.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ grabarEvento @@@@@@"
				,"\n@@@@@@ cdmodulo="    , cdmodulo
				,"\n@@@@@@ cdevento="    , cdevento
				,"\n@@@@@@ fecha="       , fecha
				,"\n@@@@@@ cdusuari="    , cdusuari
				,"\n@@@@@@ cdsisrol="    , cdsisrol
				,"\n@@@@@@ ntramite="    , ntramite
				,"\n@@@@@@ cdunieco="    , cdunieco
				,"\n@@@@@@ cdramo="      , cdramo
				,"\n@@@@@@ estado="      , estado
				,"\n@@@@@@ nmpoliza="    , nmpoliza
				,"\n@@@@@@ nmsolici="    , nmsolici
				,"\n@@@@@@ cdagente="    , cdagente
				,"\n@@@@@@ cdusuariDes=" , cdusuariDes
				,"\n@@@@@@ cdsisrolDes=" , cdsisrolDes
				));
		
		String paso = "Grabando evento";
		try
		{
			cotizacionDAO.grabarEvento(
					sb
					,cdmodulo
					,cdevento
					,fecha
					,cdusuari
					,cdsisrol
					,ntramite
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsolici
					,cdagente
					,cdusuariDes
					,cdsisrolDes, null
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
	}
	
	@Override
	public void recibosSubsecuentes(
			String rutaDocumentosTemporal
			,boolean test
			) throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recibosSubsecuentes @@@@@@"
				,"\n@@@@@@ rutaDocumentosTemporal=" , rutaDocumentosTemporal
				,"\n@@@@@@ test="                   , test
				));
		
		String paso = null;
		try
		{
			paso = "Calculando fechas";
			logger.debug("\npaso: {}",paso);
			
			Calendar fechaInicio = Calendar.getInstance();
			Calendar fechaFin    = Calendar.getInstance();
			fechaFin.add(Calendar.MONTH, 2);
			fechaFin.set(Calendar.DAY_OF_MONTH, 1);
			fechaFin.add(Calendar.DAY_OF_MONTH,-1);
			logger.debug("\nFecha de inicio: {}\nFecha de fin: {}",fechaInicio.getTime(),fechaFin.getTime());
			
			paso = "Habilitando recibos";
			logger.debug("\npaso: {}",paso);
			
			List<Map<String,String>> listaCduniecos = endososDAO.habilitaRecibosSubsecuentes(
					fechaInicio.getTime()
					,fechaFin.getTime()
					,null //cdunieco
					,null //cdramo
					,null //estado
					,null //nmpoliza
					);
			
			paso = "Creando URL de reporte";
			logger.debug("\npaso: {}",paso);
			
			String direccionIPLocal  = ServletActionContext.getRequest().getLocalAddr();
			int    puertoLocal       = ServletActionContext.getRequest().getLocalPort();
			String contexto          = ServletActionContext.getServletContext().getServletContextName();
			String urlReporte        = "http://"+direccionIPLocal+":"+puertoLocal+"/"+contexto+"/reportes/procesoObtencionReporte.action";
			
			SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd_(HH_mm_ss_SSS)");
			
			for(Map<String,String>iCdunieco:listaCduniecos)
			{
				String cdunieco = iCdunieco.get("CDUNIECO");
				
				paso = Utils.join("Recuperando correos de sucursal ",cdunieco);
				logger.debug("\npaso: {}",paso);
				
				List<Map<String,String>> listaCorreos = endososDAO.recuperarCorreoElectronicoSucursal("1", cdunieco);
				String[]                 correos      = new String[listaCorreos.size()];
				int i = 0;
				for(Map<String,String>iCorreo:listaCorreos)
				{
					correos[i++] = iCorreo.get("DESCRIPL");
				}
				
				if(test)
				{
					correos = new String[]
							{
							   "jtezva@gmail.com"
							   ,"dricardok1@hotmail.com"
							   ,"alonsoalexbar@hotmail.com"
							   ,"mdguzmanm@gseguros.com.mx"
							};
				}
				
				String nombreArchivo = Utils.join(
						"recibos_habilitados_("
				        ,cdunieco
				        ,")_"
				        ,formateador.format(new Date())
				        ,".xls"
				        );
				
				String urlArchivo = Utils.join(
						urlReporte
						,"?cdreporte=REPEXC008"
						,"&params.pv_feproces_i=" , Utils.format(fechaInicio.getTime())
						,"&params.pv_cdunieco_i=" , cdunieco
						);
				
				try
				{
					String       nombreCompletoArchivo = rutaDocumentosTemporal + File.separator + nombreArchivo;
					List<String> archivos              = new ArrayList<String>();
					
					paso = Utils.join("Generando reporte urlArchivo=",urlArchivo,",nombreCompletoArchivo=",nombreCompletoArchivo);
					logger.debug("\npaso: {}",paso);
					
					if(HttpUtil.generaArchivo(urlArchivo, nombreCompletoArchivo))
					{
						archivos.add(nombreCompletoArchivo);
					}
					else
					{ 
						throw new ApplicationException(Utils.join(
								"El archivo ",nombreCompletoArchivo," no existe, no se adjuntar\u00E1"
								));
					}
					
					mailService.enviaCorreo(
							correos
							,new String[]{}
							,new String[]{}
							,Utils.join("Recibos subsecuentes habilitados" , test?" (PRUEBA)":"")
							,Utils.join("Se habilitaron recibos subsecuentes a partir de la fecha ",Utils.format(fechaInicio.getTime()))
							,archivos.toArray(new String[archivos.size()])
							,false
							);
				}
				catch(Exception ex)
				{
					logger.error("Error al mandar correos de sucursal ",cdunieco,", no afecta el flujo",ex);
				}
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ recibosSubsecuentes @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
}