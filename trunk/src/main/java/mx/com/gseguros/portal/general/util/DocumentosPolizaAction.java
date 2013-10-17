package mx.com.gseguros.portal.general.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import mx.com.aon.utils.Constantes;
import mx.com.gseguros.utils.HttpUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class DocumentosPolizaAction extends ActionSupport {

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
				fileInputStream = new FileInputStream(new File(this.getText("ruta.documentos.poliza")+"/"+idPoliza+"/"+filename));
	
				String fileType = filename.substring(filename.lastIndexOf(".") + 1,
						filename.length());
				fileType = fileType.trim();
	
				if (Constantes.FORMAT_TXT.equalsIgnoreCase(fileType)) {
					contentType = "text/plain";
				} else if (Constantes.FORMAT_HTM.equalsIgnoreCase(fileType)) {
					contentType = "text/html";
				} else if (Constantes.FORMAT_HTML.equalsIgnoreCase(fileType)) {
					contentType = "text/html";
				} else if (Constantes.FORMAT_DOC.equalsIgnoreCase(fileType)) {
					contentType = "application/msword";
				} else if (Constantes.FORMAT_DOCX.equalsIgnoreCase(fileType)) {
					contentType = "application/msword";
				} else if (Constantes.FORMAT_XLS.equalsIgnoreCase(fileType)) {
					contentType = "application/vnd.ms-excel";
				} else if (Constantes.FORMAT_XLSX.equalsIgnoreCase(fileType)) {
					contentType = "application/vnd.ms-excel";
				} else if (Constantes.FORMAT_PDF.equalsIgnoreCase(fileType)) {
					contentType = "application/pdf";
				} else if (Constantes.FORMAT_PPT.equalsIgnoreCase(fileType)) {
					contentType = "application/ppt";
				} else if (Constantes.FORMAT_GIF.equalsIgnoreCase(fileType)) {
					contentType = "image/gif";
				} else if (Constantes.FORMAT_BMP.equalsIgnoreCase(fileType)) {
					contentType = "image/bmp";
				} else if (Constantes.FORMAT_JPG.equalsIgnoreCase(fileType)) {
					contentType = "image/jpeg";
				} else if (Constantes.FORMAT_JPEG.equalsIgnoreCase(fileType)) {
					contentType = "image/jpeg";
				} else if (Constantes.FORMAT_PNG.equalsIgnoreCase(fileType)) {
					contentType = "image/png";
				} else if (Constantes.FORMAT_TIF.equalsIgnoreCase(fileType)) {
					contentType = "image/tiff";
				} else {
					contentType = "application/octet-stream";
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
