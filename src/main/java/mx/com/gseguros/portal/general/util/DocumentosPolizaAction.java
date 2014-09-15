package mx.com.gseguros.portal.general.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.utils.DocumentosUtils;
import mx.com.gseguros.utils.HttpUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class DocumentosPolizaAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 5866297387639852014L;

	private static final Logger logger = Logger.getLogger(DocumentosPolizaAction.class);

	// private transient ArchivosManager archivosManagerJdbcTemplate;

	private InputStream fileInputStream;
	private String filename;
	private String idPoliza;
	private Map<String,String>smap1;
	private List<Map<String,String>>slist1;

	protected boolean success;
	protected String contentType;
	
	private String url;
	
	private String  respuesta       = null;
	private String  respuestaOculta = null;
	private boolean exito           = false;

	/**
	 * Metodo para la descarga de los archivos de los Movimientos en los casos
	 * de BO
	 * 
	 * @return
	 */
	public String descargaDocumento() {
		
		logger.debug("Parametros de entrada para la descarga del archivo");
		logger.debug("ntramite: " + idPoliza);
		logger.debug("filename: " + filename);
		logger.debug("url: " + url);
		logger.debug("contentType: " + contentType);
		logger.debug("Ruta: " + this.getText("ruta.documentos.poliza"));
		
		try {
		
			if(StringUtils.isNotBlank(url) && StringUtils.isNotBlank(contentType)) {
				fileInputStream = HttpUtil.obtenInputStream(url);
			} else {
				logger.debug(this.getText("ruta.documentos.poliza")+"/"+idPoliza+"/"+filename);
				fileInputStream = new FileInputStream(new File(this.getText("ruta.documentos.poliza")+"/"+idPoliza+"/"+filename));
	
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
				
			}

		} catch (Exception e) {
			addActionError(e.getMessage());
		}

		success = true;
		return SUCCESS;
	}
	
	public String descargaDocumentoPersona() {
		
		logger.debug("Parametros de entrada para la descarga del archivo");
		logger.debug("cdperson: " + idPoliza);
		logger.debug("filename: " + filename);
		logger.debug("url: " + url);
		logger.debug("contentType: " + contentType);
		logger.debug("Ruta: " + this.getText("ruta.documentos.persona"));
		
		try {
		
			if(StringUtils.isNotBlank(url) && StringUtils.isNotBlank(contentType)) {
				fileInputStream = HttpUtil.obtenInputStream(url);
			} else {
				logger.debug(this.getText("ruta.documentos.persona")+"/"+idPoliza+"/"+filename);
				fileInputStream = new FileInputStream(new File(this.getText("ruta.documentos.persona")+"/"+idPoliza+"/"+filename));
	
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
				
			}

		} catch (Exception e) {
			addActionError(e.getMessage());
		}

		success = true;
		return SUCCESS;
	}

	public String ventanaDocumentosPoliza()
	{
		logger.debug(""
				+ "\n#####################################"
				+ "\n#####################################"
				+ "\n###### ventanaDocumentosPoliza ######"
				+ "\n######                         ######"
				+ "\n######                         ######"
				);
		logger.debug("smap1: "+smap1);
		if(smap1!=null && !smap1.containsKey("cdtiptra"))
		{
			smap1.put("cdtiptra","1");
		}
		logger.debug(""
				+ "\n######                         ######"
				+ "\n######                         ######"
				+ "\n###### ventanaDocumentosPoliza ######"
				+ "\n#####################################"
				+ "\n#####################################"
				);
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
							.append("/").append(ntramite)
							.append("/").append(iArchivo)
							.toString()
							);
					logger.debug(new StringBuilder().append("archivo iterado=").append(file).toString());
					files.add(file);
				}
		
				File fusionado = DocumentosUtils.fusionarDocumentosPDF(files,new File(
						new StringBuilder()
						.append(this.getText("ruta.documentos.temporal")).append("/")
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
	
	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
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

	public String getIdPoliza() {
		return idPoliza;
	}

	public void setIdPoliza(String idPoliza) {
		this.idPoliza = idPoliza;
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
	
}
