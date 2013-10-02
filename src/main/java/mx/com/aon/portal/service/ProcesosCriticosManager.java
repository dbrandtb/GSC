package mx.com.aon.portal.service;

import mx.com.aon.portal.model.MensajesVO;
import mx.com.gseguros.exception.ApplicationException;

public interface ProcesosCriticosManager {

	public MensajesVO confirmaPassword(String usuario, String password) throws ApplicationException;
}
