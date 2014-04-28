package mx.com.gseguros.portal.general.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.utils.HttpUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DocumentosPolizaAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 5866297387639852014L;

	private static Logger logger = Logger.getLogger(DocumentosPolizaAction.class);

	// private transient ArchivosManager archivosManagerJdbcTemplate;

	private InputStream fileInputStream;
	private String filename;
	private String idPoliza;
	private Map<String,String>smap1;
	private List<Map<String,String>>slist1;

	protected boolean success;
	protected String contentType;
	
	private String url;

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
	

}
