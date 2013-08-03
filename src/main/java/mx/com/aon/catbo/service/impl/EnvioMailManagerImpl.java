package mx.com.aon.catbo.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.MailVO;
import mx.com.aon.catbo.service.EnvioMailManager;
import mx.com.aon.portal.model.EmailVO;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;

/**
 * Clase que implementa la interfaz EnvioMailManager.
 * 
 * @extends AbstractManager
 *
 */
public class EnvioMailManagerImpl extends AbstractManagerJdbcTemplateInvoke implements EnvioMailManager {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(EnvioMailManagerImpl.class);

	public MailVO obtenerDatosMail(String cdCaso) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i", cdCaso);

		return (MailVO)getBackBoneInvoke(map, "OBTENER_DATOS_MAIL");
	}
}