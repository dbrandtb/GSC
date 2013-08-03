package mx.com.aon.catbo.service;

import mx.com.aon.catbo.model.NotificacionVO;
import mx.com.aon.core.ApplicationException;

public interface NotificacionMailManager {
	
	public NotificacionVO obtieneNotificacionMail(String cdProceso) throws ApplicationException;
	
}
