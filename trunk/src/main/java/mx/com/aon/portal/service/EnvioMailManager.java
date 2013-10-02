package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

public interface EnvioMailManager {
	
	
	

	public String enviarMail () throws ApplicationException;

	
}
