package mx.com.gseguros.portal.general.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.mesacontrol.service.MesaControlManager;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.DocumentosUtils;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class DocumentosPolizaAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 5866297387639852014L;

	private static final Logger logger = Logger.getLogger(DocumentosPolizaAction.class);
	
	private InputStream fileInputStream;
	private String path;
	private String subfolder;
	private String filename;
	
	private Map<String,String>smap1;
	private List<Map<String,String>>slist1;

	protected boolean success;
	protected String contentType;
	
	private String url;
	
	private String  respuesta;
	private String  respuestaOculta = null;
	private boolean exito           = false;
	private int reintentoRegeneraRepore = 1;// variable para solo reintentar la regeneracion de reporte una vez 
	
	private ConsultasManager consultasManager;
	
	@Autowired
	private MesaControlManager mesaControlManager;
	
	/**
	 * Metodo para la descarga de los archivos de los Movimientos en los casos
	 * de BO
	 * 
	 * @return
	 */
	public String descargaDocumento() {
		
		logger.debug("Parametros de entrada para la descarga del archivo");
		logger.debug("path: " + path);
		logger.debug("subfolder: " + subfolder);
		logger.debug("filename: " + filename);
		logger.debug("contentType: " + contentType);
		logger.debug("url: " + url);
		
		try {
			if(StringUtils.isNotBlank(url) && StringUtils.isNotBlank(contentType)) {
				fileInputStream = HttpUtil.obtenInputStream(url);
			} else {
				// Se asigna el fileInputStream:
				String rutaArchivo = generaRutaArchivo(path, subfolder, filename);
				logger.info("Se va a descargar el archivo: " + rutaArchivo);
				fileInputStream = new FileInputStream(new File(rutaArchivo));
				
				// Se asigna el contentType:
				contentType = obtieneContentType(filename);
			}
		} catch (Exception e) {
			boolean reintentoRealizado = false; 
			if(fileInputStream == null){
				reintentoRealizado = reintentaRegeneraReporte();
			}
			
			// para mantener el flujo como estaba y si no se realiza el reintento pone el mensaje de error al Action
			if(!reintentoRealizado){
				addActionError(e.getMessage());
			}
		}
		
		if(fileInputStream == null){
			reintentaRegeneraReporte();
		}
		
		success = true;
		return SUCCESS;
	}
	
	/**
	 * Regenera un reporte que se ha generado con errores 
	 */
	public String regeneraReporte() {
		
		logger.debug("**********  Parametros de entrada para regenerar reporte ***********");
		logger.debug("smap1: " + smap1);
		
		try {
		    if(consultasManager.esProductoSalud(smap1.get("pv_cdramo_i"))){
		        success = mesaControlManager.regeneraReporte(smap1.get("pv_cdunieco_i"), smap1.get("pv_cdramo_i"), smap1.get("pv_estado_i"), 
	                    smap1.get("pv_nmpoliza_i"), smap1.get("pv_nmsuplem_i"), smap1.get("pv_cddocume_i"), smap1.get("pv_nmsituac_i"),
	                    smap1.get("pv_nmcertif_i"), smap1.get("pv_cdmoddoc_i"));
		    } else {
		        String paso = null;
	            
	            String cdunieco      = smap1.get("pv_cdunieco_i"), 
	                   cdramo        = smap1.get("pv_cdramo_i"), 
	                   estado        = smap1.get("pv_estado_i"),
	                   nmpoliza      = smap1.get("pv_nmpoliza_i"), 
                       nmsuplem      = "999999999999999999",//smap1.get("pv_nmsuplem_i"),
	                   nmtramite     = smap1.get("pv_nmtramite_i"),
	                   nombreReporte = smap1.get("pv_cddocume_i").substring(0 , smap1.get("pv_cddocume_i").length()-3);
	            
	            paso = "Generando URL para SOL_VIDA_AUTO.pdf";
	            String rutaReports    = getText("ruta.servidor.reports");
	            String passReports    = getText("pass.servidor.reports");
	            String rutaDocumentos = getText("ruta.documentos.poliza");
	            String url = rutaReports
	                    + "?destype=cache"
	                    + "&desformat=PDF"
	                    + "&userid="+passReports
	                    + "&report="+nombreReporte+"rdf"
	                    + "&paramform=no"
	                    + "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
	                    + "&p_unieco="+cdunieco
	                    + "&p_ramo="+cdramo
	                    + "&p_estado="+estado
	                    + "&p_poliza="+nmpoliza
	                    + "&p_suplem="+nmsuplem
	                    + "&desname="+rutaDocumentos+"/"+nmtramite+"/"+nombreReporte+"pdf";
	            
	            paso = "Guardando PDF de Vista Previa de Autos en Temporal";
	            logger.debug(paso);
	            HttpUtil.generaArchivo(url,rutaDocumentos+"/"+nmtramite+"/"+nombreReporte+"pdf");
	            success = true;
		        
		    }
			
			
		} catch (Exception e) {
			logger.error("Error al regenerar el reporte "+ smap1, e);
			addActionError(e.getMessage());
			success = false;
		}

		return SUCCESS;
	}

	/**
	 * Regenera documentos desde consulta de polizas a nivel suplemento  
	 */
	public String regeneraDocumentosEndoso() {
		
		logger.debug("**********  Parametros de entrada para regenerar documentos ***********");
		logger.debug("smap1: " + smap1);
		
		try {
			success = mesaControlManager.regeneraDocumentosEndoso(smap1.get("cdunieco"), smap1.get("cdramo"), smap1.get("estado"), 
					smap1.get("nmpoliza"), smap1.get("nmsuplem"));
			
		} catch (Exception e) {
			logger.error("Error al regenerar el reporte "+ smap1, e);
			success = false;
			respuesta = Utils.manejaExcepcion(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * Reintenta la regeneracion de un reporte regresa false si no hace el reintento. (se maneja un solo reintento para evitar recursividad)
	 * @return
	 */
	private boolean reintentaRegeneraReporte(){
		
		if(reintentoRegeneraRepore <= 0) return false; 
		
		reintentoRegeneraRepore = reintentoRegeneraRepore - 1;
		
		try{
			
			if(smap1 == null){
				logger.warn("Error sin Impacto, no hay parametros a regenerar reporte.");
			}
			
			logger.debug("<<<<<>>>>>  Volviendo a regenerar reporte...");
			regeneraReporte();
			
			logger.debug("<<<<<>>>>>  Volviendo a descargar reporte...");
			descargaDocumento();
		}catch(Exception ex){
			logger.error("Error al reintentar reporte", ex);
		}
		
		return true;
	}
	
	public String ventanaDocumentosPoliza()
	{
		logger.debug(Utils.log(
				 "\n#####################################"
				,"\n###### ventanaDocumentosPoliza ######"
				,"\n###### smap1=",smap1
				));
		logger.debug("smap1: "+smap1);
		if(smap1==null)
		{
			smap1=new HashMap<String,String>();
		}
		if(!smap1.containsKey("cdtiptra"))
		{
			smap1.put("cdtiptra","1");
		}
		
		if(smap1.containsKey("ntramite")
				&&"1".equals(smap1.get("cdtiptra"))
				&&session!=null
				&&session.get("USUARIO")!=null
				&&!smap1.containsKey("readOnly")
				)
		{
			UserVO usuario = (UserVO)session.get("USUARIO");
			boolean bloqueoPermisoVentana = false;
			try
			{
				bloqueoPermisoVentana = consultasManager.validarVentanaDocumentosBloqueada(
					smap1.get("ntramite")
					,smap1.get("cdtiptra")
					,usuario.getUser()
					,usuario.getRolActivo().getClave()
					);
			}
			catch(Exception ex)
			{
				logger.error("error al validar ventana de documentos bloqueada",ex);
			}
			
			if(bloqueoPermisoVentana&&false)
			{
				smap1.put("readOnly" , "");
			}
		}

		logger.debug(Utils.log(
				 "\n###### ventanaDocumentosPoliza ######"
				,"\n#####################################"
				));
		return SUCCESS;
	}
	
	
	public String fusionarDocumentos()
	{
		logger.info(
				new StringBuilder()
				.append("\n################################")
				.append("\n###### fusionarDocumentos ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito   = true;
		success = true;
		
		String archivosAux = null;
		String ntramite    = null;
		
		//datos completos
		try
		{
			archivosAux = smap1.get("lista");
			ntramite    = smap1.get("ntramite");
			if(StringUtils.isBlank(archivosAux))
			{
				throw new Exception("No hay lista de archivos");
			}
			if(StringUtils.isBlank(ntramite))
			{
				throw new Exception("No hay tramite");
			}
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("No se recibieron los datos necesarios #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		if(exito)
		{
			try
			{
				String[]archivos = archivosAux.split("#");
				List<File>files  = new ArrayList<File>();
				for(String iArchivo:archivos)
				{
					File file = new File(
							new StringBuilder().
							append(this.getText("ruta.documentos.poliza"))
							.append(Constantes.SEPARADOR_ARCHIVO).append(ntramite)
							.append(Constantes.SEPARADOR_ARCHIVO).append(iArchivo)
							.toString()
							);
					logger.debug(new StringBuilder().append("archivo iterado=").append(file).toString());
					files.add(file);
				}
		
				File fusionado = DocumentosUtils.fusionarDocumentosPDF(files,new File(
						new StringBuilder()
						.append(this.getText("ruta.documentos.temporal")).append(Constantes.SEPARADOR_ARCHIVO)
						.append(System.currentTimeMillis()).append("_fusion_").append(ntramite).append(".pdf")
						.toString()
						));
				
				if(fusionado==null || !fusionado.exists())
				{
					throw new Exception("El archivo no fue creado");
				}
				
				fileInputStream=new FileInputStream(fusionado);
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error al crear el archivo #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		logger.info(
				new StringBuilder()
				.append("\n###### fusionarDocumentos ######")
				.append("\n################################")
				.toString()
				);
		
		String result = SUCCESS;
		if(!exito)
		{
			result = ERROR;
		}
		return result;
	}

	
	/**
	 * Genera la ruta del archivo a descargar en base a los parametros recibidos
	 * 
	 * @param ruta Ruta a utilizar, si es null se usarï¿½ una ruta default
	 * @param subcarpeta Subcarpeta del archivo, si es null se omite
	 * @param filename Nombre del archivo a descargar
	 * @return Ruta absoluta del archivo a descargar 
	 */
	private String generaRutaArchivo(String ruta, String subcarpeta, String filename) {
		
		StringBuilder sbRutaArchivo = new StringBuilder();
		// Agregamos la ruta:
		sbRutaArchivo.append(StringUtils.isNotBlank(ruta) ? ruta : this.getText("ruta.documentos.poliza"));
		sbRutaArchivo.append(Constantes.SEPARADOR_ARCHIVO);
		// Agregamos la subcarpeta si existe:
		if(StringUtils.isNotBlank(subcarpeta)) {
			sbRutaArchivo.append(subcarpeta).append(Constantes.SEPARADOR_ARCHIVO);
		}
		sbRutaArchivo.append(filename);
		
		return sbRutaArchivo.toString();
	}
	
	
	/**
	 * Obtiene el contentType a partir del nombre de un archivo
	 * @param filename Nombre del archivo
	 * @return contentType del archivo, o uno contentType por default
	 */
	private String obtieneContentType(String filename) {

		String contentType = null;
		String fileType = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		fileType = fileType.trim();
		
		// Se asigna el contentType asociado al tipo de archivo, si no existe le asignamos uno por default:
		for (TipoArchivo tipoArch : TipoArchivo.values()) {
			if(tipoArch.toString().equalsIgnoreCase(fileType)) {
				contentType = tipoArch.getContentType();
				break;
			}
	    }
		if(contentType == null) {
			contentType = TipoArchivo.DEFAULT.getContentType();
		}
		return contentType;
	}
	
	
	
	//Getters and setters:
	
	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSubfolder() {
		return subfolder;
	}

	public void setSubfolder(String subfolder) {
		this.subfolder = subfolder;
	}


	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getRespuestaOculta() {
		return respuestaOculta;
	}

	public void setRespuestaOculta(String respuestaOculta) {
		this.respuestaOculta = respuestaOculta;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}


	public void setConsultasManager(ConsultasManager consultasManager) {
		this.consultasManager = consultasManager;
	}
	
}
