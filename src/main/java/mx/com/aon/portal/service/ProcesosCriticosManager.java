package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MensajesVO;

public interface ProcesosCriticosManager {

	public MensajesVO confirmaPassword(String usuario, String password) throws ApplicationException;
}
