package mx.com.gseguros.portal.general.controller;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.utils.HttpUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("EncoderURLAction")
@Scope("prototype")
@ParentPackage(value="struts-default, json-default")
@Namespace("/seguridad")
public class EncoderURLAction extends PrincipalCoreAction {
	
	private static Logger logger = LoggerFactory.getLogger(EnvironmentAction.class);
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> params;
	
	private InputStream fileInputStream;
	protected String contentType;
	
	/**
	 * Obtiene el flujo de bytes de un reporte
	 * @return 
	 * @throws Exception
	 */
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
							"bufferSize"        ,"4096"
							// opcional "contentDisposition", "attachment; filename=\"${filename}\""
						}
				)
		}
	)
	public String redireccionaReporte() {
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
			logger.error(contentType, new StringBuilder("Error: ").append(e.getMessage()), e);
		}
		return SUCCESS;
	}

//Getters & setters

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
}