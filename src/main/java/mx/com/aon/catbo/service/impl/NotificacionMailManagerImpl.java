package mx.com.aon.catbo.service.impl;

import java.util.HashMap;

import org.apache.log4j.Logger;

import mx.com.aon.catbo.model.MailVO;
import mx.com.aon.catbo.model.NotificacionVO;
import mx.com.aon.catbo.service.NotificacionMailManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;

public class NotificacionMailManagerImpl extends AbstractManagerJdbcTemplateInvoke implements NotificacionMailManager{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(NotificacionMailManagerImpl.class);
	
	@SuppressWarnings("unchecked")
	public NotificacionVO obtieneNotificacionMail(String cdProceso) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdproceso_i", ConvertUtil.nvl(cdProceso));
		
		return (NotificacionVO)getBackBoneInvoke(map, "OBTIENE_FORMATONOTIF");
	}

}
