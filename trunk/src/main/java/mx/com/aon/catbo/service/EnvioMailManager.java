package mx.com.aon.catbo.service;

import mx.com.aon.catbo.model.MailVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.EmailVO;
import mx.com.aon.portal.util.WrapperResultados;

public interface EnvioMailManager {
	public MailVO obtenerDatosMail (String cdCaso) throws ApplicationException;
}
