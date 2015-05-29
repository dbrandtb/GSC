package mx.com.gseguros.portal.renovacion.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.renovacion.dao.RenovacionDAO;
import mx.com.gseguros.portal.renovacion.service.RenovacionManager;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;
import mx.com.gseguros.ws.recibossigs.service.RecibosSigsService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class RenovacionManagerImpl implements RenovacionManager
{
	private static final Logger           logger       = Logger.getLogger(RenovacionManagerImpl.class);
	private static final SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy"); 
	
	//Dependencias inyectadas
	private RenovacionDAO renovacionDAO;
	private PantallasDAO  pantallasDAO;
	private CotizacionDAO cotizacionDAO;
	
	private transient Ice2sigsService ice2sigsService;
	private transient RecibosSigsService recibosSigsService;
	
	@Override
	public ManagerRespuestaImapVO pantallaRenovacion(String cdsisrol)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ pantallaRenovacion @@@@@@")
				.append("\n@@@@@@ cdsisrol=").append(cdsisrol)
				.toString());
		ManagerRespuestaImapVO resp = new ManagerRespuestaImapVO(true);
		
		//obtener componentes
		try
		{
			List<ComponenteVO>componentesBusqueda=pantallasDAO.obtenerComponentes(
					null,null,null,null,null,cdsisrol,"PANTALLA_RENOVACION","BUSQUEDA",null);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaComponentes(componentesBusqueda, true, false, true, false, false, false);
			
			Map<String,Item> imap = new HashMap<String,Item>();
			resp.setImap(imap);
			
			imap.put("busquedaItems" , gc.getItems());
			
			List<ComponenteVO>componentesGrid=pantallasDAO.obtenerComponentes(
					null,null,null,null,null,cdsisrol,"PANTALLA_RENOVACION","GRID",null);
			
			gc.generaComponentes(componentesGrid, true, true, false, true, true, false);
			
			imap.put("gridFields"  , gc.getFields());
			imap.put("gridColumns" , gc.getColumns());
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al obtener componentes de busqueda #")
					.append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ pantallaRenovacion @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString());
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistVO buscarPolizasRenovables(String cdunieco,String cdramo,String anio,String mes)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ buscarPolizasRenovables @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ anio=")    .append(anio)
				.append("\n@@@@@@ mes=")     .append(mes)
				.toString()
				);
		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		
		//obtener polizas
		try
		{
			List<Map<String,String>>listaPolizasRenovables = renovacionDAO.buscarPolizasRenovables(cdunieco,cdramo,anio,mes);
			resp.setSlist(listaPolizasRenovables);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al buscar las polizas renovables #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ buscarPolizasRenovables @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO renovarPolizas(
			List<Map<String,String>>polizas
			,String cdusuari
			,String anio
			,String mes
			,String rutaDocumentosPoliza
			,String rutaServidorReports
			,String passServidorReports
			,UserVO usuario
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ renovarPolizas @@@@@@")
				.append("\n@@@@@@ cdusuari=").append(cdusuari)
				.append("\n@@@@@@ anio=").append(anio)
				.append("\n@@@@@@ mes=").append(mes)
				.append("\n@@@@@@ polizas=").append(polizas)
				.append("\n@@@@@@ rutaDocumentosPoliza=").append(rutaDocumentosPoliza)
				.append("\n@@@@@@ rutaServidorReports=").append(rutaServidorReports)
				.append("\n@@@@@@ passServidorReports=").append(passServidorReports)
				.toString()
				);
		
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		
		//marcar polizas
		try
		{
			for(Map<String,String>poliza:polizas)
			{
				renovacionDAO.marcarPoliza(
						poliza.get("anio")
						,poliza.get("mes")
						,poliza.get("cdtipopc")
						,"1"//cdtipacc
						,poliza.get("cdunieco")
						,poliza.get("cdramo")
						,poliza.get("nmpoliza")
						,new Date()//feemisio
						,"S"//swrenova
						,"S"//swaproba
						,"1"//nmsituac
						,poliza.get("cducreno")
						);
			}
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al marcar polizas #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		List<Map<String,String>>polizasRenovadas=null;
		
		//ejecutar renovacion
		if(resp.isExito())
		{
			try
			{
			    polizasRenovadas = renovacionDAO.renovarPolizas(
			    		cdusuari
			    		,anio
			    		,mes
			    		,"2"//cdtipopc
			    		);
			}
			catch(ApplicationException ax)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
				resp.setRespuestaOculta(ax.getMessage());
				logger.error(resp.getRespuesta(),ax);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(
						new StringBuilder()
						.append("Error al renovar polizas #")
						.append(timestamp)
						.toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		StringBuilder respBuilder = new StringBuilder("P&oacute;lizas renovadas:");
		
		//crear documentacion
		if(resp.isExito())
		{
			try
			{
				for(Map<String,String>iPoliza:polizasRenovadas)
				{
					String esDxN     = iPoliza.get("SWDXN");
					String cdunieco  = iPoliza.get("cdunieco");
					String cdramo    = iPoliza.get("cdramo");
					String estado    = iPoliza.get("estado");
					String nmpoliza  = iPoliza.get("nmpoliza");
					String nmsuplem  = iPoliza.get("nmsuplem");
					String ntramite  = iPoliza.get("ntramite");
					String cdtipopc  = iPoliza.get("cdtipopc");
					String nmpolant  = iPoliza.get("nmpolant");
					String uniecoant = iPoliza.get("uniecoant");
					
					try
					{
						if(StringUtils.isNotBlank(esDxN) && "S".equalsIgnoreCase(esDxN))
						{	
							// Ejecutamos el Web Service de Recibos:
							ice2sigsService.ejecutaWSrecibos(cdunieco, cdramo,
									estado, nmpoliza, 
									nmsuplem, rutaDocumentosPoliza,
									cdunieco, nmpoliza, ntramite, 
									false, "33", //tipMov 33 para Renovacion
									usuario);
							// Ejecutamos el Web Service de Recibos DxN:
							recibosSigsService.generaRecibosDxN(cdunieco, cdramo, estado, nmpoliza, nmsuplem, cdunieco, nmpoliza, ntramite, usuario);
						}
						else
						{
							// Ejecutamos el Web Service de Recibos:
							ice2sigsService.ejecutaWSrecibos(cdunieco, cdramo,
									estado, nmpoliza, 
									nmsuplem, rutaDocumentosPoliza,
									cdunieco, nmpoliza,ntramite, 
									true, "33",//tipMov 33 para Renovacion
									usuario);
						}
					}
					catch(Exception ex)
					{
						logger.error("Error al lanzar ws recibos en Renovacion",ex);
					}
					

					logger.debug(new StringBuilder("\n@@@@@@ Documentacion para poliza=").append(iPoliza));
					
					List<Map<String,String>>iPolizaDocs =
							cotizacionDAO.impresionDocumentosPoliza(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite);
					
					String iPolizaRutaCarpeta = new StringBuilder(rutaDocumentosPoliza).append("/").append(ntramite).toString();
					File   iPolizaCarpeta     = new File(iPolizaRutaCarpeta);
					
					if(!iPolizaCarpeta.exists())
					{
						if(!iPolizaCarpeta.mkdir())
						{
							throw new ApplicationException("Sin permisos para crear carpeta de documentos");
						}
					}
					
					for(Map<String,String>iDoc:iPolizaDocs)
					{
						String descripc          = iDoc.get("descripc");
						String descripl          = iDoc.get("descripl");
						StringBuilder urlBuilder = new StringBuilder()
						        .append(rutaServidorReports)
						        .append("?destype=cache")
								.append("&desformat=PDF")
								.append("&paramform=no")
								.append("&ACCESSIBLE=YES")
								.append("&userid=")  .append(passServidorReports)
								.append("&report=")  .append(descripl)
								.append("&p_unieco=").append(cdunieco)
								.append("&p_ramo=")  .append(cdramo)
								.append("&p_estado=").append(estado)
								.append("&p_poliza=").append(nmpoliza)
								.append("&p_suplem=").append(nmsuplem)
								.append("&desname=") .append(iPolizaRutaCarpeta).append("/").append(descripc);
						if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
						{
							// C R E D E N C I A L _ X X X X X X . P D F
							//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
							
							urlBuilder.append("&p_cdperson=").append(descripc.substring(11, descripc.lastIndexOf(".")));
						}
						String url = urlBuilder.toString();
						HttpUtil.generaArchivo(url,iPolizaRutaCarpeta+"/"+descripc);
					}
					
					//copiar documentos de usuario
					List<Map<String,String>>iPolizaUserDocs=renovacionDAO.cargarDocumentosSubidosPorUsuario(uniecoant,cdramo,estado,nmpolant);
					for(Map<String,String>iUserDoc:iPolizaUserDocs)
					{
						String ntramiteAnt = iUserDoc.get("ntramite");
						String cddocume    = iUserDoc.get("cddocume");
						
						File doc = new File(
								new StringBuilder(rutaDocumentosPoliza)
								.append("/")
								.append(ntramiteAnt)
								.append("/")
								.append(cddocume)
								.toString()
								);
						
						File newDoc = new File(
								new StringBuilder(rutaDocumentosPoliza)
								.append("/")
								.append(ntramite)
								.append("/")
								.append(cddocume)
								.toString()
								);
						
						if(doc!=null&&doc.exists())
						{
							try
							{
								FileUtils.copyFile(doc, newDoc);
							}
							catch(Exception ex)
							{
								logger.error("Error copiando archivo de usuario",ex);
							}
						}
						else
						{
							logger.error(new StringBuilder("No existe el documento").append(doc).toString());
						}
					}
					
					renovacionDAO.actualizaRenovacionDocumentos(anio,mes,cdtipopc,uniecoant,cdramo,nmpolant);
					
					respBuilder
					    .append("<br/>Antes ").append(uniecoant).append(" - ").append(nmpolant)
					    .append(", ahora ")   .append(cdunieco) .append(" - ").append(nmpoliza)
					    .append(" con tr&aacute;mite ").append(ntramite);
				}
			}
			catch(ApplicationException ax)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
				resp.setRespuestaOculta(ax.getMessage());
				logger.error(resp.getRespuesta(),ax);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(
						new StringBuilder()
						.append("Error al generar documentos de polizas #")
						.append(timestamp)
						.toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		if(resp.isExito())
		{
			resp.setRespuesta(respBuilder.toString());
			resp.setRespuestaOculta("Todo OK");
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ renovarPolizas @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}

	//Getters y setters
	public void setRenovacionDAO(RenovacionDAO renovacionDAO) {
		this.renovacionDAO = renovacionDAO;
	}

	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}

	public void setCotizacionDAO(CotizacionDAO cotizacionDAO) {
		this.cotizacionDAO = cotizacionDAO;
	}

	public void setIce2sigsService(Ice2sigsService ice2sigsService) {
		this.ice2sigsService = ice2sigsService;
	}

	public void setRecibosSigsService(RecibosSigsService recibosSigsService) {
		this.recibosSigsService = recibosSigsService;
	}
}