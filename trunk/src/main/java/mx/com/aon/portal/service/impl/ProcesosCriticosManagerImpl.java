package mx.com.aon.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.service.ProcesosCriticosManager;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;

public class ProcesosCriticosManagerImpl extends AbstractManager implements ProcesosCriticosManager {

	public MensajesVO confirmaPassword(String usuario, String password) throws ApplicationException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("usuario", usuario);
		parameters.put("contrasena", password);
		logger.debug("usuario:" + usuario);
		logger.debug("contrasena:" + password);
		MensajesVO mensajesVO = new MensajesVO();
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("CONFIRMA_PASSWORD");
			mensajesVO = (MensajesVO) endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke CONFIRMA_PASSWORD.. ",bae);
			throw new ApplicationException("Error intentando confirmar contrasena");
		} catch (Exception e) {
			logger.error("Error: " + e, e);
			throw new ApplicationException("Error intentando confirmar contrasena");
		}
		return mensajesVO;
	}

}
