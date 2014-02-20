package mx.com.gseguros.portal.general.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class EnvironmentAction extends ActionSupport {

	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EnvironmentAction.class);
	
	private static final long serialVersionUID = 1L;

	private Map<String, Object> props;
	
	
	/**
	 * Obtiene los datos del ambiente en el que se ejecuta la aplicaci&oacute;n 
	 * @return
	 * @throws Exception
	 */
	public String obtieneDatosAmbiente() throws Exception {
		
		try {
			props = new HashMap<String, Object>();
			props.put("DisplayName", Locale.getDefault().getDisplayName());
			props.put("user.country", System.getProperty("user.country"));
			props.put("Country", Locale.getDefault().getCountry());
			props.put("DisplayCountry", Locale.getDefault().getDisplayCountry());
			props.put("ISO3Country", Locale.getDefault().getISO3Country());
			props.put("user.language", System.getProperty("user.language"));
			props.put("Language", Locale.getDefault().getLanguage());
			props.put("DisplayLanguage", Locale.getDefault().getDisplayLanguage());
			props.put("ISO3Language", Locale.getDefault().getISO3Language());
			props.put("user.variant", System.getProperty("user.variant"));
			props.put("Variant", Locale.getDefault().getVariant());
			props.put("DisplayVariant", Locale.getDefault().getDisplayVariant());
		} catch(Exception e) {
			logger.error(new StringBuilder("Error: ").append(e.getMessage()), e);
		}
		return SUCCESS;
	}

	
	//Getters and setters:

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}


}