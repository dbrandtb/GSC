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
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.DocumentosUtils;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
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
	
	private ConsultasManager consultasManager;
	
	private Map<String,Item> items;
	
	@Autowired
	private PantallasManager pantallasManager;
	
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
			addActionError(e.getMessage());
		}
		success = true;
		return SUCCESS;
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
		
		String proceso = smap1.get("aux");
		if(StringUtils.isNotBlank(proceso))
		{
			items = new HashMap<String,Item>();
			
			List<ComponenteVO> comboDocs = pantallasManager.recuperarComboDocs(proceso);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			try
			{
				gc.generaComponentes(comboDocs, true, false, true, false, false, false);
				items.put("comboDocs",gc.getItems());
			}
			catch(Exception ex)
			{
				logger.error(Utils.join("Error al generar combo de docs #",System.currentTimeMillis()),ex);
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
	 * @param ruta Ruta a utilizar, si es null se usar� una ruta default
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

	public Map<String, Item> getItems() {
		return items;
	}

	public void setItems(Map<String, Item> items) {
		this.items = items;
	}
	
}
