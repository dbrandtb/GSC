package mx.com.gseguros.portal.general.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.mesacontrol.dao.FlujoMesaControlDAO;
import mx.com.gseguros.mesacontrol.service.FlujoMesaControlManager;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.ParametroGeneral;
import mx.com.gseguros.portal.emision.dao.EmisionDAO;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.general.service.MailService;
import mx.com.gseguros.portal.general.service.ServiciosManager;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
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
	
	@Autowired
	private FlujoMesaControlDAO flujoMesaControlDAO;
	
	@Autowired
	private ConsultasDAO consultasDAO;
	
	@Autowired
	private FlujoMesaControlManager flujoMesaControlManager;
	
	@Autowired
	private MesaControlDAO mesaControlDAO;
	
	@Autowired
	private EmisionDAO emisionDAO;
	
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
	
	@Override
	public int generarFlagsTramites () throws Exception {
		logger.debug(Utils.log(
			"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
			"\n@@@@@@ generarFlagsTramites @@@@@@"
		));
		int generadas = 0;
		String paso = null;
		try {
			paso = "Recuperando tramites que no tengan flag actualizada";
			logger.debug(paso);
			List<Map<String, String>> tramitesSinFlag = flujoMesaControlDAO.recuperarTramitesSinFlag();
			
			int minutosAmarillosDefault = 0,
				minutosRojosDefault     = 0,
				minutosMaxDefault       = 0;
			
			if (tramitesSinFlag.size() > 0) {
				paso = "Recuperando minutos default";
				logger.debug(paso);
				
				minutosAmarillosDefault = Integer.parseInt(consultasDAO.recuperarTparagen(ParametroGeneral.MINUTOS_FLAG_AMARILLA));
				minutosRojosDefault     = Integer.parseInt(consultasDAO.recuperarTparagen(ParametroGeneral.MINUTOS_FLAG_ROJA));
				minutosMaxDefault       = Integer.parseInt(consultasDAO.recuperarTparagen(ParametroGeneral.MINUTOS_FLAG_MAXIMA));
			}
			
			for (Map<String, String> tramite: tramitesSinFlag) {
				paso = Utils.join("Iterando tramite ", tramite);
				logger.debug(paso);
				
				String ntramite = tramite.get("NTRAMITE");
				String status   = tramite.get("STATUS");
				
				Date fecstatu = Utils.parseConHora(tramite.get("FECSTATU"));
				Calendar fecstatu2 = Calendar.getInstance();
				fecstatu2.setTime(fecstatu);
				if (fecstatu2.get(Calendar.HOUR_OF_DAY) == 0 && fecstatu2.get(Calendar.MINUTE) == 0) {
					fecstatu2.set(Calendar.HOUR_OF_DAY, 12);
				}
				
				int minutosAmarillos = Integer.parseInt(tramite.get("TIMEWRN1"));
				if (minutosAmarillos == 0) {
					minutosAmarillos = minutosAmarillosDefault;
				}
				
				int minutosRojos = Integer.parseInt(tramite.get("TIMEWRN2"));
				if (minutosRojos == 0) {
					minutosRojos = minutosRojosDefault;
				}
				
				int minutosMax = Integer.parseInt(tramite.get("TIMEMAX"));
				if (minutosMax == 0) {
					minutosMax = minutosMaxDefault;
				}
				
				flujoMesaControlDAO.insertarFlagTramite(
						ntramite,
						fecstatu,
						"V", //verde
						Utils.calcularFechaLaboral(fecstatu2, minutosAmarillos).getTime(),
						Utils.calcularFechaLaboral(fecstatu2, minutosRojos).getTime(),
						Utils.calcularFechaLaboral(fecstatu2, minutosMax).getTime()
						);
				
				try {
					cotizacionDAO.grabarEvento(
							new StringBuilder()
							,"FLAGS"
							,"VERDE"
							,new Date()
							,"CRON"
							,"CRON"
							,ntramite
							,null //cdunieco
							,null //cdramo
							,null //estado
							,null //nmpoliza
							,null //nmsolici
							,null //cdagente
							,null //cdusuariDes
							,null //cdsisrolDes
							,status
							);
					Thread.sleep(1000l);
				} catch (Exception ex) {
					logger.debug("Error al grabar evento", ex);
				}
				
				generadas++;
			}
		} catch (Exception ex) {
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
			"\n@@@@@@ generadas = ", generadas,
			"\n@@@@@@ generarFlagsTramites @@@@@@",
			"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
		));
		return generadas;
	}
	
	@Override
	public int actualizarFlagsTramites () throws Exception {
		logger.debug(Utils.log(
			"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
			"\n@@@@@@ actualizarFlagsTramites @@@@@@"
		));
		int actualizadas = 0;
		String paso = null;
		try {
			paso = "Recuperando tramites tengan flags vencidas";
			logger.debug(paso);
			List<Map<String, String>> tramitesConFlagVencida = flujoMesaControlDAO.recuperarTramitesConFlagVencida();
						
			for (Map<String, String> tramite: tramitesConFlagVencida) {
				paso = Utils.join("Iterando tramite ", tramite);
				logger.debug(paso);
				
				String ntramite  = tramite.get("NTRAMITE");
				String cdtipflu  = tramite.get("CDTIPFLU");
				String cdflujomc = tramite.get("CDFLUJOMC");
				String status    = tramite.get("STATUS");
				String nuevaflag = tramite.get("NUEVAFLAG");
				String statusout = tramite.get("STATUSOUT");
				
				Date fecstatu   = Utils.parseConHora(tramite.get("FECSTATU"));
				Date feamarilla = Utils.parseConHora(tramite.get("FEAMARILLA"));
				Date feroja     = Utils.parseConHora(tramite.get("FEROJA"));
				Date fevencim   = Utils.parseConHora(tramite.get("FEVENCIM"));
				
				flujoMesaControlDAO.insertarFlagTramite(
						ntramite,
						fecstatu,
						nuevaflag,
						feamarilla,
						feroja,
						fevencim
						);
				
				if ("X".equals(nuevaflag)) { // Cuando el tramite vence
					
					flujoMesaControlManager.mandarCorreosStatusTramite(
							ntramite,
							"CRON",
							true
							);
					
					if (!"-1".equals(statusout)) { // Status de vencimiento definido
						
						flujoMesaControlDAO.actualizarStatusTramite(
								ntramite,
								statusout,
								new Date(),
								null //cdusuari (con null le deja el mismo)
								);
						
						mesaControlDAO.movimientoDetalleTramite(
								ntramite,
								new Date(),
								null, // cdclausu
								Utils.join(
										"Tr\u00e1mite vencido",
										"\nFecha de recepci\u00f3n: " , Utils.formatConHora(fecstatu)
										,"\nFecha primera alerta: " , Utils.formatConHora(feamarilla)
										,"\nFecha segunda alerta: " , Utils.formatConHora(feroja)
										,"\nFecha l\u00edmite: " , Utils.formatConHora(fevencim)
								),
								"SISTEMA", // cdusuari
								null, // cdmotivo
								"SISTEMA", // cdsisrol
								"N", // swagente
								null, // cdusuariDest
								null, // cdsisrolDest
								statusout,
								true //cerrado
								);
					}
				}
				
				String nombreFlag = "nombreFlag";
				if ("A".equals(nuevaflag)) {
					nombreFlag = "AMARILLA";
				} else if ("R".equals(nuevaflag)) {
					nombreFlag = "ROJA";
				} else if ("X".equals(nuevaflag)) {
					nombreFlag = "VENCIDA";
				}
				
				try {
					cotizacionDAO.grabarEvento(
							new StringBuilder()
							,"FLAGS"
							,nombreFlag
							,new Date()
							,"CRON"
							,"CRON"
							,ntramite
							,null //cdunieco
							,null //cdramo
							,null //estado
							,null //nmpoliza
							,null //nmsolici
							,null //cdagente
							,null //cdusuariDes
							,null //cdsisrolDes
							,status
							);
					Thread.sleep(1000l);
				} catch (Exception ex) {
					logger.debug("Error al grabar evento", ex);
				}
				
				actualizadas++;
			}
		} catch (Exception ex) {
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
			"\n@@@@@@ actualizadas = ", actualizadas,
			"\n@@@@@@ actualizarFlagsTramites @@@@@@",
			"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
		));
		return actualizadas;
	}
	
	@Override
	public void bloquearProceso (String cdproceso, boolean bloquear, String cdusuari, String cdsisrol) throws Exception {
		emisionDAO.bloquearProceso(cdproceso, bloquear, cdusuari, cdsisrol);
	}
}