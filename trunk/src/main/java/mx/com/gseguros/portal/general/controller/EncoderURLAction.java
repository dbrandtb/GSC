package mx.com.gseguros.portal.general.controller;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.utils.HttpUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("EncoderURLAction")
@Scope("prototype")
@ParentPackage(value="default")
@Namespace("/seguridad")
public class EncoderURLAction extends PrincipalCoreAction {
	
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EnvironmentAction.class);
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> props;
	private Map<String, String> params;
	
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
	
	private Map<String,Item> items;
	
	
	/**
	 * Obtiene los datos de
	 * @return
	 * @throws Exception
	@Action
	(
		value   = "redireccionaReporte",
		results = 
	    {
				@Result(name="success", 
						type="stream", 
						params = {
							"contentType"       ,"${contentType}",
							"inputName"         ,"fileInputStream",
							"contentDisposition","attachment; filename=\"${filename}\"",
							"bufferSize"        ,"4096"
						}
				)
		}
	)
	 */
	public String codificaReporte() {
		try 
		{
			StringBuilder url = new StringBuilder()
			.append(getText("ruta.servidor.reports"))
            .append("?userid=").append(getText("pass.servidor.reports"))
            .append("&destype=cache")
            .append("&desformat=PDF")
            .append("&ACCESSIBLE=YES")
            .append("&paramform=no");
			
			if(StringUtils.isNotBlank(params.get("report")))
			{
				for(Entry<String,String>en:params.entrySet())
	            {
	            	String key=en.getKey();
	            	String value=en.getValue();
	            	
	            	if(StringUtils.isNotBlank(value))
	            	{
	            		url.append("&").append(key).append("=").append(value.trim());
	            	}
	            }
				logger.debug(url.toString());
			}
			else
			{
				throw new ApplicationException("Falta Reporte");
			}
			
			fileInputStream = HttpUtil.obtenInputStream(url.toString());
			
		} catch(Exception e) {
			logger.error(new StringBuilder("Error: ").append(e.getMessage()), e);
		}
		return SUCCESS;
	}
	
//Getters & setters

	public Map<String, Object> getProps() {
		return props;
	}
	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public org.apache.log4j.Logger getLogger() {
		return logger;
	}

	public void setLogger(org.apache.log4j.Logger logger) {
		this.logger = logger;
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

	public int getReintentoRegeneraRepore() {
		return reintentoRegeneraRepore;
	}

	public void setReintentoRegeneraRepore(int reintentoRegeneraRepore) {
		this.reintentoRegeneraRepore = reintentoRegeneraRepore;
	}

	public Map<String, Item> getItems() {
		return items;
	}

	public void setItems(Map<String, Item> items) {
		this.items = items;
	}
	
	
}